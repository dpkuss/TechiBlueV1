/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */

package com.techiblue.client;



import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.ImageButton;
import android.widget.TabHost.TabSpec;
import android.widget.ViewSwitcher;
import android.view.View;
import android.view.View.OnClickListener;


import org.apache.cordova.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.loopj.android.http.*;


@SuppressWarnings("deprecation")
public class InitTabActivity extends Activity
{
	
	  ProgressDialog prgDialog;
	//Button button;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
			 
			
			 prgDialog = new ProgressDialog(this);
		        // Set Progress Dialog Text
		        prgDialog.setMessage("Please wait...");
		        // Set Cancelable as False
		        prgDialog.setCancelable(false);

			addListenerOnButton();

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
    prgDialog.show();
    // Make RESTful webservice call using AsyncHttpClient object
    
    AsyncHttpClient client = new AsyncHttpClient();
    
    System.out.println("Setting header et");
     client.addHeader("IBM-APPLICATION-SECRET", "6d34ee0664485d04c1ca6e5ee82246ba40725000");
     
    System.out.println("GCM COnfi");
    
    String setEn = "https://mobile.ng.bluemix.net/push/v1/apps/0a0038c9-1aa8-4e2f-898c-326c1c71d2d1";
    
    System.out.println("GCM COnfi" + setEn);
    
    https://mobile.ng.bluemix.net/push/v1/apps/0a0038c9-1aa8-4e2f-898c-326c1c71d2d1/settings/gcmConf?environment=sandbox/
    client.get(setEn,params ,new AsyncHttpResponseHandler() {
        // When the response returned by REST has Http response code '200'
        @Override
        public void onSuccess(String response) {
            // Hide Progress Dialog
            prgDialog.hide();
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
            prgDialog.hide();
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
		
		public void connectDB()
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
			            String empname = rset.getString("TrainName");
			           /* double price = rset.getDouble("price");
			            int    qty   = rset.getInt("qty");*/
			            System.out.println(empname + " --------- Emp name ");
			            ++rowCount;
			         }
			         System.out.println("Total number of records = " + rowCount);

			      } catch(SQLException | ClassNotFoundException   ex) {
			         ex.printStackTrace();
			      }
			      // Step 5: Close the resources - Done automatically 
		}

		
		
	 public void addListenerOnButton() {

		 
		 ImageButton elsBtn = (ImageButton) findViewById(R.id.ELstNotify);
		  		 
		 elsBtn.setOnClickListener(new OnClickListener() {
			 //SQL DBName -SQL Database-80
			@Override
			public void onClick(View arg0) {
				
				arg0.bringToFront();
				 
				System.out.println("Calling WS ");
				 
					 
				        	  callWS();

			}
			});
		 
			ImageButton button = (ImageButton) findViewById(R.id.ReqNewNotify);
			 
			button.setOnClickListener(new OnClickListener() {
				 //SQL DBName -SQL Database-80
				@Override
				public void onClick(View arg0) {
					
					arg0.bringToFront();
					 
					System.out.println("Inside on click ");
					 
					
					
					
					 new Thread(new Runnable(){
					      @Override
					       public void run() {
					          try {

					        	  connectDB();

					              } catch (Exception ex) {
					                      ex.printStackTrace();
					                }
					         } 
					     }).start();
					 
					 
					 
					 
					 
					 
					// callWS();
	 
					/*DefaultHttpClient httpclient = new DefaultHttpClient();

					HttpHost target = new HttpHost("", 80, "http");
				       */
				      // specify the get request
				   
				}  
	 
	});
	 


		} 
	 
	 
	 /*
	  * 
	  *     HttpGet getRequest = new HttpGet("/forecastrss?p=80020&u=f");
				 
				      System.out.println("executing request to " + target);
				 
				      HttpResponse httpResponse;
					try {
						httpResponse = httpclient.execute(target, getRequest);
					 
				      HttpEntity entity = httpResponse.getEntity();
				 
				      System.out.println("----------------------------------------");
				      System.out.println(httpResponse.getStatusLine());
				      Header[] headers = httpResponse.getAllHeaders();
				      for (int i = 0; i < headers.length; i++) {
				        System.out.println(headers[i]);
				      }
				      System.out.println("----------------------------------------");
				 
				      if (entity != null) {
				        System.out.println(EntityUtils.toString(entity));
				      }
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	  
	  */
	
	 
}
