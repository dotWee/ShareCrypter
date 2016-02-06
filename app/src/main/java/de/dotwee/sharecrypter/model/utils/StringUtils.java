package de.dotwee.sharecrypter.model.utils;

import android.support.annotation.NonNull;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 * <p/>
 * Utilities class for the {@link String} object.
 */
public final class StringUtils {
    private static final String LOG_TAG = "StringUtils";

    /**
     * This method capitalizes the first letter of its input
     * string and returns it.
     *
     * @param param The string to capitalize.
     * @return Input string with capitalized first letter.
     */
    @NonNull
    public static String capitalizeFirstLetter(@NonNull String param) {
        return param.length() == 0 ? param : param.substring(0, 1).toUpperCase() + param.substring(1);
    }
}
