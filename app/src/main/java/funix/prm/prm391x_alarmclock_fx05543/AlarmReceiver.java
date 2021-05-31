package funix.prm.prm391x_alarmclock_fx05543;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author Luan.Nguyen
 * @since May 31st 2021
 *                      Alarm broadcast receiver.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int alarmStatus = intent.getIntExtra("ALARM_STATUS", 0);
        Intent myIntent = new Intent(context, RingtoneService.class);
        myIntent.putExtra("ALARM_STATUS", alarmStatus);
        context.startService(myIntent);

        Toast.makeText(context.getApplicationContext(), "Alarm is ringing", Toast.LENGTH_SHORT).show();
    }
}
