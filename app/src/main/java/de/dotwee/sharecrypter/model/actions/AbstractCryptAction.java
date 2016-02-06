package de.dotwee.sharecrypter.model.actions;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

import de.dotwee.sharecrypter.model.callbacks.CryptActionCallback;
import timber.log.Timber;

import static de.dotwee.sharecrypter.model.utils.IntentUtils.STATE_UNKNOWN;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public abstract class AbstractCryptAction implements CryptActionInterface, Runnable {
    private static final String LOG_TAG = "AbstractCryptAction";
    private CryptActionCallback cryptActionCallback;
    private File baseFile, targetFile;

    public AbstractCryptAction(@NonNull File baseFile, @NonNull File targetFile, @Nullable CryptActionCallback cryptActionCallback) {
        this.baseFile = baseFile;
        this.cryptActionCallback = cryptActionCallback;
        this.targetFile = targetFile;
    }

    @Override
    public void run() {
        Looper.prepare();

        Timber.i("Starting work...");

        if (cryptActionCallback != null) {
            cryptActionCallback.onWorkStarted();
        }

        try {
            final byte[] bytes = work(readFile(getBaseFile()));
            Timber.i("Target file: %s", getTargetFile().getAbsolutePath());
            writeFile(getTargetFile(), bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cryptActionCallback != null) {
            cryptActionCallback.onWorkFinished(getTargetFile());
        }
    }

    @NonNull
    @Override
    public File getBaseFile() {
        return baseFile;
    }

    @Override
    public void writeFile(@NonNull File file, @NonNull byte[] data) throws IOException {
        FileUtils.writeByteArrayToFile(file, data);
    }

    @NonNull
    @Override
    public byte[] readFile(@NonNull File file) throws IOException {
        return FileUtils.readFileToByteArray(file);
    }

    @Override
    public int getState() {
        return STATE_UNKNOWN;
    }

    @NonNull
    @Override
    public File getTargetFile() {
        return targetFile;
    }
}
