package funix.prm.prm391x_alarmclock_fx05543;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.Nullable;

public class RingtoneService extends Service {
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
