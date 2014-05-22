package com.pivotal.cf.mobile.datasdk.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.pivotal.cf.mobile.datasdk.authorization.AuthorizationEngine;
import com.pivotal.cf.mobile.datasdk.prefs.AuthorizationPreferencesProvider;
import com.pivotal.cf.mobile.datasdk.prefs.AuthorizationPreferencesProviderImpl;

public abstract class BaseAuthorizationActivity extends Activity {

    private AuthorizationPreferencesProvider authorizationPreferencesProvider;
    private AuthorizationEngine authorizationEngine;

    public abstract void onAuthorizationComplete();
    public abstract void onAuthorizationFailed(String reason);

    // TODO - do we need to onCreate and provide a null content view?

    @Override
    protected void onResume() {
        super.onResume();
        setupPreferences();
        if (intentHasCallbackUrl(getIntent())) {
            // TODO - check state field in intent.data URI
            setupAuthorizationEngine();
            reenterAuthorizationEngine(getIntent());
        }

        // TODO - finish here?
//        finish();
    }

    private void setupPreferences() {
        if (authorizationPreferencesProvider == null) {
            // TODO - find a way to provide an alternate preferences provider in unit tests
            authorizationPreferencesProvider = new AuthorizationPreferencesProviderImpl(this);
        }
    }

    private boolean intentHasCallbackUrl(Intent intent) {
        if (intent == null) {
            return false;
        }
        if (!intent.hasCategory(Intent.CATEGORY_BROWSABLE)) {
            return false;
        }
        if (intent.getData() == null) {
            return false;
        }
        return intent.getData().toString().startsWith(authorizationPreferencesProvider.getRedirectUrl().toString());
    }

    // TODO - find a way to get the state token in here
//    private boolean verifyCallbackState(Uri uri) {
//        return uri.getQueryParameter("state").equals(STATE_TOKEN);
//    }

    private void setupAuthorizationEngine() {
        if (authorizationEngine == null) {
            authorizationEngine = new AuthorizationEngine(this, authorizationPreferencesProvider);
        }
    }

    private void reenterAuthorizationEngine(Intent intent) {
        final String authorizationCode = getAuthorizationCode(intent.getData());
        authorizationEngine.authorizationCodeReceived(this, authorizationCode);
    }

    private String getAuthorizationCode(Uri uri) {
        return uri.getQueryParameter("code");
    }
}