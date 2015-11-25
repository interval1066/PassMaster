package com.hellotechie.PassMaster;

/**
 * Created by bwana on 11/12/15.
 */

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Xml;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.*;
import java.util.ArrayList;
import java.nio.charset.Charset;
import android.preference.PreferenceManager;
import android.content.SharedPreferences.Editor;
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
    private Site site;
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
                    serializer.attribute("", "name", site.getName());
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

    public void importSites(String path) throws PassMasterException {
        this.path = path;
        String content = "";

        try {
            if (db.Get_Total_Sites() < 1)
                db.clearDB();
            InputStream is = new ByteArrayInputStream(this
                    .path.getBytes(Charset.defaultCharset()));

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            DocumentBuilder parser = factory.newDocumentBuilder();

            Document document = parser.parse(new InputSource(readStreamAsString(is)));
            NodeList conf = document.getElementsByTagName("globals");
            NamedNodeMap globals = conf.item(0).getAttributes();

            Node masterpw = globals.getNamedItem("masterpw");
            Editor editor = sharedPreferences.edit();
            editor.putString("masterpw", masterpw.getNodeValue());

            editor.apply();
            NodeList sections = document.getElementsByTagName("Site");
            for (int i = 0; i < sections.getLength(); i++) {

                NamedNodeMap attributes = sections.item(i).getAttributes();
                Node name = attributes.getNamedItem("name");
                Node url = attributes.getNamedItem("url");

                Node user = attributes.getNamedItem("user");
                Node pw = attributes.getNamedItem("pw");
                Node desc = attributes.getNamedItem("desc");

                Node type = attributes.getNamedItem("type");
                Site site = new Site(name.getNodeValue(), url.getNodeValue(),
                        user.getNodeValue(), pw.getNodeValue(),
                        desc.getNodeValue(), type.getNodeValue());

                db.Add_Site(site);
            }
        }
        catch (Exception e) {
            Log.d("error", e.getMessage());
            throw new PassMasterException("There was a problem importing the sites list");
        }
    }

    private void Show_Toast(String msg) {
        Toast.makeText(this.context, msg, Toast.LENGTH_LONG).show();
    }

    private static String readStreamAsString(InputStream in)
            throws IOException {

        BufferedInputStream bis = new BufferedInputStream(in);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result = bis.read();

        while(result != -1) {
            byte b = (byte)result;
            buf.write(b);

            result = bis.read();
        }
        String ret = "file://";
        return ret += buf.toString();
    }
}
