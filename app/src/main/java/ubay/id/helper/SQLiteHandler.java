package ubay.id.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ubay.id.model.UserData;

/**
 * Created              : Rahman on 10/26/2016.
 * Date Created         : 10/26/2016 / 2:07 PM.
 * ===================================================
 * Package              : ubay.id.helper.
 * Project Name         : graveyard.
 * Copyright            : Copyright @ 2016 Indogamers.
 */
public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "mobile_api";

    // Login table name
    private static final String TB_USER = "user";
    private static final String TB_MAKAM = "makam";
    private static final String TB_WARIS = "waris";

    //global key
    private static final String KEY_ID = "id";
    //table user key
    private static final String KEY_UID = "uid";
    private static final String KEY_UNAME = "uname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLEID = "role_id";
    // table makam key
    private static final String KEY_MID = "mid";
    private static final String KEY_MNAME = "mname";
    private static final String KEY_MBIN = "mbin";
    private static final String KEY_MLAHIR = "mlahir";
    private static final String KEY_MWAFAT = "mwafat";
    private static final String KEY_MMASUK = "mmasuk";
    private static final String KEY_MSTATUS = "mstatus";
    private static final String KEY_MIMAGE = "mimage";
    //table waris key
    private static final String KEY_WID = "wid";
    private static final String KEY_WNAMA = "wnama";
    private static final String KEY_WALAMAT = "walamat";
    private static final String KEY_WTELP = "wtelp";


    private static final String TB_BLOK = "blok";
    private static final String KEY_BID = "bid";
    private static final String KEY_BNAMA = "bnama";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TB_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_UID + " INTEGER,"
                + KEY_UNAME + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_ROLEID + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //memhapus semua table jika ada
        db.execSQL("DROP TABLE IF EXISTS " + TB_USER );
        db.execSQL("DROP TABLE IF EXISTS " + TB_MAKAM );
        db.execSQL("DROP TABLE IF EXISTS " + TB_WARIS );
        db.execSQL("DROP TABLE IF EXISTS " + TB_USER );

        //membuat table kembali
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(UserData userData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_UID, userData.getUid()); // Email
        values.put(KEY_UNAME, userData.getUname()); // Name
        values.put(KEY_EMAIL, userData.getEmail()); // Email
        values.put(KEY_ROLEID, userData.getRole_id()); // Created At

        // Inserting Row
        long id = db.insert(TB_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }


    /**
     * Getting user data from database
     * */
    public UserData getUserData() {
        String selectQuery = "SELECT  * FROM " + TB_USER + " LIMIT 1";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        if (cursor !=null){
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                UserData mUserData = new UserData();
                mUserData.setId(cursor.getInt(0));
                mUserData.setUid(cursor.getInt(1));
                mUserData.setUname(cursor.getString(2));
                mUserData.setEmail(cursor.getString(3));
                mUserData.setRole_id(cursor.getString(4));

                return mUserData;
            }
        }

        cursor.close();
        db.close();

        return null;
    }


}
