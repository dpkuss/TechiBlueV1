 package com.techiblue.client;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import bolts.Continuation;
import bolts.Task;

 

import com.ibm.mobile.services.core.http.IBMHttpRequest;
import com.ibm.mobile.services.core.http.IBMMutableHttpRequest.IBMHttpMethod;
import com.ibm.mobile.services.core.service.IBMBluemixService;
import com.ibm.mobile.services.data.*;
import com.ibm.mobile.services.push.*;

  
public class ViewNotificationDetails extends ListActivity {
	   TextView selection;
	   public String toAdd ="";
	   public Editor ed ;
	   PushNotificationApps blApplication;
	   List<String> str = new ArrayList<String>();
	   public static IBMPush push = null;
	   private IBMPushNotificationListener notificationListener = null;
	   public String newNotification="";
	   public String  messageP ="";
	   public boolean mIsRunning=false; 
	   WebView myBrowser;
      public boolean stateAct =true;
		public static final String CLASS_NAME = "MainActivity";
		public static final String APP_NAME = "BlueServiceDemo";
		
	 	
		public boolean getStateAct()
		{
			return stateAct;
		}
		 
		@Override

		   public void onCreate(Bundle savedInstanceState) {
			   
		       super.onCreate(savedInstanceState);
		       
		       System.out.println(":::::::::::::::::::::::::::: this activity created ...");
		       
		      
	   	       setContentView(R.layout.viewnotify);
	   	   
	   	    

		       blApplication = (PushNotificationApps) getApplication();
		        
		       str = blApplication.getItemList();
				  
		       
		 
		   	    
		   	    
		setListAdapter(new ArrayAdapter<String>(
		      this,
		      android.R.layout.simple_expandable_list_item_1,
		      str));
		
	 	
		  onNewIntent(getIntent());

		   }

		  
	 
		public void setMessageP(String smsg)
		{
			
		  str =blApplication.getItemList();
          //  blApplication.addItem("setting P msg");
           setListAdapter(new ArrayAdapter<String>(
	    		      this,
	    		      android.R.layout.simple_expandable_list_item_1,str));
           
           
		}
 
		
	
	
@Override
protected void onPause() {
	// TODO Auto-generated method stub
	super.onPause();
	  onNewIntent(getIntent());
	  stateAct=false;
		 
}
 

@Override
protected void onRestart() {
	// TODO Auto-generated method stub
	super.onRestart();
	  System.out.println(":::::::::::restart  in view ");
	
	  onNewIntent(getIntent());
	 
}
	 
