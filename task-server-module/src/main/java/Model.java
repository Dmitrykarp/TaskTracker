import java.io.Serializable;
import java.util.ArrayList;

/**
 * Класс описывает логику работы с данными.
 * Экземпляр класса используется для сериализации в файл.
 *
 * @author Karpenko Dmitry
 */
public class Model implements Serializable {
    ArrayList<Task> tasks = new ArrayList<Task>();
    ArrayList<User> users = new ArrayList<User>();

    Model(){
    }

    /**
     * Метод добавляет дочернюю задачу.
     *
     * @param task Дочерняя задача.
     */
    public synchronized void addTask(Task task){
        tasks.add(task);
    }

    /**
     * Метод добавляет нового пользователя в базу.
     *
     * @param user Экземпляр пользователя.
     */
    public synchronized void addUser(User user){
        users.add(user);
    }

    /**
     * Метод позволяет получить экземпляр user'a из базы.
     *
     * @param id Номер пользователя.
     *
     * @return Экземпляр пользователя.
     *
     * @throws IllegalArgumentException Возникает, если пользователя с данным номером нет в базе.
     */
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

    /**
     * Метод проверяет наличие пользователя в базе.
     *
     * @param tempUser Экземпляр пользователя для поиска.
     *
     * @return true - если пользователь найден, false - в противном случае.
     */
    public synchronized boolean findEqualsUser(User tempUser){
        for(User user: users){
            if(user.equals(tempUser)){
                return true;
            }
        }
        return false;
    }

    /**
     * Метод позволяет получить список всех задач.
     *
     * @return Список задач из базы.
     */
    public synchronized ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Метод производит поиск максимального значения идентификатора задачи.
     *
     * @param tempTask Список задач для поиска.
     *
     * @return Максимальное значение идентификатора задачи.
     */
    public synchronized int findMaxId(ArrayList<Task> tempTask){
        int temp=0;
        for(Task task: tempTask){
            if(task.getTasks().isEmpty()){
                if(task.getId()>temp) temp=task.getId();
            } else{
                temp=findMaxId(task.getTasks());


            }
        }
        return temp;
    }

    /**
     * Метод производит поиск задачи по имени, в списке всех задач.
     *
     * @param name Имя искомой задачи.
     * @param tempTask Корневая задача для поиска.
     *
     * @return true - если искомая задача найдена, false - в противном случае.
     */
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

    /**
     * Метод позволяет получить экземпляр задачи из базы по ее имени.
     *
     * @param name Имя задачи для поиска.
     * @param tempTask Корневая задача для поиска.
     *
     * @return Экземпляр найденной задачи.
     */
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

    /**
     * Метод удаляет задачу из базы.
     *
     * @param task Экземпляр задачи для удаления.
     */
    public synchronized void dellTask(Task task){
        tasks.remove(task);
    }

    /**
     * Метод позволяет изменить ссылку на список задач.
     *
     * @param tasks Новый список задач.
     */
    public synchronized void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Метод позволяет получить список всех пользователей.
     *
     * @return Список пользователей с базы.
     */
    public synchronized ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Метод позволяет изменить ссылку на список пользователей.
     *
     * @param users Новый список пользователей.
     */
    public synchronized void setUsers(ArrayList<User> users) {
        this.users = users;
    }

}
