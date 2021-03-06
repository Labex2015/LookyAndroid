package labex.feevale.br.looky.utils;

import android.content.Context;
import android.content.SharedPreferences;

import labex.feevale.br.looky.model.User;
import labex.feevale.br.looky.service.utils.GCMVariables;

/**
 * Created by PabloGilvan on 27/12/2014.
 */
public class SharedPreferencesUtils {

    public static final String MY_SHARED_PREF = "UserShared";

    public static final String KEY_ID = "USER_ID";
    public static final String KEY_LATITUDE = "USER_LAT";
    public static final String KEY_LONGITUDE = "USER_LONGITUDE";
    public static final String KEY_USERNAME = "USER_USERNAME";
    public static final String KEY_NAME = "USER_NAME";
    public static final String KEY_ID_ACCOUNT = "KEY_ID_ACCOUNT";
    private static final String KEY_PICTURE_PATH = "KEY_PICTURE_PATH";
    public static final String USER_KEY = "USER_KEY";
    private static final String KEY_DESCRIPTION = "KEY_DESCRIPTION";
    private static final String KEY_DEGREE = "KEY_DEGREE";
    private static final String KEY_DEGREE_ID = "KEY_DEGREE_ID";
    private static final String KEY_SEMESTER = "KEY_SEMESTER";
    private static final String KEY_TOKEN = "KEY_TOKEN";
    private static final String KEY_DEVICE_KEY = "KEY_DEVICE_KEY";
    private static final String KEY_ACCOUNT_TYPE = "KEY_ACCOUNT_TYPE";
    private static final String KEY_PROFILE_STATUS = "KEY_PROFILE_STATUS";

    public static final String CHAT_CURRENT_ACTIVE = "CHAT_CURRENT_ACTIVE";
    private static final String PROFILE_STATUS = "PROFILE_STATUS";

    public void saveUser(Context context, User user) {
        SharedPreferences.Editor editor = returnMySharedPref(context).edit();
        editor.putLong(KEY_ID, user.getId() == null ? 0 : user.getId());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_PICTURE_PATH, user.getPicturePath());
        editor.putString(KEY_LATITUDE, user.getLatitude() + "");
        editor.putString(KEY_LONGITUDE, user.getLongitude() + "");
        editor.putString(KEY_ID_ACCOUNT, user.getAccountID());

        editor.putString(KEY_DESCRIPTION, user.getDescription());
        editor.putString(KEY_DEGREE, user.getDegree());
        editor.putLong(KEY_DEGREE_ID, user.getDegreeID() == null ? 0L : user.getDegreeID());
        editor.putInt(KEY_SEMESTER, user.getSemester() == null ? 0 : user.getSemester());
        editor.putString(KEY_ACCOUNT_TYPE, user.getAccountType());
        editor.putString(KEY_PROFILE_STATUS, String.valueOf(user.getProfileStatus()));
        editor.commit();
    }

    public void saveItemToLoad(Context context, int item) {
        SharedPreferences.Editor editor = returnMySharedPref(context).edit();
        editor.putInt(GCMVariables.ITEM_TO_LOAD, item);
        editor.commit();
    }

    public void clearItemToLoad(Context context) {
        saveItemToLoad(context, 0);
    }

    public int getItemToLoad(Context context){
        SharedPreferences preferences = returnMySharedPref(context);
        return preferences.getInt(GCMVariables.ITEM_TO_LOAD, 0);
    }

    public SharedPreferences returnMySharedPref(Context context) {
        return context.getSharedPreferences(MY_SHARED_PREF, Context.MODE_PRIVATE);
    }

    public Boolean checkMyPrefs(Context context) {
        SharedPreferences preferences = returnMySharedPref(context);
        Long id = preferences.getLong(KEY_ID, 0);
        if (id > 0)
            return true;
        else
            return false;
    }

    public User getUser(Context context) {
        SharedPreferences preferences = returnMySharedPref(context);
        if (checkMyPrefs(context)) {
            User user = new User();
            user.setId(preferences.getLong(KEY_ID, 0));
            user.setUsername(preferences.getString(KEY_USERNAME, ""));
            try {
                user.setLatitude(Float.parseFloat(preferences.getString(KEY_LATITUDE, "0")));
                user.setLongitude(Float.parseFloat(preferences.getString(KEY_LONGITUDE, "0")));
                user.setDegreeID(preferences.getLong(KEY_DEGREE_ID, 0L));
                user.setSemester(preferences.getInt(KEY_SEMESTER, 0));
            }catch (Exception e){
                user.setLatitude(0f);
                user.setLongitude(0f);
            }
            user.setName(preferences.getString(KEY_NAME, ""));
            user.setPicturePath(preferences.getString(KEY_PICTURE_PATH, ""));
            user.setAccountID(preferences.getString(KEY_ID_ACCOUNT, ""));
            user.setDescription(preferences.getString(KEY_DESCRIPTION, ""));
            user.setDegree(preferences.getString(KEY_DEGREE, ""));
            user.setAccountType(preferences.getString(KEY_ACCOUNT_TYPE, ""));
            user.setProfileStatus(preferences.getString(KEY_PROFILE_STATUS, "P").charAt(0));

            return user;
        }else{
           return null;
        }
    }

    public void clear(Context context){
        SharedPreferences.Editor editor = returnMySharedPref(context).edit();
        editor.clear();
        editor.commit();
    }

    public Boolean isChatActive(Context context){
        SharedPreferences preferences = returnMySharedPref(context);
        return preferences.getBoolean(CHAT_CURRENT_ACTIVE, false);
    }

    public void saveChatStatus(Boolean status, Context context){
        SharedPreferences.Editor editor = returnMySharedPref(context).edit();
        editor.putBoolean(CHAT_CURRENT_ACTIVE, status);
        editor.commit();
    }

    public void saveUserKey(String s, Context context) {
        SharedPreferences.Editor editor = returnMySharedPref(context).edit();
        editor.putString(USER_KEY, s);
        editor.commit();
    }

    public String getUserKey(Context context) {
        SharedPreferences preferences = returnMySharedPref(context);
        return preferences.getString(USER_KEY, null);
    }

    public void saveToken(String token, Context context) {
        SharedPreferences.Editor editor = returnMySharedPref(context).edit();
        editor.putString(KEY_TOKEN, token);
        editor.commit();
    }

    public String getToken(Context context) {
        SharedPreferences preferences = returnMySharedPref(context);
        return preferences.getString(KEY_TOKEN, null);
    }

    public void saveState(boolean b, Context context) {
        SharedPreferences.Editor editor = returnMySharedPref(context).edit();
        editor.putBoolean(PROFILE_STATUS, b);
        editor.commit();
    }

    public Boolean getState(Context context) {
        SharedPreferences preferences = returnMySharedPref(context);
        return preferences.getBoolean(PROFILE_STATUS, false);
    }
}
