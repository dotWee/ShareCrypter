package de.dotwee.sharecrypter.model.utils;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

import timber.log.Timber;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public final class IntentUtils {
    public static final int STATE_ENCRYPT = Integer.MIN_VALUE,
            STATE_DECRYPT = Integer.MAX_VALUE,
            STATE_UNKNOWN = 0;
    private static final String LOG_TAG = "IntentUtils";

    public static boolean isSupported(@NonNull Intent intent) {
        boolean state = false;

        String receivedAction = intent.getAction();
        String[] acceptedTypes = new String[]{
                "image/", "file/"
        };

        for (String type : acceptedTypes) {
            if (!state && receivedAction.startsWith(type)) {
                state = true;
            }
        }

        return state;
    }

    @NonNull
    public static String getFilePath(@NonNull Intent intent) {
        String filePath = "";

        switch (intent.getAction()) {
            case Intent.ACTION_SEND:
                Timber.i("Using Intent.ACTION_SEND method to get the file path");

                try {
                    Uri fileUri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
                    filePath = fileUri.toString();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;

            case Intent.ACTION_VIEW:
                Timber.i("Using Intent.ACTION_VIEW method to get the file path");

                filePath = intent.getDataString().replace("file:/", "");
                break;
        }

        if (filePath.isEmpty()) {
            Timber.w("File path is empty. Couldn't fetch file path from intent.");
        } else Timber.i("Fetched file path from intent: %s", filePath);

        return filePath;
    }

    @Nullable
    public static File getFile(@NonNull Intent intent) {
        String filePath = getFilePath(intent);

        File file = new File(filePath);
        if (file.exists()) {
            return file;
        } else {
            Timber.e("Couldn't find file with path %s", filePath);
            return null;
        }
    }

    public static int getState(@NonNull Intent intent) {
        int i = STATE_UNKNOWN;

        switch (intent.getAction()) {

            case Intent.ACTION_SEND:
                i = STATE_ENCRYPT;
                break;

            case Intent.ACTION_VIEW:
                i = STATE_DECRYPT;
                break;
        }

        return i;
    }
}
