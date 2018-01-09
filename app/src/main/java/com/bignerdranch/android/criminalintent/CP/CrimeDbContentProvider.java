package com.bignerdranch.android.criminalintent.CP;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bignerdranch.android.criminalintent.CrimeDbSchema;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Koren Vitalii on 05.01.2018.
 */

public class CrimeDbContentProvider extends ContentProvider {
    private static Uri sBaseUri;
    private CrimeBaseHelper mCrimeBaseHelper;
    private final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private final List<String> mTables;

    public CrimeDbContentProvider() {
        mTables = new ArrayList<>();
    }

    public CrimeBaseHelper getCrimeBaseHelper() {
        return mCrimeBaseHelper;
    }


    @Override
    public boolean onCreate() {
        mCrimeBaseHelper = new CrimeBaseHelper(getContext());

        mUriMatcher.addURI(mCrimeBaseHelper.getAUTHORITY(), CrimeDbSchema.CrimeTable.NAME, 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        int index = mUriMatcher.match(uri);
        if (index < 0 || index >= mTables.size()) {
            return "";
        }
        return mTables.get(index);
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
