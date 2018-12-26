package aegismatrix.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Observable;
import java.util.Observer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SFSplashScreen extends AppCompatActivity implements Observer {
    private ProgressBar progressBar;
    private NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        ObservableObject.getInstance().addObserver(this);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    public void animateSlideLeft(Context context) {
        ((SFSplashScreen) context).overridePendingTransition(R.anim.animate_slide_left_enter, R.anim.animate_slide_left_exit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void update(Observable observable, Object intent) {
        if (null != intent) {
            if (((Intent) intent).getBooleanExtra("status", Boolean.TRUE)) {
                Intent mainIntent = new Intent(SFSplashScreen.this, MainActivity.class);
                startActivity(mainIntent);
                animateSlideLeft(this);
                finish();
            } else {
                Snackbar NICSnackbbar = Snackbar.make(findViewById(android.R.id.content), "Check internet connection!!", Snackbar.LENGTH_INDEFINITE);
                // get snackbar view
                View mView = NICSnackbbar.getView();
                Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) mView;

                layout.setPadding(0, 0, 0, 0);//set padding to 0
// get textview inside snackbar view
                TextView mTextView = mView.findViewById(com.google.android.material.R.id.snackbar_text);
// set text to center
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                } else {
                    mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
                }
                mTextView.setBackgroundColor(ContextCompat.getColor(SFSplashScreen.this, R.color.design_default_color_error));
// show the snackbar
                NICSnackbbar.show();
            }
        }
    }
}
