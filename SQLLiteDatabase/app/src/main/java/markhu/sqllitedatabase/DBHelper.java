package markhu.sqllitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String NAME_NUMBER_TABLE = "NAME_NUMBER";

    //column names in the number table
    public static final String KEY_NAME = "KEY_NAME";
    public static final String KEY_NUMBER = "KEY_NUMBER";

    //string SQL to create table
    private static final String NAME_NUMBER_TABLE_CREATE =
            "CREATE TABLE" + NAME_NUMBER_TABLE + " (" +
            KEY_NAME + " TEXT, " +
            KEY_NUMBER + " TEXT);";

    private static final String WHERE_CLAUSE_NAME = KEY_NAME + " =? ";

    public DBHelper(Context context) {
        super(context, NAME_NUMBER_TABLE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NAME_NUMBER_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNameNumber(String name, String number){
        SQLiteDatabase writer = getWritableDatabase();
        ContentValues newNameNumber = new ContentValues();
        newNameNumber.put(KEY_NAME,name);
        newNameNumber.put(KEY_NUMBER,number);
        writer.insert(NAME_NUMBER_TABLE,null,newNameNumber);
        writer.close();
    }

    public String findNumberFromName(String name){
        SQLiteDatabase reader = getReadableDatabase();
        Cursor cursor = reader.query(NAME_NUMBER_TABLE, new String[]{KEY_NUMBER},
                WHERE_CLAUSE_NAME, new String[]{name}, null, null, null);
        if(cursor.moveToFirst()){
            int numberIndex = cursor.getColumnIndex(KEY_NUMBER);
            return cursor.getString(numberIndex);
        }
        else {
            return null;
        }
    }
}
