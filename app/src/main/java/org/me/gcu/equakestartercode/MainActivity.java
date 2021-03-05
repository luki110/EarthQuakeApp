package org.me.gcu.equakestartercode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnClickListener
{

    private ListView itemList;
    private LinkedList<EarthQItem> alist;
    private ArrayAdapter<EarthQItem> adapter;
    private Button startButton;
    private String result = "";
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        LinkedList<EarthQItem> alist = null;
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");
        itemList = (ListView)findViewById(R.id.itemList);
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
        Log.e("MyTag","after startButton");
        // More Code goes here
    }

    public void onClick(View aview)
    {
        Log.e("MyTag","in onClick");
        startProgress();
        Log.e("MyTag","after startProgress");
    }

    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag","in run");

            try
            {
                Log.e("MyTag","in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag","after ready");
                //
                // Throw away the first 2 header lines before parsing
                //
                //
                //
                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;
                    Log.e("MyTag",inputLine);

                }
                in.close();
            }
            catch (IOException ae)
            {
                Log.e("MyTag", "ioexception in run");
            }

            //
            // Now that you have the xml data you can parse it
            //
            //result = result.substring(4);
//            alist=parseData(result);
            try
            {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( new StringReader( result ) );
                int eventType = xpp.getEventType();

                LinkedList<EarthQItem> alist = new LinkedList<>();
                EarthQItem currentItem = null;

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    String eltName = null;
                    switch (eventType)
                    {
                        case XmlPullParser.START_TAG:

                        eltName = xpp.getName();
                        if(eltName.equalsIgnoreCase("item")) {


                            currentItem = new EarthQItem();
                            alist.add(currentItem);
                        }
                        else if(currentItem != null)
                        {
                            if("title".equalsIgnoreCase(eltName))
                            {
                                currentItem.setTitle(xpp.nextText());
                            }
                            else if("description".equalsIgnoreCase(eltName))
                            {
                                currentItem.setDescription(xpp.nextText());
                                List<String> ListStrings = new ArrayList<String>(Arrays.asList(currentItem.getDescription().split(";")));
                                Log.e("MyTag", "List " + ListStrings);
                                String location = ListStrings.get(1);
                                String magnitude = ListStrings.get(4);
                                currentItem.setLocation(location);
                                currentItem.setMagnitude(magnitude);
                            }
                            else if("pubdate".equalsIgnoreCase(eltName))
                            {
                                currentItem.setDate(xpp.nextText());
                            }
                            else if("lat".equalsIgnoreCase(eltName))
                            {
                                currentItem.setLatitude(xpp.nextText());
                            }
                            else if("long".equalsIgnoreCase(eltName))
                            {
                                currentItem.setLongitude(xpp.nextText());
                            }
                        }
                        break;
                    }

                    eventType = xpp.next();
                }

                adapter = new ArrayAdapter<EarthQItem>(getBaseContext(),
                        android.R.layout.simple_list_item_1, alist);

            }
            catch (XmlPullParserException | IOException ae1)
            {
                Log.e("MyTag","Parsing error" + ae1.toString());
            }


            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    Log.d("UI thread", "I am the UI thread");
                    //rawDataDisplay.setText(result);

                    itemList.setAdapter(adapter);
                }
            });
        }

    }

}