package com.github.khandroid.activity;

import android.content.Intent;


public class ResultFromActivityContainer {
    private final int mRequestCode;
    private final int mResultCode;
    private final Intent mData;


    public ResultFromActivityContainer(int requestCode, int resultCode, Intent data) {
        super();
        mRequestCode = requestCode;
        mResultCode = resultCode;
        mData = data;
    }


    public int getRequestCode() {
        return mRequestCode;
    }


    public int getResultCode() {
        return mResultCode;
    }


    public Intent getData() {
        return mData;
    }
}
