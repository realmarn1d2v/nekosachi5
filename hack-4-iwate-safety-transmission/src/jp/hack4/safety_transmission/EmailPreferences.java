package jp.hack4.safety_transmission;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class EmailPreferences extends Activity {	
	public static ArrayAdapter<String> adapter = null;
	private EditText text = null;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_preferences);
        ListView list = (ListView)findViewById(R.id.email_list);
        if(adapter == null){
        	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        }
        list.setAdapter(adapter);
        
        text = (EditText) findViewById(R.id.email_input);
        
        text.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(((event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_ENTER))){
					adapter.add(text.getText().toString());
				}
				return false;
			}
		});
        
        Button btn = (Button) findViewById(R.id.email_save);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	adapter.add(text.getText().toString());
            }
        });
	}
	
}
