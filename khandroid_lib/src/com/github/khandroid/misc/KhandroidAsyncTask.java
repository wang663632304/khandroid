package com.github.khandroid.misc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.github.khandroid.activity.KatExecutor.IKatExecutorFunctionality;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;


abstract public class KhandroidAsyncTask<Params, Progress, ResultData> {
    private IKatExecutorFunctionality mExecutor;
    private InnerAsyncTask mTask;


    public KhandroidAsyncTask(IKatExecutorFunctionality executor) {
        attach(executor);
        
        mTask = new InnerAsyncTask(); 
    }

    
    public final AsyncTask<Params, Progress, Result<ResultData>> execute(Params... params) {
        if (mExecutor != null) {
            KhandroidLog.v("Executing KhandroidAsyncTask");
            return mTask.execute(params);
        } else {
            throw new IllegalStateException("Trying to execute the task before setting executor with pluggedTo()");
        }
    }

    public void detach() {
        KhandroidLog.v("detach " + this);
        mExecutor = null;
    }


    public void attach(IKatExecutorFunctionality executor) {
        if (executor != null) {
            KhandroidLog.v("attach " + this);
            this.mExecutor = executor;
        } else {
            throw new IllegalArgumentException("executor == null");
        }
    }
    
    
    public Activity getActivity() {
        if (mExecutor != null) {
            return mExecutor.getActivity();    
        } else {
            return null;
        }
    }


    public AsyncTask.Status getStatus() {
        if (mExecutor != null) {
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
    
    
    public final Result<ResultData> get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return mTask.get(timeout, unit);
    }
    
    
    public final Result<ResultData> get() throws InterruptedException, ExecutionException {
        return mTask.get();
    }
                                 
    
    public static class Result<ResultData> { 
        private final int status;
        private final ResultData data;
        
        public Result(int status, ResultData result) {
            super();
            this.status = status;
            this.data = result;
        }

        public int getStatus() {
            return status;
        }

        public ResultData getData() {
            return data;
        }
    }
    
    
    protected ProgressDialog getExecutorDialog() {
        return mExecutor.getProgressDailog();
    }
    
    
    protected void onPostExecute(Result<ResultData> result) {
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
    
    
    abstract protected Result<ResultData> doInBackground(Params... params);
    
    
    private class InnerAsyncTask extends AsyncTask<Params, Progress, Result<ResultData>> {
        @Override
        protected void onPreExecute() {
            KhandroidAsyncTask.this.onPreExecute();
        }


        @Override
        protected void onPostExecute(Result<ResultData> result) {
            KhandroidLog.v("onPostExecute");
            KhandroidAsyncTask.this.onPostExecute(result);
            mExecutor.onTaskCompleted(KhandroidAsyncTask.this);
        }


        @Override
        protected void onProgressUpdate(Progress... values) {
            KhandroidAsyncTask.this.onProgressUpdate(values);
        }


        @Override
        protected void onCancelled() {
            KhandroidAsyncTask.this.onCancelled();
        }


        @Override
        protected Result<ResultData> doInBackground(Params... params) {
            return KhandroidAsyncTask.this.doInBackground(params);
        }


        protected void publishProgressInner(Progress... values) {
            super.publishProgress(values);
        }
    }


    public IKatExecutorFunctionality getExecutor() {
        return mExecutor;
    }
}
