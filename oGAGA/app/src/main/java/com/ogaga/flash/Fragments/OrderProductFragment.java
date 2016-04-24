package com.ogaga.flash.Fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ogaga.flash.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderProductFragment extends DialogFragment {
    public interface OnOrderListener{
        void onOrder(Integer count);
    }
    private OnOrderListener orderListener;
    public void setOnOrderListener(OnOrderListener onSaveListener){

        this.orderListener=onSaveListener;
    }
    public OrderProductFragment() {
        // Required empty public constructor
    }

    @Bind(R.id.tvPrices)TextView tvPrices;
    @Bind(R.id.tvTotal)TextView tvTotal;
    @Bind(R.id.etOrderCount)EditText etOrderCount;
    @Bind(R.id.btnOrder)Button btnOder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_order_product, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.btnOrder)
    public void onOrderClick(View view){
        orderListener.onOrder(Integer.parseInt(etOrderCount.getText().toString()));
        getDialog().dismiss();
    }



}
