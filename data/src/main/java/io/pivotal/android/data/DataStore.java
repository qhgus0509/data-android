/*
 * Copyright (C) 2014 Pivotal Software, Inc. All rights reserved.
 */
package io.pivotal.android.data;

public interface DataStore<T> {

    public Response<T> execute(final Request<T> request);

    public void execute(final Request<T> request, final Listener<T> listener);

    public boolean addObserver(final Observer<T> observer);

    public boolean removeObserver(final Observer<T> observer);


    public static interface Observer<T> {
        public void onResponse(Response<T> response);
    }

    public static interface Listener<T> {
        public void onResponse(Response<T> response);
    }
}
