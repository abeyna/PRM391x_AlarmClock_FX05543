package funix.prm.prm391x_alarmclock_fx05543;

public class Alarm {
    private int id;
    private int hour;
    private int minute;
    private boolean isSet;

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
