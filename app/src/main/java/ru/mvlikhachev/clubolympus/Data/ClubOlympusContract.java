package ru.mvlikhachev.clubolympus.Data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class ClubOlympusContract {

    private ClubOlympusContract() {

    }
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "olympus";

    // FOR URI
    public static final String SCHEME = "content://";
    public static final String AUTHORITY = "ru.mvlikhachev.clubolympuss";
    public static final String PATH_MEMBERS = "members";

    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEME+AUTHORITY);

    public static final class MemberEntry implements BaseColumns {

        //DB
        public static final String TABLE_NAME = "members";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_FIRSTNAME = "firstName";
        public static final String COLUMN_LASTNAME = "lastName";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_SPORT = "sport";


        //GENDER
        public static final int GENDER = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;

        //URI
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,
                PATH_MEMBERS);
    }

}