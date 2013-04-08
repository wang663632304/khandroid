package com.github.khandroid.kat;

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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.github.khandroid.misc.KhandroidLog;

import android.os.AsyncTask;


abstract public class KhandroidAsyncTask2<Params, Progress, Result> {
    private Kat2Executor mExecutor;
    private InnerAsyncTask mTask;


    public KhandroidAsyncTask2() {
        mTask = new InnerAsyncTask(); 
    }

//    public final AsyncTask<Params, Progress, Result> execute() {
//        return mTask.execute();
//    }
    
    public final AsyncTask<Params, Progress, Result> execute(Kat2Executor executor, Params... params) {
        attach(executor);
        KhandroidLog.v("Executing KhandroidAsyncTask");
        return mTask.execute(params);
    }

    public void detach() {
        KhandroidLog.v("detach " + this);
        mExecutor = null;
    }


    public void attach(Kat2Executor executor) {
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
    
    
    abstract protected Result doInBackground(Params... params);
    
    
    private class InnerAsyncTask extends AsyncTask<Params, Progress, Result> {
        @Override
        protected void onPreExecute() {
            KhandroidAsyncTask2.this.onPreExecute();
        }


        @Override
        protected void onPostExecute(Result result) {
            KhandroidLog.v("onPostExecute");
            KhandroidAsyncTask2.this.onPostExecute(result);
            mExecutor.onTaskCompleted(KhandroidAsyncTask2.this);
        }


        @Override
        protected void onProgressUpdate(Progress... values) {
            KhandroidAsyncTask2.this.onProgressUpdate(values);
        }


        @Override
        protected void onCancelled() {
            KhandroidAsyncTask2.this.onCancelled();
        }


        @Override
        protected Result doInBackground(Params... params) {
            return KhandroidAsyncTask2.this.doInBackground(params);
        }


        protected void publishProgressInner(Progress... values) {
            super.publishProgress(values);
        }
    }


    public Kat2Executor getExecutor() {
        return mExecutor;
    }
    
    
    public interface TaskCompletedListener<T> {
        void onTaskCompleted(T result); 
    }
}
