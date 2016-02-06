package de.dotwee.sharecrypter.model.commands;

import java.util.Arrays;

import de.dotwee.sharecrypter.model.actions.AbstractCryptAction;
import de.dotwee.sharecrypter.model.utils.IntentUtils;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public class CryptCommand {
    private static final String LOG_TAG = "CryptCommand";

    AbstractCryptAction cryptoActionClass;
    Runnable runnable;
    int STATE;

    public CryptCommand(AbstractCryptAction cryptoActionClass) {
        this.cryptoActionClass = cryptoActionClass;
        this.STATE = cryptoActionClass.getState();
        this.runnable = cryptoActionClass;
    }

    public Runnable getRunnable() {
        return runnable;
    }

    @Override
    public String toString() {
        return Arrays.toString(new String[]{
                "CRYPT_ACTION - " + (STATE == IntentUtils.STATE_ENCRYPT ? "ENCRYPT" : "DECRYPT"),
                "BASE_FILE_PATH - " + cryptoActionClass.getBaseFile().getAbsolutePath(),
                "TARGET_FILE_PATH - " + cryptoActionClass.getTargetFile().getAbsolutePath(),
        });
    }
}
