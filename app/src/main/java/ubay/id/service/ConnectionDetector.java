package ubay.id.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created              : Rahman on 10/26/2016.
 * Date Created         : 10/26/2016 / 6:25 PM.
 * ===================================================
 * Package              : ubay.id.service.
 * Project Name         : graveyard.
 * Copyright            : Copyright @ 2016 Indogamers.
 */
public class ConnectionDetector {

    public static boolean isConnectionAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}
