package funix.prm.prm391x_alarmclock_fx05543;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent myIntent = new Intent(context, RingtoneService.class);
        context.startService(myIntent);

        Toast.makeText(context.getApplicationContext(), "Alarm is ringing", Toast.LENGTH_SHORT).show();
    }
}
