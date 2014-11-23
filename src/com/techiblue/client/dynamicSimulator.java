 package com.techiblue.client;
 
 


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List; 

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
  
  

 public class dynamicSimulator extends Activity {

	 
	 TableLayout table_layout; 
   private Spinner spinner1, spinner2;
   private Button button;
   
   public  String[] TDetail ;
   
   public String[] TID;
   
   public  String[] LocDetails ;
   
   public  String[] LOCID ;

   boolean DBConnectionFlag = false;
   
   boolean locDBFlag = false;
   boolean saveflag= false;
   
   
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
 	super.onCreate(savedInstanceState);
 	setContentView(R.layout.dynamicframe);
 	 spinner1 = (Spinner) findViewById(R.id.spinner1); 

 	getDataFrmDB();
 	 
 	addItemsOnSpinner2stat();
 	addListenerOnButton();
  
   }
   
   

   public void addItemsOnSpinner1() {
	   
	   System.out.println(":;:::::::::: DBConnectionFlag" +DBConnectionFlag);
		 
	   if(!DBConnectionFlag )
	   {
		   System.out.println("::::::::::: DB not connected ");
		   TDetail = new String[1];
		   TID = new String[1];
		   TDetail[0]="--Please Select Train";
		   TID[0]="0";
	   }
	   
		spinner1 = (Spinner) findViewById(R.id.spinner1);
      
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, TDetail);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter);
	  //  spinner1.setSelection(0);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
			/*	image.setImageResource(imgs.getResourceId(
						spinner.getSelectedItemPosition(), -1));*/
				
				
				System.out.println("::::::: TDetail " + TDetail);
				System.out.println(DBConnectionFlag+ "spinner.getSelectedItemPosition()   "+ spinner1.getSelectedItemPosition());
				
				
				
				if(DBConnectionFlag)
				{ 
					
					if(spinner1.getSelectedItemPosition() != 0)
					{
					String selectedTID = TID[spinner1.getSelectedItemPosition()];
									 
						System.out.println("::::::::::: Selected  TID " + selectedTID);
						getLocDataFrmDB(selectedTID);
					 
					}
					if(spinner1.getSelectedItemPosition() == 0)
					{
					
						addItemsOnSpinner2stat();
					}
					
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	   
   }
   
   
   public void addItemsOnSpinner2stat() {

	 	spinner2 = (Spinner) findViewById(R.id.spinner2);
	 	
	 	 
	 	LocDetails = new String[1];
	 	LOCID = new String[1];
	 	LocDetails[0]="-- Please Select Location";
	 	LOCID[0]="0";
	  
	 	
	 	
	 	ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, LocDetails);
	 	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 	spinner2.setAdapter(dataAdapter);
	 	spinner2.setSelection(0);
	   }

   // add items into spinner dynamically
   public void addItemsOnSpinner2() {

 	spinner2 = (Spinner) findViewById(R.id.spinner2);
 	 	
 	ArrayAdapter dataAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, LocDetails);
 	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 	spinner2.setAdapter(dataAdapter);
 	spinner2.setSelection(0);
   }

   public void addListenerOnSpinnerItemSelection() {
 	spinner1 = (Spinner) findViewById(R.id.spinner1);
 	spinner1.setOnItemSelectedListener(new MyOnItemSelectedListener());
   }

   public void addListenerOnButton() {

 	spinner1 = (Spinner) findViewById(R.id.spinner1);
 	spinner2 = (Spinner) findViewById(R.id.spinner2);
 	button = (Button) findViewById(R.id.button);

 	button.setOnClickListener(new OnClickListener() {
 	  @Override
 	  public void onClick(View v) {

 		 System.out.println("::::::: click to srt pu");
 	    InitiateProcess();
 	     
 	  }
 
 	});
   }
   
   
   public void  InitiateProcess()
   {
	   
	   Toast.makeText(getApplicationContext(),
		       "simulation is in progress",
		       Toast.LENGTH_SHORT).show();
	   
	   
	   
	   callWS();
	/*   table_layout = (TableLayout) findViewById(R.id.tableLayout1);

	   int row12 =1 ;
	   int col12 = 2;
	   System.out.print("::::::::::: -- build tree ");
	   BuildTable(row12, col12);*/
   }
   
   
   private void BuildTable(int rows, int cols) {

	   
	   System.out.println(":::::::::::::::: Enter into Build Table Layout");
	   // outer for loop
	   for (int i = 1; i <= rows; i++) {

	    TableRow row = new TableRow(this);
	    row.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
	      LayoutParams.WRAP_CONTENT));

	    // inner for loop
	    for (int j = 1; j <= cols; j++) {

	     TextView tv = new TextView(this);
	     tv.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
	       LayoutParams.WRAP_CONTENT));
	     tv.setBackgroundResource(R.drawable.common_signin_btn_icon_light);
	     tv.setPadding(5, 5, 5, 5);
	     tv.setText("R " + i + ", C" + j);

	     row.addView(tv);
	     
	     
	     new Thread() {

    		 public void run() {

    		 try{

    		 sleep(1000);

    		 } catch (Exception e) {

    		 System.out.println(":::::::::::::::::::: ");

    		 }
    		 }
    	 };
    	 
    	 

	    }

	    table_layout.addView(row);

	   }
	  }
   
   
   



