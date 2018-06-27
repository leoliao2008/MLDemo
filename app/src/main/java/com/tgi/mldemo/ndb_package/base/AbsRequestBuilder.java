package com.tgi.mldemo.ndb_package.base;

public abstract class AbsRequestBuilder<REQUEST> {

    public AbsRequestBuilder() {
        initRequestParams();
    }

    protected String[] mParams;

    protected abstract void initRequestParams();

    public abstract REQUEST build();

}
