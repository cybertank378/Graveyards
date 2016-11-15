package ubay.id.service;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ubay.id.app.AppConfig;
import ubay.id.app.AppController;
import ubay.id.helper.SQLiteHandler;
import ubay.id.model.UserData;
import ubay.id.ui.LoginActivity;

/**
 * Created              : Rahman on 10/26/2016.
 * Date Created         : 10/26/2016 / 4:32 PM.
 * ===================================================
 * Package              : ubay.id.service.
 * Project Name         : graveyard.
 * Copyright            : Copyright @ 2016 Indogamers.
 */
public class WebService {
    private AppController mAppController;
    private AppConfig mAppConfig;
    private String mDataApi = "";
    private Context mContext;
    private Activity mActivity;
    private int mAction;
    public static  final int POST_LOGIN = 1;
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "failed";
    private SQLiteHandler mDb;
    private UserData mUserData;


    public WebService(AppController appController, Context context) {
        mAppController = appController;
        mContext = context;
    }

    public void postLoginData(LoginActivity loginActivity){
        this.mActivity = loginActivity;
        mAction = WebService.POST_LOGIN;
        HttpTask task = new HttpTask();

    }




    class PostHttpTask extends AsyncTask<String, String, String>{

        boolean result = false;
        List<NameValuePair> params;
        String url;
        Map<String, Object> data = new HashMap<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            ((LoginActivity) mActivity).showDialog();
        }

        @Override
        protected String doInBackground(String... strings) {
            ServiceHandler sh = new ServiceHandler();
            String response = "";


            switch (mAction){
                case POST_LOGIN:
                    response = sh.makeServiceCall(url, ServiceHandler.POST, params);
                    if(response !=null){
                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean err = jObj.getBoolean("error");
                            if (!err){
                                JSONObject uObj = jObj.getJSONObject("data");
                                mDb = new SQLiteHandler(((LoginActivity) mActivity).getApplicationContext());
                                Log.d("DATA", "doInBackground: " + String.valueOf(mDb));
                                mUserData = mDb.getUserData();
                                if (mUserData == null){
                                    mUserData =  new UserData();
                                    mUserData.setUid(uObj.getInt("user_id"));
                                    mUserData.setUname(uObj.getString("user_login"));
                                    mUserData.setEmail(uObj.getString("user_email"));
                                    mDb.addUser(mUserData);
                                    mDb.close();
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            switch (mAction){
                case POST_LOGIN:
                    LoginActivity loginActivity = (LoginActivity) mActivity;

                    loginActivity.closeDialog();
            }
        }



    }

    class HttpTask extends AsyncTask<Void, Void, ArrayList>{

        private Context sContext;
        private String sUrl;
        private String sData;

        public Context getsContext() {
            return sContext;
        }

        public void setsContext(Context sContext) {
            this.sContext = sContext;
        }

        public String getsUrl() {
            return sUrl;
        }

        public void setsUrl(String sUrl) {
            this.sUrl = sUrl;
        }

        public String getsData() {
            return sData;
        }

        public void setsData(String sData) {
            this.sData = sData;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            switch (mAction){
                case POST_LOGIN:
                    LoginActivity loginActivity = (LoginActivity) mActivity;
                    loginActivity.showDialog();
                    break;
            }
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            super.onPostExecute(arrayList);
        }

        @Override
        protected ArrayList doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            String response = "";

            return null;
        }
    }
}
