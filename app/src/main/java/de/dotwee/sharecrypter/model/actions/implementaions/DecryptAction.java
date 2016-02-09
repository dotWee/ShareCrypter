package de.dotwee.sharecrypter.model.actions.implementaions;

import android.support.annotation.NonNull;

import de.dotwee.sharecrypter.Constants;
import de.dotwee.sharecrypter.model.actions.AbstractCryptAction;
import de.dotwee.sharecrypter.model.actions.CryptActionConfig;
import de.dotwee.sharecrypter.model.utils.CryptUtils;

import static de.dotwee.sharecrypter.model.utils.IntentUtils.STATE_DECRYPT;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public final class DecryptAction extends AbstractCryptAction {
    private static final String LOG_TAG = "DecryptAction";
    private final String password;
    private CryptActionConfig cryptActionConfig;

    public DecryptAction(@NonNull CryptActionConfig cryptActionConfig) {
        super(cryptActionConfig.getBaseFile(), Constants.getDecryptedFile(cryptActionConfig.getBaseFile()), cryptActionConfig.getCryptActionCallback());
        this.password = cryptActionConfig.getPassword();
        this.cryptActionConfig = cryptActionConfig;
    }

    @Override
    public int getState() {
        return STATE_DECRYPT;
    }

    @NonNull
    @Override
    public byte[] work(@NonNull byte[] in) throws Exception {
        return CryptUtils.decrypt(in, password, cryptActionConfig.isHash());
    }
}
