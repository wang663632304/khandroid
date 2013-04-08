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

import android.os.AsyncTask;
import android.os.Bundle;
import com.github.khandroid.activity.ActivityAttachedFunctionality;
import com.github.khandroid.activity.HostActivity;
import com.github.khandroid.kat.Kat2Executor;
import com.github.khandroid.kat.Kat2Executor.Kat2ExecutorFunctionality;
import com.github.khandroid.kat.KhandroidAsyncTask2;
import com.github.khandroid.misc.KhandroidLog;


public class ActivityKat2ExecutorFunctionality extends ActivityAttachedFunctionality implements
        Kat2ExecutorFunctionality {
    
    private Kat2Executor mKatExecutor = new Kat2Executor(this);
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KhandroidLog.v("onCreate");
        
        //TODO fix this
        Object task = getActivity().getLastNonConfigurationInstance();
        KhandroidLog.v("Task: " + task);
                
        if (task != null && task instanceof KhandroidAsyncTask2) {
            KhandroidAsyncTask2<?, ?, ?> kTask = (KhandroidAsyncTask2<?, ?, ?>) task;
            mKatExecutor.setTask(kTask);
            
            if (kTask.getStatus() == AsyncTask .Status.RUNNING) {
                onContinuingWithTask();
            }
            
        }
    }
    
    public <T, U> void execute(KhandroidAsyncTask2<T, ?, U> task,
                                KhandroidAsyncTask2.TaskCompletedListener<U> listener,
                                T... params
                            ) {
        mKatExecutor.setTask(task);
        mKatExecutor.execute(task, params);
    }

    
    public <T> void execute(KhandroidAsyncTask2<T, ?, ?> task,
                               T... params
                           ) {
        execute(task, null, params);
    }
    
    
    public <U> void execute(KhandroidAsyncTask2<?, ?, U> task,
                               KhandroidAsyncTask2.TaskCompletedListener<U> listener
                           ) {
        execute(task, listener, null);
    }
    
    
    
    private void onContinuingWithTask() {
        // TODO Auto-generated method stub
        
    }


    public ActivityKat2ExecutorFunctionality(HostActivity activity) {
        super(activity);
    }


//    @Override
//    public KhandroidAsyncTask<?, ?, ?> getTask() {
//        // TODO Auto-generated method stub
//        return null;
//    }


    @Override
    public void onTaskCancelled() {
        // TODO Auto-generated method stub

    }


    @Override
    public void onTaskCompleted(KhandroidAsyncTask2<?, ?, ?> task) {
        // TODO Auto-generated method stub

    }

}
