package com.techiblue.client;

import android.app.Activity;
import android.app.ProgressDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class TimerActivity extends Activity {  CheckBox optSingleShot;
Button btnStart, btnCancel;
TextView textCounter;
ProgressDialog prgDialog;
Timer timer;
MyTimerTask myTimerTask;

@Override
protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.timerlayout);
 optSingleShot = (CheckBox)findViewById(R.id.singleshot);
 btnStart = (Button)findViewById(R.id.start);
 btnCancel = (Button)findViewById(R.id.cancel);
 textCounter = (TextView)findViewById(R.id.counter);
  
 prgDialog = new ProgressDialog(this);
 btnStart.setOnClickListener(new OnClickListener(){

  @Override
  public void onClick(View arg0) {

   if(timer != null){
    timer.cancel();
   }
   
   new sendNotification().start();
   
   //re-schedule timer here
   //otherwise, IllegalStateException of
   //"TimerTask is scheduled already" 
   //will be thrown
   timer = new Timer();
   myTimerTask = new MyTimerTask();
   
   if(optSingleShot.isChecked()){
    //singleshot delay 1000 ms
    timer.schedule(myTimerTask, 1000);
   }else{
    //delay 1000ms, repeat in 5000ms
    timer.schedule(myTimerTask, 1000, 20000);
   }
  }});
 
 btnCancel.setOnClickListener(new OnClickListener(){

  @Override
  public void onClick(View v) {
   if (timer!=null){
    timer.cancel();
    timer = null;
    textCounter.setText("");
   }
  }
 });
 
}



class MyTimerTask extends TimerTask {

 @Override
 public void run() {
  Calendar calendar = Calendar.getInstance();
  SimpleDateFormat simpleDateFormat =  new SimpleDateFormat("dd:MMMM:yyyy HH:mm:ss a");
  final String strDate = simpleDateFormat.format(calendar.getTime());
  System.out.println(":::::::::::: send push notificaitons ");
 
  new sendNotification().start();
  runOnUiThread(new Runnable(){

   @Override
   public void run() {
	   
    textCounter.setText(strDate);
     
   }});
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
       
       
	/*
	new Thread(new Runnable() {
		public void run() {
	        // Start service
			callWS();
	    }
	}).start();
	*/
	
	/*
	
	 new AsyncTask<Void, Void, String>() {

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
            // showProgressDialog("Please Wait...");
         }

         @Override
         protected String doInBackground(Void... params) {
        	 
        callWS();
        	
        	  return "S";
         }

         @Override
         protected void onPostExecute(String result) {
             super.onPostExecute(result);
          
             
              
         }
     }.execute();
*/     
//}

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
}








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
 

package com.techiblue.client;

 
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
public class FifthActivity extends Activity
{
	
	 
	//public ThreadProgress mThreadProgress;
     int progressValue = 0;
	public  ProgressBar progressBar;
	public int progressStatus = 0;
	public TextView textView;
	public Handler handler = new Handler();

	 private Button btnReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabtext5);
 
        TextView txtView = (TextView) findViewById(R.id.txtDisplayedTab5);
        txtView.setText("Simulatore ");
        
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
                
        
        btnReset = (Button) findViewById(R.id.btnreset);
        
        

        btnReset.setOnClickListener(new OnClickListener() {

    	  @Override
    	  public void onClick(View v) {

    	   progressBar.setProgress(0);
    	   showProgress();
    	  }

    	});
     
        textView = (TextView) findViewById(R.id.textView1);
        // Start long running operation in a background thread
        showProgress();
       }
    
    
    
    public void showProgress()
    {
    	
    	
    	runOnUiThread(new Runnable() {
            public void run() {
                progressBar.setProgress(progressValue);
            }
        }); 
    	
    	
    	
    	 new Thread(new Runnable() {
             public void run() {
                while (progressStatus < 100) {
                   progressStatus += 1;
            // Update the progress bar and display the 
                                 //current value in the text view
            handler.post(new Runnable() {
            public void run() {
               progressBar.setProgress(progressStatus);
               
               textView.setText(progressStatus+"/"+progressBar.getMax());
            }
                });
                try {
                	
                	 if(progressBar.getMax() == 20)
                     {
                		 System.out.println("::::::::: initiate backend process ");
                    	 Toast.makeText(getApplicationContext()," Trigger Notification in backend   : " ,Toast.LENGTH_SHORT).show();
         		    	
                     }
                   // Sleep for 200 milliseconds. 
                                 //Just to display the progress slowly
                	
                 	 
     					// sleeping for 1 second after operation completed
     					try {
     						Thread.sleep(1000);
     						
     					} catch (InterruptedException e) {e.printStackTrace();}
      
     					// close the progress bar dialog
     					 
     				
                } catch (Exception e) {
                   e.printStackTrace();
                }
                
                
             }
          }
             
             
          }).start();
    	 
    	 
    }
public void onResume()
{
	
	super.onResume();
	
	progressBar.setProgress(0);
	showProgress();
      
    }



public void startMe(View v){
    // start progress bar
    progressValue = 0;
    progressBar.setProgress(progressValue);
    mThreadProgress = new ThreadProgress();
    mThreadProgress.start();
    
}


   

public void stopMe(View v){
    
  //  mThreadProgress.interrupt();
    progressValue=0;
    progressBar.setProgress(progressValue);
 //   Log.d("unicorn","in stop me");

}



}


 


class ThreadProgress extends Thread implements Runnable {
  
  @Override
  public void run() {
          
       while( progressValue <= 15 && !mThreadProgress.isInterrupted()) {
                try{
                    Log.d("unicorn","in while loop");
                            progressValue++;
                            Message message = new Message();
                            message.arg1 = progressValue;
                            handler.sendMessage(message);
                            Thread.sleep(1000);
                          } catch (InterruptedException e){
                                  e.printStackTrace();
                                  break;
                          }
          }
  }

}    

*/