package com.ogaga.flash.acitivies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.ogaga.flash.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SellActivity extends AppCompatActivity {

    @Bind(R.id.etProductname)EditText etProductname;
    @Bind(R.id.spProductType)Spinner spProductType;
    @Bind(R.id.etProductPrice)EditText etProductPrice;
    @Bind(R.id.spProductQuanlity)Spinner spProductQuanlity;
    @Bind(R.id.spProductStatus)Spinner spProductStatus;
    @Bind(R.id.etDateStart)EditText etDateStart;
    @Bind(R.id.etDateFinished)EditText etDateFinished;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell);
        ButterKnife.bind(this);
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
        spProductQuanlity.setAdapter(adapter);
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
                R.array.product_status, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spProductStatus.setAdapter(adapter);
    }


}
