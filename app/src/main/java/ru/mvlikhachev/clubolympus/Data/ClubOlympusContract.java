package ru.mvlikhachev.clubolympus.Data;

import android.provider.BaseColumns;

public final class ClubOlympusContract {

    private ClubOlympusContract() {

    }

    public static final class MemberEntry implements BaseColumns {
        public static final String TABLE_NAME = "members";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_FIRSTNAME = "firstName";
        public static final String COLUMN_LASTNAME = "lastName";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_GROUP = "group";

        public static final int GENDER = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }

}
