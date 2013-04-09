package com.github.khandroid.kat;


import java.util.concurrent.ExecutionException;

import android.os.AsyncTask;
import android.os.Bundle;

import com.github.khandroid.activity.ActivityAttachedFunctionality;
import com.github.khandroid.activity.HostActivity;
import com.github.khandroid.misc.KhandroidLog;


public class ActivityKat3ExecutorFunctionality<T, U, V> extends ActivityAttachedFunctionality implements Kat3Executor<T, U, V> {
    private KhandroidAsyncTask3<T, U, V> mTask;
    TaskExecutorListener<U, V> mListener;


    public ActivityKat3ExecutorFunctionality(HostActivity activity) {
        super(activity);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KhandroidLog.v("onCreate");
        Object task = getActivity().getLastNonConfigurationInstance();
        KhandroidLog.v("Task: " + task);
                
        if (task != null && task instanceof KhandroidAsyncTask3) {
            KhandroidAsyncTask3<T, U, V> mTask = (KhandroidAsyncTask3<T, U, V>) task;
            mTask.attach(this);
            
            if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
                onContinueWithTask();
            } else if (mTask.getStatus() == AsyncTask.Status.FINISHED) {
                try {
                    onTaskCompleted(mTask.get());
                } catch (InterruptedException e) {
                    // Cannot happen. We check if it is finished
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    // Cannot happen. We check if it is finished
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    public Object onRetainNonConfigurationInstance() {
        Object ret;
        
        if (mTask != null) {
            mTask.detach();
            ret = mTask;
        } else {
            ret = null;
        }
        
        return ret;
    }
    
    
    @Override
    public void execute(KhandroidAsyncTask3<T, U, V> task,
                        TaskExecutorListener<U, V> listener,
                        T... params) {
        
        mListener = listener;
        execute(task, params);
    }


    @Override
    public void execute(KhandroidAsyncTask3<T, U, V> task, T... params) {
        mTask = task;
        mTask.execute(this, params);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void execute(KhandroidAsyncTask3<T, U, V> task,
                        TaskExecutorListener<U, V> listener) {

        mListener = listener;
        mTask.execute(this, (T) null);
    }


    @Override
    public void onTaskCompleted(V result) {
        mTask.detach();
        mTask = null;
        if (mListener != null) {
            mListener.onTaskCompleted(result);
        }
    }


    @Override
    public void onTaskProgressUpdate(U... progress) {
        if (mListener != null) {
            mListener.onTaskPublishProgress(progress);
        }
    }


    @Override
    public void onTaskCancelled() {
        if (mListener != null) {
            mListener.onTaskCancelled();
        }
    }
    
    
    protected void onContinueWithTask() {
        // empty
    }
}
