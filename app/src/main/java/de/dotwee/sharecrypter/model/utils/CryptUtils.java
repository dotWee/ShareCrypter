package de.dotwee.sharecrypter.model.utils;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import de.dotwee.sharecrypter.Constants;
import timber.log.Timber;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public final class CryptUtils {
    private static final String LOG_TAG = "CryptUtils";

    @NonNull
    public static byte[] encrypt(@NonNull byte[] data, @NonNull final String password, boolean hash) throws Exception {
        return doFinal(data, generateKey(password, hash), Cipher.ENCRYPT_MODE);
    }

    @NonNull
    public static byte[] decrypt(@NonNull byte[] data, @NonNull final String password, boolean hash) throws Exception {
        return doFinal(data, generateKey(password, hash), Cipher.DECRYPT_MODE);
    }


    @NonNull
    private static byte[] doFinal(@NonNull byte[] data, SecretKeySpec secretKeySpec, int CRYPT_MODE) throws Exception {
        Cipher cipher = Cipher.getInstance(Constants.ENCRYPTION_ALGORITHM);
        cipher.init(CRYPT_MODE, secretKeySpec);

        return cipher.doFinal(data);
    }

    /**
     * Generates SHA256 hash of the password which is used as key
     *
     * @param password used to generated key
     * @return SHA256 of the password
     */
    @NonNull
    private static SecretKeySpec generateKey(@NonNull final String password, boolean hash) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] key = password.getBytes("UTF-8");

        if (hash) {
            final MessageDigest digest = MessageDigest.getInstance(Constants.HASH_ALGORITHM);
            digest.update(key, 0, key.length);
            key = digest.digest();

            Timber.i("SHA-256 key %s", Arrays.toString(key));
        }

        return new SecretKeySpec(key, "AES");
    }
}
