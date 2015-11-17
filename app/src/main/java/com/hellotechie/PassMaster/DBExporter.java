package com.hellotechie.PassMaster;

/**
 * Created by bwana on 11/12/15.
 */

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.StringWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class DBExporter {
    private ArrayList<Site> site_data = new ArrayList<Site>();
    private DatabaseHandler db;
    private Context context;
    protected XmlSerializer serializer;
    private StringWriter writer;
    private SharedPreferences sharedPreferences;
    private String path;
    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser myparser;

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

    public void importSites(String path) {
        this.path = path;
        String content = "";

        try {
            if (db.Get_Total_Sites() > 0) {
                xmlFactoryObject = XmlPullParserFactory.newInstance();
                myparser = xmlFactoryObject.newPullParser();
                myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

                InputStream is = new ByteArrayInputStream(this
                        .path.getBytes(Charset.defaultCharset()));
                myparser.setInput(is, null);

                int event = myparser.getEventType();
                while (event != XmlPullParser.END_DOCUMENT) {
                    String name = myparser.getName();
                    switch (event) {
                        case XmlPullParser.START_TAG:
                            break;

                        case XmlPullParser.END_TAG:
                            if (name.equals("name")) {
                                content = myparser.getAttributeValue(null, "name");
                            }
                            break;
                    }
                    event = myparser.next();
                }
                Log.d("found name: ", content);
            }
            else
                Show_Toast("The site list is empty.");
        }
        catch (Exception e) {
            Log.d("Error", e.toString());
        }
    }

    public void Show_Toast(String msg) {
        Toast.makeText(this.context, msg, Toast.LENGTH_LONG).show();
    }

}
