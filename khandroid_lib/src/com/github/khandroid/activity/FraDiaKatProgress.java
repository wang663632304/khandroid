package com.github.khandroid.activity;

import com.github.khandroid.activity.KatExecutor.IKatExecutorFunctionality.DialogCreator;
import com.github.khandroid.misc.KhandroidAsyncTask;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;


public class FraDiaKatProgress extends DialogFragment {
    private DialogCreator mDialogCreator;
    private ProgressDialog mProgressDialog;
    private KhandroidAsyncTask<?, ?, ?> mTask;


    public FraDiaKatProgress() {
        super();
    }


    public FraDiaKatProgress(DialogCreator dialogCreator, KhandroidAsyncTask<?, ?, ?> task) {
        super();
        setRetainInstance(true);
        mDialogCreator = dialogCreator;
        mTask = task;
    }


    public static FraDiaKatProgress newInstance(DialogCreator dialogCreator,
                                                 KhandroidAsyncTask<?, ?, ?> task) {
        
        //TODO intercept message parameter that should be shown along with the wait indicator
        return new FraDiaKatProgress(dialogCreator, task);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mProgressDialog = mDialogCreator.create(mTask, getActivity());
        return mProgressDialog;
    }


    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }
}