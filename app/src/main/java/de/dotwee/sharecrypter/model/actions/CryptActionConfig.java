package de.dotwee.sharecrypter.model.actions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

import de.dotwee.sharecrypter.model.callbacks.CryptActionCallback;

/**
 * Created by Lukas Wolfsteiner on 09.02.2016.
 */
public final class CryptActionConfig {
    private static final String LOG_TAG = "CryptActionConfig";

    CryptActionCallback cryptActionCallback;
    String password;
    File baseFile;
    boolean hash;

    public CryptActionConfig(@Nullable CryptActionCallback cryptActionCallback, @NonNull String password, @NonNull File baseFile, boolean hash) {
        this.cryptActionCallback = cryptActionCallback;
        this.password = password;
        this.baseFile = baseFile;
        this.hash = hash;
    }

    public CryptActionCallback getCryptActionCallback() {
        return cryptActionCallback;
    }

    public void setCryptActionCallback(CryptActionCallback cryptActionCallback) {
        this.cryptActionCallback = cryptActionCallback;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public File getBaseFile() {
        return baseFile;
    }

    public void setBaseFile(File baseFile) {
        this.baseFile = baseFile;
    }

    public boolean isHash() {
        return hash;
    }

    public void setHash(boolean hash) {
        this.hash = hash;
    }


}
