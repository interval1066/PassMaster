package com.hellotechie.PassMaster;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Random;
import java.util.Arrays;
import java.util.List;

public class Add_Update_User extends Activity {
    EditText add_name, add_url, add_user, add_pw, add_desc; //, add_type;
    Spinner add_type;
    Button add_save_btn, add_view_all, update_btn, update_view_all, gen_pw;
    LinearLayout add_view, update_view;
    String Toast_msg = null;
    int USER_ID;
    DatabaseHandler dbHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.add_update_screen);
	    Set_Add_Update_Screen();
        List<String> types = Arrays.asList(getResources().getStringArray(R.array.site_types_array));
	    String called_from = getIntent().getStringExtra("called");

	    if (called_from.equalsIgnoreCase("add")) {
	        add_view.setVisibility(View.VISIBLE);
	        update_view.setVisibility(View.GONE);
	    }
        else {
	        update_view.setVisibility(View.VISIBLE);
	        add_view.setVisibility(View.GONE);
	        USER_ID = Integer.parseInt(getIntent().getStringExtra("USER_ID"));

	        Site s = dbHandler.Get_Site(USER_ID);
            add_name.setText(s.getName());
	        add_url.setText(s.getUrl());
            add_user.setText(s.getUser());
	        add_pw.setText(s.getPw());
		    add_desc.setText(s.getDesc());
            add_type.setSelection(types.indexOf(s.getType()));
	        // dbHandler.close();
	    }

	    add_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add_name.getText().length() > 0) {
                    int ret = dbHandler.Add_Site(new Site(add_name.getText().toString(),
                            add_url.getText().toString(), add_user.getText().toString(),
                            add_pw.getText().toString(), add_desc.getText().toString(),
                            String.valueOf(add_type.getSelectedItem())));

                    Intent view_user = new Intent(Add_Update_User.this,
                            Main_Screen.class);

                    view_user.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(view_user);
                    finish();
                    Log.d("insert", String.valueOf(ret));
                    if (ret == -1) {
                        Toast_msg = "You can't insert sites with the same name.";
                        Show_Toast(Toast_msg);
                    }
                }
                else {
                    Toast_msg = "A site must at least include a name";
                    Show_Toast(Toast_msg);
                }
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = dbHandler.Update_Site(new Site(USER_ID, add_name.getText().toString(),
                        add_url.getText().toString(), add_user.getText().toString(), add_pw.getText().toString(),
                        add_desc.getText().toString(), String.valueOf(add_type.getSelectedItem())));
			    dbHandler.close();
                Toast_msg = add_pw.getText().toString();
                Show_Toast(Toast_msg);
                Intent view_user = new Intent(Add_Update_User.this,
                        Main_Screen.class);
                view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(view_user);
                finish();
            }
        });

	    update_view_all.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
		        Intent view_user = new Intent(Add_Update_User.this,
			        Main_Screen.class);
                    view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(view_user);
		        finish();
            }
	    });

	    add_view_all.setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
		        // TODO Auto-generated method stub
		        Intent view_user = new Intent(Add_Update_User.this,
		    	    Main_Screen.class);
                    view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(view_user);
		        finish();
	        }
	    });

        gen_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_pw.setText(genpw());
            }
        });
    }

    public void Set_Add_Update_Screen() {
		add_name = (EditText) findViewById(R.id.add_name);
		add_url = (EditText) findViewById(R.id.add_url);
        add_user = (EditText) findViewById(R.id.add_user);
		add_pw = (EditText) findViewById(R.id.add_pw);
		add_desc = (EditText) findViewById(R.id.add_desc);
		add_type = (Spinner) findViewById(R.id.widget13);

		add_save_btn = (Button) findViewById(R.id.add_save_btn);
		update_btn = (Button) findViewById(R.id.update_btn);
		add_view_all = (Button) findViewById(R.id.add_view_all);
		update_view_all = (Button) findViewById(R.id.update_view_all);
        gen_pw = (Button) findViewById(R.id.gen_pw);

		add_view = (LinearLayout) findViewById(R.id.add_view);
		update_view = (LinearLayout) findViewById(R.id.update_view);

        add_view.setVisibility(View.GONE);
		update_view.setVisibility(View.GONE);
    }

    public void Show_Toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    private static String genpw() {
        String ret = "";
        Random ran = new Random();
        int x = ran.nextInt(12) + 8;
        for (int n = 0; n < x; n++) {
            int y = ran.nextInt(65) + 60;
            ret += Character.toString((char)y);
        }
        return ret;
    }

    public void Reset_Text() {
		add_name.getText().clear();
		add_url.getText().clear();
        add_user.getText().clear();
		add_pw.getText().clear();
		add_desc.getText().clear();
        add_type.setSelection(0);
    }
}
