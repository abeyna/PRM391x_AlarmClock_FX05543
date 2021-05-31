package funix.prm.prm391x_alarmclock_fx05543;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * @author Luan.Nguyen
 * @since May 31st 2021
 *                      Alarm Database.
 */
public class AlarmDatabase extends SQLiteOpenHelper {
    /** Alarm database name. */
    private static final String DB_NAME = "AlarmDb.sqlite";

    /** Database version. */
    private static final int DB_VERSION = 1;

    /** Database table name. */
    private static final String TABLE_ALARM = "Alarm";

    /** Alarm ID. */
    private static final String ID = "Id";

    /** Alarm hour. */
    private static final String HOUR = "Hour";

    /** Alarm minute. */
    private static final String MINUTE = "Minute";

    /** Alarm status. */
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

    /**
     * Add alarm data.
     * @param alarm
     *              alarm object.
     */
    public void addAlarmData(Alarm alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(HOUR, alarm.getHour());
        contentValues.put(MINUTE, alarm.getMinute());
        contentValues.put(IS_SET, alarm.isSet());
        db.insert(TABLE_ALARM, null, contentValues);
        db.close();
    }

    public void queryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor getData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
}