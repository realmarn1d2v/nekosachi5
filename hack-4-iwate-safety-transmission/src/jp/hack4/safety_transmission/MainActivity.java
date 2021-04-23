package jp.hack4.safety_transmission;

import java.util.List;
import java.util.Properties;

import org.apache.http.cookie.SM;

import android.app.Activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MainActivity extends Activity implements LocationListener {
    private LocationManager mgr;
    private StringBuffer idokeido = new StringBuffer(100);
    private final static String DB_TABLE="bujidesu";   //テーブル名
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //データベース参照
    	SQLiteDatabase db;
    	DatabaseHelper hlpr = new DatabaseHelper(getApplicationContext());
    	db = hlpr.getReadableDatabase();

    	Cursor c=db.query(DB_TABLE,new String[]{"id","value","sort"},
                "id='gmailaddress'",null,null,null,null);
        if (c.getCount()!=0){
                c.moveToFirst();
                GmailPreferences.gmailaddress=(c.getString(1));
        }
        c.close();
        
        c=db.query(DB_TABLE,new String[]{"id","value","sort"},
                "id='gmailpass'",null,null,null,null);
        if (c.getCount()!=0){
                c.moveToFirst();
                GmailPreferences.gmailpass=(c.getString(1));
        }
        c.close();
        
        c=db.query(DB_TABLE,new String[]{"id","value","sort"},
                "id='sms'",null,null,null,null);
        SMSPreferences.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        if (c.getCount()!=0){
                c.moveToFirst();
                for (int i = 0; i < c.getCount(); i++) {
                	SMSPreferences.adapter.add(c.getString(1));
                    c.moveToNext();
                }
        }
        c.close();
     
        c=db.query(DB_TABLE,new String[]{"id","value","sort"},
                "id='email'",null,null,null,null);
        EmailPreferences.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        if (c.getCount()!=0){
                c.moveToFirst();
                for (int i = 0; i < c.getCount(); i++) {
                	EmailPreferences.adapter.add(c.getString(1));
                    c.moveToNext();
                }
        }
        c.close();
        
        
        /* 位置情報の取得 */  
		 // ロケーションマネージャの取得  
		 LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);  
		 // 最適な位置情報プロバイダの選択  
		 // Criteriaを変更することで，各種設定変更可能  
		 String bs = lm.getBestProvider(new Criteria(), true);  
		   
		 Location locate = lm.getLastKnownLocation(bs);  
		 if(locate == null){  
		  // 現在地が取得できなかった場合，GPSで取得してみる  
		  locate = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);  
		 }  
		 if(locate == null){  
		  // 現在地が取得できなかった場合，無線測位で取得してみる  
		  locate = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);  
		 }  
		 
		 if(locate != null){ // 現在地情報取得成功  
		  // 緯度の取得  
		  int latitude = (int) (locate.getLatitude() * 1e6);  
		  // 経度の取得  
		  int longitude = (int) (locate.getLongitude() * 1e6);  
		  Log.d("MYTAG", String.valueOf(latitude));  
		  Log.d("MYTAG", String.valueOf(longitude));
		  idokeido.append("Lat:" + locate.getLatitude() + ", Long:" + locate.getLongitude()).toString(); 

		 } else {  
		  /* 現在地情報取得失敗処理 */
			 // 端末の位置情報設定画面へ遷移  
			 try {  
			  startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));  
			 } catch (final ActivityNotFoundException e) {  
			  // 位置情報設定画面がない端末の場合  
			 } 
			 
		 }
		 
		
        Button button = (Button)findViewById(R.id.save_button);
        button.setOnClickListener(new View.OnClickListener() {	
        	
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText text = (EditText) findViewById(R.id.editText1);
			    String msg = text.getText().toString() + " 現在地は: " + idokeido;
			    Log.d("TEST","Enter here2");
                // send SMS here
			    if (SMSPreferences.adapter != null) {
			        int sms_size = SMSPreferences.adapter.getCount();
			        SMS sms = new SMS();
			        for (int i = 0 ; i < sms_size; i++) {
			            String number = SMSPreferences.adapter.getItem(i);
			            sms.send(number, msg);
			        }
			    }

				// Send Mail
			    if (EmailPreferences.adapter != null) {
				    Properties props = new Properties();
				    props.put("mail.smtp.host", "smtp.gmail.com"); // SMTPサーバ名
				    props.put("mail.host", "smtp.gmail.com");      // 接続するホスト名
				    props.put("mail.smtp.port", "587");       // SMTPサーバポート
				    props.put("mail.smtp.auth", "true");    // smtp auth
				    props.put("mail.smtp.starttls.enable", "true");	// STTLS
				    // セッション
				    Session session = Session.getDefaultInstance(props);
				    session.setDebug(true);
				    MimeMessage emailmsg = new MimeMessage(session);
				    try {
				    	emailmsg.setSubject("ブジだす君", "utf-8");
				    	emailmsg.setFrom(new InternetAddress(GmailPreferences.gmailaddress));
				    	emailmsg.setSender(new InternetAddress(GmailPreferences.gmailaddress));
				    	emailmsg.setText(msg,	"utf-8");

				    	Transport t = session.getTransport("smtp");
				    	t.connect(GmailPreferences.gmailaddress, GmailPreferences.gmailpass);

				    	int email_size = EmailPreferences.adapter.getCount();
				        for (int i = 0 ; i < email_size; i++) {
					    	emailmsg.setRecipient(Message.RecipientType.TO, new InternetAddress(EmailPreferences.adapter.getItem(i)));
					    	t.sendMessage(emailmsg, emailmsg.getAllRecipients());
				        }

				    
				    } catch (MessagingException e) {
				    	e.printStackTrace();
				    }

			    	
 
			    }
			    text.setText("");
			}
		});
    }

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}


	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ret = false;
        switch(item.getItemId()) {
        case R.id.menu_preferences:
            Intent intent = new Intent(getApplicationContext(), PreferenceActivity.class);
            startActivity(intent);
            ret = true;
            break;
        default:
            ret = super.onOptionsItemSelected(item);
        }
        return ret;
    }


	
	


}