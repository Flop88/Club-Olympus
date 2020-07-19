package ru.mvlikhachev.clubolympus;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import ru.mvlikhachev.clubolympus.Data.ClubOlympusContract.MemberEntry;

public class AddMemberActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>  {

    public static final int EDIT_MEMBER_LOADER = 111;
    Uri currentMemberUri;

    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText groupEditText;
    private Spinner genderSpinner;
    private int gender = 0;

    private ArrayAdapter spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        Intent intent = getIntent();

        currentMemberUri = intent.getData();

        if (currentMemberUri == null) {
            setTitle("Add a Member");
            invalidateOptionsMenu();
        } else {
            setTitle("Edit the Member");
            getSupportLoaderManager().initLoader(EDIT_MEMBER_LOADER,
                    null, this);
        }

        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        groupEditText = findViewById(R.id.groupEditText);
        genderSpinner = findViewById(R.id.genderSpinner);



        spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(spinnerAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedGender = (String) parent.getItemAtPosition(position);
                if(!TextUtils.isEmpty(selectedGender)){
                    if (selectedGender.equals("Male")){
                        gender = MemberEntry.GENDER_MALE;
                    } else if(selectedGender.equals("Female")) {
                        gender = MemberEntry.GENDER_FEMALE;
                    } else {
                        gender = MemberEntry.GENDER;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = 0;
            }
        });

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
            if (currentMemberUri == null) {
                MenuItem menuItem = menu.findItem(R.id.delete_member);
                menuItem.setVisible(false);
            }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_member_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_member:
                saveMember();
                return true;
            case R.id.delete_member:
                showDeleteMemberDialog();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void saveMember() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String sport = groupEditText.getText().toString().trim();

        if(TextUtils.isEmpty(firstName)) {
            Toast.makeText(this, "Input the first name",
                    Toast.LENGTH_LONG).show();
            return;
        } else if(TextUtils.isEmpty(lastName)) {
            Toast.makeText(this, "Input the last name",
                    Toast.LENGTH_LONG).show();
            return;
        } else if(TextUtils.isEmpty(sport)) {
            Toast.makeText(this, "Input the sport group",
                    Toast.LENGTH_LONG).show();
            return;
        } else if(gender == MemberEntry.GENDER) {
            Toast.makeText(this, "Choose the gender",
                    Toast.LENGTH_LONG).show();
            return;
        }


        ContentValues contentValues = new ContentValues();
        contentValues.put(MemberEntry.COLUMN_FIRSTNAME, firstName);
        contentValues.put(MemberEntry.COLUMN_LASTNAME, lastName);
        contentValues.put(MemberEntry.COLUMN_SPORT, sport);
        contentValues.put(MemberEntry.COLUMN_GENDER, gender);

        if (currentMemberUri == null) { // Если currentMemberUri равен null - добавляем нового пользователя в БД
            ContentResolver contentResolver = getContentResolver();
            Uri uri = contentResolver.insert(MemberEntry.CONTENT_URI,
                    contentValues);

            if (uri == null) {
                Toast.makeText(this, "Insertion of data in the table failed",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
            }
        } else { // В противном случае - сохраняем изменения для выбранного пользователя
            int rowsChanged = getContentResolver().update(currentMemberUri, contentValues, null, null);

            if (rowsChanged == 0) {
                Toast.makeText(this, "Saving of data in the table failed",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Member updated", Toast.LENGTH_LONG).show();
            }
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {
                MemberEntry._ID,
                MemberEntry.COLUMN_FIRSTNAME,
                MemberEntry.COLUMN_LASTNAME,
                MemberEntry.COLUMN_GENDER,
                MemberEntry.COLUMN_SPORT
        };
        return new CursorLoader(this,
                currentMemberUri,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (data.moveToFirst()) {
            int firstNameColumnIndex = data.getColumnIndex(
                    MemberEntry.COLUMN_FIRSTNAME );
            int lastNameColumnIndex = data.getColumnIndex(
                    MemberEntry.COLUMN_LASTNAME );
            int genderColumnIndex = data.getColumnIndex(
                    MemberEntry.COLUMN_GENDER );
            int sportColumnIndex = data.getColumnIndex(
                    MemberEntry.COLUMN_SPORT );

            String firstName = data.getString(firstNameColumnIndex);
            String lastName = data.getString(lastNameColumnIndex);
            int gender = data.getInt(genderColumnIndex);
            String sport = data.getString(sportColumnIndex);

            firstNameEditText.setText(firstName);
            lastNameEditText.setText(lastName);
            groupEditText.setText(sport);

            switch (gender) {
                case MemberEntry.GENDER_MALE:
                    genderSpinner.setSelection(1);
                    break;
                case MemberEntry.GENDER_FEMALE:
                    genderSpinner.setSelection(2);
                    break;
                case MemberEntry.GENDER:
                        genderSpinner.setSelection(0);
                        break;
            }
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
    private void showDeleteMemberDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Do you want delete the member?");
        builder.setPositiveButton("Delete",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMemeber();
                    }
                });
        builder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteMemeber(){
        if (currentMemberUri != null) {
            int rowsDeleted = getContentResolver().delete(currentMemberUri, null, null);
            if(rowsDeleted == 0) {
                Toast.makeText(this, "Deleting of data from the table failed",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Member is deleted",
                        Toast.LENGTH_LONG).show();
            }
            finish();
        }

    }
}