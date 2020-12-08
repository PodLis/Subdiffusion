package net.mz.rb.subdiffusion.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.mz.rb.subdiffusion.data.SubContract.Pattern;

/**
 * Created by samsung on 04.05.2018.
 */

public class SubDataHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = SubDataHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "subdiffusion.db";

    private static final int DATABASE_VERSION = 1;

    public SubDataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_PATTERNS_TABLE = "CREATE TABLE " + Pattern.TABLE_NAME + " ("
                + SubContract.Pattern._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Pattern.COLUMN_NAME + " TEXT NOT NULL, "
                + Pattern.COLUMN_WIDTH + " INTEGER NOT NULL DEFAULT 10, "
                + Pattern.COLUMN_HEIGHT + " INTEGER NOT NULL DEFAULT 10, "
                + Pattern.COLUMN_RADIUS + " INTEGER NOT NULL DEFAULT 1, "
                + Pattern.COLUMN_POINTS_IN_HOR + " INTEGER NOT NULL DEFAULT 1, "
                + Pattern.COLUMN_POINTS_IN_VER + " INTEGER NOT NULL DEFAULT 1, "
                + Pattern.COLUMN_POINTS + " INTEGER NOT NULL DEFAULT 1, "
                + Pattern.COLUMN_DIF_SPEED + " INTEGER NOT NULL DEFAULT 1, "
                + Pattern.COLUMN_SIGMA_1 + " REAL NOT NULL DEFAULT 1.0, "
                + Pattern.COLUMN_SIGMA_2 + " REAl NOT NULL DEFAULT 0.0, "
                + Pattern.COLUMN_ALPHA + " REAl NOT NULL DEFAULT 1.0, "
                + Pattern.COLUMN_KOEFF + " REAl NOT NULL DEFAULT 100.0);";
        sqLiteDatabase.execSQL(SQL_CREATE_PATTERNS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w("SQLite", "Обновляемся с версии " + i + " на версию " + i1);
        String s = new String("IT");
        sqLiteDatabase.execSQL("DROP TABLE IF " + s + " EXISTS " + DATABASE_NAME);
        onCreate(sqLiteDatabase);
    }
}
