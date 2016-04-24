package com.ogaga.flash.Fragments;

import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.client.annotations.Nullable;
import com.ogaga.flash.adapters.CategoryAdapter;
import com.ogaga.flash.adapters.ProductAdapter;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.models.Product;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by carot on 4/21/2016.
 */
public class ProfileSallerFragment extends ProfileFragment {

    public static ProfileSallerFragment newInstance(User user) {
        ProfileSallerFragment fragment = new ProfileSallerFragment();
        Bundle bundle=new Bundle();
        bundle.putParcelable("user", Parcels.wrap(user));
        fragment.setArguments(bundle);
        return fragment;
    }

    Firebase mFirebase;
    User mUser;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, parent, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser=Parcels.unwrap(getArguments().getParcelable("user"));
        mFirebase= FirebaseClient.getProduct();
        Query query =mFirebase.orderByChild("id_user").equalTo(mUser.getId());
        addAll(query);
    }

}
