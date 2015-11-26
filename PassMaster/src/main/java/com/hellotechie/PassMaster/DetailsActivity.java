package com.hellotechie.PassMaster;

import android.app.Activity;
import android.os.AsyncTask;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends Activity {
    private Button backBtn;
    private DatabaseHandler db;
    private TextView name, url, usr, desc, type;
    public TextView pw;
    private SharedPreferences sharedPreferences;

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
            AsyncTaskRunner runner = new AsyncTaskRunner();
            db = new DatabaseHandler(this);

            String pass = sharedPreferences.getString("masterpw", "");
            Site site = db.Get_Site_By_Name(nm);
            runner.execute(site.getPw(), pass);

            name = (TextView) findViewById(R.id.show_name);
            name.setText(site.getName());
            url = (TextView) findViewById(R.id.show_url);

            url.setText(site.getUrl());
            usr = (TextView) findViewById(R.id.show_user);
            usr.setText(site.getUser());

            pw = (TextView) findViewById(R.id.show_pw);
            //pw.setText()
            desc = (TextView) findViewById(R.id.show_desc);

            desc.setText(site.getDesc());
            type = (TextView) findViewById(R.id.show_type);
            type.setText(site.getType());
        }
        catch (Exception e) {
            Log.d("Error: ", e.toString());
        }
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {
        private String resp;
        private AES aes;

        @Override
        protected String doInBackground(String... params) {
            try {
                AES aes = new AES();
                if(params[0].length() > 0)
                    resp = aes.decrypt(params[0], params[1]);
                else
                    resp = "";
            }
            catch (Exception e) {
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {
            pw.setText(result);
        }

        @Override
        protected void onPreExecute() {

            Show_Toast("Please wait while the password is being decoded");
        }
    }

    public void Show_Toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
}
