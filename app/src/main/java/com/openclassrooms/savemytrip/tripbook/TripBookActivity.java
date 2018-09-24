package com.openclassrooms.savemytrip.tripbook;


import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.openclassrooms.savemytrip.R;
import com.openclassrooms.savemytrip.base.BaseActivity;
import com.openclassrooms.savemytrip.utils.StorageUtils;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.view.View.*;
import static com.openclassrooms.savemytrip.utils.StorageUtils.*;

public class TripBookActivity extends BaseActivity {

    //FOR DESIGN
    @BindView(R.id.trip_book_activity_external_choice)
    LinearLayout linearLayoutExternalChoice;
    @BindView(R.id.trip_book_activity_internal_choice)
    LinearLayout linearLayoutInternalChoice;
    @BindView(R.id.trip_book_activity_radio_external)
    RadioButton radioButtonExternalChoice;
    @BindView(R.id.trip_book_activity_radio_public)
    RadioButton radioButtonExternalPublicChoice;
    @BindView(R.id.trip_book_activity_radio_volatile)
    RadioButton radioButtonInternalVolatileChoice;
    @BindView(R.id.trip_book_activity_edit_text)
    EditText editText;

    private static final String FILENAME = "tripBook.txt";
    private static final String FOLDERNAME = "bookTrip";
    private static final int RC_STORAGE_WRITE_PERMS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureToolbar();
        this.readFromStorage();
    }

    @Override
    public int getLayoutContentViewID() {
        return R.layout.activity_trip_book;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                /*TODO*/
                return true;
            case R.id.action_save:
                this.save();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // --------------------
    // ACTIONS
    // --------------------

    @OnCheckedChanged({R.id.trip_book_activity_radio_internal, R.id.trip_book_activity_radio_external,
                       R.id.trip_book_activity_radio_private, R.id.trip_book_activity_radio_public,
                       R.id.trip_book_activity_radio_normal, R.id.trip_book_activity_radio_volatile})
    public void onClickRadioButton(CompoundButton button, boolean isChecked){
        if (isChecked) {
            switch (button.getId()) {
                case R.id.trip_book_activity_radio_internal:
                    this.linearLayoutExternalChoice.setVisibility(GONE);
                    this.linearLayoutInternalChoice.setVisibility(VISIBLE);
                    break;
                case R.id.trip_book_activity_radio_external:
                    this.linearLayoutExternalChoice.setVisibility(VISIBLE);
                    this.linearLayoutInternalChoice.setVisibility(GONE);
                    break;
            }
        }

        this.readFromStorage();
    }

    private void save(){
        if (this.radioButtonExternalChoice.isChecked()){
            this.writeOnExternalStorage(); //Save on external storage
        } else {
            this.writeOnInternalStorage();
        }
    }

    // ----------------------------------
    // UTILS - STORAGE
    // ----------------------------------

    @AfterPermissionGranted(RC_STORAGE_WRITE_PERMS)
    private void readFromStorage(){
        if (!EasyPermissions.hasPermissions(this, WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(this, getString(R.string.title_permission), RC_STORAGE_WRITE_PERMS, WRITE_EXTERNAL_STORAGE);
            return;
        }

        if (this.radioButtonExternalChoice.isChecked()){
            if (isExternalStorageReadable()){
                // EXTERNAL
                if (radioButtonExternalPublicChoice.isChecked()){
                    // External - Public
                    this.editText.setText(getTextFromStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),this, FILENAME, FOLDERNAME));
                } else {
                    // External - Privatex
                    this.editText.setText(getTextFromStorage(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), this, FILENAME, FOLDERNAME));
                }
            }
        } else {
            // INTERNAL
            if (radioButtonInternalVolatileChoice.isChecked()){
                // Cache
                this.editText.setText(StorageUtils.getTextFromStorage(getCacheDir(), this, FILENAME, FOLDERNAME));
            } else {
                // Normal
                this.editText.setText(StorageUtils.getTextFromStorage(getFilesDir(), this, FILENAME, FOLDERNAME));
            }
        }
    }

    private void writeOnExternalStorage(){
        if (isExternalStorageWritable()){
            if (radioButtonExternalPublicChoice.isChecked()){
                setTextInStorage(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), this, FILENAME, FOLDERNAME, this.editText.getText().toString());
            } else {
                setTextInStorage(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), this, FILENAME, FOLDERNAME, this.editText.getText().toString());
            }
        } else {
            Toast.makeText(this, getString(R.string.external_storage_impossible_create_file), Toast.LENGTH_LONG).show();
        }
    }

    private void writeOnInternalStorage() {
        if (radioButtonInternalVolatileChoice.isChecked()){
            setTextInStorage(getCacheDir(), this, FILENAME, FOLDERNAME, this.editText.getText().toString());
        } else {
            setTextInStorage(getFilesDir(), this, FILENAME, FOLDERNAME, this.editText.getText().toString());
        }
    }
}
