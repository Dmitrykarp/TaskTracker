import java.util.Calendar;

/**
 * Created by Support on 06.12.2015.
 */
public class Timer {
    private Calendar startDate;
    private Calendar endDate;
    private long longDate;

    public Timer() {
        this.startDate = Calendar.getInstance();
    }

    public void setEndDate() {
        this.endDate = Calendar.getInstance();
    }

    public void setLongDate(){
        longDate = endDate.getTime().getTime() - startDate.getTime().getTime();
    }

    //TODO Перенести к клиенту, а тут возвращать просто LONG!
    public long[] getLongDate() {
        long[] time = new long[3];
        time[0] = longDate / 86400000; //Days
        time[1] = (longDate / 3600000) - time[0]*24 ; //Hours
        time[2] = longDate / 60000 - time[1]*60; //Minutes

        return time;
    }
}
