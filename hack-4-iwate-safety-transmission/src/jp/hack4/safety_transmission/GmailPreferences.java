package jp.hack4.safety_transmission;

import jp.hack4.safety_transmission.R.string;
import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class GmailPreferences extends Activity {	
	public static String gmailaddress = "";
	public static String gmailpass = "";
	private EditText text = null;
    private final static String DB_TABLE="bujidesu";   //テーブル名
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gmail_preferences);

        
        text = (EditText) findViewById(R.id.gmail_input);
        text.setText(gmailaddress);
        text.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(((event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_ENTER))){
					//adapter.add(text.getText().toString());
				}
				return false;
			}
		});

        text = (EditText) findViewById(R.id.gmailpass_input);
        text.setText(gmailpass);
        text.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(((event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_ENTER))){
					//adapter.add(text.getText().toString());
				}
				return false;
			}
		});
        
        Button btn = (Button) findViewById(R.id.gmail_save);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	//adapter.add(text.getText().toString());
            }
        });
	
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
	    if(keyCode == KeyEvent.KEYCODE_BACK){
	    	Log.d("TEST","KEYCODE_BACK here");

	    	text = (EditText) findViewById(R.id.gmail_input);
	    	gmailaddress=text.getText().toString();

	    	text = (EditText) findViewById(R.id.gmailpass_input);
	    	gmailpass=text.getText().toString();
	    	
	    	SQLiteDatabase db;
	    	DatabaseHelper hlpr = new DatabaseHelper(getApplicationContext());
	    	db = hlpr.getWritableDatabase();
	    	
	    	//データベース更新
	        db.delete(DB_TABLE,"id='gmailaddress'",null);
			ContentValues values=new ContentValues();
	        values.put("id","gmailaddress");
	        values.put("value",gmailaddress);
	        values.put("sort",0);
	        db.insert(DB_TABLE,"",values);

	        db.delete(DB_TABLE,"id='gmailpass'",null);
			values=new ContentValues();
	        values.put("id","gmailpass");
	        values.put("value",gmailpass);
	        values.put("sort",0);
	        db.insert(DB_TABLE,"",values);

	    }
		return super.onKeyDown(keyCode, event);
	}

}
