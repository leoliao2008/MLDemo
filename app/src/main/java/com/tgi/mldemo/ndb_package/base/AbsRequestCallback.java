package com.tgi.mldemo.ndb_package.base;

public abstract class AbsRequestCallback<RESPONSE> {
    public abstract void onPreExecute();
    public abstract void onPostExecute(RESPONSE responses);
    public abstract void onError(Exception e);
}
