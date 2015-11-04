package com.hellotechie.passmaster;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.content.Context;

public class DisplaySitesListActivity extends Activity {

    DBHandler db;
    private static Context mContext;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getApplicationContext();
        Intent intent = getIntent();
        setContentView(R.layout.activity_list);
        db = new DBHandler(this.mContext);
        btnSubmit = (Button) findViewById(R.id.btnNewSite);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EnterNewSiteActivity.class);
                startActivity(intent);
            }
        });
    }
}