		@Override
		  protected void onResume() {
			
			
			  
			super.onResume();
			
			 System.out.println(":::::::::::onResume in view ");
			  str =blApplication.getItemList();
	          //  blApplication.addItem("setting P msg");
	           setListAdapter(new ArrayAdapter<String>(
		    		      this,
		    		      android.R.layout.simple_expandable_list_item_1,str));
	           
	           
			
			  onNewIntent(getIntent());

				 
		}
	   
		
	  
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		System.out.println(">>>>>>>>>> Remove ");
		/*  blApplication.removeItem(str.get(position));
	       str=blApplication.getItemList();
	      
	       
	       new AlertDialog.Builder(this)
	       .setTitle("Push notification received")
	       .setMessage("Details of the Alarm \n  CircuitID: XXX.XXX.XXXYYY \n Issue : PORT Down \n Assigned to : uk1935  \n Expected Fix date : Immediately")
	       .setPositiveButton(android.R.string.ok,
			    new DialogInterface.OnClickListener() {
	          public void onClick(DialogInterface dialog, int whichButton) {
	          }
	       })
	       .show();
	    		 */
	       setListAdapter(new ArrayAdapter<String>(
	    		      this,
	    		      android.R.layout.simple_expandable_list_item_1,
	    		      str));
	    		 
    		 
	}
	
	
	
 
	
	
	public void pushNotifyEnd(View v)
	{
		int cnt =0;
		
		if(str != null)
			cnt=str.size();
		 
		Intent phoneNumberInfo = new Intent();
		phoneNumberInfo.putExtra("countValue", cnt);
		setResult(RESULT_OK, phoneNumberInfo);
		finish();
		
	}
	public void createItem(View v) {
		EditText itemToAdd = (EditText) findViewById(R.id.itemToAdd);
		  toAdd = itemToAdd.getText().toString();
	 
		if (!toAdd.equals("")) {
		 
				 
			System.out.println("::::::::::::::: add inside list ");
			str.add(toAdd);
			setListAdapter(new ArrayAdapter<String>(
				      this,
				      android.R.layout.simple_expandable_list_item_1,
				      str));
			
		
		//Store the Data in the Cloud Mobile 
		Item item = new Item();
		item.setName(toAdd);
		/**
		 * IBMObjectResult is used to handle the response from the server after 
		 * either creating or saving an object.
		 * 
		 * onResult is called if the object was successfully saved
		 * onError is called if an error occurred saving the object 
	 */
		
		
		item.save().continueWith(new Continuation<IBMDataObject, Void>() {

			@Override
			public Void then(Task<IBMDataObject> task) throws Exception {

				 // Log error message, if the save task fail.
				if (task.isFaulted()) {
					Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
					return null;
				}

				 // If the result succeeds, load the list
				if (!isFinishing()) {
					//listItems();
					//updateOtherDevices();
				}
				return null;
			}

		});
		}
		itemToAdd.setText("");
	}
	
	
 
	 
	public void listenNotification()
	{
		 System.out.println(" checking notification");
		notificationListener = new IBMPushNotificationListener() {
					@Override
					public void onReceive(final IBMSimplePushNotification message) {
						 
						
						 System.out.println(" checking notification");
							 
						
									  System.out.println("Response received .Uma.........." +message.getAlert() );
									  
									    newNotification = message.getAlert() ;
									    
										if (!newNotification.equals("")) {
											str.add(newNotification);
											
											setListAdapter(new ArrayAdapter<String>(
													getBaseContext(),
												      android.R.layout.simple_expandable_list_item_1,
												      str));
										}
					}
				};
								 
											 									 
				 
	}
	
	
	public Editor isActiveActivity()
	{
		return ed;
	}
	
	
 
	
	@Override
	public void onStart() { 
	super.onStart(); 
	  System.out.println(":::::::::::start in view ");

     
	}

	 @Override
	public void onStop() { 
		 
		 
	super.onStop(); 
	 
	  System.out.println(":::::::::::onstop in view ");
	}
	 
	 @Override
	 public void onNewIntent(Intent intent){
	/*	 System.out.println("::::::::::::::::: new intent ");
	     Bundle extras = intent.getExtras();
	     if(extras != null){
	         if(extras.containsKey("NotificationMessage"))
	         {	             
	             
	             System.out.println(":::::::::::::::::::: onnew intenet" +extras.getString("NotificationMessage"));
	         	str.add(extras.getString("NotificationMessage"));
				setListAdapter(new ArrayAdapter<String>(
					      this,
					      android.R.layout.simple_expandable_list_item_1,
					      str));
					 
	         }


	 }*/
	 }
	 
	/* public class MyJavaScriptInterface {
		  Context mContext;

		     MyJavaScriptInterface(Context c) {
		         mContext = c;
		     }
	 }*/
		    

	 
		public void listItems() {
			try {
				IBMQuery<Item> query = IBMQuery.queryForClass(Item.class);
				/**
				 * IBMQueryResult is used to receive array of objects from server.
				 * 
				 * onResult is called when it successfully retrieves the objects associated with the 
				 * query, and will reorder these items based on creation time.
				 * 
				 * onError is called when an error occurs during the query.
				 */
				query.find().continueWith(new Continuation<List<Item>, Void>() {

					@Override
					public Void then(Task<List<Item>> task) throws Exception {
						 // Log error message, if the save task fails.
						if (task.isFaulted()) {
							Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
							return null;
						}
						final List<Item> objects = task.getResult();
						
						 // If the result succeeds, load the list.
						if (!isFinishing()) {
							runOnUiThread(new Runnable() {
								public void run() {
									Editor itemList = null;
									// Clear local itemList, as we'll be reordering & repopulating from DataService.
									itemList.clear();
									for(IBMDataObject item:objects) {
										//itemList.add((Item) item);
										
									}
									
								}
							});
						}
						return null;
					}
				});
				
			}  catch (IBMDataException error) {
				Log.e(CLASS_NAME, "Exception : " + error.getMessage());
			}
		}

	 
	}