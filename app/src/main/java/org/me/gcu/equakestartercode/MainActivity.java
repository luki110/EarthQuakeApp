package org.me.gcu.equakestartercode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnClickListener
{
    private static final String TAG = "MainActivity";
    private SharedViewModel viewModel;
    private HomeFragment homeFragment;
    private MapsFragment mapsFragment;
    private FilterFragment filterFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("MyTag","in onCreate");
        homeFragment = new HomeFragment();
        mapsFragment = new MapsFragment();
        filterFragment = new FilterFragment();

        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        viewModel.getList().observe(this, mapsFragment);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch(item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_map:
                            selectedFragment = new MapsFragment();
                            break;
                        case R.id.nav_date:
                            selectedFragment = new FilterFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    private void doFragmentTransaction(Fragment fragment, String tag, boolean addToBackStack, String message)
    {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, fragment, tag);

        if(addToBackStack){
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }
//    public void ParseXML(){
//        try
//        {
//            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//            factory.setNamespaceAware(true);
//            XmlPullParser xpp = factory.newPullParser();
//            xpp.setInput( new StringReader( result ) );
//            int eventType = xpp.getEventType();
//
//            ArrayList<EarthQItem> alist = new ArrayList<>();
//            EarthQItem currentItem = null;
//
//            while (eventType != XmlPullParser.END_DOCUMENT)
//            {
//                String eltName = null;
//                switch (eventType)
//                {
//                    case XmlPullParser.START_TAG:
//
//                        eltName = xpp.getName();
//                        if(eltName.equalsIgnoreCase("item")) {
//
//
//                            currentItem = new EarthQItem();
//                            alist.add(currentItem);
//                        }
//                        else if(currentItem != null)
//                        {
//                            if("title".equalsIgnoreCase(eltName))
//                            {
//                                currentItem.setTitle(xpp.nextText());
//                            }
//                            else if("description".equalsIgnoreCase(eltName))
//                            {
//                                currentItem.setDescription(xpp.nextText());
//                                List<String> ListStrings = new ArrayList<String>(Arrays.asList(currentItem.getDescription().split(";")));
//                                Log.e("MyTag", "List " + ListStrings);
//                                String location = ListStrings.get(1);
//                                String magnitude = ListStrings.get(4);
//                                currentItem.setLocation(location);
//                                currentItem.setMagnitude(magnitude);
//                            }
//                            else if("pubdate".equalsIgnoreCase(eltName))
//                            {
//                                currentItem.setDate(xpp.nextText());
//                            }
//                            else if("lat".equalsIgnoreCase(eltName))
//                            {
//                                currentItem.setLatitude(xpp.nextText());
//                            }
//                            else if("long".equalsIgnoreCase(eltName))
//                            {
//                                currentItem.setLongitude(xpp.nextText());
//                            }
//                        }
//                        break;
//                }
//
//                eventType = xpp.next();
//            }
//
//            EarthQAdapter adapter = new EarthQAdapter(this, R.layout.adapter_layout, alist);
////            adapter = new ArrayAdapter<EarthQItem>(getBaseContext(),
////                    android.R.layout.simple_list_item_1, alist);
//            itemList.setAdapter(adapter);
//        }
//        catch (XmlPullParserException | IOException ae1)
//        {
//            Log.e("MyTag","Parsing error" + ae1.toString());
//        }
//    }

//    public void onClick(View aview)
//    {
//        Log.e("MyTag","in onClick");
//        startProgress();
//        Log.e("MyTag","after startProgress");
//    }

//    public void startProgress()
//    {
//        // Run network access on a separate thread;
//        new Thread(new Task(urlSource)).start();
//    } //




//    @Override
//    public void handleTaskUpdate() {
////        runOnUiThread(new Runnable() {
////
////            @Override
////            public void run() {
////                homeFragment.runAsyncTask();
////                // Stuff that updates the UI
////
////            }
////        });
//
//    }

    @Override
    public void onClick(View v) {

    }

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
//    class Task implements Runnable
//    {
//        private String url;
//
//        public Task(String aurl)
//        {
//            url = aurl;
//        }
//        @Override
//        public void run()
//        {
//
//            URL aurl;
//            URLConnection yc;
//            BufferedReader in = null;
//            String inputLine = "";
//
//
//            Log.e("MyTag","in run");
//
//            try
//            {
//                Log.e("MyTag","in try");
//                aurl = new URL(url);
//                yc = aurl.openConnection();
//                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
//                Log.e("MyTag","after ready");
//                //
//                // Throw away the first 2 header lines before parsing
//                //
//                //
//                //
//                while ((inputLine = in.readLine()) != null)
//                {
//                    result = result + inputLine;
//                    Log.e("MyTag",inputLine);
//
//                }
//                in.close();
//            }
//            catch (IOException ae)
//            {
//                Log.e("MyTag", "ioexception in run");
//            }

            //
            // Now that you have the xml data you can parse it
            //
            //result = result.substring(4);
//            alist=parseData(result);



            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !
//
//            MainActivity.this.runOnUiThread(new Runnable()
//            {
//                public void run() {
//                    Log.d("UI thread", "I am the UI thread");
//                    ParseXML();
//
//                }
//            });
//        }

   // }


}