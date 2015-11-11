package com.hellotechie.PassMaster;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

public class DetailsActivity extends Activity {
    Button backBtn;
    DatabaseHandler db;
    TextView name, url, usr, pw, desc, type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

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
        Log.d("getSiteDetails: ", "Entering getSiteDetails");
        db = new DatabaseHandler(this);
        Site site = db.Get_Site_By_Name(nm);
        name = (TextView) findViewById(R.id.show_name);
        name.setText(site.getName());
        url = (TextView) findViewById(R.id.show_url);
        url.setText(site.getUrl());
        usr = (TextView) findViewById(R.id.show_user);
        usr.setText(site.getUser());
        pw = (TextView) findViewById(R.id.show_pw);
        pw.setText(site.getPw());
        desc = (TextView) findViewById(R.id.show_desc);
        desc.setText(site.getDesc());
        type = (TextView) findViewById(R.id.show_type);
        type.setText(site.getType());
        Log.d("getSiteDetails: ", pw.getText().toString());
    }
}
