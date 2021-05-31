package funix.prm.prm391x_alarmclock_fx05543;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;

/**
 * @author Luan.Nguyen
 * @since May 31st 2021
 *                      Ringtone service.
 */
public class RingtoneService extends Service {
    /** Media player to control alarm.*/
    MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int alarmStatus = intent.getIntExtra("ALARM_STATUS", 0);
        if (alarmStatus == 1) {
            mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_ALARM_ALERT_URI);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            Toast.makeText(getApplicationContext(), "Alarm is ringing", Toast.LENGTH_SHORT).show();
        } else {
            if(mediaPlayer != null) mediaPlayer.stop();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null) mediaPlayer.stop();
    }
}
