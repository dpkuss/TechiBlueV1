/*
 * Copyright 2014 IBM Corp. All Rights Reserved
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.techiblue.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.test.UiThreadTest;
import android.util.Log;
import android.widget.ArrayAdapter;
import bolts.Continuation;
import bolts.Task;

import com.ibm.mobile.services.core.IBMBluemix;
import com.ibm.mobile.services.data.IBMData;
import com.ibm.mobile.services.push.IBMPush;
import com.ibm.mobile.services.push.IBMPushNotificationListener;
import com.ibm.mobile.services.push.IBMSimplePushNotification;

public final class PushNotificationApps extends Application {
	

	public static final int EDIT_ACTIVITY_RC = 1;
	public static IBMPush push = null;
	private Activity mActivity;
	 
	private static final String deviceAlias = "TechiBlue21";		
	private static final String consumerID = "TechiBlue21";	
	private static final String CLASS_NAME = PushNotificationApps.class.getSimpleName();
	private static final String APP_ID = "applicationID";
	private static final String APP_SECRET = "applicationSecret";
	private static final String APP_ROUTE = "applicationRoute";
	private static final String PROPS_FILE = "bluelist.properties";

	private IBMPushNotificationListener notificationListener = null;
    List<String> itemList;
    
    private boolean activitystate =false;
    
    
	
	public List<String> getItemList() {
		return itemList;
	}
	
	
	public void addItem(String svalue)
	{
		System.out.println("list updated with the new value " + svalue);
		
		itemList.add(svalue);
		 
	
	}
	
	public void removeItem(String svalue)
	{
		System.out.println("list updated with the new value " + svalue);
		
		itemList.remove(svalue);
		 
	
	}
	public PushNotificationApps() {
		
		System.out.println("::::::::::::::::::: when this called .............. ");
		registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
			@Override
			public void onActivityCreated(Activity activity,Bundle savedInstanceState) {
				Log.d(CLASS_NAME, "Activity created: " + activity.getLocalClassName());
				mActivity = activity;
				activitystate=true;
				// Define IBMPushNotificationListener behavior on push notifications.
				lookNotification();
			}
			@Override
			public void onActivityStarted(Activity activity) {
				mActivity = activity;
				Log.d(CLASS_NAME, "Activity started: " + activity.getLocalClassName());
				activitystate=true;
			}
			@Override
			public void onActivityResumed(Activity activity) {
				
				mActivity = activity;
				activitystate=true;
				Log.d(CLASS_NAME, "Activity resumed: " + activity.getLocalClassName());
				if (push != null) {
					push.listen(notificationListener);
				}
				
			}
			@Override
			public void onActivitySaveInstanceState(Activity activity,Bundle outState) {
				Log.d(CLASS_NAME, "Activity saved instance state: " + activity.getLocalClassName());
				activitystate=false;
			}
			@Override
			public void onActivityPaused(Activity activity) {
				/*if (push != null) {
					push.hold();
				}
				Log.d(CLASS_NAME, "Activity paused: " + activity.getLocalClassName());
				if (activity != null && activity.equals(mActivity))
		    		mActivity = null;
				*/
				activitystate =false;
				lookNotification();
				
			}
			@Override
			public void onActivityStopped(Activity activity) {
				activitystate =false;
				Log.d(CLASS_NAME, "Activity stopped: " + activity.getLocalClassName());
				lookNotification();
			}
			@Override
			public void onActivityDestroyed(Activity activity) {
				activitystate =false;
				Log.d(CLASS_NAME, "Activity destroyed: " + activity.getLocalClassName());
			}
		});
	}
	
	/**
	 * (non-Javadoc)
	 * Called when the application is starting, before any activity, service, 
	 * or receiver objects (excluding content providers) have been created.
	 * 
	 * @see android.app.Application#onCreate()
	 * 
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		
		 deviceDetl.getSingletonObject().setYourVar("TechiBlue");
		    

		itemList = new ArrayList<String>(); 	
		
	
		// Read from properties file.
		Properties props = new java.util.Properties();
		Context context = getApplicationContext();
		try {
			AssetManager assetManager = context.getAssets();					
			props.load(assetManager.open(PROPS_FILE));
			Log.i(CLASS_NAME, "Found configuration file: " + PROPS_FILE);
		} catch (FileNotFoundException e) {
			Log.e(CLASS_NAME, "The bluelist.properties file was not found.", e);
		} catch (IOException e) {
			Log.e(CLASS_NAME, "The bluelist.properties file could not be read properly.", e);
		}
		Log.i(CLASS_NAME, "Application ID is: " + props.getProperty(APP_ID));

		// Initialize the IBM core backend-as-a-service.
		IBMBluemix.initialize(this, props.getProperty(APP_ID), props.getProperty(APP_SECRET), props.getProperty(APP_ROUTE));
	    // Initialize the IBM Data Service.
	    IBMData.initializeService();
		// Register Item Specialization here.
	    Item.registerSpecialization(Item.class);
		// Initialize IBM Push service.
		IBMPush.initializeService();
		// Retrieve instance of the IBM Push service.
		push = IBMPush.getService();
		// Register the device with the IBM Push service.
		
		
		push.register(deviceAlias, consumerID).continueWith(new Continuation<String, Void>() {
        
			@Override
			public Void then(Task<String> task) throws Exception {
				
				if (task.isFaulted()) {
					Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
					System.out.println("::::::::::::: Device not registered ");
					return null;
				}
				Log.d(CLASS_NAME, "Device Successfully Registered");
				System.out.println("::::::::::::: Device   registered ");
				
				return null;
			}
			
		});
			
		 
	}
	
	 
	
	 public void onNewIntent(Intent intent){
	/*	 System.out.println("::::::::::::::::: new intent ");
	     Bundle extras = intent.getExtras();
	     if(extras != null){
	         if(extras.containsKey("NotificationMessage"))
	         {	             
	             
	             System.out.println(":::::::::::::::::::: onnew intenet" +extras.getString("NotificationMessage"));
	          
					 
	         }


	 }*/
	 }
	 
	 public void chkActivityState(String message)
	 {
		final  String msg = message;
		 
		itemList.add(msg);
		
		Class<? extends Activity> actClass = mActivity.getClass();
		if (actClass == ViewNotificationDetails.class) {
			 mActivity.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						Class<? extends Activity> actClass = mActivity.getClass();
						if (actClass == ViewNotificationDetails.class) {
							//((ViewNotificationDetails)mActivity).listItems();
							 
							// Present the message when sent from Push notification console.
							//if(!message.getAlert().contains("ItemList was updated")){								
								mActivity.runOnUiThread(new Runnable() {
									public void run() {
										 
									 
										System.out
												.println(":::::::::::::: notificaiton received " +msg);
									 
										((ViewNotificationDetails) mActivity).setMessageP(msg); 
									}
								});
								
							//}											
						}
					}
				});
		}
		else
			{
			
			System.out.println("Activity is not in foreground .. so send the notificaiton ");
			 long when = System.currentTimeMillis();
			NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification(R.drawable.icon, message, when);
			String title = getApplicationContext().getString(R.string.app_name);
			Intent notificationIntent = new Intent(getApplicationContext(), ViewNotificationDetails.class);
			notificationIntent.putExtra("NotificationMessage", message);
			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
 
			notification.setLatestEventInfo(getApplicationContext(), title, message, intent);
 //  notification.vibrate = new long[] { 500, 500 };
			notification.sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

			notification.flags = 
			    Notification.FLAG_AUTO_CANCEL;

			notificationManager.notify(0, notification);
			}
	   
			 
		  }
	
	public void lookNotification()
	{
		
		System.out.println(":::::::::  look for notification ");
		notificationListener = new IBMPushNotificationListener() {
			@Override
			public void onReceive(final IBMSimplePushNotification message) {
				
				System.out.println(":::::::::onReceive --  look for notification " +message);
				
				 
			    chkActivityState(message.getAlert());	
				 
				 
			}					
		};
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	public static final int EDIT_ACTIVITY_RC = 1;
	public static IBMPush push = null;
	 
	private Intent notificationIntent;
 	private Activity mActivity;
	private static final String deviceAlias = "NSMBDMapConsumer";		
	private static final String consumerID = "NSMBDMapDevice";	
	private static final String CLASS_NAME = PushNotificationApps.class.getSimpleName();
	private static final String APPID = "19a4f85a-6a77-4c25-8fae-f3a258d40a3d";
	private static final String APP_SECRET = "44b2951b98a294c02854c440f00851f99d648a4c";
	private static final String APP_ROUTE = "BlueServiceDemo.mybluemix.net";
	//private static final String PROPS_FILE = "bluelist.properties";	
	
	
 
	private IBMPushNotificationListener notificationListener = null;
	private IBMPushIntentService intentService =null;
	static List<String> itemList;
	
	
	MyObserver observerObj =null;
	 
	 
	   
	public PushNotificationApps() {
		
		
		
		registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
			@Override
			public void onActivityCreated(Activity activity,Bundle savedInstanceState) {
				 
				
				
				Log.d(CLASS_NAME, "Activity created: " + activity.getLocalClassName());
				mActivity = activity;
				 	//Define IBMPushNotificationListener behavior on push notifications
				//Define IBMPushNotificationListener behavior on push notifications
				notificationListener = new IBMPushNotificationListener() {
					@Override
					public void onReceive(final IBMSimplePushNotification message) {
						mActivity.runOnUiThread(new Runnable(){
							@Override
							public void run() {
								Class<? extends Activity> actClass = mActivity.getClass();
								if (actClass == ViewNotificationDetails.class) {
									//((ViewNotificationDetails)mActivity).listItems();
									Log.e(CLASS_NAME, "Notification message received: " + message.toString());
									//present the message when sent from Push notification console.
									if(!message.getAlert().contains("ItemList was updated")){								
										mActivity.runOnUiThread(new Runnable() {
											public void run() {
												
												
												
											   itemList.add(message.getAlert());	
												
											 	 
												System.out.println(" Application is in forground");
												((ViewNotificationDetails) mActivity).setMessageP(message.getAlert());
										 
											}
										});
										
									}											
								}
							}
						});
					}					
				};
			}
			@Override
			public void onActivityStarted(Activity activity) {
				
				mActivity = activity;
				Log.d(CLASS_NAME, "Activity started: " + activity.getLocalClassName());
				lookForNotification();
			}
			@Override
			public void onActivityResumed(Activity activity) {
				mActivity = activity;
				 
				System.out.println("::::::::::  ACTVITY RESUMNED ");
				lookForNotification();
				
				Log.d(CLASS_NAME, "Activity resumed: " + activity.getLocalClassName());
				
				if (push != null) {
					push.listen(notificationListener);
				}
			}
			@Override
			public void onActivitySaveInstanceState(Activity activity,Bundle outState) {
				
				System.out.println("::::::::::::: sAVE INSTA..");
				Log.d(CLASS_NAME, "Activity saved instance state: " + activity.getLocalClassName());
			lookForNotification();
				
			}
			@Override
			public void onActivityPaused(Activity activity) {
				mActivity = activity;
				System.out.println("::::::::::  ACTVITY PASSED ");
				
				 if (push != null) {
					 System.out.println("::::::::::::::: Actvity is passed.....");
					push.hold();				 
		 			
					 
				 }
				
				
				if (push != null) {
					push.listen(notificationListener);
				}
				
				lookForNotification();		
				  		 	}
			@Override
			public void onActivityStopped(Activity activity) {
				Log.d(CLASS_NAME, "Activity stopped: " + activity.getLocalClassName());
				mActivity = activity;
				System.out.println("::::::::::  ACTVITY STOPED ");
		           //
				if (push != null) {
					push.listen(notificationListener);
				}
				 
				
				
				//
			}
			@Override
			public void onActivityDestroyed(Activity activity) {
				Log.d(CLASS_NAME, "Activity destroyed: " + activity.getLocalClassName());
				System.out.println("::::::::::  ACTVITY DESTROEDSS ");
			}
			
			
		});
	}
	
	
	   public MyObserver getObserver() {
           return observerObj;
       }
	
	@Override
	public void onCreate() {
		
		 
		 
		itemList = new ArrayList<String>(); 	
		
	

		super.onCreate();
	 
		// Read from properties file
		Properties props = new java.util.Properties();
		Context context = getApplicationContext();
		try {
			AssetManager assetManager = context.getAssets();					
			props.load(assetManager.open(PROPS_FILE));
			Log.i(CLASS_NAME, "Found configuration file: " + PROPS_FILE);
		} catch (FileNotFoundException e) {
			Log.e(CLASS_NAME, "The bluelist.properties file was not found.", e);
		} catch (IOException e) {
			Log.e(CLASS_NAME, "The bluelist.properties file could not be read properly.", e);
		}
		Log.i(CLASS_NAME, "Application ID is: " + props.getProperty(APP_ID));

		// initialize the IBM core backend-as-a-service
		IBMBluemix.initialize(this, this.APPID, this.APP_SECRET, this.APP_ROUTE);
	    // initialize the IBM Data Service
	    IBMData.initializeService();
		// register Item Specialization here
	    Item.registerSpecialization(Item.class);
		// initialize IBM Push service
		IBMPush.initializeService();
		// retrieve instance of the IBM Push service
		push = IBMPush.getService();
		// register the device with the IBM Push service
		
		
		push.register(deviceAlias, consumerID).continueWith(new Continuation<String, Void>() {

			@Override
			public Void then(Task<String> task) throws Exception {
				
				if (task.isFaulted()) {
					Log.e(CLASS_NAME, "Exception : " + task.getError().getMessage());
					return null;
				}
				Log.d(CLASS_NAME, "Device Successfully Registered");
				
				return null;
			}

	
			//@Override
			public Void then(Task<String> arg0) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		
		push.listen(notificationListener);
	}
	
 
	
	 
	
	
	
	public class MyCustomReceiver extends IBMPushBroadcastReceiver {
		private static final String TAG = "MyCustomReceiver";
		 
		
		
		
		  @Override
		  public void onReceive(Context context, Intent intent) {
			  
			 
			  System.out.println("::::::::::::::::::: broadcast");
		    try {
		      String action = intent.getAction();
		      String channel = intent.getExtras().getString("com.parse.Channel");
		      JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
		 
		      Log.d(TAG, "got action " + action + " on channel " + channel + " with:");
		      Iterator itr = json.keys();
		      while (itr.hasNext()) {
		        String key = (String) itr.next();
		        Log.d(TAG, "..." + key + " => " + json.getString(key));
		      }
		    } catch (JSONException e) {
		      Log.d(TAG, "JSONException: " + e.getMessage());
		    }
		  }
 		}
	
	*//**
	 * returns the itemList, an array of Item objects.
	 * @return itemList
	 *//*
	public List<String> getItemList() {
		return itemList;
	}
	
	
	public void addItem(String svalue)
	{
		System.out.println("list updated with the new value " + svalue);
		
		itemList.add(svalue);
		 
	
	}
	
	public void removeItem(String svalue)
	{
		System.out.println("list updated with the new value " + svalue);
		
		itemList.remove(svalue);
		 
	
	}
    
    private static void generateNotification(Context context, IBMSimplePushNotification splMsg , Intent notificationIntent) {
	     int icon = android.R.drawable.btn_star_big_on;
	     long when = System.currentTimeMillis();
	   	 
	   	
	     
	    System.out.println(":::::::::generateNotification:::::: IBMPushIntentService.isAppForeground() " + IBMPushIntentService.isAppForeground());
	 	
		if(IBMPushIntentService.isAppForeground() )
		{
			System.out.println("Just add as the appli is in forground");
			itemList.add(splMsg.getAlert());
		    	 
		}
		
		else
		{
			 IBMPushIntentService.setAppForeground(true);
				itemList.add(splMsg.getAlert());
			System.out.println("send nottificaiton as the app is in back ground ");
			 
	 	String message = splMsg.getAlert();
	 	  String title = context.getString(R.string.app_name);
	 	  
	 	 PendingIntent intent =PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		    
	 	  
	    Notification notification = new Notification(icon, message, when);
	    notification.setLatestEventInfo(context, title, message, intent);
	    notification.flags = Notification.FLAG_AUTO_CANCEL;
	   

	    // set intent so it does not start a new activity
	    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
	            Intent.FLAG_ACTIVITY_SINGLE_TOP);
	    
	    
	    NotificationManager notificationManager = (NotificationManager)
	            context.getSystemService(Context.NOTIFICATION_SERVICE);
	    notificationManager.notify(0, notification);
	    
		}
	 }
    
    public void lookForNotification()
    {
    	 
    	
    	System.out.println(" inside lookgin for the mssage handler ..");
    	
 	    	    	    	    	
    	
		notificationListener = new IBMPushNotificationListener() {				
						
			public void onReceive(final IBMPushMessage message) {
					 			        
				mActivity.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						Class<? extends Activity> actClass = mActivity.getClass();
						if (actClass == ViewNotificationDetails.class) {						   
							System.out.println("Response received IBMPushMessage.Uma.........." +message.getAlert() );
							  
						 							
									itemList.add(message.getAlert());	
									
									Intent intent = mActivity.getIntent();
								 
									intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
									 
									startActivity(intent);
								 								
						}
					}
				});
				
				 
			}			
						 
			@Override
			public void onReceive(final IBMSimplePushNotification message) {
					 			        
				mActivity.runOnUiThread(new Runnable(){
					@Override
					public void run() {
						Class<? extends Activity> actClass = mActivity.getClass();
						if (actClass == ViewNotificationDetails.class) {
						   
							System.out.println("Response received .Uma.........." +message.getAlert() + "  before adding "+itemList.size() );
							         
						 							
							itemList.add(message.getAlert());	
								
									if(IBMPushIntentService.isAppForeground())
									{
										 
										System.out.println(" Application is in forground");
										((ViewNotificationDetails) mActivity).setMessageP(message.getAlert());
									}
									else
									{
									
									System.out.println(IBMPushIntentService.isAppForeground() + " i am in after setting ......." +itemList.size() );
									
									Intent intent = mActivity.getIntent();
									System.out.println("Response received .Uma.........." + mActivity.getLoaderManager().toString());
									
								 
									intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									System.out.println("Response received .FLAG_ACTIVITY_NO_ANIMATION.........." +message.getAlert() );
									
									 
									startActivity(intent);
									System.out.println("Response received .start actviity.........." +message.getAlert() );
									}
								 								
						}
					}
				});
				
				 
			}					//
		};
    }
    
      
	public void onReceive(IBMSimplePushNotification arg0) {
		// TODO Auto-generated method stub
		 
		System.out.println(":::::::::::::::::::::::Uma's work.......:::::::: " + arg0.getAlert());
	}
	
	public void onActivityCreated(Activity activity,Bundle savedInstanceState) {
		Log.d(CLASS_NAME, "Activity created: " + activity.getLocalClassName());
		mActivity = activity;
					
	 
	}

*/}
 
/*
UMASANKARI KANNAN started screen sharing (st://jrkumar@in.ibm.com:1405069229574) RAJESH K. J2:29 PMif (env.containsKey("VCAP_SERVICES")) {
	// we are running on cloud foundry, let's grab the service details from vcap_services
	JSONParser parser = new JSONParser();
	JSONObject vcap = (JSONObject) parser.parse(env.get("VCAP_SERVICES"));
	JSONObject service = null;
	
	// We don't know exactly what the service is called, but it will contain "postgresql"
	for (Object key : vcap.keySet()) {
		String keyStr = (String) key;
		if (keyStr.toLowerCase().contains("postgresql")) {
			service = (JSONObject) ((JSONArray) vcap.get(keyStr)).get(0);
			break;
		}
	}
	
	if (service != null) {
		JSONObject creds = (JSONObject) service.get("credentials");
		String name = (String) creds.get("name");
		String host = (String) creds.get("host");
		Long port = (Long) creds.get("port");
		String user = (String) creds.get("user");
		String password = (String) creds.get("password");
		RAJESH K. J is recording this meeting*/