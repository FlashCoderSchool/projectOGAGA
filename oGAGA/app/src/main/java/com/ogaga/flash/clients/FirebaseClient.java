package com.ogaga.flash.clients;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
/**
 * Created by Kanet on 4/12/2016.
 */
public class FirebaseClient {

    public static Firebase getCatalogies(){
        Firebase mFirebaseRef= new Firebase("https://ogaga.firebaseio.com").child("catalogies");
        return  mFirebaseRef;
    }


}
