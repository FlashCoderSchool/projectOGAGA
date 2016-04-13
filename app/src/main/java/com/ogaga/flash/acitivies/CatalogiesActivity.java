package com.ogaga.flash.acitivies;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.ogaga.flash.R;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.clients.ImgurClient;
import com.ogaga.flash.helpers.DocumentHelper;
import com.ogaga.flash.imgurmodel.ImageResponse;
import com.ogaga.flash.imgurmodel.Upload;
import com.ogaga.flash.models.Catalogies;

import java.io.File;
import java.io.IOException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CatalogiesActivity extends AppCompatActivity {
    Firebase firebase;
    // PICK_PHOTO_CODE is a constant integer
    public final static int PICK_PHOTO_CODE = 1046;
    public ImageResponse imageResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        firebase=FirebaseClient.getCatalogies();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPickPhoto(view);
                if (imageResponse!=null){
                    Catalogies catalogies = new Catalogies(1, "food", System.currentTimeMillis(), "null", imageResponse.data.link);
                    firebase.push().setValue(catalogies);
                }
                /*Catalogies catalogies = new Catalogies(1, "food", System.currentTimeMillis(), "null", null);
                firebase.push().setValue(catalogies);*/
            }
        });


    }
    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_catalogies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_PHOTO_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try{
                        Uri photoUri = data.getData();
                        // Do something with the photo based on Uri
                        Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
                        Upload upload = new Upload();

                        upload.image = new File(DocumentHelper.getPath(this, photoUri));
                        upload.title = "abc";
                        upload.description = "abc";
                        imageResponse=new ImgurClient(this).Execute(upload, new UiCallback());
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (imageResponse!=null){
                            Catalogies catalogies = new Catalogies(1, "food", System.currentTimeMillis(), "null", imageResponse.data.link);
                            firebase.push().setValue(catalogies);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }

    }
    private class UiCallback implements Callback<ImageResponse> {

        @Override
        public void success(ImageResponse imageResponse, Response response) {

        }

        @Override
        public void failure(RetrofitError error) {
            //Assume we have no connection, since error is null
            if (error == null) {
                //Snackbar.make(findViewById(R.id.rootView), "No internet connection", Snackbar.LENGTH_SHORT).show();
            }
        }
    }
}
