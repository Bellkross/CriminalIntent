package com.bignerdranch.android.criminalintent;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class CrimeProvider extends ContentProvider {

    public static final String LOG_TAG = CrimeProvider.class.getSimpleName();

    public static final int CRIMES = 100;
    public static final int CRIME_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private CrimeBaseHelper mCrimeBaseHelper;

    static {
        sUriMatcher.addURI(CrimeContract.CONTENT_AUTHORITY, CrimeContract.PATH_CRIMES, CRIMES);
        sUriMatcher.addURI(CrimeContract.CONTENT_AUTHORITY, CrimeContract.PATH_CRIMES + "/*",
                CRIME_ID);
    }

    @Override
    public boolean onCreate() {
        mCrimeBaseHelper = new CrimeBaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase database = mCrimeBaseHelper.getReadableDatabase();

        Cursor cursor;
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case CRIMES:
                cursor = database.query(CrimeContract.CrimeTable.NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case CRIME_ID:
                selection = CrimeContract.CrimeTable.Cols.UUID + "=?";
                selectionArgs = new String[]{String.valueOf(uri.getLastPathSegment())};
                cursor = database.query(CrimeContract.CrimeTable.NAME, projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CRIMES:
                return insertCrime(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertCrime(Uri uri, ContentValues contentValues) {
        SQLiteDatabase database = mCrimeBaseHelper.getWritableDatabase();

        long id = database.insert(CrimeContract.CrimeTable.NAME, null, contentValues);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case CRIMES:
                return updateCrime(uri, values, selection, selectionArgs);
            case CRIME_ID:
                selection = CrimeContract.CrimeTable.Cols.UUID + "=?";
                selectionArgs = new String[]{String.valueOf(uri.getLastPathSegment())};
                return updateCrime(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateCrime(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        if (contentValues == null) {
            return 0;
        }
        SQLiteDatabase db = mCrimeBaseHelper.getWritableDatabase();

        return db.update(CrimeContract.CrimeTable.NAME, contentValues, selection, selectionArgs);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mCrimeBaseHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case CRIMES:
                return database.delete(CrimeContract.CrimeTable.NAME, selection, selectionArgs);
            case CRIME_ID:
                selection = CrimeContract.CrimeTable.Cols.UUID + "=?";
                selectionArgs = new String[]{String.valueOf(uri.getLastPathSegment())};
                return database.delete(CrimeContract.CrimeTable.NAME, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case CRIMES:
                return CrimeContract.CrimeTable.CONTENT_LIST_TYPE;
            case CRIME_ID:
                return CrimeContract.CrimeTable.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

}
