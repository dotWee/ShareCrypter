package de.dotwee.sharecrypter.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

import de.dotwee.sharecrypter.Constants;
import de.dotwee.sharecrypter.R;
import de.dotwee.sharecrypter.model.actions.AbstractCryptAction;
import de.dotwee.sharecrypter.model.actions.CryptActionConfig;
import de.dotwee.sharecrypter.model.actions.implementaions.DecryptAction;
import de.dotwee.sharecrypter.model.actions.implementaions.EncryptAction;
import de.dotwee.sharecrypter.model.callbacks.CryptActionCallback;
import de.dotwee.sharecrypter.model.commands.CryptCommand;
import de.dotwee.sharecrypter.model.commands.CryptCommandManager;
import de.dotwee.sharecrypter.model.utils.DialogUtils;
import de.dotwee.sharecrypter.view.MainActivity;
import timber.log.Timber;

import static de.dotwee.sharecrypter.model.utils.IntentUtils.STATE_DECRYPT;
import static de.dotwee.sharecrypter.model.utils.IntentUtils.STATE_ENCRYPT;
import static de.dotwee.sharecrypter.model.utils.IntentUtils.getFile;
import static de.dotwee.sharecrypter.model.utils.IntentUtils.getState;
import static de.dotwee.sharecrypter.model.utils.StringUtils.capitalizeFirstLetter;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public class MainPresenterImpl implements MainPresenter, CryptActionCallback {
    private static final String LOG_TAG = "MainPresenterImpl";
    private Context applicationContext;
    private MainActivity mainActivity;

    private String state_action;
    private File baseFile;
    private Intent intent;
    private int STATE;

    private MainPresenterImpl() {

    }

    private MainPresenterImpl(MainActivity mainActivity) {
        this.applicationContext = mainActivity.getApplicationContext();
        this.intent = mainActivity.getIntent();
        this.mainActivity = mainActivity;

        onIntentReceived(intent);
    }

    public static synchronized MainPresenter bind(MainActivity mainActivity) {
        Timber.i("Creating new instance of %s", LOG_TAG);
        return new MainPresenterImpl(mainActivity);
    }

    @Override
    public void onIntentReceived(@NonNull Intent intent) {
        this.STATE = getState(intent);
        this.baseFile = getFile(intent);

        switch (STATE) {

            case STATE_ENCRYPT:
                state_action = mainActivity.getString(R.string.action_encrypt);
                break;

            case STATE_DECRYPT:
                state_action = mainActivity.getString(R.string.action_decrypt);
                break;

            default:
                DialogUtils.showDialog(
                        applicationContext,
                        R.string.dialog_name_error,
                        R.string.message_no_file_provided
                );

                this.onButtonNegative();
                return;
        }

        this.onTextChanges();
    }

    @Override
    public void onButtonNegative() {

        // Close application using Activity.finish();
        mainActivity.finish();
    }

    @Override
    public void onButtonPositive() {

        // 12345678
        String password = mainActivity.editTextPassword.getText().toString();
        if (password.isEmpty()) {
            Toast.makeText(mainActivity, mainActivity.getString(R.string.message_no_empty_password_allowed), Toast.LENGTH_SHORT).show();

            // Abort process
            return;
        }

        try {
            this.baseFile = getFile(intent);
            if (baseFile == null) {

                throw new FileNotFoundException("Couldn't get base file.");
            }

            CryptActionConfig cryptActionConfig = new CryptActionConfig(
                    this, password, baseFile, mainActivity.checkBoxHash.isChecked()
            );

            AbstractCryptAction cryptAction;

            switch (STATE) {
                case STATE_ENCRYPT:
                    cryptAction = new EncryptAction(cryptActionConfig);
                    break;

                case STATE_DECRYPT:
                    cryptAction = new DecryptAction(cryptActionConfig);
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();

            Toast.makeText(applicationContext, applicationContext.getString(R.string.message_unable_to_locate_basefile), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTextChanges() {

        this.onTitleChange();
        this.onContentTextChange();
    }

    /**
     * This method handles changes on the main dialog title.
     */
    @Override
    public void onTitleChange() {
        state_action = capitalizeFirstLetter(state_action);
        if (baseFile != null) {

            String activityTitle = String.format(
                    state_action,
                    baseFile.getName()
            );

            mainActivity.setTitle(activityTitle);
        }
    }

    /**
     * This method handles text changes on the main dialog content.
     */
    @Override
    public void onContentTextChange() {

        // R.id.textViewCaptionPassword
        mainActivity.textViewCaptionPassword.setText(state_action.concat("ion password"));

        // R.id.buttonPositive
        mainActivity.buttonPositive.setText(state_action);

        // R.id.checkBoxHash
        mainActivity.checkBoxHash.setText(String.format(mainActivity.getString(R.string.caption_hash), Constants.HASH_ALGORITHM));
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
