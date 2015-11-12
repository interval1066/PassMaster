package com.hellotechie.PassMaster;

/**
 * Created by bwana on 11/11/15.
 */
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.content.Intent;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;

/** Class Must extends with Dialog */
/** Implement onClickListener to dismiss dialog when OK Button is pressed */
public class LoginDlg extends Activity {
    Button okButton;
    private SharedPreferences sharedPreferences;
    private EditText password;
    Context context;
    private ConfigPW confDlg;
    private AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.login_main);

        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        confDlg = new ConfigPW(this);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        password = (EditText) findViewById(R.id.editText);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(getResources().getString(R.string.app_name))
                .setMessage(getResources().getString(R.string.pw_warning))
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        //MainActivity.this.finish();
                        confDlg.show();
                    }
                });
        alertDialog = alertDialogBuilder.create();

        Button btnSubmit;
        btnSubmit = (Button) findViewById(R.id.OkButton);
        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Main_Screen.class);
                String pw = sharedPreferences.getString("masterpw", "");

                if (pw.length() == 0)
                    alertDialog.show();
                else if (Crypt.crypt("zyxwpq", password.getText().toString()).compareTo(pw) == 0) {
                    startActivity(intent);
                    finish();
                }
                else {
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            }
        });
    }
}
