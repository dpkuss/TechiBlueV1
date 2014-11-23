package  com.techiblue.client;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

 
 import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

 import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

 public class startNotificationService extends IntentService {

   private int result = Activity.RESULT_CANCELED;
   public static final String URL = "urlpath";
   public static final String FILENAME = "filename";
   public static final String FILEPATH = "filepath";
   public static final String RESULT = "result";
   public static final String NOTIFICATION = "com.vogella.android.service.receiver";

   public startNotificationService() {
     super("startNotificationService");
   }

   // will be called asynchronously by Android
   @Override
   protected void onHandleIntent(Intent intent) {
	   callWS();
   }

   
   public int onStartCommand(Intent intent, int flags, int startId) {
	    Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
	    return super.onStartCommand(intent,flags,startId);
	}
public void callWS()
{
	 
	 RequestParams params = new RequestParams();
	 
	 
	params.put("environment", "sandbox"); 
	 	 System.out.println("::::::::: param " + params);
	 
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
          //  prgDialog.hide();
            try {
            	
            	System.out.println(" Res Obje " +response );
                     // JSON Object
                    JSONObject obj = new JSONObject(response);
                    // When the JSON response has status boolean value assigned with true
                    System.out.println(" response ... " +response);
                    
                
                     
            } catch (JSONException e) {
                // TODO Auto-generated catch block
         
                e.printStackTrace();

            }
        }
        // When the response returned by REST has Http response code other than '200'
        @Override
        public void onFailure(int statusCode, Throwable error,
            String content) {
        	
        	System.out.println(content + " :::::: " + statusCode + " ::::: "+ error.getMessage());
             
        }
    });
	 

}




   private void publishResults(String outputPath, int result) {
  /*   Intent intent = new Intent(NOTIFICATION);
     intent.putExtra(FILEPATH, outputPath);
     intent.putExtra(RESULT, result);
     sendBroadcast(intent);*/
   }
 } 