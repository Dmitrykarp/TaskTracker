import java.io.Serializable;
import java.util.Calendar;

/**
 * 
 */
public class Timer implements Serializable {
    private static final long serialVersionUID = 8812341020159611234L;
    private Calendar startDate;
    private Calendar endDate;
    private long longDate;

    public Timer() {
        this.startDate = Calendar.getInstance();
    }

    public synchronized void setEndDate() {
        this.endDate = Calendar.getInstance();
    }

    public synchronized void setLongDate(){
        longDate = endDate.getTime().getTime() - startDate.getTime().getTime();
    }

    public synchronized void setLongDate(Timer t){
        longDate+=t.getLongDate();
    }

    public synchronized Calendar getStartDate() {
        return startDate;
    }

    public synchronized long getLongDate() {
        return longDate;
    }

    public synchronized boolean oneDay (Timer t){
        if(this.startDate.getTime().getYear() == t.startDate.getTime().getYear()){
            if(this.startDate.getTime().getMonth() == t.startDate.getTime().getMonth()){
                if(this.startDate.getTime().getDay() == t.startDate.getTime().getDay()){
                    return true;
                }
            }
        }
        return false;
    }
}
