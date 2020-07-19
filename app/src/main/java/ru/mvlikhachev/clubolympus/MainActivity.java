package ru.mvlikhachev.clubolympus;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import ru.mvlikhachev.clubolympus.Data.ClubOlympusContract.MemberEntry;



public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    public static final int MEMBER_LOADER = 123;
    MembersCursorAdapter membersCursorAdapter;
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

        membersCursorAdapter = new MembersCursorAdapter(this, null, false);
        dataListView.setAdapter(membersCursorAdapter);
        getSupportLoaderManager().initLoader(MEMBER_LOADER,null, this);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        displayData();
//    }

//    private void displayData() {
//        String[] projection = {
//                MemberEntry._ID,
//                MemberEntry.COLUMN_FIRSTNAME,
//                MemberEntry.COLUMN_LASTNAME,
//                MemberEntry.COLUMN_GENDER,
//                MemberEntry.COLUMN_SPORT,
//        };
//        Cursor cursor = getContentResolver().query(
//                MemberEntry.CONTENT_URI,
//                projection,
//                null,
//                null,
//                null
//        );
//
//        MembersCursorAdapter cursorAdapter = new MembersCursorAdapter(this, cursor, false);
//        dataListView.setAdapter(cursorAdapter);
//    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        String[] projection = {
                MemberEntry._ID,
                MemberEntry.COLUMN_FIRSTNAME,
                MemberEntry.COLUMN_LASTNAME,
                MemberEntry.COLUMN_GENDER,
                MemberEntry.COLUMN_SPORT,
        };
        CursorLoader cursorLoader = new CursorLoader(this,
                MemberEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object data) {

        membersCursorAdapter.swapCursor((Cursor) data);

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        membersCursorAdapter.swapCursor(null);
    }
}