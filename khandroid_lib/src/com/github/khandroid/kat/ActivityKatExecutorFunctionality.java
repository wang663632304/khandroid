package com.github.khandroid.kat;

import android.os.AsyncTask;
import android.os.Bundle;

import com.github.khandroid.activity.ActivityAttachedFunctionality;
import com.github.khandroid.activity.HostActivity;
import com.github.khandroid.fragment.FragmentAttachable;
import com.github.khandroid.kat.KhandroidAsyncTask.TaskListener;
import com.github.khandroid.misc.KhandroidLog;


public class ActivityKatExecutorFunctionality<T, U, V> extends ActivityAttachedFunctionality
        implements KatExecutor<T, U, V>, TaskListener<U,V> {
    private KhandroidAsyncTask<T, U, V> mTask;
    private TaskExecutorListener<U, V> mListener;


    public <KatHostActivity extends HostActivity & ActivityKatExecutorFunctionality.HostingAble<U, V>> 
            ActivityKatExecutorFunctionality(KatHostActivity activity) {
        super(activity);
        
        mListener = activity.getKatExecutorListener();
    }


    protected void onContinueWithTask() {
        if (mListener != null) {
            mListener.onContinueWithTask();
        }
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        KhandroidLog.v("onCreate");
        Object task = getActivity().getLastNonConfigurationInstance();
        KhandroidLog.v("Task: " + task);

        if (task != null && task instanceof KhandroidAsyncTask) {
            mTask = (KhandroidAsyncTask<T, U, V>) task;
            mTask.attach(this);

            if (mTask.getStatus() == AsyncTask.Status.RUNNING) {
                onContinueWithTask();
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
    public void execute(KhandroidAsyncTask<T, U, V> task, T... params) {
        mTask = task;
        mTask.execute(this, params);
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
    public void onTaskPublishProgress(U... progress) {
        if (mListener != null) {
            mListener.onTaskPublishProgress(progress);
        }
    }

    @Override
    public void onTaskCancelled() {
        mTask = null;
        if (mListener != null) {
            mListener.onTaskCancelled();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTask != null) {
            if (getActivity().isFinishing()) {
                mTask.cancel(true);
            }

            mTask.detach();
        }
    }


    @Override
    public boolean cancelTask(boolean mayInterruptIfRunning) {
        boolean ret = false;
        
        if (mTask != null) {
            ret = mTask.cancel(mayInterruptIfRunning);
        }
        
        return ret;
    }


    @Override
    public boolean isExecuting() {
        boolean ret = false;

        if (mTask != null) {
            ret = true;
        }
            
        return ret;
    }

    
    public interface HostingAble<U, V> extends FragmentAttachable.HostingAble {
        TaskExecutorListener<U, V> getKatExecutorListener();
    }
}
