package de.dotwee.sharecrypter.model.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import de.dotwee.sharecrypter.Constants;
import timber.log.Timber;

import static de.dotwee.sharecrypter.Constants.ENCRYPTED_FILE_EXTENSION;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public final class CryptUtils {
    private static final String LOG_TAG = "CryptUtils";

    @NonNull
    public static byte[] encrypt(@NonNull byte[] data, @NonNull final String password) throws Exception {
        return doFinal(data, generateKey(password), Cipher.ENCRYPT_MODE);
    }

    @NonNull
    public static byte[] decrypt(@NonNull byte[] data, @NonNull final String password) throws Exception {
        return doFinal(data, generateKey(password), Cipher.DECRYPT_MODE);
    }


    @NonNull
    private static byte[] doFinal(@NonNull byte[] data, SecretKeySpec secretKeySpec, int CRYPT_MODE) throws Exception {
        Cipher cipher = Cipher.getInstance(Constants.ENCRYPTION_ALGORITHM);
        cipher.init(CRYPT_MODE, secretKeySpec);

        return cipher.doFinal(data);
    }

    /**
     * Checks if a file is encrypted by reading its name
     *
     * @param file to check
     * @return if file is encrypted or not
     */
    public static boolean isEncrypted(@Nullable File file) {
        return file != null && file.getName().endsWith(ENCRYPTED_FILE_EXTENSION);
    }

    /**
     * Generates SHA256 hash of the password which is used as key
     *
     * @param password used to generated key
     * @return SHA256 of the password
     */
    @NonNull
    private static SecretKeySpec generateKey(@NonNull final String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        final MessageDigest digest = MessageDigest.getInstance(Constants.HASH_ALGORITHM);
        byte[] bytes = password.getBytes("UTF-8");

        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();

        Timber.i("SHA-256 key %s", Arrays.toString(key));
        return new SecretKeySpec(key, "AES");
    }
}
