package de.dotwee.sharecrypter.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.File;

import de.dotwee.sharecrypter.R;
import de.dotwee.sharecrypter.model.actions.AbstractCryptAction;
import de.dotwee.sharecrypter.model.actions.DecryptAction;
import de.dotwee.sharecrypter.model.actions.EncryptAction;
import de.dotwee.sharecrypter.model.callbacks.CryptActionCallback;
import de.dotwee.sharecrypter.model.commands.CryptCommand;
import de.dotwee.sharecrypter.model.commands.CryptCommandManager;
import de.dotwee.sharecrypter.model.utils.IntentUtils;
import de.dotwee.sharecrypter.model.utils.StringUtils;
import de.dotwee.sharecrypter.view.MainActivity;
import timber.log.Timber;

import static de.dotwee.sharecrypter.model.utils.IntentUtils.STATE_DECRYPT;
import static de.dotwee.sharecrypter.model.utils.IntentUtils.STATE_ENCRYPT;
import static de.dotwee.sharecrypter.model.utils.IntentUtils.getState;
import static de.dotwee.sharecrypter.model.utils.LogUtils.logIntent;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public class MainPresenterImpl implements MainPresenter, CryptActionCallback {
    private static final String LOG_TAG = "MainPresenterImpl";
    private Context applicationContext;
    private MainActivity mainActivity;
    private Intent intent;
    private int STATE;

    private MainPresenterImpl() {

    }

    private MainPresenterImpl(MainActivity mainActivity) {
        this.applicationContext = mainActivity.getApplicationContext();
        this.mainActivity = mainActivity;
        this.intent = mainActivity.getIntent();

        onIntentReceived(intent);
    }

    public static synchronized MainPresenter bind(MainActivity mainActivity) {
        Timber.i("Creating new instance of %s", LOG_TAG);
        return new MainPresenterImpl(mainActivity);
    }

    @Override
    public void onIntentReceived(@NonNull Intent intent) {
        this.STATE = getState(intent);
        String state_action;

        logIntent(intent, applicationContext);
        switch (STATE) {

            case STATE_ENCRYPT:
                state_action = mainActivity.getString(R.string.action_encrypt);
                break;

            case STATE_DECRYPT:
                state_action = mainActivity.getString(R.string.action_decrypt);
                break;

            default:
                this.onButtonNegative();
                return;
        }

        state_action = StringUtils.capitalizeFirstLetter(state_action);

        mainActivity.setTitle(state_action.concat(" ").concat(mainActivity.getString(R.string.caption_file)));
        mainActivity.textViewPasswordHint.setText(state_action.concat("ion password"));
        mainActivity.buttonPositive.setText(state_action);
    }

    @Override
    public void onButtonNegative() {

        // finish activity
        mainActivity.finish();
    }

    @Override
    public void onButtonPositive() {

        String password = mainActivity.editTextPassword.getText().toString();
        if (password.isEmpty()) {
            Toast.makeText(mainActivity, mainActivity.getString(R.string.message_no_empty_password_allowed), Toast.LENGTH_SHORT).show();

            // Abort process
            return;
        }

        try {
            File baseFile = IntentUtils.getFile(intent);
            AbstractCryptAction cryptAction;

            switch (STATE) {
                case STATE_ENCRYPT:
                    cryptAction = new EncryptAction(
                            baseFile,
                            password,
                            this
                    );
                    break;

                case STATE_DECRYPT:
                    cryptAction = new DecryptAction(
                            baseFile,
                            password,
                            this
                    );
                    break;

                default:
                    throw new IllegalStateException("Couldn't detect crypt state. Aborting.");
            }

            CryptCommand cryptCommand = new CryptCommand(cryptAction);

            // Add crypt command to pool manager and ask for execution
            CryptCommandManager
                    .getInstance()
                    .execute(cryptCommand);

        } catch (IllegalStateException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWorkStarted() {
        Timber.i("OnWorkStarted at %s", System.currentTimeMillis());
    }

    @Override
    public void onWorkFinished(@NonNull File handledFile) {
        Timber.i("OnWorkFinished at %s", System.currentTimeMillis());

        if (!handledFile.exists()) {
            Timber.e("Handled file doesn't exist. Path: %s", handledFile.getAbsolutePath());
        }

        String action = mainActivity.getString(STATE == STATE_ENCRYPT ? R.string.action_encrypt : R.string.action_decrypt);
        String message = mainActivity.getString(R.string.message_work_finished).replaceFirst("%1", action);
        Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show();
    }
}
