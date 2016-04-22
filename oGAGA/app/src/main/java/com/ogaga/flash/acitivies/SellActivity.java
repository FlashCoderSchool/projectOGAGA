package com.ogaga.flash.acitivies;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.MutableData;
import com.firebase.client.Transaction;
import com.ogaga.flash.R;
import com.ogaga.flash.clients.FirebaseClient;
import com.ogaga.flash.clients.ImgurClient;
import com.ogaga.flash.helpers.DocumentHelper;
import com.ogaga.flash.imgurmodel.ImageResponse;
import com.ogaga.flash.imgurmodel.Upload;
import com.ogaga.flash.models.Product;
import com.ogaga.flash.models.UiCallback;
import com.ogaga.flash.models.User;

import org.parceler.Parcels;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SellActivity extends AppCompatActivity{

    @Bind(R.id.btnChoseAvatar)Button btnChoseAvatar;
    @Bind(R.id.etProductname)EditText etProductname;
    @Bind(R.id.spProductType)Spinner spProductType;
    @Bind(R.id.etProductPrice)EditText etProductPrice;
    @Bind(R.id.spProductUnit)Spinner spProductUnit;
    @Bind(R.id.spProductStatus)Spinner spProductStatus;
    @Bind(R.id.etDateStart)EditText etDateStart;
    @Bind(R.id.etDateFinished)EditText etDateFinished;
    @Bind(R.id.trDateFinished)TableRow trDateFinished;
    @Bind(R.id.trDateStart)TableRow trDateStart;
    @Bind(R.id.ivAvatar)ImageView ivAvatar;
    private long mStartDate;
    private long mFinishedDate;
    User mUser;
    private Uri mPhotoUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        ButterKnife.bind(this);
        mUser= Parcels.unwrap(getIntent().getParcelableExtra("user"));
        setupProductType();
        setupProdcutStatus();
        setupUnit();
    }

    private void setupUnit() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_unit, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spProductUnit.setAdapter(adapter);
    }

    private void setupProductType() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spProductType.setAdapter(adapter);
    }

    private void setupProdcutStatus() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.product_status_new, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spProductStatus.setAdapter(adapter);
        spProductStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                trDateFinished.setVisibility(View.VISIBLE);
                trDateStart.setVisibility(View.VISIBLE);
                switch (position) {
                    case 0: {
                        trDateFinished.setVisibility(View.VISIBLE);
                        trDateStart.setVisibility(View.VISIBLE);
                        break;
                    }
                    case 1: {
                        trDateStart.setVisibility(View.INVISIBLE);
                        mStartDate=System.currentTimeMillis();
                        trDateFinished.setVisibility(View.VISIBLE);
                    }
                    case 2: {
                        trDateStart.setVisibility(View.INVISIBLE);
                        mStartDate=System.currentTimeMillis();
                        trDateFinished.setVisibility(View.INVISIBLE);
                        mFinishedDate=System.currentTimeMillis();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.btnChoseAvatar)
    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, getResources().getInteger(R.integer.PICK_PHOTO_CODE));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == getResources().getInteger(R.integer.PICK_PHOTO_CODE)) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try{
                        mPhotoUri = data.getData();
                        // Do something with the photo based on Uri
                        Bitmap selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mPhotoUri);

                        ivAvatar.setImageBitmap(selectedImage);

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @OnClick(R.id.etDateStart)
    public void onClickStartDate(View view){
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mStartDate=myCalendar.getTimeInMillis();
                String myFormat = "dd/MM/yyyy"; // your format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                etDateStart.setText(sdf.format(myCalendar.getTime()));
            }

        };
        DatePickerDialog datePickerDialog;
        datePickerDialog=new DatePickerDialog(this,dateSetListener,myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        return;
    }

    @OnClick(R.id.etDateFinished)
    public void onClickFinishedDate(View view){
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mFinishedDate=myCalendar.getTimeInMillis();
                String myFormat = "dd/MM/yyyy"; // your format
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                etDateFinished.setText(sdf.format(myCalendar.getTime()));
            }

        };
        DatePickerDialog datePickerDialog;
        datePickerDialog=new DatePickerDialog(this,dateSetListener,myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
        return;
    }

    @OnClick(R.id.btnSubmit)
    public void onSubmit(View view){
        final Firebase firebaseProducts= FirebaseClient.getProduct();
        Upload upload = new Upload();
        upload.image = new File(DocumentHelper.getPath(this, mPhotoUri));
        upload.description = etProductname.getText().toString();
        new ImgurClient(this, new ImgurClient.ImgurClientListener() {
            @Override
            public void postUploadImage(final ImageResponse imageResponse) {
                firebaseProducts.runTransaction(new Transaction.Handler() {
                    @Override
                    public Transaction.Result doTransaction(MutableData mutableData) {
                        return Transaction.success(mutableData); //we can also abort by calling Transaction.abort()
                    }

                    @Override
                    public void onComplete(FirebaseError firebaseError, boolean b, DataSnapshot dataSnapshot) {
                        if (firebaseError != null) {
                            System.out.println("Firebase counter increment failed.");
                        } else {
                            Product product = new Product();
                            long id = dataSnapshot.getChildrenCount();
                            product.setId(id + 1);
                            product.setUrl(imageResponse.data.link);
                            product.setName(etProductname.getText().toString());
                            product.setId_productType(spProductType.getSelectedItemPosition()+1);
                            product.setId_productStatus(spProductStatus.getSelectedItemPosition()+1);
                            product.setId_unit(spProductUnit.getSelectedItemPosition()+1);
                            product.setId_shipping(0);//Define
                            product.setCreate_at(System.currentTimeMillis());
                            product.setStart_date(mStartDate);
                            product.setFinished_date(mFinishedDate);
                            //Firebase firebaseProduct=firebaseProducts.child(String.valueOf(mUser.getId()));
                            firebaseProducts.push().setValue(product);
                            //
                            finish();//finishing activity
                        }
                    }
                });
            }}).Execute(upload, new UiCallback());

    }
}
