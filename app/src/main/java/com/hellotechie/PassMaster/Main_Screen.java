package com.hellotechie.PassMaster;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class Main_Screen extends Activity {
    Button add_btn;
    ListView Site_listview;
    ArrayList<Site> site_data = new ArrayList<Site>();
    Site_Adapter cAdapter;
    DatabaseHandler db;
    String Toast_msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		try {
	    	Site_listview = (ListView) findViewById(R.id.list);
	    	Site_listview.setItemsCanFocus(false);
            add_btn = (Button) findViewById(R.id.add_btn);
            ArrayAdapter<Site> adapter = new ArrayAdapter<Site>(this,
                    android.R.layout.activity_list_item, site_data);
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
		}
		catch (Exception e) {
	    	// TODO: handle exception
	    	Log.e("some error", "" + e);
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
    }

    public void Set_Referash_Data() {
		site_data.clear();
		db = new DatabaseHandler(this);
		ArrayList<Site> site_array_from_db = db.Get_Sites();
        Log.d("Set_Refresh_Data", "In set_refresh_data");
		for (int i = 0; i < site_array_from_db.size(); i++) {
		    int tidno = site_array_from_db.get(i).getID();
		    String name = site_array_from_db.get(i).getName();
		    String url = site_array_from_db.get(i).getUrl();
		    String pw = site_array_from_db.get(i).getPw();
            String desc = site_array_from_db.get(i).getDesc();
			String type = site_array_from_db.get(i).getType();
		    Site site = new Site();
		    site.setID(tidno);
	    	site.setName(name);
		    site.setUrl(url);
		    site.setPw(pw);
			site.setDesc(desc);
            site.setType(type);
		    site_data.add(site);
            Log.d("Set_Refresh_Data", "Leaving set_refresh_data");
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
				//holder.url = (TextView) row.findViewById(R.id.user_email_txt);
				//holder.pw = (TextView) row.findViewById(R.id.user_mob_txt);
				//holder.desc = (TextView) row.findViewById(R.id.user_desc_txt);
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
	    	//holder.url.setText(site.getUrl());
	    	//holder.pw.setText(site.getPw());
			//holder.desc.setText(site.getDesc());
			//holder.type.setText(site.getType());
	    	holder.edit.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
		    // TODO Auto-generated method stub
		    Log.i("Edit Button Clicked", "**********");

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

	    class SiteHolder {
	        TextView name;
	        TextView url;
	        TextView pw;
		    TextView desc;
		    TextView type;
	        Button edit;
	        Button delete;
	    }
    }
}
