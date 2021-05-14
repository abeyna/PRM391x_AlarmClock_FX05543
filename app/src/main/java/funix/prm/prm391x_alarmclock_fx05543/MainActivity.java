package funix.prm.prm391x_alarmclock_fx05543;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author Luan.Nguyen
 * @since May 14th 2021
 *                      Main Activity for running
 */
public class MainActivity extends AppCompatActivity {
    /** A database store all alarms. */
    private AlarmDatabase mAlarmDatabase;

    /** ListView to display all alarms. */
    private ListView mAlarmsListView;

    /** An array for all alarms. */
    private ArrayList<Alarm> mAlarmsList;

    /** An adapter for alarm list. */
    private AlarmAdapter mAlarmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAlarmsListView = (ListView) findViewById(R.id.activity_main_lv_alarm);
        mAlarmsList = new ArrayList<>();
        mAlarmAdapter = new AlarmAdapter(this, R.layout.item_alarm, mAlarmsList);
        mAlarmsListView.setAdapter(mAlarmAdapter);
        mAlarmDatabase = new AlarmDatabase(this);
        getAlarmData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_alarm, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add_alarm_btn) addAlarmDialog();
        return super.onOptionsItemSelected(item);
    }

    /**
     * Add alarm to database after setting it on TimePicker from dialog.
     */
    private void addAlarmDialog() {
        /** Dialog show TimePicker for setting alarm. */
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_time_picker);

        /** TimePicker for alarm setting. */
        TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.dialog_add_time_picker);

        /** Button active after finish alarm choice. */
        Button btnAddAlarm = (Button) dialog.findViewById(R.id.dialog_add_btn_add);

        /** Taking time zone and default locale. */
        Calendar calendar = Calendar.getInstance();

        btnAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                boolean status = true;

                mAlarmDatabase = new AlarmDatabase(MainActivity.this);
                Alarm alarm = new Alarm();
                alarm.setHour(hour);
                alarm.setMinute(minute);
                alarm.setSet(status);
                mAlarmDatabase.addAlarmData(alarm);
                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getAlarmData();
            }
        });

        dialog.show();
    }

    /**
     *
     */
    private void getAlarmData() {
        Cursor cursor = mAlarmDatabase.getData("SELECT * FROM Alarm");
        mAlarmsList.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            int hour = cursor.getInt(1);
            int minute = cursor.getInt(2);
            mAlarmsList.add(new Alarm(id, hour, minute, true));
        }
        mAlarmAdapter.notifyDataSetChanged();
    }


    public void editAlarm(int id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_time_picker);
        dialog.show();

        Calendar calendar = Calendar.getInstance();
        TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.dialog_edit_time_picker);

        Button btnEditAlarm = (Button) dialog.findViewById(R.id.dialog_edit_btn_confirm);
        btnEditAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                mAlarmDatabase.queryData("UPDATE Alarm SET hour = '"+hour+"', minute = '"+minute+"' WHERE id = '"+id+"'");
                Toast.makeText(MainActivity.this, "Edited", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                getAlarmData();
            }
        });
    }

    public void removeAlarm(int id) {
        AlertDialog.Builder removeDialog = new AlertDialog.Builder(this);
        removeDialog.setMessage("Remove this alarm?");
        removeDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAlarmDatabase.queryData("DELETE FROM Alarm WHERE Id = '"+id+"'");
                Toast.makeText(MainActivity.this, "Removed", Toast.LENGTH_SHORT).show();
                getAlarmData();
            }
        });
        removeDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        removeDialog.show();
    }
}