package com.bignerdranch.android.criminalintent.DB;

import com.bignerdranch.android.criminalintent.Model.Crime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vitalii Koren on 30.12.2017.
 */

public class CrimeDBHelper extends DBHelper<Crime> {
    public List<Crime> getAllSolved() {
        return new ArrayList<Crime>();
    }
}
