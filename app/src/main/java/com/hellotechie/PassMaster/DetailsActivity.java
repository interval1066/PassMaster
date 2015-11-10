package com.hellotechie.PassMaster;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.util.Log;

public class DetailsActivity extends Activity {
    Button backBtn;
    DatabaseHandler db;

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
    private void getSiteDetails(String name) {
        db = new DatabaseHandler(this);
        Site site = db.Get_Site_By_Name(name);
        if(site != null) Log.d("Site", site.getUrl());
    }
}
