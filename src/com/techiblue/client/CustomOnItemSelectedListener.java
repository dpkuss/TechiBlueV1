package com.techiblue.client;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
  

public class CustomOnItemSelectedListener implements OnItemSelectedListener {
	
	String selectTID ="";

	 
	  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
		  
		  Toast.makeText(parent.getContext(), "The subject is  " +
                  parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
       System.out.println(parent.getItemAtPosition(pos).toString());
        //name=parent.getItemAtPosition(pos).toString();


   
	    selectTID = parent.getItemAtPosition(pos).toString();
	  new AsyncTask<Void, Void, String>() {

	         @Override
	         protected void onPreExecute() {
	             super.onPreExecute();
	            // showProgressDialog("Please Wait...");
	         }

	         @Override
	         protected String doInBackground(Void... params) {
	        	       	 
	       	    
	       	  
	        	  getLocDetl(selectTID);
	        	  
	        	  return "S";
	         }

	         @Override
	         protected void onPostExecute(String result) {
	             super.onPostExecute(result);
	          
	             //hideProgressDialog();
	              
	         }
	     }.execute();
	     
	     
	     
	     
	Toast.makeText(parent.getContext(), 
		"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
		Toast.LENGTH_SHORT).show();
  }
 
  @Override
  public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
  }
 
  
  public void getLocDetl(String tid)
  {
  	 
   
  		try {
  			
  				Class.forName("com.mysql.jdbc.Driver");

  		  
  		         Connection conn = DriverManager.getConnection("jdbc:mysql://us-cdbr-iron-east-01.cleardb.net/ad_ddd019f774052fa", "b151a01f793005", "f10f4731"); // MySQL
//  		      
  		         System.out.println("> got connection ");
  		         
  		         Statement stmt = conn.createStatement();
  		      
  		         String strSelect = "select TID ,TrainName from Train";
  		          
  		         System.out.println("The SQL query is: " + strSelect); // Echo For debugging
  		       
  		         ResultSet rset = stmt.executeQuery(strSelect);

  		         
  		         System.out.println("The records selected are:");
  		         int rowCount = 0;
  		         while(rset.next()) {   // Move the cursor to the next row
  		            String tname = rset.getString("TrainName");
  		            String ttid = rset.getString("TID");
  		            
  		            ++rowCount;
  		         }
  		         System.out.println("Total number of records = " + rowCount);
  		         
  		         rset.close();
  		         conn.close();
  		         
  		      } catch(SQLException | ClassNotFoundException   ex) {
  		         ex.printStackTrace();
  		      }
  		      // Step 5: Close the resources - Done automatically 
  	}

  


}