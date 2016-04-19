package com.ogaga.flash.clients;

import android.content.Context;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.ogaga.flash.helpers.AuthorHelper;
import com.ogaga.flash.models.User;

/**
 * Created by Kanet on 4/12/2016.
 */
public class FirebaseClient {

    public static Firebase getRoot(){
        Firebase mFirebaseRef= new Firebase("https://ogaga.firebaseio.com");
        return  mFirebaseRef;
    }

    public static Firebase getCatalogies(){
        Firebase mFirebaseRef= new Firebase("https://ogaga.firebaseio.com").child("catalogies");
        return  mFirebaseRef;
    }

    public static Firebase getUsers(){
        Firebase mFirebaseRef= new Firebase("https://ogaga.firebaseio.com").child("users");
        return  mFirebaseRef;
    }

    public static User Login(Context context){
        String uid=AuthorHelper.readString(context, "uid");
        final User[] mUser = {new User()};
        mUser[0].getUserLogin(uid, new User.LoginUser() {
            @Override
            public void onLoginSuccess(User user) {
                mUser[0] = user;
            }
        });

        return mUser[0];
    }

}
