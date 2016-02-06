package de.dotwee.sharecrypter;

import android.app.Application;

import com.karumi.dexter.Dexter;

import timber.log.Timber;
import timber.log.Timber.DebugTree;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public class ShareCrypterApplication extends Application {
    private static final String LOG_TAG = "ShareCrypterApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new DebugTree());
        Dexter.initialize(this);
    }
}
