package com.hellotechie.PassMaster;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailsActivity extends Activity {
    private Button backBtn;
    private DatabaseHandler db;
    private TextView name, url, usr, pw, desc, type;
    private SharedPreferences sharedPreferences;
    private AES aes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);

        backBtn = (Button) findViewById(R.id.back_main);
        Intent sender = getIntent();
        String extraData = sender.getExtras().getString("Site");
        getSiteDetails(extraData);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent details_user = new Intent(DetailsActivity.this,
                        Main_Screen.class);
                details_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(details_user);
                finish();
            }
        });
    }
    private void getSiteDetails(String nm) {
        try {
            db = new DatabaseHandler(this);
            AES aes = new AES();
            String pass = sharedPreferences.getString("masterpw", "");
            Site site = db.Get_Site_By_Name(nm);

            name = (TextView) findViewById(R.id.show_name);
            name.setText(site.getName());
            url = (TextView) findViewById(R.id.show_url);

            url.setText(site.getUrl());
            usr = (TextView) findViewById(R.id.show_user);
            usr.setText(site.getUser());

            pw = (TextView) findViewById(R.id.show_pw);
            pw.setText(aes.decrypt(site.getPw(), pass));
            desc = (TextView) findViewById(R.id.show_desc);

            desc.setText(site.getDesc());
            type = (TextView) findViewById(R.id.show_type);
            type.setText(site.getType());
        }
        catch (Exception e) {
            Log.d("Error: ", e.toString());
        }
    }
}
