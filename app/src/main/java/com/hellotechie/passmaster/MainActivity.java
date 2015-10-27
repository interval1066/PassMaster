package com.hellotechie.passmaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;
import java.util.Properties;
import android.app.AlertDialog;
import android.content.DialogInterface;
//import android.widget.Toast;
//import com.hellotechie.passmaster.DisplaySitesListActivity;

public class MainActivity extends Activity {

    private EditText password;
    private Context context;
    private Properties properties;
    private AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        PropertyReader propertyReader;
        setContentView(R.layout.content_main);
        propertyReader = new PropertyReader(context);
        properties = propertyReader.getMyProperties("app.properties");
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
                                MainActivity.this.finish();
                            }
                        });
        alertDialog = alertDialogBuilder.create();

        Button btnSubmit;
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DisplaySitesListActivity.class);
                //if(password.getText().toString() == "")
                    alertDialog.show();

                /*if(password.getText().toString() == properties.getProperty("master_pw"))
                    startActivity(intent);
                else {
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }*/
            }
        });
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplaySitesListActivity.class);
    }
}