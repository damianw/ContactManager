/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.contactmanager;

import java.util.ArrayList;

import android.app.Activity;

import com.kinvey.android.AsyncAppData;
import com.kinvey.android.Client;
import com.kinvey.android.callback.KinveyListCallback;
import com.kinvey.android.callback.KinveyPingCallback;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public final class ContactManager extends Activity
{

    public static final String TAG = "ContactManager";

    private Button mAddAccountButton;
    private ListView mContactList;
    
    private ContactManagerApp mApp;

    /**
     * Called when the activity is first created. Responsible for initializing the UI.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.v(TAG, "Activity State: onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_manager);
        
        // Obtain handles to UI objects
        mAddAccountButton = (Button) findViewById(R.id.addContactButton);
        mContactList = (ListView) findViewById(R.id.contactList);
        
        // Grab the application
        mApp = (ContactManagerApp) getApplication();
        
        // Test the Kinvey client's connection
        mApp.getClient().ping(new KinveyPingCallback() {
            public void onFailure(Throwable t) {
                Log.e(TAG, "Kinvey Ping Failed", t);
                Toast.makeText(ContactManager.this,
                		"Kinvey Ping Failed",
                		Toast.LENGTH_SHORT).show();
            }
            public void onSuccess(Boolean b) {
                Log.d(TAG, "Kinvey Ping Success");
                Toast.makeText(ContactManager.this,
                		"Kinvey Ping Success",
                		Toast.LENGTH_SHORT).show();
            }
        });

        // Register handler for UI elements
        mAddAccountButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "mAddAccountButton clicked");
                launchContactAdder();
            }
        });

        // Populate the contact list
        populateContactList();
    }

    /**
     * Populate the contact list based on account currently selected in the account spinner.
     */
    private void populateContactList() {
        // Build adapter with contact entries
    	AsyncAppData<ContactEntity> myevents = mApp.getClient().appData("contacts", ContactEntity.class);
    	myevents.get(new KinveyListCallback<ContactEntity>()     {
    	  @Override
    	  public void onSuccess(ContactEntity[] result) { 
    	    Log.v(TAG, "received "+ result.length + " events");
    	    ArrayList<String> names = new ArrayList<String>();
    	    for (ContactEntity entity : result) {
    	    	names.add((String)entity.get("name"));
    	    }
    	    ArrayAdapter adapter = new ArrayAdapter(ContactManager.this,  android.R.layout.simple_list_item_1, names.toArray());
    	    mContactList.setAdapter(adapter);
    	  }
    	  @Override
    	  public void onFailure(Throwable error)  { 
    	    Log.e(TAG, "failed to fetch all", error);
    	  }
    	});
    }


    /**
     * Launches the ContactAdder activity to add a new contact to the selected accont.
     */
    protected void launchContactAdder() {
        Intent i = new Intent(this, ContactAdder.class);
        startActivity(i);
    }
}
