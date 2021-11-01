package com.example.publicohelpline.Databases;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences userSession;
    SharedPreferences.Editor editor;
    Context context;

    //session names
    public static final String SESSION_USER_SESSION = "userLoginSession";
    public static final String SESSION_REMEMBER_ME_SESSION = "userRememberMeSession";


//user session variables
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_FULL_NAME = "fullName";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_CONSTITUENCY = "constituency";
    public static final String KEY_USER_TYPE = "userType";

    //remember me variables
    private static final String IS_REMEMBER_ME = "IsRememberMe";
    public static final String KEY_SESSION_FULL_NAME = "fullName";
    public static final String KEY_SESSION_PASSWORD = "password";

     //user login session
    //constructor
    public SessionManager(Context _context,String sessionName) {
        context = _context;
        userSession = context.getSharedPreferences(sessionName, Context.MODE_PRIVATE);
        // every time this is called and executed and check whether session with the name is created if not created it will be created
        editor = userSession.edit();
    }

    public void create_login_session(String fullName, String email, String password, String phone, String constituency, String userType) {

        editor.putBoolean(IS_LOGIN, true);//key value pair key can be used to get stored values
        editor.putString(KEY_FULL_NAME, fullName);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_PHONE, phone);
        editor.putString(KEY_CONSTITUENCY, constituency);
        editor.putString(KEY_USER_TYPE, userType);

        editor.commit();//session created

    }

    public HashMap<String, String> getUserDataFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_FULL_NAME, userSession.getString(KEY_FULL_NAME, null));//adds data to hash map
        userData.put(KEY_EMAIL, userSession.getString(KEY_EMAIL, null));
        userData.put(KEY_PASSWORD, userSession.getString(KEY_PASSWORD, null));
        userData.put(KEY_PHONE, userSession.getString(KEY_PHONE, null));
        userData.put(KEY_CONSTITUENCY, userSession.getString(KEY_CONSTITUENCY, null));
        userData.put(KEY_USER_TYPE, userSession.getString(KEY_USER_TYPE, null));

        return userData;
    }

    public Boolean check_loin() {
        if (userSession.getBoolean(IS_LOGIN, false)) {
            return true;
        } else {
            return false;
        }

    }

    public void log_out_user_from_session() {
        editor.clear();
        editor.commit();

    }


    //remember me session
    public void create_remember_me_session(String fullName,String password) {

        editor.putBoolean(IS_REMEMBER_ME, true);//key value pair key can be used to get stored values
        editor.putString(KEY_SESSION_FULL_NAME, fullName);
        editor.putString(KEY_SESSION_PASSWORD, password);
        editor.commit();//REMEMBER ME session created

    }

    public HashMap<String, String> getRememberMeFromSession() {
        HashMap<String, String> userData = new HashMap<String, String>();

        userData.put(KEY_SESSION_FULL_NAME, userSession.getString(KEY_SESSION_FULL_NAME, null));//adds data to hash map
        userData.put(KEY_SESSION_PASSWORD, userSession.getString(KEY_SESSION_PASSWORD, null));

        return userData;
    }

    public Boolean check_remember_me() {
        if (userSession.getBoolean(IS_REMEMBER_ME, false)) {
            return true;
        } else {
            return false;
        }

    }


}
