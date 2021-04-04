package jp.hack4.safety_transmission;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class SMSPreferences extends Activity {	
	public static ArrayAdapter<String> adapter = null;
	private EditText text = null;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_preferences);
        ListView list = (ListView)findViewById(R.id.sms_list);
        if(adapter == null){
        	adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        }
        list.setAdapter(adapter);
        
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        	@Override
    	    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
        		ListView list = (ListView)parent;
        		String item = (String)list.getItemAtPosition(position);
        		ArrayAdapter<String> adapter = (ArrayAdapter<String>)list.getAdapter();
        		adapter.remove(item);
				return false;
        	} 
    	});
        
        text = (EditText) findViewById(R.id.sms_input);
        
        text.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(((event.getAction() == KeyEvent.ACTION_UP) && (keyCode == KeyEvent.KEYCODE_ENTER))){
					adapter.add(text.getText().toString());
					text.setText("");
				}
				return false;
			}
		});
        
        Button btn = (Button) findViewById(R.id.sms_save);
        btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	adapter.add(text.getText().toString());
            	text.setText("");
            }
        });
	}
	
}
