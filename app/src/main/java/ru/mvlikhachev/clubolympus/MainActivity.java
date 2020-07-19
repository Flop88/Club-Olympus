package ru.mvlikhachev.clubolympus;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.mvlikhachev.clubolympus.Data.ClubOlympusContract.MemberEntry;

public class MainActivity extends AppCompatActivity {

    ListView dataListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataListView = findViewById(R.id.dataListView);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayData();
    }

    private void displayData() {
        String[] projection = {
                MemberEntry._ID,
                MemberEntry.COLUMN_FIRSTNAME,
                MemberEntry.COLUMN_LASTNAME,
                MemberEntry.COLUMN_GENDER,
                MemberEntry.COLUMN_SPORT,
        };
        Cursor cursor = getContentResolver().query(
                MemberEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        MembersCursorAdapter cursorAdapter = new MembersCursorAdapter(this, cursor, false);
        dataListView.setAdapter(cursorAdapter);
    }
}