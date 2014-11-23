package com.techiblue.client;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

public class FifthActivity extends Activity {

   ProgressBar progressBar;
     ThreadProgress mThreadProgress;
     int progressValue = 0;
     
         
    @Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.tabtext5);
         progressBar = (ProgressBar) findViewById(R.id.progressBar1);
     }
     
     public void startMe(View v){
         // start progress bar
         progressValue = 0;
         progressBar.setProgress(progressValue);
         mThreadProgress = new ThreadProgress();
         mThreadProgress.start();
         
     }
     
     
     Handler handler = new Handler(new Callback() {

        @Override
         public boolean handleMessage(final Message msg) {
             runOnUiThread(new Runnable() {
                  public void run() {
                      progressBar.setProgress(msg.arg1);
                  }
              });
             return false;
         }
     });

    public class ThreadProgress extends Thread implements Runnable {
         
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
     
     
     public void stopMe(View v){
         
         mThreadProgress.interrupt();
         progressValue=0;
         progressBar.setProgress(progressValue);
         Log.d("unicorn","in stop me");

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