package de.dotwee.sharecrypter.view;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.dotwee.sharecrypter.R;
import de.dotwee.sharecrypter.presenter.MainPresenter;
import de.dotwee.sharecrypter.presenter.MainPresenterImpl;
import timber.log.Timber;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, PermissionListener {
    private static final String LOG_TAG = "MainActivity";

    @Bind(R.id.dialogTitle)
    public TextView textViewDialogTitle;

    @Bind(R.id.textViewCaptionPassword)
    public TextView textViewCaptionPassword;

    @Bind(R.id.buttonPositive)
    public Button buttonPositive;

    @Bind(R.id.editTextPassword)
    public EditText editTextPassword;

    @Bind(R.id.checkBoxHash)
    public CheckBox checkBoxHash;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Applying activity layout
        setContentView(R.layout.activity_main);

        // Check for permission
        Dexter.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        Dexter.checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        ButterKnife.bind(this);
        Timber.tag(LOG_TAG);

        Timber.i("MainActivity created. Binding presenter...");
        mainPresenter = MainPresenterImpl.bind(this);

        int[] clickableViewIds = new int[]{
                R.id.buttonNegative,
                R.id.buttonPositive
        };

        for (int clickableViewId : clickableViewIds) {
            findViewById(clickableViewId)
                    .setOnClickListener(this);
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);

        textViewDialogTitle.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(R.id.dialogTitle);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.buttonPositive:
                mainPresenter.onButtonPositive();
                break;

            case R.id.buttonNegative:
                mainPresenter.onButtonNegative();
                break;

        }
    }

    /**
     * Method called whenever a requested permission has been granted
     *
     * @param response A response object that contains the permission that has been requested and
     *                 any additional flags relevant to this response
     */
    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {

    }

    /**
     * Method called whenever a requested permission has been denied
     *
     * @param response A response object that contains the permission that has been requested and
     *                 any additional flags relevant to this response
     */
    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {
        Toast.makeText(this, getString(R.string.message_permission_necessary), Toast.LENGTH_LONG).show();
        this.finish();
    }

    /**
     * Method called whenever Android asks the application to inform the user of the need for the
     * requested permission. The request process won't continue until the token is properly used
     *
     * @param permission The permission that has been requested
     * @param token      Token used to continue or cancel the permission request process. The permission
     */
    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

    }
}
