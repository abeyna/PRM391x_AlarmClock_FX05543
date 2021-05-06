package funix.prm.prm391x_alarmclock_fx05543;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private AlarmDatabase mAlarmDatabase;
    private ListView mAlarmsListView;
    private ArrayList<Alarm> mAlarmsList;
    private AlarmAdapter mAlarmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAlarmsListView = (ListView) findViewById(R.id.activity_main_lv_alarm);
        mAlarmsList = new ArrayList<>();
        mAlarmAdapter = new AlarmAdapter(this, R.layout.item_alarm, mAlarmsList, mAlarmDatabase);
        mAlarmsListView.setAdapter(mAlarmAdapter);

        mAlarmDatabase = new AlarmDatabase(this);
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

    private void addAlarmDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_time_picker);

        Calendar calendar = Calendar.getInstance();
        TimePicker timePicker = (TimePicker) dialog.findViewById(R.id.dialog_add_time_picker);
        Button btnAddAlarm = (Button) dialog.findViewById(R.id.dialog_add_btn_add);
        btnAddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();

                Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}