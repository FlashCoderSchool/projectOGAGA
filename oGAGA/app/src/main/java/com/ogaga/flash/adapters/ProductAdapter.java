package com.ogaga.flash.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.ogaga.flash.R;
import com.ogaga.flash.models.Product;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by carot on 4/23/2016.
 */
public class ProductAdapter extends FirebaseRecyclerAdapter<Product,ProductAdapter.ViewHolder> {


    private Context context;
    public ProductAdapter(Query ref, Context parentContext ) {
        super(Product.class, R.layout.product_item, ProductAdapter.ViewHolder.class, ref);
        context = parentContext;
    }
    @Override
    protected void populateViewHolder(ProductAdapter.ViewHolder viewHolder, Product product, int i) {
        viewHolder.tvProductName.setText(product.getName());
        //holder.ivImage.setImageResource(cate.getLocalImage());
        Picasso.with(context).load(product.getUrl()).placeholder(R.drawable.im_placeholder).into(viewHolder.ivProductImage);
        /*viewHolder.tvProductOrigin.setText(product.getUser().getAddress_user());*/
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivProductImage)ImageView ivProductImage;
        @Bind(R.id.tvProductName) TextView tvProductName;
        @Bind(R.id.tvProducer) TextView tvProducer;
        @Bind(R.id.tvProductOrigin) TextView tvProductOrigin;
        @Bind(R.id.tvProductPrice) TextView tvProductPrice;
        @Bind(R.id.ivProductStatus) ImageView ivProductStatusImage;
        @Bind(R.id.tvProductStatus) TextView tvProductStatus;
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
