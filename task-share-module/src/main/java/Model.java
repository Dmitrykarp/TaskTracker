import java.util.ArrayList;

/**
 * Created by Support on 06.12.2015.
 */
public class Model {
    //TODO Добавить поиск юзеров! ну и наполнить ее всю)))
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

    public int findMaxId(){
        int temp=0;
        for(Task task: tasks){
            if(task.getTasks().isEmpty()){
                if(task.getId()>temp) temp=task.getId();
            } else{
                for (Task t: task.getTasks()){
                    temp=findMaxId();
                    if(t.getId()>temp) temp=t.getId();
                }
            }
        }
        return temp;
    }

    public boolean findTask(String name){
        boolean b = false;
        for (Task task: tasks){
            if(task.getTasks().isEmpty()){
                if(task.getName().equals(name)) {
                    b=true;
                    break;
                }
            } else{
                for (Task t: task.getTasks()){
                    if(!b){
                        b=findTask(name);
                    }
                }
            }
        }
        return b;
    }

    public Task getFindTask (String name){
        Task temp=null;
        for (Task task: tasks){
            if(task.getTasks().isEmpty()){
                if(task.getName().equals(name)) {
                    temp=task;
                    break;
                }
            } else{
                for (Task t: task.getTasks()){
                      temp=getFindTask(name);
                }
            }
        }
        return temp;
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
