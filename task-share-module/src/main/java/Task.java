import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Task implements Serializable {
    private static final long serialVersionUID = 8887001020159611234L;
    private String name;
    private int id;
    private ArrayList<Task> tasks= new ArrayList<Task>();
    private HashMap<User, Timer> history = new HashMap<User,Timer>();

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

    public HashMap<User, Timer> getHistory() {
        return history;
    }

    public void setOneHistory(User u, Timer t){
        history.put(u, t);
    }

    public void setHistory(HashMap<User, Timer> history) {
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
}
