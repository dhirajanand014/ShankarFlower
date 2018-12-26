package aegismatrix.com.myapplication;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import androidx.core.content.ContextCompat;

public class NetworkChangeReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isOnline(context, intent)) {
            Snackbar connectedSB = Snackbar.make(((Activity) context).findViewById(android.R.id.content), "Connected to internet.", Snackbar.LENGTH_SHORT);
            // get snackbar view
            View mView = connectedSB.getView();
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
            mTextView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen));
// show the snackbar
            connectedSB.show();
            sendInternalBroadcast(context, true);
        } else {
            sendInternalBroadcast(context, false);

        }
    }

    /**
     * This method is responsible to send status by internal broadcast
     *
     * @param context
     * @param status
     */
    private void sendInternalBroadcast(Context context, boolean status) {
        try {
            Intent intent = new Intent();
            intent.putExtra("status", status);
            ObservableObject.getInstance().updateValue(intent);
        } catch (Exception ex) {
            Toast.makeText(context, "Cannot connect to internet", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Check if network available or not
     *
     * @param context
     * @param intent
     */
    public boolean isOnline(Context context, Intent intent) {
        boolean isOnline = false;
        try {
            //should check null because in airplane mode it will be null
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                isOnline = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
            }

        } catch (Exception ex) {
            Toast.makeText(context, "Cannot connect to internet", Toast.LENGTH_SHORT).show();
        }
        return isOnline;
    }
}
