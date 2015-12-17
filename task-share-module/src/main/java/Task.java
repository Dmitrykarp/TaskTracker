import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class Task implements Serializable {
    private static final long serialVersionUID = 8887001020159611234L;
    private String name;
    private int id;
    private long allTime=0;
    private ArrayList<Task> tasks= new ArrayList<Task>();
    private HashMap<User, ArrayList<Timer>> history = new HashMap<User,ArrayList<Timer>>();

    public Task(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public Task(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public HashMap<User, ArrayList<Timer>> getHistory() {
        return history;
    }

    public void setOneHistory(User u, Timer t){
        if(history.containsKey(u)) {
            ArrayList<Timer> list = history.get(u);
            Timer tempTimer = list.get(list.size()-1);
            if(tempTimer.oneDay(t)){
                tempTimer.setLongDate(t);
            }else{
                history.get(u).add(t);
            }
        } else{
            ArrayList<Timer> list = new ArrayList<Timer>();
            list.add(t);
            history.put(u, list);
        }

    }

    public void updateAllTime(User user){
        allTime=0;
        if(history.containsKey(user)){
            ArrayList<Timer> list = history.get(user);

            for (Timer t: list){
                allTime+=t.getLongDate();
            }
            if(tasks.size() != 0){
                for(Task task: tasks){
                    task.updateAllTime(user);
                }
            }
        }
    }

    public void upd(User user){
        allTime+=updParent(user);
    }

    private long updParent(User user){
        if(history.containsKey(user)){
            if(this.tasks.size() == 0){
                return allTime;
            } else{
                for (Task task: this.tasks){
                    allTime+=task.updParent(user);
                }
                return allTime;
            }
        }else {
            return 0;
        }
    }

    public long getAllTime() {
        return allTime;
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
