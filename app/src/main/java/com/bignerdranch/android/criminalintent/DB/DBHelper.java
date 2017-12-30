package com.bignerdranch.android.criminalintent.DB;

import com.bignerdranch.android.criminalintent.DB.CrimeDBHelper;
import com.bignerdranch.android.criminalintent.Model.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitalii Koren on 30.12.2017.
 */

public class DBHelper<E extends Model> {
    public static final CrimeDBHelper CRIME_DB_HELPER = new CrimeDBHelper();

    public void insert(E entity) {
    }

    public void replace(E entity) {
    }

    public void delate(E entity) {
    }

    public List<E> getAll() {
        return new ArrayList<E>();
    }

}
