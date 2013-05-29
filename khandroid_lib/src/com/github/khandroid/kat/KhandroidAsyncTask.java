package com.github.khandroid.kat;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.github.khandroid.misc.KhandroidLog;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;


abstract public class KhandroidAsyncTask<Params, Progress, Result> {
    private InnerAsyncTask<Params, Progress, Result> mTask;
    private TaskListener<Progress, Result> mListener;
    private boolean mIsSerialized = true;


    public KhandroidAsyncTask() {
        mTask = new InnerAsyncTask<Params, Progress, Result>(this);
    }


    public final AsyncTask<Params, Progress, Result> execute(TaskListener<Progress, Result> listener,
                                                             Params... params) {
        mListener = listener;
        return execute(params);
    }


    @SuppressLint("NewApi")
    public final AsyncTask<Params, Progress, Result> execute(Params... params) {
        KhandroidLog.v("Executing KhandroidAsyncTask");

        if (!mIsSerialized && (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)) {
            return mTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            return mTask.execute(params);
        }
    }


    public void detach() {
        KhandroidLog.v("detach " + this);
        mListener = null;
    }


    public void attach(TaskListener<Progress, Result> listener) {
        if (listener != null) {
            KhandroidLog.v("attach " + this);
            this.mListener = listener;
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


    public final Result get(long timeout, TimeUnit unit) throws InterruptedException,
                                                        ExecutionException,
                                                        TimeoutException {
        return mTask.get(timeout, unit);
    }


    public final Result get() throws InterruptedException, ExecutionException {
        return mTask.get();
    }


    protected void onPostExecuteCaller(Result result) {
        onPostExecute(result);
        if (mListener != null) {
            mListener.onTaskCompleted(result);
        }
    }


    protected void onPostExecute(Result result) {
        // intentionally left empty
    }


    protected void onPreExecute() {
        // intentionally left empty
    }


    protected void onProgressUpdateCaler(Progress... values) {
        onProgressUpdate(values);
        if (mListener != null) {
            mListener.onTaskPublishProgress(values);
        }
    }


    protected void onProgressUpdate(Progress... values) {
        // intentionally left empty
    }


    protected void onCancelledCaller() {
        onCancelled();
        if (mListener != null) {
            mListener.onTaskCancelled();
        }
    }


    protected void onCancelled() {
        // intentionally left empty
    }


    protected final void publishProgress(Progress... values) {
        mTask.publishProgressInner(values);
    }


    abstract protected Result doInBackground(Params... params);

    private static class InnerAsyncTask<T, U, V> extends AsyncTask<T, U, V> {
        private KhandroidAsyncTask<T, U, V> mWrapper;


        public InnerAsyncTask(KhandroidAsyncTask<T, U, V> wrapper) {
            super();
            mWrapper = wrapper;
        }


        @Override
        protected void onPreExecute() {
            mWrapper.onPreExecute();
        }


        @Override
        protected void onPostExecute(V result) {
            KhandroidLog.v("onPostExecute");
            mWrapper.onPostExecuteCaller(result);
        }


        @Override
        protected void onProgressUpdate(U... values) {
            mWrapper.onProgressUpdateCaler(values);
        }


        @Override
        protected void onCancelled() {
            mWrapper.onCancelledCaller();
        }


        @Override
        protected V doInBackground(T... params) {
            return (V) mWrapper.doInBackground(params);
        }


        protected void publishProgressInner(U... values) {
            super.publishProgress(values);
        }
    }

    public interface TaskListener<Progress, Result> {
        void onTaskPublishProgress(Progress... progress);


        void onTaskCancelled();


        void onTaskCompleted(Result result);
    }


    public boolean isSerialized() {
        return mIsSerialized;
    }


    public void setIsSerialized(boolean isSerialized) {
        mIsSerialized = isSerialized;
    }

}
