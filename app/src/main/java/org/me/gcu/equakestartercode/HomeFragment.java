package org.me.gcu.equakestartercode;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment  {

    private SharedViewModel viewModel;
    private ListView itemList;
    private Button startButton;
    private String result = "";
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        itemList = v.findViewById(R.id.itemList);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Log.i("HomeFragment", "Attached");

        runAsyncTask();
    }

    public void ParseXML(){
        try
        {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput( new StringReader( result ) );
            int eventType = xpp.getEventType();

            ArrayList<EarthQItem> alist = new ArrayList<>();
            EarthQItem currentItem = null;

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                String eltName = null;
                switch (eventType)
                {
                    case XmlPullParser.START_TAG:

                        eltName = xpp.getName();
                        if(eltName.equalsIgnoreCase("item"))
                        {
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
                                String location = ListStrings.get(1).substring(1);
                                currentItem.setLocation(location);

                            }
                            else if("pubdate".equalsIgnoreCase(eltName))
                            {
                                String sDate = xpp.nextText();
                                SimpleDateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy");
                                Date date = formatter.parse(sDate);
                                currentItem.setDate(date);
                            }
                            else if("lat".equalsIgnoreCase(eltName))
                            {
                                currentItem.setLatitude(Double.parseDouble(xpp.nextText()));
                            }
                            else if("long".equalsIgnoreCase(eltName))
                            {
                                currentItem.setLongitude(Double.parseDouble(xpp.nextText()));
                            }
                        }
                        break;
                }

                eventType = xpp.next();
            }

            EarthQAdapter adapter = new EarthQAdapter(getActivity(), R.layout.adapter_layout, alist);

            itemList.setAdapter(adapter);
            viewModel.setList(alist);
        }
        catch (XmlPullParserException | IOException | ParseException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
    }

    public void runAsyncTask()
    {
        new MyAsyncTask(urlSource).execute();

    }


    class MyAsyncTask extends AsyncTask<String, String, Void> {

        private String url;
        public  MyAsyncTask(String aurl)
        {
            url = aurl;
        }

        @Override
        protected Void doInBackground(String... params) {
            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";


            Log.e("MyTag", "in run");

            try {
                Log.e("MyTag", "in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                Log.e("MyTag", "after ready");

                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;
                    Log.e("MyTag", inputLine);

                }
                in.close();
            } catch (IOException ae) {
                Log.e("MyTag", "ioexception in run");
            }

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ParseXML();
        }
    }
}
