package funix.prm.prm391x_alarmclock_fx05543;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Luan.Nguyen
 * @since May 31st 2021
 *                      Alarm adapter for ListView
 */
public class AlarmAdapter extends BaseAdapter {
    /** Main context for receive method from MainActivity. */
    private MainActivity mContext;

    /** Resource layout. */
    private int mLayout;

    /** Alarms array list.*/
    private List<Alarm> mAlarmsList;

    public AlarmAdapter(MainActivity context, int layout, List<Alarm> alarmsList) {
        this.mContext = context;
        this.mLayout = layout;
        this.mAlarmsList = alarmsList;
    }

    @Override
    public int getCount() {
        return mAlarmsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        RelativeLayout itemLayout;
        ToggleButton toggleButton;
        TextView textAlarm;
        ImageView imgDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        Alarm alarm = mAlarmsList.get(position);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mLayout, null);

            viewHolder.itemLayout = (RelativeLayout) convertView.findViewById(R.id.item_alarm_layout);
            viewHolder.toggleButton = (ToggleButton) convertView.findViewById(R.id.item_alarm_toggle_btn);
            viewHolder.textAlarm = (TextView) convertView.findViewById(R.id.item_alarm_tv_alarm);
            viewHolder.imgDelete = (ImageView) convertView.findViewById(R.id.item_alarm_img_delete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (alarm.getHour() <= 10 && alarm.getMinute() > 10) {
            viewHolder.textAlarm.setText("0" + alarm.getHour() + ":" + alarm.getMinute());
        } else if (alarm.getHour() >= 10 && alarm.getMinute() < 10) {
            viewHolder.textAlarm.setText(alarm.getHour() + ":0" + alarm.getMinute());
        } else if(alarm.getHour() <= 10 && alarm.getMinute() < 10) {
            viewHolder.textAlarm.setText("0" + alarm.getHour() + ":0" + alarm.getMinute());
        } else {
            viewHolder.textAlarm.setText(alarm.getHour() + ":" + alarm.getMinute());
        }

        viewHolder.toggleButton.setChecked(alarm.isSet());
        viewHolder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                Intent intent= new Intent(mContext, AlarmReceiver.class);;
                PendingIntent pendingIntent;

                if(isChecked) {
                    int hourOfDay = alarm.getHour();
                    int minute = alarm.getMinute();

                    Calendar c = Calendar.getInstance();
                    c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    c.set(Calendar.MINUTE, minute);
                    c.set(Calendar.SECOND, 0);

                    if (c.before(Calendar.getInstance())) {
                        c.add(Calendar.DATE, 1);
                    }

                    intent.putExtra("ALARM_STATUS", 1);
                    pendingIntent = PendingIntent.getBroadcast(mContext, alarm.getId(), intent, 0);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
                    alarm.setSet(true);
                    Toast.makeText(mContext.getApplicationContext(), "Alarm is ON " + alarm.getHour() + ":" + alarm.getMinute(), Toast.LENGTH_SHORT).show();
                } else {
                    intent.putExtra("ALARM_STATUS", 0);
                    pendingIntent = PendingIntent.getBroadcast(mContext, alarm.getId(), intent, 0);
                    alarmManager.cancel(pendingIntent);
                    if (alarm.isSet() == true) {
                        mContext.sendBroadcast(intent);
                    }
                    alarm.setSet(false);
                    Toast.makeText(mContext.getApplicationContext(), "Alarm is OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.removeAlarm(alarm.getId());
            }
        });

        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.editAlarm(alarm.getId());
            }
        });

        return convertView;
    }
}
