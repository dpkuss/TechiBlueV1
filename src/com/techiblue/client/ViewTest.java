package com.techiblue.client;

import java.util.concurrent.ExecutorService;

import org.apache.cordova.Config;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

 


public class ViewTest extends Activity implements CordovaInterface {
    CordovaWebView cwv;
    /* Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  setContentView(R.layout.main);
        cwv = (CordovaWebView) findViewById(R.id.tutorialView);
        Config.init(this);
        cwv.loadUrl(Config.getStartUrl());*/
    }
	@Override
	public Activity getActivity() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ExecutorService getThreadPool() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object onMessage(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setActivityResultCallback(CordovaPlugin arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void startActivityForResult(CordovaPlugin arg0, Intent arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}