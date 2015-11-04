package com.hellotechie.passmaster;

/**
 * Created by bwana on 11/2/15.
 */
import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.app.Activity;

public class EnterNewSiteActivity extends Activity implements OnClickListener {
    private Context context;
    private Button bEnter;
    private Button bGen;
    private TextView buf;
    private ListView list;
    private String typ;
    private static Context mContext;

    public EnterNewSiteActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getApplicationContext();
        Intent intent = getIntent();
        setContentView(R.layout.enter_site_layout);
    }

    public EnterNewSiteActivity(Context context) {
        this.context = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        bEnter = (Button) findViewById(R.id.widget15);
        list = (ListView) findViewById(R.id.widget13);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                typ = (String) (list.getItemAtPosition(myItemInt));
            }
        });
     }

    public void onClick(View view) {
        if(view == bEnter) {
            DBHandler db = new DBHandler(this.context);
            buf = (TextView) findViewById(R.id.widget2);
            String nm = buf.getText().toString();
            buf = (TextView) findViewById(R.id.widget4);
            String addr = buf.getText().toString();
            buf = (TextView) findViewById(R.id.widget6);
            String pw = buf.getText().toString();
            buf = (TextView) findViewById(R.id.widget8);
            String desc = buf.getText().toString();
            Site site = new Site(nm, addr, pw, desc, typ);
            db.addSite(site);
        }
    }
}
