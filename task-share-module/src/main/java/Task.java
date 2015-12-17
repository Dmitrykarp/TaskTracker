import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Класс задачи.
 *
 * @author Karpenko Dmitry
 */
public class Task implements Serializable {
    private static final long serialVersionUID = 8887001020159611234L;
    private String name;
    private int id;
    private long allTime=0;
    private ArrayList<Task> tasks= new ArrayList<Task>();
    private HashMap<User, ArrayList<Timer>> history = new HashMap<User,ArrayList<Timer>>();

    /**
     * Конструктору необходимо указать имя задачи и ее идентификатор.
     *
     * @param name Текст имени задачи.
     * @param id Номер задачи.
     */
    public Task(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**
     * Конструктор для создания временной задачи без идентификатора.
     *
     * @param name Текстовое имя задачи.
     */
    public Task(String name){
        this.name=name;
    }

    /**
     * Метод выводит имя задачи.
     *
     * @return Текстовое имя задачи.
     */
    public synchronized String getName() {
        return name;
    }

    /**
     * Метод позволяет получить всю историю использования задачи всеми пользователями.
     *
     * @return Коллекция истории.
     */
    public synchronized HashMap<User, ArrayList<Timer>> getHistory() {
        return history;
    }

    /**
     * Метод добавляет новую историю использования задачи пользователем.
     *
     * @param user Пользователь.
     * @param timer Таймер пользователя.
     */
    public synchronized void setOneHistory(User user, Timer timer){
        if(history.containsKey(user)) {
            ArrayList<Timer> list = history.get(user);
            Timer tempTimer = list.get(list.size()-1);
            if(tempTimer.oneDay(timer)){
                tempTimer.setLongDate(timer);
            }else{
                history.get(user).add(timer);
            }
        } else{
            ArrayList<Timer> list = new ArrayList<Timer>();
            list.add(timer);
            history.put(user, list);
        }

    }

    /**
     * Метод обновляет время использования задачи за все дни с ее создания для конкретного пользователя.
     *
     * @param user Пользователь.
     */
    public synchronized void updateAllTime(User user){
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

    /**
     * Метод обновляет суммарное время для родительских задач.
     *
     * @param user Экземпляр конкретного пользователя.
     */
    public synchronized void updateParentAllTime(User user){
        allTime+=updParent(user);
    }

    /**
     * Утилитный метод для обновления суммарного времени.
     *
     * @param user Пользователь.
     *
     * @return Время использования задач.
     */
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

    /**
     * Метод позволяет получить суммарное время использования задачи.
     *
     * @return Суммарное время использования.
     */
    public synchronized long getAllTime() {
        return allTime;
    }

    /**
     * Метод позволяет изменить ссылку на историю использования.
     *
     * @param history Ссылка на новую историю.
     */
    public synchronized void setHistory(HashMap<User, ArrayList<Timer>> history) {
        this.history = history;
    }

    /**
     * Метод позволяет установить имя задачи.
     *
     * @param name Текстовое значение имени задачи.
     */
    public synchronized void setName(String name) {
        this.name = name;
    }

    /**
     * Метод позволяет получить номер задачи.
     *
     * @return Номер задачи.
     */
    public int getId() {
        return id;
    }

    /**
     * Метод устанавливает значение номера задачи.
     *
     * @param id Номер задачи.
     */
    public synchronized void setId(int id) {
        this.id = id;
    }

    /**
     * Метод возвращает список всех дочерних задач.
     *
     * @return Список задач.
     */
    public synchronized ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Метод позволяет получить конкретную дочернюю задачу.
     *
     * @param id Номер задачи.
     *
     * @return Экземпляр задачи.
     *
     * @throws IllegalArgumentException Возникает, если задача не найдена в списке.
     */
    public synchronized Task getTask(int id){
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

    /**
     * Метод позволяет изменить ссылку на новый список дочерних задач.
     *
     * @param tasks Список задач.
     */
    public synchronized void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Метод добавляет дочернюю задачу.
     *
     * @param task Экземпляр задачи.
     */
    public synchronized void addTask (Task task){
        tasks.add(task);
    }

    /**
     * Метод удаляет конкретную задачу из списка дочерних задач.
     *
     * @param task Экземпляр задачи.
     */
    public synchronized void dellTask(Task task) {
        tasks.remove(task);
    }

    @Override
    public synchronized boolean equals(Object o) {
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
