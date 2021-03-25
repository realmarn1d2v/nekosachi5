package jp.hack4.safety_transmission;

import java.util.List;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements LocationListener {
    private LocationManager mgr;
    
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
				Log.d("TEST","Enter here2");

												
			    Intent intent = new Intent();  
			    // アクションを指定  
			    intent.setAction(Intent.ACTION_SENDTO);  
			    // データを指定  
			    intent.setData(Uri.parse("mailto:kawamurh@gmail.com"));  
			    // 件名を指定  
			    intent.putExtra(Intent.EXTRA_SUBJECT, "件名");  
			    // 本文を指定  
			    intent.putExtra(Intent.EXTRA_TEXT, "本文の内容");  
			    // Intentを発行  
			    startActivity(intent);  
			    
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


}