package com.bignerdranch.android.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.criminalintent.CrimeDbSchema.CrimeTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static CrimeLab get(Context context) {
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    private CrimeCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        //String sql = String.format("SELECT * FROM $1%s $2%s = $3%s",CrimeTable.NAME,CrimeTable.Cols.UUID,whereArgs.toString());
        String sql;
        if (whereArgs   == null)
            sql = String.format("SELECT * FROM %1$s;", CrimeTable.NAME);
        else
            sql = String.format("SELECT * FROM %1$s WHERE %2$s = ?", CrimeTable.NAME, CrimeTable.Cols.UUID);
//        Cursor cursor = mDatabase.query(
//                CrimeTable.NAME,
//                null, // columns - с null выбираются все столбцы
//                whereClause,
//                whereArgs,
//                null, // groupBy
//                null, // having
//                null // orderBy
//        );
        Cursor cursor = mDatabase.rawQuery(sql, whereArgs);
        return new CrimeCursorWrapper(cursor);
    }

    public void addCrime(final Crime c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeTable.NAME, null, values);
    }

    public void deleteCrime(final Crime c) {
        ContentValues values = getContentValues(c);
        mDatabase.delete(CrimeTable.NAME, CrimeTable.Cols.UUID + " =  ?",
                new String[]{values.getAsString(CrimeTable.Cols.UUID)});
    }

    public void updateCrime(Crime crime) {
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);
        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }

    private static ContentValues getContentValues(Crime crime) {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID, crime.getId().toString());
        values.put(CrimeTable.Cols.TITLE, crime.getTitle());
        values.put(CrimeTable.Cols.DATE, crime.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED, crime.isSolved() ? 1 : 0);
        return values;
    }

    private CrimeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();
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
