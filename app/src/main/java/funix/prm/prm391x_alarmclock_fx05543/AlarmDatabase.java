package funix.prm.prm391x_alarmclock_fx05543;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AlarmDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "AlarmDb.sqlite";
    private static final int DB_VERSION = 1;
    private static final String TABLE_ALARM = "Alarm";
    private static final String ID = "Id";
    private static final String HOUR = "Hour";
    private static final String MINUTE = "Minute";
    private static final String IS_SET = "IsSet";

    public AlarmDatabase(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TABLE_ALARM + " (" + ID + " INTEGER PRIMARY KEY, " + HOUR + " INTEGER, " + MINUTE + " INTEGER, " + IS_SET + " BOOLEAN)";
        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALARM);
        onCreate(db);
    }
}