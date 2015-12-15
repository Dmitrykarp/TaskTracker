import java.util.ArrayList;

/**
 * Created by Support on 06.12.2015.
 */
public class Model {
    ArrayList<Task> tasks = new ArrayList<Task>();
    ArrayList<User> users = new ArrayList<User>();

    Model(){

    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public void addUser(User user){
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

    public boolean findEqualsUser(User u){
        for(User user: users){
            if(user.equals(u)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int findMaxId(ArrayList<Task> tasks1){
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

    public boolean findTask(String name, Task tempTask){
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

    public Task getFindTask (String name,Task tempTask){
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

    public void dellTask(Task task){
        tasks.remove(task);
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

}
