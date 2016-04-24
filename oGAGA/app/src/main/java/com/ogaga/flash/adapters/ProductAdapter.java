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
        Picasso.with(context).load(product.getUrl()).into(viewHolder.ivPicture);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivPicture)ImageView ivPicture;
        @Bind(R.id.ivStatus)ImageView ivStatus;
        @Bind(R.id.tvProductName)TextView tvProductName;
        @Bind(R.id.tvAdressUser)TextView tvAdressUser;
        @Bind(R.id.tvOrderCount)TextView tvOrderCount;
        @Bind(R.id.tvPriceByUnit)TextView tvPriceByUnit;
        @Bind(R.id.tvStatusName)TextView tvStatusName;
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}
