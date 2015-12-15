import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class Task implements Serializable {
    private static final long serialVersionUID = 8887001020159611234L;
    private String name;
    private int id;
    private Calendar calendar;
    private ArrayList<Task> tasks= new ArrayList<Task>();
    private HashMap<User, ArrayList<Timer>> history = new HashMap<User,ArrayList<Timer>>();

    public Task(String name, int id) {
        this.name = name;
        this.id = id;
        this.calendar = Calendar.getInstance();
    }

    public Task(String name){
        this.name=name;
        this.calendar = Calendar.getInstance();
    }

    public String getName() {
        return name;
    }

    public HashMap<User, ArrayList<Timer>> getHistory() {
        return history;
    }

    public void setOneHistory(User u, Timer t){
        if(history.containsKey(u)) {
            history.get(u).add(t);
        } else{
            ArrayList<Timer> list = new ArrayList<Timer>();
            list.add(t);
            history.put(u, list);
        }

    }

    public void setHistory(HashMap<User, ArrayList<Timer>> history) {
        this.history = history;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public Task getTask(int id){
        Task tempTask=null;
        for(Task t: tasks){
            if(t.getId() == id){
                tempTask=t;
            }
        }
        if(tempTask == null){
            throw new IllegalArgumentException();
        }else return tempTask;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask (Task task){
        tasks.add(task);
    }

    public void dellTask(Task task) {
        tasks.remove(task);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (id != task.id) return false;
        return !(name != null ? !name.equals(task.name) : task.name != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + id;
        return result;
    }
}
