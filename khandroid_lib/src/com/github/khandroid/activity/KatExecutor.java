package com.github.khandroid.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.github.khandroid.activity.KatExecutor.IKatExecutorFunctionality.DialogCreator;
import com.github.khandroid.misc.KhandroidAsyncTask;
import com.github.khandroid.misc.KhandroidLog;


public class KatExecutor {
    private KhandroidAsyncTask<?, ?, ?> mTask;
    private DialogCreator mDialogCreator;
    private ProgressDialog mProgressDailog;
    private IKatExecutorFunctionality mHost;
    
    
    public KatExecutor(IKatExecutorFunctionality host) {
        super();
        mHost = host;
    }
    
    
    public KatExecutor(IKatExecutorFunctionality host, DialogCreator dialogCreator) {
        this(host);
        mDialogCreator = dialogCreator;
    }


    public <T> void execute(KhandroidAsyncTask<T, ?, ?> task, T... params) {
        if (mDialogCreator != null) {
            mProgressDailog = mHost.showProgressDialog(task);
        }
        
        mTask = task;
        task.execute(params);
    }
    
    
    public DialogCreator getDialogCreator() {
        return mDialogCreator;
    }


    public KhandroidAsyncTask<?, ?, ?> getTask() {
        return mTask;
    }


    public void setTask(KhandroidAsyncTask<?, ?, ?> task) {
        mTask = task;
    }


    public AsyncTask.Status getTaskStatus() {
        if (mTask != null) {
            return mTask.getStatus();
        } else {
            return null;
        }
    }
    
    
    public boolean isExecuting() {
        boolean ret;
        
        if (mTask != null && mTask.getStatus() == AsyncTask.Status.RUNNING && !mTask.isCancelled()) {
            ret = true;
        } else {
            ret = false;
        }
        
        return ret;
    }
    
    
    public boolean cancelExecution(boolean mayInterruptIfRunning) {
        boolean ret;
        
        if (isExecuting()) { 
            ret = mTask.cancel(mayInterruptIfRunning);
        } else {
            ret = false;
        }
            
        
        return ret;
    }
    
    
    public void onTaskCancelled() {
        dismissDialog();
        KhandroidLog.v("onTaskCancelled");
        mTask.detach();
    }
    
    
    public void setDialogCreator(DialogCreator dialogCreator) {
        if (!isExecuting()) {
            if (mDialogCreator != dialogCreator) {
                mDialogCreator = dialogCreator;
                mProgressDailog = null;
            }
        } else {
            throw new IllegalStateException("Cannot call setDialogCreator() when still executing");
        }
    }
    
    
    protected void setProgressDailog(ProgressDialog dialog) {
        mProgressDailog = dialog;
    }
    

    public ProgressDialog getProgressDailog() {
        return mProgressDailog;
    }
    
    
    public void onTaskCompleted(KhandroidAsyncTask<?, ?, ?> task) {
        KhandroidLog.v("onTaskCompleted");
        if (task == mTask) { // same instance
            dismissDialog();
            mTask.detach();
        }
    }
    
    
    private void dismissDialog() {
        mHost.dismissProgressDialog();
    }
    
    
    public interface IKatExecutorFunctionality {
        public Activity getActivity();
        
        public void setDialogCreator(DialogCreator dialogCreator);
        public ProgressDialog getProgressDailog();
        public ProgressDialog showProgressDialog(KhandroidAsyncTask<?,?,?> task);
        public void dismissProgressDialog();
        
        public void onTaskCompleted(KhandroidAsyncTask<?, ?, ?> task);
        public <T> void execute(KhandroidAsyncTask<T, ?, ?> task, T... params);
        public AsyncTask.Status getTaskStatus();
        public boolean isExecuting();
        public boolean cancelExecution(boolean mayInterruptIfRunning);
        public void onTaskCancelled();
        
                
        public interface DialogCreator {
            ProgressDialog create(final KhandroidAsyncTask<?, ?, ?> task, Activity act);
        }
    }    
}


