package com.hellotechie.PassMaster;

/**
 * Created by bwana on 11/12/15.
 */

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Xml;
import org.xmlpull.v1.XmlSerializer;
import java.io.StringWriter;
import java.util.ArrayList;
import java.io.IOException;
import android.content.SharedPreferences;
import android.util.Log;

public class DBExporter {
    private ArrayList<Site> site_data = new ArrayList<Site>();
    private DatabaseHandler db;
    private Context context;
    protected XmlSerializer serializer;
    private StringWriter writer;
    private SharedPreferences sharedPreferences;

    public DBExporter(Context context) {
        this.context = context;
        serializer = Xml.newSerializer();

        writer = new StringWriter();
        db = new DatabaseHandler(context);

        sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this.context);
    }

    public String exportSites() {
        site_data = db.Get_Sites();
        if (site_data.size() > 0) {

            try {
                serializer.setOutput(writer);
                serializer.startDocument("UTF-8", true);
                serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

                String pw = sharedPreferences.getString("masterpw", "");
                serializer.startTag("", "PassMaster");
                serializer.startTag("", "globals");

                serializer.attribute("", "masterpw", pw);
                serializer.endTag("", "globals");
                for (final Site site : site_data) {

                    serializer.startTag("", "Site");
                    serializer.attribute("", "name", site.getType());
                    serializer.attribute("", "url", site.getUrl());

                    serializer.attribute("", "user", site.getUser());
                    serializer.attribute("", "pw", site.getPw());
                    serializer.attribute("", "desc", site.getDesc());

                    serializer.attribute("", "type", site.getType());
                    serializer.endTag("", "Site");
                }
                serializer.endTag("", "PassMaster");
                serializer.endDocument();
                serializer.flush();

                String buf = writer.toString();
                buf = buf.substring(0, buf.length() - buf.lastIndexOf("<?xml"));
                return buf;

            } catch (IOException e) {
                Log.d("error", e.toString());
            }
        }
        return null;
    }
}
