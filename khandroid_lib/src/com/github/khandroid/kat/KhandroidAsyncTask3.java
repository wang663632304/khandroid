package com.github.khandroid.kat;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.github.khandroid.misc.KhandroidLog;

import android.os.AsyncTask;


abstract public class KhandroidAsyncTask3<Params, Progress, Result> {
    private InnerAsyncTask mTask;
    private Kat3Executor<Params, Progress, Result> mExecutor;

    
    public KhandroidAsyncTask3() {
        mTask = new InnerAsyncTask(); 
    }

//    public final AsyncTask<Params, Progress, Result> execute() {
//        return mTask.execute();
//    }
    
    
    
    public final AsyncTask<Params, Progress, Result> execute(Kat3Executor<Params, Progress, Result> executor, Params... params) {
        attach(executor);
        KhandroidLog.v("Executing KhandroidAsyncTask");
        return mTask.execute(params);
    }


    public void detach() {
        KhandroidLog.v("detach " + this);
        mExecutor = null;
    }


    public void attach(Kat3Executor<Params, Progress, Result> executor) {
        if (executor != null) {
            KhandroidLog.v("attach " + this);
            this.mExecutor = executor;
        } else {
            throw new IllegalArgumentException("executor == null");
        }
    }
    
    
    public AsyncTask.Status getStatus() {
        if (mTask != null) {
            return mTask.getStatus();    
        } else {
            return null;
        }
    }
    
    
    public final boolean isCancelled() {
        return mTask.isCancelled();
    }
    
    
    public final boolean cancel(boolean mayInterruptIfRunning) {
        return mTask.cancel(mayInterruptIfRunning);
    }
    
    
    public final Result get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return mTask.get(timeout, unit);
    }
    
    
    public final Result get() throws InterruptedException, ExecutionException {
        return mTask.get();
    }
                                 
    
    protected void onPostExecute(Result result) {
        // intentionally left empty
    }

    
    protected void onPreExecute() {
        // intentionally left empty
    }
    
    
    protected void onProgressUpdate(Progress... values) {
        // intentionally left empty
    }
    
    
    protected void onCancelled() {
        // intentionally left empty
    }
    
    
    protected final void publishProgress(Progress... values) {
        mTask.publishProgressInner(values);
    }
    
    
    abstract protected Result doInBackground();
    
    
    private class InnerAsyncTask extends AsyncTask<Params, Progress, Result> {
        @Override
        protected void onPreExecute() {
            KhandroidAsyncTask3.this.onPreExecute();
        }


        @Override
        protected void onPostExecute(Result result) {
            KhandroidLog.v("onPostExecute");
            KhandroidAsyncTask3.this.onPostExecute(result);
            mExecutor.onTaskCompleted(result);
        }


        @Override
        protected void onProgressUpdate(Progress... values) {
            KhandroidAsyncTask3.this.onProgressUpdate(values);
        }


        @Override
        protected void onCancelled() {
            KhandroidAsyncTask3.this.onCancelled();
            mExecutor.onTaskCancelled();
        }


        @Override
        protected Result doInBackground(Params... params) {
            return KhandroidAsyncTask3.this.doInBackground();
        }


        protected void publishProgressInner(Progress... values) {
            super.publishProgress(values);
        }
    }


    public Kat3Executor<Params, Progress, Result> getExecutor() {
        return mExecutor;
    }
}
