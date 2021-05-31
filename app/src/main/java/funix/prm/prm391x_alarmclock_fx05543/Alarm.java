package funix.prm.prm391x_alarmclock_fx05543;

/**
 * @author Luan.Nguyen
 * @since May 31st 2021
 *                      Alarm object
 */
public class Alarm {
    /** Alarm ID. */
    private int id;

    /** Alarm hour. */
    private int hour;

    /** Alarm minute. */
    private int minute;

    /** Alarm status (ON/OFF)*/
    private boolean isSet;

    public Alarm() {}

    public Alarm(int id, int hour, int minute, boolean isSet) {
        this.id = id;
        this.hour = hour;
        this.minute = minute;
        this.isSet = isSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isSet() {
        return isSet;
    }

    public void setSet(boolean set) {
        isSet = set;
    }
}
