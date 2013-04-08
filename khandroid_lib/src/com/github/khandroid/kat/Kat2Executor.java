package com.github.khandroid.kat;

import com.github.khandroid.misc.KhandroidLog;

import android.os.AsyncTask;



public class Kat2Executor {
    private KhandroidAsyncTask2<?, ?, ?> mTask;
    private Kat2ExecutorFunctionality mHost; 
    
    public Kat2Executor(Kat2ExecutorFunctionality host) {
        super();
    }
    
    
    @SuppressWarnings("unchecked")
    public <T> void execute(KhandroidAsyncTask2<T, ?, ?> task) {
        mTask = task;
        task.attach(this);
        task.execute(this);
    }
    
    public <T> void execute(KhandroidAsyncTask2<T, ?, ?> task, T... params) {
        mTask = task;
        task.execute(this, params);
    }
    
    
    public KhandroidAsyncTask2<?, ?, ?> getTask() {
        return mTask;
    }


    public void setTask(KhandroidAsyncTask2<?, ?, ?> task) {
        mTask = task;
        mTask.attach(this);
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
        mTask = null;

    }
    
    
    public void onTaskCompleted(KhandroidAsyncTask2<?, ?, ?> task) {
        KhandroidLog.v("onTaskCompleted");
//        if (task == mTask) { // same instance
            mTask.detach();
            mTask = null;
//        }
          //TODO        mHost.onTaskCompleted();
    }
    
    
    public interface Kat2ExecutorFunctionality {
//        KhandroidAsyncTask<?, ?, ?> getTask();
        void onTaskCancelled();
        void onTaskCompleted(KhandroidAsyncTask2<?, ?, ?> task);
    }
    
    
//    public interface Kat2ExecutorFunctionality {
//        public Activity getActivity();
//        
//        public <T> void execute(KhandroidAsyncTask2<T, ?, ?> task, T... params);
//        public AsyncTask.Status getTaskStatus();
//        public boolean isExecuting();
//        public boolean cancelExecution(boolean mayInterruptIfRunning);
//        public void onTaskCancelled();
//        public void onTaskCompleted(KhandroidAsyncTask2<?, ?, ?> task);
//    }    
}


