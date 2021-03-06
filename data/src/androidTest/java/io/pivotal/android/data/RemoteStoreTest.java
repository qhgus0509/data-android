/*
 * Copyright (C) 2014 Pivotal Software, Inc. All rights reserved.
 */
package io.pivotal.android.data;

import android.test.AndroidTestCase;

import org.mockito.Mockito;

import java.util.Random;

@SuppressWarnings("unchecked")
public class RemoteStoreTest extends AndroidTestCase {

    private static final boolean RESULT = new Random().nextBoolean();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        System.setProperty("dexmaker.dexcache", mContext.getCacheDir().getPath());
    }

    public void testAddObserverInvokesHandler() {
        final ObserverHandler observerHandler = Mockito.mock(ObserverHandler.class);
        final DataStore.Observer observer = Mockito.mock(DataStore.Observer.class);
        final RemoteStore remoteStore = Mockito.spy(new DefaultRemoteStore(observerHandler, null));

        Mockito.when(observerHandler.addObserver(observer)).thenReturn(RESULT);

        assertEquals(RESULT, remoteStore.addObserver(observer));

        Mockito.verify(observerHandler).addObserver(observer);
    }

    public void testRemoveObserverInvokesHandler() {
        final ObserverHandler observerHandler = Mockito.mock(ObserverHandler.class);
        final DataStore.Observer observer = Mockito.mock(DataStore.Observer.class);
        final RemoteStore remoteStore = Mockito.spy(new DefaultRemoteStore(observerHandler, null));

        Mockito.when(observerHandler.removeObserver(observer)).thenReturn(RESULT);

        assertEquals(RESULT, remoteStore.removeObserver(observer));

        Mockito.verify(observerHandler).removeObserver(observer);
    }


    // ==============================================================


    public static class DefaultRemoteStore extends RemoteStore<Object> {

        public DefaultRemoteStore(final ObserverHandler<Object> handler, final RemoteClient client) {
            super(handler, client);
        }

        @Override
        public Response<Object> execute(final Request<Object> request) {
            return null;
        }
    }

}