public void cllWSAsyn()
{  
	   Runnable runnable = new Runnable() {
		   Handler handler = new Handler();

           @Override
           public void run() {
               handler.post(new Runnable() { // This thread runs in the UI
                   @Override
                   public void run() {
                        callWS();
                   }
               });
           }
       };
       new Thread(runnable).start();
}


       class sendNotification extends Thread {
    	   public void run () {
    	     callWS();
    	   }
    	}
       
       
       

public void callWS()
{
	 
	 RequestParams params = new RequestParams();
	 
	 
	params.put("environment", "sandbox"); 
	// params.put("APP_SECRET","6d34ee0664485d04c1ca6e5ee82246ba40725000");
	// params.put("app_id","0a0038c9-1aa8-4e2f-898c-326c1c71d2d1");
	 
		//	 params.put("body","setting Env");
		 
			 
			 System.out.println("::::::::: param " + params);
	// Show Progress Dialog
			   
			 
    // Make RESTful webservice call using AsyncHttpClient object
    
    AsyncHttpClient client = new AsyncHttpClient();
    
    System.out.println("Setting header et");
     client.addHeader("IBM-APPLICATION-SECRET", "6d34ee0664485d04c1ca6e5ee82246ba40725000");
     
    System.out.println("GCM COnfi");
	params.put("message", "alert:Uma ..sending message "); 
    
    
    String setEn = "https://mobile.ng.bluemix.net/push/v1/apps/0a0038c9-1aa8-4e2f-898c-326c1c71d2d1";
   
    String pushNotify = "https://mobile.ng.bluemix.net:443/push/v1/apps/0a0038c9-1aa8-4e2f-898c-326c1c71d2d1/messages";
    
    System.out.println("GCM COnfi" + setEn);
    
    https://mobile.ng.bluemix.net/push/v1/apps/0a0038c9-1aa8-4e2f-898c-326c1c71d2d1/settings/gcmConf?environment=sandbox/
    client.get(pushNotify,params ,new AsyncHttpResponseHandler() {
        // When the response returned by REST has Http response code '200'
        @Override
        public void onSuccess(String response) {
            // Hide Progress Dialog
          
            try {
            	
            	System.out.println(" Res Obje " +response );
                     // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    System.out.println(" response ... " +response);
                    
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                     
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }
        }
        // When the response returned by REST has Http response code other than '200'
        @Override
        public void onFailure(int statusCode, Throwable error,
            String content) {
        	
        	System.out.println(content + " :::::: " + statusCode + " ::::: "+ error.getMessage());
            // Hide Progress Dialog 
           
            // When Http response code is '404'
            if(statusCode == 404){
                Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
            } 
            // When Http response code is '500'
            else if(statusCode == 500){
                Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
            } 
            // When Http response code other than 404, 500
            else{
                Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
            }
        }
    });
	 
}
       
   public void saveReq(String TIDv , String LIDv)
   {
	   
	   System.out.println(TIDv +" --------------- " + LIDv);
	   
	  
	   	
	   	final String tidv = TIDv;
	   	
	   	final String lidv = LIDv;
	   	System.out.println("Inside details ");
	   	
	   	 new AsyncTask<Void, Void, String>() {

	            @Override
	            protected void onPreExecute() {
	                super.onPreExecute();
	               // showProgressDialog("Please Wait...");
	            }

	            @Override
	            protected String doInBackground(Void... params) {
	           	 
	           	 System.out.println(":::::::::::::call get Train details");
	           	 saveReqDetl(tidv,lidv);;
	           	 
	           	  System.out.println(":::::::::::::end  get Train details");
	           	
	           	  return "S";
	            }

	            @Override
	            protected void onPostExecute(String result) {
	                super.onPostExecute(result);
	             
	               // addItemsOnSpinner2();
	                
	                System.out.println(":::::::::::::::::::::: Save completed show msg to the user ");
	                
	                if(saveflag)
	             	    Toast.makeText(getApplicationContext(),
	             		"Request for Notifcation Successful",
	             			Toast.LENGTH_SHORT).show();
	             		  else
	             		  {
	             			 Toast.makeText(getApplicationContext(),
	             			 		"Result : Request for Notifcation failed .. Please try after some time ",Toast.LENGTH_SHORT).show();
	             		  }
	                 
	            }
	        }.execute();
	        
	   
	      
   }
   
   
   public void  saveReqDetl(String tidv , String lidv)
   {
	   System.out.println(":::::::::::::inside  get Train  loc details" );
	   
		
		try {
			
				Class.forName("com.mysql.jdbc.Driver");

		  
		         Connection conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-01.cleardb.net/ad_ddd019f774052fa", "b151a01f793005", "f10f4731"); // MySQL
//		      
		         System.out.println("> got connection ");
		         
		         Statement stmt = conn.createStatement();
		         
		         
		         
		         String strSelect1 = "insert into userNotifyReqDetl(tid,lid) values('"+tidv+"' , '"+lidv+"');";
			        
		         
		         int retV = stmt.executeUpdate(strSelect1);
		         
		          
		         
		         saveflag = true;
		         stmt.close();
		         
		         conn.close();
		         
		         
		      } catch(SQLException | ClassNotFoundException   ex) {
		         ex.printStackTrace();
		         
		         saveflag=false;
		      }
		      // Step 5: Close the resources - Done automatically 
	 

	 
	   
   }

