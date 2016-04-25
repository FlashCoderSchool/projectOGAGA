package com.ogaga.flash.fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.Firebase;
import com.ogaga.flash.R;
import com.ogaga.flash.adapters.CategoryAdapter;
import com.ogaga.flash.adapters.OrderAdapter;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.models.Product;
import com.ogaga.flash.utils.SpacesItemDecoration;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderViewFragment extends DialogFragment {


    public OrderViewFragment() {
        // Required empty public constructor
    }

    @Bind(R.id.rvOrders)RecyclerView rvOders;
    Product mProdcut;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_order_view, container, false);
        ButterKnife.bind(this,root);
        mProdcut= Parcels.unwrap(getArguments().getParcelable("product"));
        setupOrderView();
        return root;
    }

    private void setupOrderView() {
        Firebase fireOrders= FirebaseClient.getProduct().child(String.valueOf(mProdcut.getId())).child("orders");
        OrderAdapter orderAdapter = new OrderAdapter(fireOrders, getActivity().getApplicationContext());
        rvOders.setHasFixedSize(true);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rvOders.setLayoutManager(gridLayoutManager);
        rvOders.setItemAnimator(new SlideInUpAnimator());
        //RecyclerView.ItemDecoration itemDecoration =
        //       new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        //set space for each photo in recycleview
        SpacesItemDecoration itemDecoration = new SpacesItemDecoration(8);
        rvOders.addItemDecoration(itemDecoration);
        rvOders.setAdapter(orderAdapter);
    }

}
