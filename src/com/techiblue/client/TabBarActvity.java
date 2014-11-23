package  com.techiblue.client;

import android.app.TabActivity;
 import android.content.Intent;
 import android.os.Bundle;
 import android.view.View;
 import android.widget.Button;
 import android.widget.TabHost;
 import android.widget.TabHost.TabSpec;

 public class TabBarActvity extends TabActivity {
        /** Called when the activity is first created. */
        Button Tab1,Tab2,Tab3,Tab4,Tab5;
        TabHost tabHost;
        public static String Tab="";
        @Override
        public void onCreate(Bundle savedInstanceState) {
               super.onCreate(savedInstanceState);
               setContentView(R.layout.dashboard);

                tabHost = getTabHost();
              
               Tab1=(Button)findViewById(R.id.Tab1);
               Tab2=(Button)findViewById(R.id.Tab2);
               Tab3=(Button)findViewById(R.id.Tab3);
               Tab4=(Button)findViewById(R.id.Tab4);
               Tab5=(Button)findViewById(R.id.Tab5);

               // Tab for tab1
               TabSpec spec1 = tabHost.newTabSpec("Tab1");
               // setting Title and Icon for the Tab
               spec1.setIndicator("New Notify ");
               Intent Intent1 = new Intent(this, FirstActivity.class);
               spec1.setContent(Intent1);

               // Tab for tab2
               TabSpec spec2 = tabHost.newTabSpec("Tab2");
               // setting Title and Icon for the Tab
               spec2.setIndicator("Tab2");
               Intent Intent2 = new Intent(this, SecondActivity.class);
               spec2.setContent(Intent2);

               // Tab for tab3
               TabSpec spec3 = tabHost.newTabSpec("Tab3");
               // setting Title and Icon for the Tab
               spec3.setIndicator("Tab3");
               Intent Intent3 = new Intent(this,  ThridActivity.class);
               spec3.setContent(Intent3);

               // Tab for tab4

               TabSpec spec4 = tabHost.newTabSpec("Tab4");
               // setting Title and Icon for the Tab
               spec4.setIndicator("Tab4");
               Intent Intent4 = new Intent(this, FourthActivity.class);
               spec4.setContent(Intent4);

               // Tab for tab5
               TabSpec spec5 = tabHost.newTabSpec("Tab5");
               // setting Title and Icon for the Tab
               spec5.setIndicator("Tab5");
               Intent Intent5 = new Intent(this, dynamicSimulator.class);
               spec5.setContent(Intent5);
                           
              
               // Adding all TabSpec to TabHost

               tabHost.addTab(spec1); // Adding tab1
               tabHost.addTab(spec2); // Adding tab2

               tabHost.addTab(spec3); // Adding tab3
               tabHost.addTab(spec4); // Adding tab4
               tabHost.addTab(spec5); // Adding tab5
              
              
        }

//======================= click Handling for the tab layout buttons=============
        public void tabHandler(View target) {
               if (target.getId() == R.id.Tab1) {
                      Tab="this is Tab 1";
                      tabHost.setCurrentTab(0);
                       
              
               } else if (target.getId() == R.id.Tab2) {
                      Tab="this is Tab 2";
                                        
                      tabHost.setCurrentTab(1);
                     
                     
               } else if (target.getId() == R.id.Tab3) {
                      Tab="this is Tab 3";
                     
                      tabHost.setCurrentTab(2);
                     
               }
               else if (target.getId() == R.id.Tab4) {
                      Tab="this is Tab 4";
                     
                      tabHost.setCurrentTab(3);
                     
               }
               else if (target.getId() == R.id.Tab5) {
                      Tab="this is Tab 5";
                     
                      tabHost.setCurrentTab(4);
                     
               }
        }
 }
