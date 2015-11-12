package com.hellotechie.PassMaster;

import android.app.Dialog;
import android.content.Context;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by bwana on 11/12/15.
 */
public class ConfigPW extends Dialog implements OnClickListener {
    private Button okButton;
    private EditText pwField;
    private Context context;
    private SharedPreferences prefs;

    public ConfigPW(Context context) {
        super(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.config_pw);
        pwField = (EditText) findViewById(R.id.editText2);
        okButton = (Button) findViewById(R.id.button5);
        okButton.setOnClickListener(this);
        loadSavedPreferences();
    }

    @Override
    public void onClick(View v) {
        if (v == okButton) {
            String strPw = Crypt.crypt("zyxwpq", pwField.getText().toString());
            savePreferences("masterpw", strPw);
            dismiss();
        }
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
}
