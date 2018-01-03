package com.bignerdranch.android.criminalintent.ContentProvider;

/**
 * Created by Vitalii Koren on 31.12.2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.sqlite.database.DatabaseErrorHandler;
import org.sqlite.database.sqlite.SQLiteDatabase.CursorFactory;

/**
 * A helper class to manage database creation and version management.
 * <p/>
 * <p>You create a subclass implementing {@link #onCreate}, {@link #onUpgrade} and
 * optionally {@link #onOpen}, and this class takes care of opening the database
 * if it exists, creating it if it does not, and upgrading it as necessary.
 * Transactions are used to make sure the database is always in a sensible state.
 * <p/>
 * <p>This class makes it easy for {@link android.content.ContentProvider}
 * implementations to defer opening and upgrading the database until first use,
 * to avoid blocking application startup with long-running database upgrades.
 * <p/>
 * <p>For an example, see the NotePadProvider class in the NotePad sample application,
 * in the <em>samples/</em> directory of the SDK.</p>
 * <p/>
 * <p class="note"><strong>Note:</strong> this class assumes
 * monotonically increasing version numbers for upgrades.</p>
 */
public abstract class CrimeSQLiteHelper extends SQLiteOpenHelper{
}
