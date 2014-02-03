package com.example.android.contactmanager;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;
import com.kinvey.java.model.KinveyMetaData;

public class ContactEntity extends GenericJson {
    @Key("_id")
    private String id; 
    @Key
    private String name;
    @Key
    private String phone;
    @Key
    private int phoneType;
    @Key
    private int emailType;
    @Key
    private String email;
    @Key
    private String owner_email;
    @Key 
    private String owner_username;
    @Key("_kmd")
    private KinveyMetaData meta; // Kinvey metadata, OPTIONAL
    @Key("_acl")
    private KinveyMetaData.AccessControlList acl; //Kinvey access control, OPTIONAL
    public ContactEntity(){}  //GenericJson classes must have a public empty constructor
}