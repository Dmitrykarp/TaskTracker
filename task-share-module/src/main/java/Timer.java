import java.io.Serializable;
import java.util.Calendar;

public class Timer implements Serializable {
    private static final long serialVersionUID = 8812341020159611234L;
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

    public void setLongDate(Timer t){
        longDate+=t.getLongDate();
    }

    public Calendar getStartDate() {
        return startDate;
    }

    //TODO Перенести к клиенту, а тут возвращать просто LONG!
    public long getLongDate() {
        return longDate;
    }

    public boolean oneDay (Timer t){
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
