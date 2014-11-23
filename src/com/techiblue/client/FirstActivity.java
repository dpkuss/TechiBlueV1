 package com.techiblue.client;
 
 


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
  
  

 public class FirstActivity extends Activity {

   private Spinner spinner1, spinner2;
   private Button button;
   
   public  String[] TDetail ;
   
   public String[] TID;
   
   public  String[] LocDetails ;
   
   public  String[] LOCID ;

   public String LID4Notify[] ;
   
   boolean DBConnectionFlag = false;
   
   boolean locDBFlag = false;
   boolean saveflag= false;
    
   
   //Local Data If DB connection is not established 
   
  public  String[] TempTDetail = {"-- Please Select Train", "BLUE", "INTERCITY"};
   
   public String[] TempTID ={"---", "777","999"};
   
   public  String[] TempLocDetails1={"--- SELECT LOCATION -- ","CHENNAI" ,"VELLORE " ,"SALEM","ERODE","COIMBATORE","AMBUR","OMALORE","BHAVANI","VADA KOVAI"};
   
   public  String[] TempLocDetails2={"CHENNAI" ,"VELLORE " ,"SALEM","ERODE","COIMBATORE","AMBUR","OMALORE","BHAVANI","VADA KOVAI"};
      
   public String loc[] = {"0", "1001" ,"1002" ,"1003" ,"1004" ,"1005" ,"1006" ,"1007" ,"1008" ,"1009"};
   
   public String Notifyloc[] = {"1001" ,"1002" ,"1003" ,"1004" ,"1005" ,"1002" ,"1003" ,"1004" ,"1005"};
   
   
   
   @Override
   public void onCreate(Bundle savedInstanceState) {
 	super.onCreate(savedInstanceState);
 	setContentView(R.layout.firstactivity);
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

 		  System.out.println("::::::: Adding details to the location table ");
 	    
 	    saveReq(TID[spinner1.getSelectedItemPosition()] ,LID4Notify[spinner1.getSelectedItemPosition()]);
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
		         LID4Notify = new String[count+1];
		      
		         String strSelect = "select LOCName, LID , LID4Notify  from LocationDetl where  (notifyFlag = 'Y'  || notifyFlag = 'B') and  TID='"+ TIDValue+"'";
		          
		         System.out.println("The SQL query is: " + strSelect); // Echo For debugging
		       
		           rset = stmt.executeQuery(strSelect);

		  
		           String lname = "";
		            String lid = "";
		            String lnid = "";

		         int rowCount = 0;
		         while(rset.next()) {   // Move the cursor to the next row
		              lname = rset.getString("LOCName");
		              lid = rset.getString("LID");
		              lnid = rset.getString("LID4Notify");
		            if(rowCount == 0)
		            	LocDetails[0] = "-- Select Location";
		            LOCID[0]="0";
		            LocDetails[rowCount+1] =lname;
		            LOCID[rowCount+1] = lid;
		            LID4Notify[rowCount+1] =lnid;
		            
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
		         
		         //TDetail[0]=  "-- Please select Train --";
		         TDetail[0] = TempTID[0] + TDetail[0];
		           
		         TDetail[1] = TempTID[1] + " -- " +  TDetail[1];
		         
		         TDetail[2] = TempTID[2] + " -- " +  TDetail[2];
		            
		        
		         System.out.println("::::::temp valiue set::::::connection exception " + ex.getMessage());
		         
		         System.out.println("Detl of Error Msg " + ex.getLocalizedMessage() );
		      }
		      // Step 5: Close the resources - Done automatically 
	}

  // return listOfItems;
}



 }
