package net.levinh.ytmusic.domain;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by virus on 12/10/2016.
 */

public abstract class BaseAsyncTask<Params,Progress,Result>
        extends AsyncTask<Params,Progress,Result> {

    Context mContext;
    int mType;
    IAsyncCallBack mCallBack;

    public BaseAsyncTask(Context context, int type, IAsyncCallBack callback) {
        mContext = context;
        mType = type;
        mCallBack = callback;
    }

    @Override
    protected void onPostExecute(Result result) {
        mCallBack.onFinish(mType,result);
    }
}
