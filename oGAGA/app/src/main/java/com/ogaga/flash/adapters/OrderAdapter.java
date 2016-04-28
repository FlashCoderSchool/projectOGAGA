package com.ogaga.flash.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.ogaga.flash.R;
import com.ogaga.flash.extra.Constant;
import com.ogaga.flash.models.Order;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Kanet on 4/25/2016.
 */
public class OrderAdapter  extends FirebaseRecyclerAdapter<Order,OrderAdapter.ViewHolder> {

    Context context;
    Firebase mRef;
    public OrderAdapter(Firebase ref, Context parentContext ) {
        super(Order.class, R.layout.order_item, OrderAdapter.ViewHolder.class, ref);
        context = parentContext;
        mRef=ref;
    }

    @Override
    protected void populateViewHolder(OrderAdapter.ViewHolder viewHolder, Order order, int i) {
        viewHolder.tvFullName.setText(order.getUser().getFullname());
        //holder.ivImage.setImageResource(cate.getLocalImage());
        Picasso.with(context).load(order.getUser().getProfile_image()).placeholder(R.drawable.vegetable).into(viewHolder.ivAvatar);
        viewHolder.tvPhonenumber.setText(order.getUser().getPhonenumber());
        viewHolder.tvOrderCount.setText(String.valueOf(order.getCount()));
        long order_total=order.getCount() * order.getPrices();
        viewHolder.tvOrderTotal.setText(String.valueOf(order_total));
        viewHolder.tvAdressuser.setText(order.getUser().getAddress_user());
        if (order.getId_shipping_status()== Constant.ORDER_SHIPPED)
            viewHolder.btnShipped.setVisibility(View.INVISIBLE);
        else
            viewHolder.btnShipped.setVisibility(View.VISIBLE);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Order mOrder;
        @Bind(R.id.ivAvatar)ImageView ivAvatar;
        @Bind(R.id.tvProductDetailDesciption)TextView tvFullName;
        @Bind(R.id.tvPhonenumber) TextView tvPhonenumber;
        @Bind(R.id.tvOrderCount) TextView tvOrderCount;
        @Bind(R.id.tvOrderTotal) TextView tvOrderTotal;
        @Bind(R.id.tvAddresuser) TextView tvAdressuser;
        @Bind(R.id.btnShippied) Button btnShipped;
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        public void bind(Order order){
            mOrder=order;
        }
        @OnClick(R.id.btnShippied)
        public void onShipped(View view){
  /*          mOrder.setId_shipping_status(Constant.ORDER_SHIPPED);
            mRef.child(String.valueOf(mOrder.getId()))*/
        }
    }
}
