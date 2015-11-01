package com.hellotechie.passmaster;

import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.content.Context;

public class DisplaySitesListActivity extends AppCompatActivity {

    DBHandler db;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getApplicationContext();
        Intent intent = getIntent();
        setContentView(R.layout.activity_list);
        db = new DBHandler(this.mContext);
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        mContext = mContext;
    }
}