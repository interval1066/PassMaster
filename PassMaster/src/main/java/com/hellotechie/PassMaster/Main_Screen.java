package com.hellotechie.PassMaster;

import java.util.ArrayList;
import java.io.File;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.net.Uri;
import android.util.Log;


class SiteHolder {
    TextView name;
    TextView type;
    Button edit;
    Button delete;
}

public class Main_Screen extends Activity {
    private Button add_btn, settings, importer;
    private ListView Site_listview;
    private ArrayList<Site> site_data = new ArrayList<Site>();
    private Site_Adapter cAdapter;
    private DatabaseHandler db;
    private String Toast_msg;
    private LoginDlg login;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.context = getApplicationContext();

        try {
            Site_listview = (ListView) findViewById(R.id.list);
            Site_listview.setItemsCanFocus(false);
            add_btn = (Button) findViewById(R.id.add_btn);

            settings = (Button) findViewById(R.id.button7);
            ArrayAdapter<Site> adapter = new ArrayAdapter<Site>(this,
                    android.R.layout.activity_list_item, site_data);

            importer = (Button) findViewById(R.id.button8);
            Site_listview.setAdapter(adapter);
            Site_listview.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> Site_listview, View view, int position, long id) {

                    LinearLayout ll = (LinearLayout) view;
                    TextView tv = (TextView) ll.findViewById(R.id.user_name_txt);
                    String item = tv.getText().toString();

                    Intent details = new Intent(Main_Screen.this,
                            DetailsActivity.class);

                    details.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_NEW_TASK);

                    details.putExtra("Site", item);
                    startActivityForResult(details, 0);
                    finish();
                }
            });
            Set_Referash_Data();
        } catch (Exception e) {
            // TODO: handle exception
        }

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent add_user = new Intent(Main_Screen.this,
                        Add_Update_User.class);

                add_user.putExtra("called", "add");
                add_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(add_user);
                finish();
            }
        });

        settings.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DBExporter exporter = new DBExporter(getApplicationContext());

                    if(exporter.exportSites() != null) {
                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, exporter.exportSites());

                        sendIntent.setType("text/plain");
                        startActivity(Intent.createChooser(sendIntent, "Send Using"));
                        finish();
                    }
                    else
                        Show_Toast("The site list is empty");
                }
                catch (Exception e) {
                    Show_Toast(e.toString());
                }
            }
        });

        importer.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent get_file = new Intent(Main_Screen.this,
                        ListFileActivity.class);
                File exterlStorage = Environment.getExternalStorageDirectory();
                get_file.putExtra("path", exterlStorage.toString());
                startActivity(get_file);
            }
        });
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0
                && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                Log.d("onActivityForResult", uri.toString());
            }
        }
    }

    public void Set_Referash_Data() {
		site_data.clear();
		db = new DatabaseHandler(this);

		ArrayList<Site> site_array_from_db = db.Get_Sites();
		for (int i = 0; i < site_array_from_db.size(); i++) {
		    int tidno = site_array_from_db.get(i).getID();

		    String name = site_array_from_db.get(i).getName();
		    String url = site_array_from_db.get(i).getUrl();
            String user = site_array_from_db.get(i).getUser();

		    String pw = site_array_from_db.get(i).getPw();
            String desc = site_array_from_db.get(i).getDesc();
			String type = site_array_from_db.get(i).getType();

		    Site site = new Site();
		    site.setID(tidno);
	    	site.setName(name);

		    site.setUrl(url);
            site.setUser(user);
		    site.setPw(pw);

			site.setDesc(desc);
            site.setType(type);
		    site_data.add(site);
        }
		db.close();
		cAdapter = new Site_Adapter(Main_Screen.this, R.layout.listview_row,
			site_data);
		Site_listview.setAdapter(cAdapter);
		cAdapter.notifyDataSetChanged();
    }

    public void Show_Toast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
	// TODO Auto-generated method stub
		super.onResume();
		Set_Referash_Data();
    }

    public class Site_Adapter extends ArrayAdapter<Site> {
		Activity activity;
		int layoutResourceId;
		Site site;
		ArrayList<Site> data = new ArrayList<Site>();

		public Site_Adapter(Activity act, int layoutResourceId,
			ArrayList<Site> data) {
		        super(act, layoutResourceId, data);

		        this.layoutResourceId = layoutResourceId;
                this.activity = act;
		        this.data = data;

		        notifyDataSetChanged();
		    }

		    @Override
		    public View getView(int position, View convertView, ViewGroup parent) {
		        View row = convertView;
    		    SiteHolder holder;

	    	    if (row == null) {
		    		LayoutInflater inflater = LayoutInflater.from(activity);
			    	row = inflater.inflate(layoutResourceId, parent, false);

    				holder = new SiteHolder();
	    			holder.name = (TextView) row.findViewById(R.id.user_name_txt);
                    holder.type = (TextView) row.findViewById(R.id.user_type_txt);

    				holder.edit = (Button) row.findViewById(R.id.btn_update);
	    			holder.delete = (Button) row.findViewById(R.id.btn_delete);
		    		row.setTag(holder);
    	    	}
	    		else
		    		holder = (SiteHolder) row.getTag();

    	    	site = data.get(position);
	        	holder.edit.setTag(site.getID());
	        	holder.delete.setTag(site.getID());

	    	    holder.name.setText(site.getName());
    	    	holder.edit.setOnClickListener(new OnClickListener() {

	    	        @Override
    		        public void onClick(View v) {
	    	        // TODO Auto-generated method stub

    		        Intent update_user = new Intent(activity,
                        Add_Update_User.class);
		            update_user.putExtra("called", "update");

		            update_user.putExtra("USER_ID", v.getTag().toString());
    		        activity.startActivity(update_user);
    		    }
	        });
	        holder.delete.setOnClickListener(new OnClickListener() {

		        @Override
		        public void onClick(final View v) {
		            AlertDialog.Builder adb = new AlertDialog.Builder(activity);
    		        adb.setTitle("Delete?");

	    	        adb.setMessage("Are you sure you want to delete ");
		            final int user_id = Integer.parseInt(v.getTag().toString());
		            adb.setNegativeButton("Cancel", null);

		            adb.setPositiveButton("Ok",
			            new AlertDialog.OnClickListener() {
        				    @Override
		    		        public void onClick(DialogInterface dialog,
			    		        int which) {
				                // MyDataObject.remove(positionToRemove);
				                DatabaseHandler dBHandler = new DatabaseHandler(

					            activity.getApplicationContext());
	    				        dBHandler.Delete_Site(user_id);
		    		            Main_Screen.this.onResume();
                            }
                        });
                adb.show();
                }
            });
            return row;
	    }
    }
}
