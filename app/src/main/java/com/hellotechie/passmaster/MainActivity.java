package com.hellotechie.passmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends Activity {

    private EditText password;
    private Context context;
    private AlertDialog alertDialog;
    private ConfigView confDlg;
    private SharedPreferences sharedPreferences;
    private DisplaySitesListActivity sites;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        setContentView(R.layout.content_main);

        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        confDlg = new ConfigView(this);
        //sites = new DisplaySitesListActivity();
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        password = (EditText) findViewById(R.id.txtPassword);
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
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySitesListActivity.class);
                String pw = sharedPreferences.getString("masterpw", "");

                if(pw.length() == 0)
                    alertDialog.show();
                else if(Crypt.crypt("zyxwpq", password.getText().toString()).compareTo(pw) == 0)
                    startActivity(intent);
                else {
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            }
        });
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplaySitesListActivity.class);
    }
}