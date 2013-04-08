/*
 * Copyright (C) 2012-2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.github.khandroid.activity.functionalities;


import com.github.khandroid.activity.ActivityUniqueAttachedFunctionality;
import com.github.khandroid.activity.HostActivity;
import com.github.khandroid.activity.KatExecutor;
import com.github.khandroid.activity.KatExecutor.IKatExecutorFunctionality;
import com.github.khandroid.activity.KatExecutor.IKatExecutorFunctionality.DialogCreator;
import com.github.khandroid.misc.KhandroidAsyncTask;
import com.github.khandroid.misc.KhandroidLog;

import android.app.ProgressDialog;
import android.os.AsyncTask.Status;
import android.os.Bundle;


public class ActivityKatExecutorFunctionality extends ActivityUniqueAttachedFunctionality implements IKatExecutorFunctionality {
    private KatExecutor mKatExecutor;
    
    
    public ActivityKatExecutorFunctionality(HostActivity activity) {
        super(activity);
        mKatExecutor = new KatExecutor(this);
    }
    
    
    public ActivityKatExecutorFunctionality(HostActivity activity, DialogCreator dialogCreator) {
        super(activity);
        mKatExecutor = new KatExecutor(this, dialogCreator);
    }

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KhandroidLog.v("onCreate");
        Object task = getActivity().getLastNonConfigurationInstance();
        KhandroidLog.v("Task: " + task);
                
        if (task != null && task instanceof KhandroidAsyncTask) {
            KhandroidAsyncTask<?, ?, ?> kTask = (KhandroidAsyncTask<?, ?, ?>) task;
            kTask.attach(this);
            mKatExecutor.setTask(kTask);
            if (isExecuting() && mKatExecutor.getDialogCreator() != null) {
                mKatExecutor.setProgressDailog(showProgressDialog(kTask));
            }
        }
    }

    
    @Override
    public ProgressDialog showProgressDialog(KhandroidAsyncTask<?, ?, ?> task) {
        KhandroidLog.v("Showing progress dialog");
        ProgressDialog dia = mKatExecutor.getDialogCreator().create(task, getActivity());
        dia.show();
        
        return dia;
    }
    
    
    @Override
    public void dismissProgressDialog() {
        KhandroidLog.v("Trying to dismissing progress dialog");
        
        if (mKatExecutor.getProgressDailog() != null) {
            KhandroidLog.v("Dismissing progress dialog");
            mKatExecutor.getProgressDailog().dismiss();
        }
    }

    
    public Object onRetainNonConfigurationInstance() {
        Object ret;
        
        KhandroidAsyncTask<?,?,?> task = mKatExecutor.getTask();
        if (task != null) {
            task.detach();
            return task;
        } else {
            ret = null;
        }
        
        return ret;
    }
    
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        KhandroidAsyncTask<?,?,?> task = mKatExecutor.getTask();
        if (task != null) {
            task.detach();
        }
    }
    
    
    @Override
    protected void onStop() {
        super.onStop();
        
        ProgressDialog dialog = mKatExecutor.getProgressDailog();        
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }


    @Override
    public ProgressDialog getProgressDailog() {
        return mKatExecutor.getProgressDailog();
    }


    @Override
    public void onTaskCompleted(KhandroidAsyncTask<?, ?, ?> task) {
        mKatExecutor.onTaskCompleted(task);
    }


    @Override
    public <T> void execute(KhandroidAsyncTask<T, ?, ?> task, T... params) {
        mKatExecutor.execute(task, params);        
    }


    @Override
    public Status getTaskStatus() {
        return mKatExecutor.getTaskStatus();
    }


    @Override
    public boolean isExecuting() {
        return mKatExecutor.isExecuting();
    }


    @Override
    public boolean cancelExecution(boolean mayInterruptIfRunning) {
        return mKatExecutor.cancelExecution(mayInterruptIfRunning);
    }


    @Override
    public void onTaskCancelled() {
        mKatExecutor.onTaskCancelled();
    }


    @Override
    public void setDialogCreator(DialogCreator dialogCreator) {
        mKatExecutor.setDialogCreator(dialogCreator);
    }
}
