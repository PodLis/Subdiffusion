package net.mz.rb.subdiffusion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.mz.rb.subdiffusion.data.SubContract;
import net.mz.rb.subdiffusion.data.SubDataHelper;

class TheBestHelperWithDB {
    private SubDataHelper mDbHelper;

    TheBestHelperWithDB(Context context) {
        mDbHelper = new SubDataHelper(context);
    }

    int getTableHeight(){
        int n;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {SubContract.Pattern._ID,};
        Cursor cursor = db.query(
                SubContract.Pattern.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки
        try {
            n = cursor.getCount();
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
        return n;
    }

    int[] getAllIDs(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {SubContract.Pattern._ID,};
        int[] iDs;
        Cursor cursor = db.query(
                SubContract.Pattern.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки
        try {
            int idColumnIndex = cursor.getColumnIndex(SubContract.Pattern._ID);

            int n = cursor.getCount();
            iDs = new int[n];

            // Проходим через все ряды
            cursor.moveToFirst();
            for (int i = 0; i < n; i++) {
                // Используем индекс для получения строки или числа
                iDs[i] = cursor.getInt(idColumnIndex);
                cursor.moveToNext();
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
        return iDs;
    }

    String[] getAllNames(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {SubContract.Pattern.COLUMN_NAME,};
        String[] names;
        Cursor cursor = db.query(
                SubContract.Pattern.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки
        try {
            int nameColumnIndex = cursor.getColumnIndex(SubContract.Pattern.COLUMN_NAME);

            int n = cursor.getCount();
            names = new String[n];

            // Проходим через все ряды
            cursor.moveToFirst();
            for (int i = 0; i < n; i++) {
                // Используем индекс для получения строки или числа
                names[i] = cursor.getString(nameColumnIndex);
                cursor.moveToNext();
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
        return names;
    }

    String getName(int iD){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                SubContract.Pattern._ID,
                SubContract.Pattern.COLUMN_NAME};
        String name;
        Cursor cursor = db.query(
                SubContract.Pattern.TABLE_NAME,   // таблица
                projection,            // столбцы
                SubContract.Pattern._ID+"="+iD,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки
        try {
            int nameColumnIndex = cursor.getColumnIndex(SubContract.Pattern.COLUMN_NAME);

            // Проходим через все ряды
            cursor.moveToFirst();
            name = cursor.getString(nameColumnIndex);
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
        return name;
    }

    int getIntParameter(String string, int iD){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                SubContract.Pattern._ID,
                string};
        int parameter;
        Cursor cursor = db.query(
                SubContract.Pattern.TABLE_NAME,   // таблица
                projection,            // столбцы
                SubContract.Pattern._ID+"="+iD,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки
        try {
            int nameColumnIndex = cursor.getColumnIndex(string);

            // Проходим через все ряды
            cursor.moveToFirst();
            parameter = cursor.getInt(nameColumnIndex);
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
        return parameter;
    }

    double getDoubleParameter(String string, int iD){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projection = {
                SubContract.Pattern._ID,
                string};
        double parameter;
        Cursor cursor = db.query(
                SubContract.Pattern.TABLE_NAME,   // таблица
                projection,            // столбцы
                SubContract.Pattern._ID+"="+iD,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // порядок сортировки
        try {
            int nameColumnIndex = cursor.getColumnIndex(string);

            // Проходим через все ряды
            cursor.moveToFirst();
            parameter = cursor.getDouble(nameColumnIndex);
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
        return parameter;
    }

    long addNewPost(String name, int[] WHRPihPivPDs, double[] SSAK){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SubContract.Pattern.COLUMN_NAME, name);
        values.put(SubContract.Pattern.COLUMN_WIDTH, WHRPihPivPDs[0]);
        values.put(SubContract.Pattern.COLUMN_HEIGHT, WHRPihPivPDs[1]);
        values.put(SubContract.Pattern.COLUMN_RADIUS, WHRPihPivPDs[2]);
        values.put(SubContract.Pattern.COLUMN_POINTS_IN_HOR, WHRPihPivPDs[3]);
        values.put(SubContract.Pattern.COLUMN_POINTS_IN_VER, WHRPihPivPDs[4]);
        values.put(SubContract.Pattern.COLUMN_POINTS, WHRPihPivPDs[5]);
        values.put(SubContract.Pattern.COLUMN_DIF_SPEED, WHRPihPivPDs[6]);
        values.put(SubContract.Pattern.COLUMN_SIGMA_1, SSAK[0]);
        values.put(SubContract.Pattern.COLUMN_SIGMA_2, SSAK[1]);
        values.put(SubContract.Pattern.COLUMN_ALPHA, SSAK[2]);
        values.put(SubContract.Pattern.COLUMN_KOEFF, SSAK[3]);

        return db.insert(SubContract.Pattern.TABLE_NAME, null, values);
    }

    void deletePost(int iD){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        db.delete(SubContract.Pattern.TABLE_NAME,
                SubContract.Pattern._ID + " = ?",
                new String[] {Integer.toString(iD)});
    }

    void updatePost(int iD, String name, int[] WHRPihPivPDs, double[] SSAK){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SubContract.Pattern.COLUMN_NAME, name);
        values.put(SubContract.Pattern.COLUMN_WIDTH, WHRPihPivPDs[0]);
        values.put(SubContract.Pattern.COLUMN_HEIGHT, WHRPihPivPDs[1]);
        values.put(SubContract.Pattern.COLUMN_RADIUS, WHRPihPivPDs[2]);
        values.put(SubContract.Pattern.COLUMN_POINTS_IN_HOR, WHRPihPivPDs[3]);
        values.put(SubContract.Pattern.COLUMN_POINTS_IN_VER, WHRPihPivPDs[4]);
        values.put(SubContract.Pattern.COLUMN_POINTS, WHRPihPivPDs[5]);
        values.put(SubContract.Pattern.COLUMN_DIF_SPEED, WHRPihPivPDs[6]);
        values.put(SubContract.Pattern.COLUMN_SIGMA_1, SSAK[0]);
        values.put(SubContract.Pattern.COLUMN_SIGMA_2, SSAK[1]);
        values.put(SubContract.Pattern.COLUMN_ALPHA, SSAK[2]);
        values.put(SubContract.Pattern.COLUMN_KOEFF, SSAK[3]);

        db.update(SubContract.Pattern.TABLE_NAME,
                values,
                SubContract.Pattern._ID + " = ?",
                new String[] {Integer.toString(iD)});
    }
}
