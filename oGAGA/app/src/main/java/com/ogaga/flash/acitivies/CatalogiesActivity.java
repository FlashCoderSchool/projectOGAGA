package com.ogaga.flash.acitivies;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.ogaga.flash.R;
import com.ogaga.flash.adapters.CategoryAdapter;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.clients.ImgurClient;
import com.ogaga.flash.helpers.DocumentHelper;
import com.ogaga.flash.imgurmodel.ImageResponse;
import com.ogaga.flash.imgurmodel.Upload;
import com.ogaga.flash.models.Catalogies;
import com.ogaga.flash.models.UiCallback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CatalogiesActivity extends AppCompatActivity {


    // PICK_PHOTO_CODE is a constant integer
    public final static int PICK_PHOTO_CODE = 1046;
    FirebaseListAdapter<Catalogies> firebaseAdapter;
    Firebase firebase;
    //@Bind(R.id.rvCate)
    //RecyclerView rvCate;
    // private ArrayList<Catalogies> cateList;
    // private CategoryAdapter cateAdapter;
    @Bind(R.id.lvCatalogies)
    ListView lvCatalogies;
    private Toolbar toolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogies);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();
        ButterKnife.bind(this);
        Firebase.setAndroidContext(this);

        firebase = FirebaseClient.getCatalogies();
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onPickPhoto(view);
                Intent intent = new Intent(CatalogiesActivity.this, UserRegistryActivity.class);
                startActivity(intent);

            }
        });*/
        popularView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        firebaseAdapter.cleanup();
    }

    public void popularView() {

        firebaseAdapter = new FirebaseListAdapter<Catalogies>(this, Catalogies.class,
                R.layout.catalogies_item, firebase) {
            @Override
            protected void populateView(View v, Catalogies model, int position) {
                ImageView ivPhoto = (ImageView) v.findViewById(R.id.ivPicture);
                Picasso.with(getApplicationContext()).load(model.getUrl()).into(ivPhoto);
                ((TextView) v.findViewById(R.id.tvName)).setText(model.getName());
            }

        };
        //get category list
        //getItems();
        //rvCate.setHasFixedSize(true);
        // ListView
        // rvCate.setLayoutManager(new LinearLayoutManager(this));
        // create an Object for Adapter
        //cateAdapter = new CategoryAdapter(cateList);
        //rvCate.setAdapter(cateAdapter);*/
        lvCatalogies.setAdapter(firebaseAdapter);
    }
    /*public void  getItems(){
        cateList = new ArrayList<>();
        final String[] cates = {"Organic fruit", "Vegetables", "Seafood"};
        final int[] images = {R.drawable.fruit,R.drawable.vegetable,R.drawable.fish};
        for (int i = 0; i < cates.length; i++) {
            Catalogies cate = new Catalogies(cates[i],images[i]);
            cateList.add(cate);
        }
    }*/


    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, R.integer.PICK_PHOTO_CODE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    // `onPostCreate` called when activity start-up is complete after `onStart()`
    // NOTE! Make sure to override the method with only a single `Bundle` argument
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == R.integer.PICK_PHOTO_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try {
                        Uri photoUri = data.getData();
                        // Do something with the photo based on Uri
                        Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                        Upload upload = new Upload();

                        upload.image = new File(DocumentHelper.getPath(this, photoUri));
                        upload.title = "abc";
                        upload.description = "abc";
                        new ImgurClient(this, new ImgurClient.ImgurClientListener() {
                            @Override
                            public void postUploadImage(ImageResponse imageResponse) {
                                Catalogies catalogies = new Catalogies(1, "food", System.currentTimeMillis(), "null", imageResponse.data.link);
                                firebase.push().setValue(catalogies);
                            }
                        }).Execute(upload, new UiCallback());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    /*Functions*/
    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

}
