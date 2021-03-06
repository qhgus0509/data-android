/*
 * Copyright (C) 2014 Pivotal Software, Inc. All rights reserved.
 */
package io.pivotal.android.data;

import android.os.Message;
import android.test.AndroidTestCase;

import org.mockito.Mockito;

@SuppressWarnings("unchecked")
public class ObserverHandlerTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", mContext.getCacheDir().getPath());
    }

    public void testAddObserver() {
        final DataStore.Observer observer = Mockito.mock(DataStore.Observer.class);
        final ObserverHandler handler = new ObserverHandler();

        assertTrue(handler.addObserver(observer));
        assertTrue(handler.getObservers().contains(observer));
    }

    public void testRemoveObserver() {
        final DataStore.Observer observer = Mockito.mock(DataStore.Observer.class);
        final ObserverHandler handler = new ObserverHandler();

        assertTrue(handler.getObservers().add(observer));
        assertTrue(handler.removeObserver(observer));
    }

    public void testHandleMessage() {
        final Response response = Mockito.mock(Response.class);
        final DataStore.Observer observer = Mockito.mock(DataStore.Observer.class);
        final ObserverHandler handler = new ObserverHandler();
        handler.addObserver(observer);

        final Message message = handler.obtainMessage(1000, response);
        handler.handleMessage(message);

        Mockito.verify(observer).onResponse(response);
    }
}