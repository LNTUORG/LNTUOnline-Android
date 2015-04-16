/*
 * Copyright (C) 2014-2016 ColoShine Inc. All Rights Reserved.
 */

package com.lntu.online.model.api;


import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CallbackAdapter<T> implements Callback<T> {

    @Override
    public void success(T t, Response response) {}

    @Override
    public void failure(RetrofitError error) {}

}
