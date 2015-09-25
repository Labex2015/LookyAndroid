package labex.feevale.br.looky.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.WakefulBroadcastReceiver;

import labex.feevale.br.looky.model.ChatResponse;
import labex.feevale.br.looky.utils.JsonUtils;

/**
 * Created by 0126128 on 18/12/2014.
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String typeString = extras.getString("type");
        Integer type = Integer.parseInt(typeString != null ? typeString : "0");
        if(type == GCMVariables.CHAT){
            Intent intentMSG = new Intent("Msg");
            String message = extras.getString("body");
            ChatResponse chatResponse = new JsonUtils().JsonToChatResponse(message);
            intentMSG.putExtra("CHAT", chatResponse);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intentMSG);
        }
        ComponentName componentName = new ComponentName(context.getPackageName(),
                GcmMessageHandler.class.getName());
        startWakefulService(context, (intent.setComponent(componentName)));
        setResultCode(Activity.RESULT_OK);
    }
}
