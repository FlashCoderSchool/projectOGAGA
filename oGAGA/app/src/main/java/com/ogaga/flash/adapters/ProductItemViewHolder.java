package com.ogaga.flash.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ogaga.flash.R;
import com.ogaga.flash.acitivies.ProductDetailActivity;
import com.ogaga.flash.models.Product;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by IceStone on 4/24/2016.
 */
public class ProductItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    @Bind(R.id.ivProductImage)
    ImageView productImage;
    @Bind(R.id.tvProductName) TextView productName;
    @Bind(R.id.tvProducer) TextView producer;
    @Bind(R.id.tvProductOrigin) TextView productOrigin;
    @Bind(R.id.tvProductPrice) TextView productPrice;
    @Bind(R.id.ivProductStatus) ImageView productStatusImage;
    @Bind(R.id.tvProductStatus) TextView productStatus;
    Product mProduct;
    User mUser;
    public ProductItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bind(Product product){
        this.mProduct=product;
    }
    public void bindUser(User user){
        mUser=user;
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), ProductDetailActivity.class);
        intent.putExtra("product", Parcels.wrap(mProduct));
        intent.putExtra("user",Parcels.wrap(mUser));
        v.getContext().startActivity(intent);
    }

    //    /*-- Without Butterkife --*/
//    public ImageView productImage;
//    public TextView productName;
//    public TextView productProducer;
//    public TextView productOrigin;
//    public TextView productPrice;
//    public ImageView productStatusImage;
//    public TextView productStatus;
//
//    public ProductItemViewHolder(View itemView) {
//        super(itemView);
//        this.productImage = (ImageView) itemView.findViewById(R.id.ivProductImage);
//        this.productName = (TextView) itemView.findViewById(R.id.tvProductName);
//        this.productProducer = (TextView) itemView.findViewById(R.id.tvProducer);
//        this.productOrigin = (TextView) itemView.findViewById(R.id.tvProductOrigin);
//        this.productPrice = (TextView) itemView.findViewById(R.id.tvProductPrice);
//        this.productStatusImage = (ImageView) itemView.findViewById(R.id.ivProductStatus);
//        this.productStatus = (TextView) itemView.findViewById(R.id.tvProductStatus);
//    }
}
