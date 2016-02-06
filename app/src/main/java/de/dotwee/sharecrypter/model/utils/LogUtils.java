package de.dotwee.sharecrypter.model.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import timber.log.Timber;

/**
 * Created by Lukas Wolfsteiner on 04.02.2016.
 * <p/>
 * Utilities class for internal logging.
 */
public final class LogUtils {
    private static final String LOG_TAG = "LogUtils";

    /**
     * This method logs interesting intent values
     * specifically for this application.
     *
     * @param intent The intent to log.
     */
    public static void logIntent(@NonNull final Intent intent, @NonNull final Context context) {
        final String[] params = new String[]{
                "action",
                "data path",
                "component name",
                "full data",
                "file path"
        };

        final String[] paramsValues = new String[]{
                intent.getAction(),
                intent.getDataString(),
                intent.getComponent().flattenToString(),
                intent.toString(),
                IntentUtils.getFilePath(intent)
        };

        Timber.d("Logging intent");
        for (int i = 0; i < params.length; i++) {
            Timber.d(params[i].concat(" :: %s"), paramsValues[i]);
        }
    }
}
