package labex.feevale.br.looky.utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import labex.feevale.br.looky.model.User;

/**
 * Created by Vitor/Thaiane on 12/12/2014.
 */
public class PreferencesHelp {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Context context;
    User user;

    private static final String KEY_SHARED_PREF = "ANDROID_WEB_CHAT";
    private static final int KEY_MODE_PRIVATE = 0;
    private static final String KEY_SESSION_ID = "sessionId",
            FLAG_MESSAGE = "message";

    public PreferencesHelp(Context context){
        this.context = context;
        final String MyPREFERENCES = "LookyPreferences" ;
        sp = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public void setUserToPreferences( User user){
        editor = sp.edit();
        editor.putLong("id", user.getId());
        editor.putString("userName", user.getUsername());
        //editor.putString("email", user.get());
        editor.putString("latitude", String.valueOf(user.getLatitude()));
        editor.putString("longitude", String.valueOf(user.getLongitude()));
        editor.apply();
    }

    public User getUserToPreferences(){
        Long id             = sp.getLong("id", 1);//TODO alterar valor padrão
        String name         = sp.getString("userName", null);
        String email        = sp.getString("email", null);
        double latitude     = 0d;
        double longitude    = 0d;
        try {
            latitude     = Double.parseDouble(sp.getString("latitude", "0"));
            longitude    = Double.parseDouble(sp.getString("longitude", "0"));
        }catch (Exception e){
            latitude     = 0d;
            longitude    = 0d;
        }
        user = new User();

        return user;
    }

    public void SetPositionUser(double longitude, double latitude){

        editor = sp.edit();
        editor.putString("latitude", String.valueOf(longitude));
        editor.putString("longitude", String.valueOf(latitude));
        editor.apply();
    }

    public void storeSessionId(String sessionId) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(KEY_SESSION_ID, sessionId);
        editor.commit();
    }

    public String getSessionId() {
        return sp.getString(KEY_SESSION_ID, null);
    }

    public String getSendMessageJSON(String message) {
        String json = null;

        try {
            JSONObject jObj = new JSONObject();
            jObj.put("flag", FLAG_MESSAGE);
            jObj.put("sessionId", getSessionId());
            jObj.put("message", message);

            json = jObj.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
