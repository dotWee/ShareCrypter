package de.dotwee.sharecrypter.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.dotwee.sharecrypter.R;
import de.dotwee.sharecrypter.presenter.MainPresenter;
import de.dotwee.sharecrypter.presenter.MainPresenterImpl;
import timber.log.Timber;

/**
 * Created by Lukas Wolfsteiner on 03.02.2016.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = "MainActivity";

    @Bind(R.id.dialogTitle)
    public TextView textViewDialogTitle;

    @Bind(R.id.textViewCaptionPassword)
    public TextView textViewCaptionPassword;

    @Bind(R.id.buttonPositive)
    public Button buttonPositive;

    @Bind(R.id.editTextPassword)
    public EditText editTextPassword;

    private MainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Applying activity layout
        setContentView(R.layout.activity_main);

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
}
