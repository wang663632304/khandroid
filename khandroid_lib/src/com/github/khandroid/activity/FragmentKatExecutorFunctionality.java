package com.github.khandroid.activity;

import com.github.khandroid.activity.KatExecutor.IKatExecutorFunctionality;
import com.github.khandroid.misc.KhandroidAsyncTask;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask.Status;
import android.support.v4.app.DialogFragment;


public class FragmentKatExecutorFunctionality extends FragmentUniqueAttachedFunctionality implements IKatExecutorFunctionality {
    private KatExecutor mKatExecutor;
    private DialogFragment mProgressDialogFragment;
    

    public FragmentKatExecutorFunctionality(HostFragment fragment) {
        super(fragment);
        mKatExecutor = new KatExecutor(this);
        
    }
    

    public FragmentKatExecutorFunctionality(HostFragment fragment, DialogCreator dialogCreator) {
        super(fragment);
        mKatExecutor = new KatExecutor(this, dialogCreator);
    }
    
    
    @Override
    public <T> void execute(KhandroidAsyncTask<T, ?, ?> task, T... params) {
        mKatExecutor.execute(task, params);        
    }


    @Override
    public void onTaskCancelled() {
        mKatExecutor.onTaskCancelled();
    }

    @Override
    public void onTaskCompleted(KhandroidAsyncTask<?, ?, ?> task) {
        mKatExecutor.onTaskCompleted(task);
    }


    @Override
    public ProgressDialog showProgressDialog(KhandroidAsyncTask<?, ?, ?> task) {
        mProgressDialogFragment = new FraDiaKatProgress(mKatExecutor.getDialogCreator(), task);
        mProgressDialogFragment.show(getFragment().getFragmentManager(), "dialog");
        
        return (ProgressDialog) mProgressDialogFragment.getDialog();
    }

    
    @Override
    public void dismissProgressDialog() {
        ProgressDialog progressDailog = getProgressDailog();
        if (progressDailog != null) {
            progressDailog.dismiss();
        }
    }
    

    @Override
    public boolean isExecuting() {
        return mKatExecutor.isExecuting();
    }

    
    @Override
    public void setDialogCreator(DialogCreator dialogCreator) {
        mKatExecutor.setDialogCreator(dialogCreator);
    }
    
    
    @Override
    public ProgressDialog getProgressDailog() {
        ProgressDialog ret = null;
        
        if (mProgressDialogFragment != null) {
            Dialog tmp = mProgressDialogFragment.getDialog();
            if (tmp != null) {
                ret = (ProgressDialog) tmp;
            }
        }
        
        return ret;
    }
    
    
    @Override
    public boolean cancelExecution(boolean mayInterruptIfRunning) {
        return mKatExecutor.cancelExecution(mayInterruptIfRunning);
    }

    
    @Override
    public Status getTaskStatus() {
        return mKatExecutor.getTaskStatus();
    }


    @Override
    public Activity getActivity() {
        return getFragment().getActivity();
    }
}







