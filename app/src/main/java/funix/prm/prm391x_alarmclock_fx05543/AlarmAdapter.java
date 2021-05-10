package funix.prm.prm391x_alarmclock_fx05543;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AlarmAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<Alarm> alarmsList;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    public AlarmAdapter(MainActivity context, int layout, List<Alarm> alarmsList) {
        this.context = context;
        this.layout = layout;
        this.alarmsList = alarmsList;
    }

    @Override
    public int getCount() {
        return alarmsList.size();
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
        Calendar calendar = Calendar.getInstance();
        Alarm alarm = alarmsList.get(position);
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);

            viewHolder.itemLayout = (RelativeLayout) convertView.findViewById(R.id.item_alarm_layout);
            viewHolder.toggleButton = (ToggleButton) convertView.findViewById(R.id.item_alarm_toggle_btn);
            viewHolder.textAlarm = (TextView) convertView.findViewById(R.id.item_alarm_tv_alarm);
            viewHolder.imgDelete = (ImageView) convertView.findViewById(R.id.item_alarm_img_delete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (alarm.getHour() < 10 && alarm.getMinute() > 10) {
            viewHolder.textAlarm.setText("0" + alarm.getHour() + ":" + alarm.getMinute());
        } else if (alarm.getHour() > 10 && alarm.getMinute() < 10) {
            viewHolder.textAlarm.setText(alarm.getHour() + ": 0" + alarm.getMinute());
        } else if(alarm.getHour() < 10 && alarm.getMinute() < 10) {
            viewHolder.textAlarm.setText("0" + alarm.getHour() + ": 0" + alarm.getMinute());
        } else {
            viewHolder.textAlarm.setText(alarm.getHour() + ":" + alarm.getMinute());
        }

        viewHolder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    calendar.set(Calendar.HOUR_OF_DAY, alarm.getHour());
                    calendar.set(Calendar.MINUTE, alarm.getMinute());

                    pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                    alarm.setSet(true);
                    Toast.makeText(context.getApplicationContext(), "Alarm is ON " + alarm.getHour() + "." + alarm.getMinute(), Toast.LENGTH_SHORT).show();
                } else {
                    alarm.setSet(false);
                    Toast.makeText(context.getApplicationContext(), "Alarm is OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.removeAlarm(alarm.getId());
            }
        });

        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.editAlarm(alarm.getId());
            }
        });

        return convertView;
    }
}
