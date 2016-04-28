package com.ogaga.flash.acitivies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.ogaga.flash.R;
import com.ogaga.flash.adapters.ProductRecyclerViewAdapter;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.models.Catalogies;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimeLineActivity extends AppCompatActivity {
    private Firebase fbProduct;
    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.rvProductList)
    RecyclerView rvProductList;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    Catalogies mCatalogies;
    private ProductRecyclerViewAdapter mProductRecyclerViewAdapter;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);

        ButterKnife.bind(this);
        mCatalogies= Parcels.unwrap(getIntent().getParcelableExtra("catalogies"));
        mUser= Parcels.unwrap(getIntent().getParcelableExtra("user"));

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mCatalogies.getName());



        populateProductListView();
        onClickSellFAB();

 /*       rvProductList.addOnItemTouchListener(new RecyclerItemClickListener(this, rvProductList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(TimeLineActivity.this, "Normal tap", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TimeLineActivity.this, ProductDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(TimeLineActivity.this, "Long tap", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, ViewPhotoDetailActivity.class);
//                intent.putExtra(PHOTO_TRANSFER, flickrRecyclerViewAdapter.getPhoto(position));
//                startActivity(intent);
            }
        }));*/
    }

    public void populateProductListView() {
        Firebase firebaseProduct  = FirebaseClient.getProduct();
        Query query=firebaseProduct.orderByChild("id_productType").equalTo(mCatalogies.getId());
        mProductRecyclerViewAdapter = new ProductRecyclerViewAdapter(query, this,mUser);
        rvProductList.setHasFixedSize(true);
        rvProductList.setLayoutManager(new LinearLayoutManager(this));
        rvProductList.setAdapter(mProductRecyclerViewAdapter);
    }

    public void onClickSellFAB() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TimeLineActivity.this, SellActivity.class);
                startActivity(intent);
            }
        });
    }
}
