package jp.hack4.safety_transmission;

import android.telephony.SmsManager;
import android.util.Log;

public class SMS {
	
	public void send(String number, String msg){ 
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, msg, null, null);
        Log.d("SMS", "sent message: " + msg);
	}

}
