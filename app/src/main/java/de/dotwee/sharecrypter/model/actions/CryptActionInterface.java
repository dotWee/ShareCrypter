package de.dotwee.sharecrypter.model.actions;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public interface CryptActionInterface {
    static final String LOG_TAG = "CryptActionInterface";

    @NonNull
    File getBaseFile();

    @NonNull
    byte[] work(@NonNull byte[] in) throws Exception;

    @NonNull
    byte[] readFile(@NonNull File file) throws IOException;

    void writeFile(@NonNull File file, @NonNull byte[] data) throws IOException;

    int getState();

    @NonNull
    File getTargetFile();
}
