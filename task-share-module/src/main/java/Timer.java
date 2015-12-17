import java.io.Serializable;
import java.util.Calendar;

/**
 * Класс реализующий логику подсчета времени активности задач
 */
public class Timer implements Serializable {
    private static final long serialVersionUID = 8812341020159611234L;
    private Calendar startDate;
    private Calendar endDate;
    private long longDate;

    /**
     * Конструктор инициализирует начало таймера.
     */
    public Timer() {
        this.startDate = Calendar.getInstance();
    }

    /**
     * Метод устанавливает конец отсчета времени.
     */
    public synchronized void setEndDate() {
        this.endDate = Calendar.getInstance();
    }

    /**
     * Метод считает разницу между стартом и остановкой таймера.
     */
    public synchronized void setLongDate(){
        longDate = endDate.getTime().getTime() - startDate.getTime().getTime();
    }

    /**
     * Метод увеличивает значение таймера на время другого таймера.
     *
     * @param timer Таймер для слияния.
     */
    public synchronized void setLongDate(Timer timer){
        longDate+=timer.getLongDate();
    }

    /**
     * Метод позволяет получить время старта таймера.
     *
     * @return Время запуска.
     */
    public synchronized Calendar getStartDate() {
        return startDate;
    }

    /**
     * Метод позволяет получить значение таймера.
     *
     * @return Числовое представление времени.
     */
    public synchronized long getLongDate() {
        return longDate;
    }

    /**
     * Метод позволяет узнать, принадлежат ли 2 таймера одному дню.
     *
     * @param timer Сравниваемый таймер.
     *
     * @return True - если принадлежат одному дню, false - в противном случае.
     */
    public synchronized boolean oneDay (Timer timer){
        if(this.startDate.getTime().getYear() == timer.startDate.getTime().getYear()){
            if(this.startDate.getTime().getMonth() == timer.startDate.getTime().getMonth()){
                if(this.startDate.getTime().getDay() == timer.startDate.getTime().getDay()){
                    return true;
                }
            }
        }
        return false;
    }
}
