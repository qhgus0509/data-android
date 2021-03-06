/*
 * Copyright (C) 2014 Pivotal Software, Inc. All rights reserved.
 */
package io.pivotal.android.data;

import android.content.Context;

public class Data {

    public static void registerTokenProvider(final TokenProvider provider) {
        TokenProviderFactory.registerTokenProvider(provider);
    }

    public static void registerConnectivityListener(final Context context, final ConnectivityListener connectivityListener) {
        ConnectivityReceiver.registerConnectivityListener(context, connectivityListener);
    }

    public static void sync(final Context context) {
        final OfflineStore<KeyValue> offlineStore = KeyValueOfflineStore.create(context);
        offlineStore.getRequestCache().executePending();
    }

    public static void syncInBackground(final Context context) {
        final OfflineStore<KeyValue> offlineStore = KeyValueOfflineStore.create(context);
        offlineStore.getRequestCache().executePendingAsync();
    }

    public static void clearLocalCache(final Context context) {
        new DataPersistence(context, KeyValueLocalStore.DATA_PREFIX).clear();
        new DataPersistence(context, EtagStore.ETAG_CACHE).clear();
    }
}
