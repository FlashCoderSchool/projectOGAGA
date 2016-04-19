package com.ogaga.flash.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.ogaga.flash.R;
import com.ogaga.flash.models.Catalogies;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by carot on 4/18/2016.
 */
public class CategoryAdapter extends FirebaseRecyclerAdapter<Catalogies,CategoryAdapter.ViewHolder> {


    private Context context;
    public CategoryAdapter(Firebase ref, Context parentContext ) {
        super(Catalogies.class, R.layout.catalogies_item, CategoryAdapter.ViewHolder.class, ref);
        context = parentContext;
    }
    @Override
    protected void populateViewHolder(ViewHolder viewHolder, Catalogies catalogies, int i) {

         viewHolder.tvName.setText(catalogies.getName());
        //holder.ivImage.setImageResource(cate.getLocalImage());
         Picasso.with(context).load(catalogies.getUrl()).into(viewHolder.ivImage);
    }
   public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivPicture)
        ImageView ivImage;
        @Bind(R.id.tvName)
        TextView tvName;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}