package ru.mvlikhachev.clubolympus;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import ru.mvlikhachev.clubolympus.Data.ClubOlympusContract.MemberEntry;

public class MembersCursorAdapter extends CursorAdapter {
    public MembersCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_member, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView idTextView = (TextView) view.findViewById(R.id.idTextView);
        TextView firstNameTextView = (TextView) view.findViewById(R.id.firsNameTextView);
        TextView lastNameTextView = (TextView) view.findViewById(R.id.lastNameTextView);
        TextView genderTextView = (TextView) view.findViewById(R.id.genderTextView);
        TextView sportTextView = (TextView) view.findViewById(R.id.sportTextView);

        String idTextFromCursor = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry._ID));
        String firstNameTextFromCursor = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_FIRSTNAME));
        String lastNameTextFromCursor = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_LASTNAME));
        String genderTextFromCursor = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_GENDER));
        String sportTextFromCursor = cursor.getString(cursor.getColumnIndexOrThrow(MemberEntry.COLUMN_SPORT));

        idTextView.setText(idTextFromCursor);
        firstNameTextView.setText(firstNameTextFromCursor);
        lastNameTextView.setText(lastNameTextFromCursor);
        genderTextView.setText(genderTextFromCursor);
        sportTextView.setText(sportTextFromCursor);

    }
}
