package ubay.id.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rahman on 10/25/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "mobile_api";

    // Login table name
    private static final String TB_USER = "user";
    private static final String KEY_UID = "uid";
    private static final String KEY_UNAME = "uname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLEID = "role_id";


    private static final String TB_MAKAM = "makam";
    private static final String KEY_MID = "mid";
    private static final String KEY_MNAME = "mname";
    private static final String KEY_MBIN = "mbin";
    private static final String KEY_MLAHIR = "mlahir";
    private static final String KEY_MWAFAT = "mwafat";
    private static final String KEY_MMASUK = "mmasuk";
    private static final String KEY_MSTATUS = "mstatus";
    private static final String KEY_MIMAGE = "mimage";

    private static final String TB_WARIS = "waris";
    private static final String KEY_WID = "wid";
    private static final String KEY_WNAMA = "wnama";
    private static final String KEY_WALAMAT = "walamat";
    private static final String KEY_WTELP = "wtelp";


    private static final String TB_BLOK = "blok";
    private static final String KEY_BID = "bid";
    private static final String KEY_BNAMA = "bnama";





    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        onCreate(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
