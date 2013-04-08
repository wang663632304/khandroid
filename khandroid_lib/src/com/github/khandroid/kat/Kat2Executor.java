package com.github.khandroid.kat;

import com.github.khandroid.misc.KhandroidLog;

import android.app.Activity;
import android.os.AsyncTask;



public class Kat2Executor {
    private KhandroidAsyncTask2<?, ?, ?> mTask;
    
    
    public Kat2Executor(Kat2ExecutorFunctionality host) {
        super();
        mHost = host;
    }
    
    
    public <T> void execute(KhandroidAsyncTask2<T, ?, ?> task, T... params) {
        mTask = task;
        task.execute(params);
    }
    
    
    public KhandroidAsyncTask2<?, ?, ?> getTask() {
        return mTask;
    }


    public void setTask(KhandroidAsyncTask2<?, ?, ?> task) {
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
        KhandroidLog.v("onTaskCancelled");
        mTask.detach();
    }
    
    
    public void onTaskCompleted(KhandroidAsyncTask2<?, ?, ?> task) {
        KhandroidLog.v("onTaskCompleted");
        if (task == mTask) { // same instance
            mTask.detach();
        }
    }
    
    
    public interface Kat2ExecutorFunctionality {
        public Activity getActivity();
        
        public <T> void execute(KhandroidAsyncTask2<T, ?, ?> task, T... params);
        public AsyncTask.Status getTaskStatus();
        public boolean isExecuting();
        public boolean cancelExecution(boolean mayInterruptIfRunning);
        public void onTaskCancelled();
        public void onTaskCompleted(KhandroidAsyncTask2<?, ?, ?> task);
    }    
}


