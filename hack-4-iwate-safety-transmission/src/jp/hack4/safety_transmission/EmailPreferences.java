package jp.hack4.safety_transmission;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EmailPreferences extends Activity {	
	public static ArrayAdapter<String> adapter = null;
	private EditText text = null;
    private final static String DB_TABLE="bujidesu";   //テーブル名
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_preferences);
        ListView list = (ListView)findViewById(R.id.email_list);
        if(adapter == null){
        	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        }
        list.setAdapter(adapter);
        
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    	    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
        		ListView list = (ListView)parent;
        		String item = (String)list.getItemAtPosition(position);
        		ArrayAdapter<String> adapter = (ArrayAdapter<String>)list.getAdapter();
        		adapter.remove(item);
				return false;
        	} 
    	});
        
        text = (EditText) findViewById(R.id.email_input);
        
        text.setOnKeyListener(new View.OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(((event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_ENTER))){
					adapter.add(text.getText().toString());
					text.setText("");
				}
				return false;
			}
		});
        
        Button btn = (Button) findViewById(R.id.email_save);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	adapter.add(text.getText().toString());
            	text.setText("");
            }
        });
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
	    if(keyCode == KeyEvent.KEYCODE_BACK){
	    	Log.d("TEST","KEYCODE_BACK here");
	    	
	    	SQLiteDatabase db;
	    	DatabaseHelper hlpr = new DatabaseHelper(getApplicationContext());
	    	db = hlpr.getWritableDatabase();
	    	
	    	//データベース更新
	        db.delete(DB_TABLE,"id='email'",null);

		    if (adapter != null) {
		        int sms_size = adapter.getCount();
		        for (int i = 0 ; i < sms_size; i++) {
		            String number = adapter.getItem(i);
			        ContentValues values=new ContentValues();
			        values.put("id","email");
			        values.put("value",number);
			        values.put("sort",i);
			        db.insert(DB_TABLE,"",values);
		        }
		    }

	    }
		return super.onKeyDown(keyCode, event);
	}
}
