package de.dotwee.sharecrypter.model.actions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

import de.dotwee.sharecrypter.Constants;
import de.dotwee.sharecrypter.model.callbacks.CryptActionCallback;
import de.dotwee.sharecrypter.model.utils.CryptUtils;

import static de.dotwee.sharecrypter.model.utils.IntentUtils.STATE_ENCRYPT;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public final class EncryptAction extends AbstractCryptAction {
    private static final String LOG_TAG = "EncryptAction";
    private final String password;

    public EncryptAction(@NonNull File baseFile, @NonNull final String password, @Nullable CryptActionCallback cryptActionCallback) {
        super(baseFile, Constants.getEncryptedFile(baseFile), cryptActionCallback);
        this.password = password;
    }

    @Override
    public int getState() {
        return STATE_ENCRYPT;
    }

    @NonNull
    @Override
    public byte[] work(@NonNull byte[] in) throws Exception {
        return CryptUtils.encrypt(in, password);
    }
}