public void getLocDataFrmDB(String TID)
{
	
	final String tidv = TID;
	System.out.println("Inside details ");
	
	 new AsyncTask<Void, Void, String>() {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
            // showProgressDialog("Please Wait...");
         }

         @Override
         protected String doInBackground(Void... params) {
        	 
        	 System.out.println(":::::::::::::call get Train details");
        	 
        	 getLoc4TID(tidv);
        	 
        	  System.out.println(":::::::::::::end  get Train details");
        	
        	  return "S";
         }

         @Override
         protected void onPostExecute(String result) {
             super.onPostExecute(result);
          
             addItemsOnSpinner2();
              
         }
     }.execute();
     
}
   

public void getLoc4TID(String TIDValue)
{
	
	 System.out.println(":::::::::::::inside  get Train  loc details" );
	   
		
		try {
			
				Class.forName("com.mysql.jdbc.Driver");

		  
		         Connection conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-01.cleardb.net/ad_ddd019f774052fa", "b151a01f793005", "f10f4731"); // MySQL
//		      
		         System.out.println("> got connection ");
		         
		         Statement stmt = conn.createStatement();
		         
		         
		         
		         String strSelect1 = "select count(*) as rowcount from LocationDetl where  (notifyFlag = 'Y'  || notifyFlag = 'B') and  TID='"+ TIDValue+"'";
			        
		         
		         ResultSet rset = stmt.executeQuery(strSelect1);
		         
		         
		         rset.next();
		         int count = rset.getInt("rowcount");
		         
		         System.out.println(":::::::::::::: " +count);
		         rset.close();
                 
		         LocDetails = new String[count+1];
		         LOCID = new String[count+1];
		      
		         String strSelect = "select LOCName, LID  from LocationDetl where  (notifyFlag = 'Y'  || notifyFlag = 'B') and  TID='"+ TIDValue+"'";
		          
		         System.out.println("The SQL query is: " + strSelect); // Echo For debugging
		       
		           rset = stmt.executeQuery(strSelect);

		  
		         

		         int rowCount = 0;
		         while(rset.next()) {   // Move the cursor to the next row
		            String lname = rset.getString("LOCName");
		            String lid = rset.getString("LID");
		            if(rowCount == 0)
		            	LocDetails[0] = "-- Select Location";
		            LocDetails[rowCount+1] =lname;
		            LOCID[rowCount+1] = lid;
		            ++rowCount;
		         }
		         System.out.println("Total number of records = " + rowCount);
		         
		         rset.close();
		         conn.close();
		         
		         if(LocDetails != null)
		         {
		        	 locDBFlag=true;
		         }
		         
		      } catch(SQLException | ClassNotFoundException   ex) {
		         ex.printStackTrace();
		         
		         locDBFlag=false;
		      }
		      // Step 5: Close the resources - Done automatically 
	 
 
	 
}



