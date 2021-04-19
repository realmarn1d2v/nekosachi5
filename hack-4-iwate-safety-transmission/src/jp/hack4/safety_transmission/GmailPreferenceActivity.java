package jp.hack4.safety_transmission;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class GmailPreferenceActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.gmail_preference);
    }
    
    
}
