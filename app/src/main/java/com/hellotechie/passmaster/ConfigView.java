package com.hellotechie.passmaster;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;

public class ConfigView extends Dialog implements OnClickListener {
    Button okButton;
    EditText pwField;
    private Context context;
    private SharedPreferences prefs;

    public ConfigView(Context context) {
        super(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.config_view);
        pwField = (EditText) findViewById(R.id.masterinput);
        okButton = (Button) findViewById(R.id.pwSubmit);
        okButton.setOnClickListener(this);
        loadSavedPreferences();
        //if(pw != null)
        //pwField.setText(pw);
    }

    private void loadSavedPreferences() {
        String pw = prefs.getString("masterpw", "");
        pwField.setText(pw);
    }

    private void savePreferences(String key, String val) {
        Editor editor = prefs.edit();
        editor.putString(key, val);
        editor.commit();
    }

    @Override
    public void onClick(View v) {
/** When OK Button is clicked, dismiss the dialog */
        if (v == okButton) {
            String strPw = Crypt.crypt("zyxwpq", pwField.getText().toString());
            savePreferences("masterpw", strPw);
            dismiss();
        }
    }
}