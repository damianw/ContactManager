package com.example.android.contactmanager;

import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyUserCallback;
import com.kinvey.java.User;

import android.app.Application;
import android.util.Log;

public class ContactManagerApp extends Application {
	// Declare the Kinvey client
    private Client mKinveyClient;
    
    public void onCreate() {
    	super.onCreate();
    	// Instantiate the Kinvey client
        mKinveyClient = new Client.Builder(this.getApplicationContext()).build();
        
        // Log the user in if they are not already logged in
        if (!mKinveyClient.user().isUserLoggedIn()) {
	        mKinveyClient.user().login(new KinveyUserCallback() {
	            @Override
	            public void onFailure(Throwable error) {
	                Log.e("LOGIN", "Login Failure", error);
	            }
	            @Override
	            public void onSuccess(User result) {
	                Log.i("LOGIN","Logged in a new implicit user with id: " + result.getId());
	            }
	        });
        }
    }
    
    public Client getClient() {
    	return mKinveyClient;
    }
}
