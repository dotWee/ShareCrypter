package de.dotwee.sharecrypter.presenter;

import android.content.Intent;
import android.support.annotation.NonNull;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public interface MainPresenter {
    static final String LOG_TAG = "MainPresenter";

    void onIntentReceived(@NonNull Intent intent);

    void onButtonNegative();

    void onButtonPositive();
}
