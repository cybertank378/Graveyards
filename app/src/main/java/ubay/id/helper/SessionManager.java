package ubay.id.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Created              : Rahman on 10/26/2016.
 * Date Created         : 10/26/2016 / 1:38 PM.
 * ===================================================
 * Package              : ubay.id.helper.
 * Project Name         : graveyard.
 * Copyright            : Copyright @ 2016 Indogamers.
 */
public class SessionManager {
    //LogCat Tag
    private static String TAG = SessionManager.class.getSimpleName();
    SharedPreferences mSharedPreferences;
    Editor mEditor;
    Context mContext;
    int PRIVATE_MODE = 0;

    //shared preference file name;
    private static final String PREF_NAME ="MakamKeramatData";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    public SessionManager(Context context){
        this.mContext = context;
        mSharedPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSharedPreferences.edit();

    }

    public void setLogin(boolean isLoggedIn) {

        mEditor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn);

        // commit changes
        mEditor.commit();

        Log.d(TAG, "User login session modified!");
    }

    public boolean isLoggedIn(){
        return mSharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }
}
