package ru.mvlikhachev.clubolympus;

import android.content.ContentResolver;
import android.content.ContentValues;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import ru.mvlikhachev.clubolympus.Data.ClubOlympusContract.MemberEntry;

public class AddMemberActivity extends AppCompatActivity {

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_member_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_member:
                insertMember();
                return true;
            case R.id.delete_member:
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void insertMember() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String sport = groupEditText.getText().toString().trim();
        
        ContentValues contentValues = new ContentValues();
        contentValues.put(MemberEntry.COLUMN_FIRSTNAME, firstName);
        contentValues.put(MemberEntry.COLUMN_LASTNAME, lastName);
        contentValues.put(MemberEntry.COLUMN_SPORT, sport);
        contentValues.put(MemberEntry.COLUMN_GENDER, gender);

        ContentResolver contentResolver = getContentResolver();
        Uri uri = contentResolver.insert(MemberEntry.CONTENT_URI, contentValues);
        
        if (uri == null) {
            Toast.makeText(this, "Insertion of data in the dable failed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Data saved", Toast.LENGTH_LONG).show();
        }
    }
}