public void getDataFrmDB()
{
	
	System.out.println("Inside details ");
	
	 new AsyncTask<Void, Void, String>() {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
            // showProgressDialog("Please Wait...");
         }

         @Override
         protected String doInBackground(Void... params) {
        	 
        	  


        	 
        	 System.out.println(":::::::::::::call get Train details");
        	 
        	 getTrainDetl("TrainDetl");
        	 
        	 new Thread() {

        		 public void run() {

        		 try{

        		 sleep(10000);

        		 } catch (Exception e) {

        		 System.out.println(":::::::::::::::::::: ");

        		 }
        		 }
        	 };
        	 
        	  System.out.println(":::::::::::::end  get Train details");
        	
        	  return "S";
         }

         @Override
         protected void onPostExecute(String result) {
             super.onPostExecute(result);
             
             new Thread() {

        		 public void run() {

        		 try{

        		 sleep(10000);

        		 } catch (Exception e) {

        		 System.out.println(":::::::::::::::::::: ");

        		 }
        		 }
        	 };
          
             addItemsOnSpinner1();
              
         }
     }.execute();
     
}






public /*List<String>*/  void  getTrainDetl(String option)
{
	
	 List<String> listOfItems = new ArrayList<String>();
	 System.out.println(":::::::::::::inside  get Train details");
   if(option.equals("TrainDetl"))
	{
		
		try {
			
				Class.forName("com.mysql.jdbc.Driver");

		  
		         Connection conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-01.cleardb.net/ad_ddd019f774052fa", "b151a01f793005", "f10f4731"); // MySQL
//		      
		         System.out.println("> got connection ");
		         
		         Statement stmt = conn.createStatement();
		         
		         String strSelect1 = "select count(*) as rowcount from Train";
			        
		         
		         ResultSet rset = stmt.executeQuery(strSelect1);
		         
		         
		         rset.next();
		         int count = rset.getInt("rowcount");
		         rset.close();
                 
		         TDetail = new String[count+1];
		         TID = new String[count+1];
		         
		         String strSelect = "select TID ,TrainName from Train";
		          
		         System.out.println("The SQL query is: " + strSelect); // Echo For debugging
		       
		           rset = stmt.executeQuery(strSelect);

		  
		         

		         int rowCount = 0;
		         while(rset.next()) {   // Move the cursor to the next row
		            String tname = rset.getString("TrainName");
		            String tid = rset.getString("TID");
		            if(rowCount == 0)
		            	TDetail[rowCount]="-- Please select Train --";
		            TDetail[rowCount+1] = tid + " -- "+ tname;
		            TID[rowCount+1]=tid;
		            ++rowCount;
		         }
		         
		         
		         
		   
		         
		         System.out.println("Total number of records = " + rowCount);
		         
		         rset.close();
		         stmt.close();
		         conn.close();
		         
		         
		         if (TDetail != null)
		         {
		             DBConnectionFlag = true;
		             
		        	 
		         }
		         
		      } catch(SQLException | ClassNotFoundException   ex) {
		         ex.printStackTrace();
		         
		         DBConnectionFlag = false;
		         
		         TDetail = new String[1];
		         TDetail[0]="-- Please select Train --";
		         TID = new String[1];
		         TID[0] ="0";
		         System.out.println("::::::::::::connection exception " + ex.getMessage());
		         
		         System.out.println("Detl of Error Msg " + ex.getLocalizedMessage() );
		      }
		      // Step 5: Close the resources - Done automatically 
	}

  // return listOfItems;
}



 }
