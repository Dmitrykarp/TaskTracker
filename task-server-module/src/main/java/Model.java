import java.io.Serializable;
import java.util.ArrayList;

public class Model implements Serializable {
    ArrayList<Task> tasks = new ArrayList<Task>();
    ArrayList<User> users = new ArrayList<User>();

    Model(){
    }

    public synchronized void addTask(Task task){
        tasks.add(task);
    }

    public synchronized void addUser(User user){
        users.add(user);
    }

    public User getUser(int id){
        User tempUser = null;
        for(User u: users){
            if(u.getId()==id){
                tempUser=u;
            }
        }
        if(tempUser==null){
            throw new IllegalArgumentException();
        } else return tempUser;
    }

    public synchronized boolean findEqualsUser(User u){
        for(User user: users){
            if(user.equals(u)){
                return true;
            }
        }
        return false;
    }

    public synchronized ArrayList<Task> getTasks() {
        return tasks;
    }

    public synchronized int findMaxId(ArrayList<Task> tasks1){
        int temp=0;
        for(Task task: tasks1){
            if(task.getTasks().isEmpty()){
                if(task.getId()>temp) temp=task.getId();
            } else{
                temp=findMaxId(task.getTasks());


            }
        }
        return temp;
    }

    public synchronized boolean findTask(String name, Task tempTask){
        boolean b = false;
        if (tempTask.equals(new Task("PARENT",-1))){
            for (Task task: tasks){
                    if(task.getName().equals(name)) {
                        b=true;
                        break;
                    }
            }
        }else{
            for (Task task:tempTask.getTasks()){
                if(task.getName().equals(name)) {
                    b=true;
                    break;
                }
            }
        }
        return b;
    }

    public synchronized Task getFindTask (String name,Task tempTask){
        Task temp=null;
        if (tempTask.equals(new Task("PARENT",-1))){
            for (Task task: tasks){
                    if(task.getName().equals(name)) {
                        temp=task;
                        break;
                    }
            }
        } else {
            for (Task task:tempTask.getTasks()){
                if(task.getName().equals(name)) {
                    temp=task;
                    break;
                }
            }
        }
        return temp;
    }

    public synchronized void dellTask(Task task){
        tasks.remove(task);
    }

    public synchronized void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public synchronized ArrayList<User> getUsers() {
        return users;
    }

    public synchronized void setUsers(ArrayList<User> users) {
        this.users = users;
    }

}
