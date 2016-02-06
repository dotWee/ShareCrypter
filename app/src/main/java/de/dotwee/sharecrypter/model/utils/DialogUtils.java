package de.dotwee.sharecrypter.model.utils;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Lukas Wolfsteiner on 06.02.2016.
 */
public final class DialogUtils {
    private static final String LOG_TAG = "DialogUtils";

    public static void showDialog(Context context, int titleId, int messageId) {
        new AlertDialog.Builder(context)
                .setTitle(titleId)
                .setMessage(messageId)
                .create().show();
    }
}
