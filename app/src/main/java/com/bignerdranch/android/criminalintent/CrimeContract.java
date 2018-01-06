package com.bignerdranch.android.criminalintent;


import android.content.ContentResolver;
import android.net.Uri;

public class CrimeContract {
    public static final String CONTENT_AUTHORITY = "com.bignerdranch.android.criminalintent";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_CRIMES = "crimes";

    private CrimeContract(){}

    public static final class CrimeTable {

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of pets.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CRIMES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single pet.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_CRIMES;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_CRIMES);

        public static final String NAME = "crimes";

        public static final class Cols {
            public static final String _ID = "_id";
            public static final String UUID = "uuid";
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
            public static final String SUSPECT = "suspect";
        }

    }

}
