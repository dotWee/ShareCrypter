package de.dotwee.sharecrypter;

import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public final class Constants {
    public static final String ENCRYPTED_FILE_EXTENSION = ".crypt";
    public static final String ENCRYPTION_ALGORITHM = "AES/CBC/PKCS5Padding";
    public static final String HASH_ALGORITHM = "SHA-256";

    private static final String LOG_TAG = "Constants";

    @NonNull
    public static File getEncryptedFile(@NonNull File baseFile) {
        return new File(baseFile.getPath().concat(Constants.ENCRYPTED_FILE_EXTENSION));
    }

    @NonNull
    public static File getDecryptedFile(@NonNull File encryptedFile) {
        return new File(encryptedFile.getPath().replaceAll(Constants.ENCRYPTED_FILE_EXTENSION, ""));
    }
}
