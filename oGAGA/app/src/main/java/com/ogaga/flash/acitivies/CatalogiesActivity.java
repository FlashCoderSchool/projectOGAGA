package com.ogaga.flash.acitivies;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.ogaga.flash.R;
import com.ogaga.flash.adapters.CategoryAdapter;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.clients.ImgurClient;
import com.ogaga.flash.helpers.DocumentHelper;
import com.ogaga.flash.imgurmodel.ImageResponse;
import com.ogaga.flash.imgurmodel.Upload;
import com.ogaga.flash.models.Catalogies;
import com.ogaga.flash.models.UiCallback;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CatalogiesActivity extends AppCompatActivity {


    // PICK_PHOTO_CODE is a constant integer
    public final static int PICK_PHOTO_CODE = 1046;
    Firebase firebase;
    @Bind(R.id.rvCate)
    RecyclerView rvCate;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @Bind(R.id.nvView)
    NavigationView nvDrawer;
    @Bind(R.id.fabSell)
    FloatingActionButton fabSell;

    private CategoryAdapter cateAdapter;
    private ActionBarDrawerToggle drawerToggle;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogies);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        mUser=Parcels.unwrap(getIntent().getParcelableExtra("user"));
        Firebase.setAndroidContext(this);
        drawerToggle = setupDrawerToggle();
        firebase = FirebaseClient.getCatalogies();

        // Tie DrawerLayout events to the ActionBarToggle
        nvDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        //selectDrawerItem(menuItem);
                        switch (menuItem.getItemId()) {
                            case R.id.navSetting:
                                Intent intent = new Intent(CatalogiesActivity.this, UserRegistryActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.navUserProfile:
                                Intent intentUserProfile = new Intent(CatalogiesActivity.this, UserProfileActivity.class);
                                intentUserProfile.putExtra("user",Parcels.wrap(mUser));
                                startActivity(intentUserProfile);
                                break;
                        }
                        return true;
                    }
                });
        firebase = FirebaseClient.getCatalogies();
        popularView();
        onClickSellFAB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cateAdapter.cleanup();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // onPostCreate` called when activity start-up is complete after `onStart()`
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

    // on Product post result
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


    public void popularView() {
        cateAdapter = new CategoryAdapter(firebase, this);
        rvCate.setHasFixedSize(true);
        rvCate.setLayoutManager(new LinearLayoutManager(this));
        rvCate.setAdapter(cateAdapter);
    }

    public void onClickSellFAB() {
        fabSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser!=null){
                    Intent intent = new Intent(CatalogiesActivity.this, SellActivity.class);
                    intent.putExtra("user", Parcels.wrap(mUser));
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(CatalogiesActivity.this, UserRegistryActivity.class);
                    startActivityForResult(intent, getResources().getInteger(R.integer.LOGIN_SUCCESS_CODE));
                }

            }
        });
    }

    public void onClickCategoryItem(){

    }

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
}