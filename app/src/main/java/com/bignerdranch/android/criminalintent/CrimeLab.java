package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.bignerdranch.android.criminalintent.CrimeContract.CrimeTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;

    private Context mContext;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        String[] projection = {
                CrimeTable.Cols.UUID,
                CrimeTable.Cols.TITLE,
                CrimeTable.Cols.DATE,
                CrimeTable.Cols.SOLVED,
                CrimeTable.Cols.SUSPECT
        };

        Cursor cursor;
        if (whereArgs == null) {
            cursor = mContext.getContentResolver().
                    query(CrimeTable.CONTENT_URI, projection,
                            null, null, null);
        } else {
            cursor = mContext.getContentResolver().
                    query(CrimeTable.CONTENT_URI, projection,
                            whereClause, whereArgs, null);
        }

        return new CrimeCursorWrapper(cursor);
    }

    public void addCrime(final Crime c) {
        ContentValues values = getContentValues(c);
        mContext.getContentResolver().insert(CrimeTable.CONTENT_URI, values);
    }

    public void deleteCrime(final Crime c) {
        ContentValues values = getContentValues(c);
        Uri uri = Uri.withAppendedPath(CrimeTable.CONTENT_URI, c.getId().toString());
        mContext.getContentResolver().delete(uri,
                CrimeTable.Cols.UUID + " =  ?",
                new String[]{values.getAsString(CrimeTable.Cols.UUID)});
    }

    public File getPhotoFile(Crime crime) {
        File filesDir = mContext.getFilesDir();
        return new File(filesDir, crime.getPhotoFilename());
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        Uri uri = Uri.withAppendedPath(CrimeTable.CONTENT_URI, uuidString);
        mContext.getContentResolver().update(uri, values,
                CrimeTable.Cols.UUID + " =  ?",
                new String[]{values.getAsString(CrimeTable.Cols.UUID)});
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        values.put(CrimeTable.Cols.SUSPECT, crime.getSuspect());
        return values;
    }

    public List<Crime> getCrimes() {
        List<Crime> crimes = new ArrayList<>();
        CrimeCursorWrapper cursor = queryCrimes(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id) {
        CrimeCursorWrapper cursor = queryCrimes(
                CrimeTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        } finally {
            cursor.close();
        }
    }
}
