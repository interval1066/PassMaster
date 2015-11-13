package com.hellotechie.PassMaster;

/**
 * Created by bwana on 11/12/15.
 */

import android.content.Context;
import android.content.Intent;
import android.util.Xml;
import org.xmlpull.v1.XmlSerializer;
import java.io.StringWriter;
import java.util.ArrayList;
import java.io.IOException;
import android.util.Log;

import static android.support.v4.app.ActivityCompat.startActivity;

public class DBExporter {
    private ArrayList<Site> site_data = new ArrayList<Site>();
    private DatabaseHandler db;
    private Context context;
    protected XmlSerializer serializer;
    private StringWriter writer;

    public DBExporter(Context context) {
        this.context = context;
        serializer = Xml.newSerializer();
        writer = new StringWriter();
        db = new DatabaseHandler(context);
    }

    public String exportSites() {
        site_data = db.Get_Sites();
        if(site_data.size() > 0) {
            try {
                serializer.setOutput(writer);
                serializer.startDocument("UTF-8", true);
                serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

                serializer.startTag("", "PassMaster");
                for (int i = 0; i < site_data.size(); i++) {
                    serializer.startTag("", "Site");
                    serializer.attribute("", "name", site_data.get(i).getName().toString());
                    serializer.attribute("", "url", site_data.get(i).getUrl().toString());
                    serializer.attribute("", "user", site_data.get(i).getUser().toString());
                    serializer.attribute("", "pw", site_data.get(i).getPw().toString());
                    serializer.attribute("", "desc", site_data.get(i).getDesc().toString());
                    serializer.attribute("", "type", site_data.get(i).getType().toString());
                    serializer.endTag("", "Site");
                }
                serializer.endTag("", "PassMaster");
                serializer.endDocument();
                serializer.flush();
                return writer.toString();

            } catch (IOException e) {
                Log.d("error", e.toString());
            }
        }
        return null;
    }
}
