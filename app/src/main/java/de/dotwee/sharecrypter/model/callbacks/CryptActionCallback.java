package de.dotwee.sharecrypter.model.callbacks;

import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public interface CryptActionCallback {
    static final String LOG_TAG = "CryptActionCallback";

    void onWorkStarted();

    void onWorkFinished(@NonNull File handledFile);
}
