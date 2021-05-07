package funix.prm.prm391x_alarmclock_fx05543;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

public class AlarmAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<Alarm> alarmsList;

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

        Alarm alarm = alarmsList.get(position);
        viewHolder.textAlarm.setText(alarm.getHour() + ":" + alarm.getMinute());

        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.removeAlarm(alarm.getId());
            }
        });

        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "ABV", Toast.LENGTH_SHORT).show();
                context.editAlarm(alarm.getId());
            }
        });

        return convertView;
    }
}
