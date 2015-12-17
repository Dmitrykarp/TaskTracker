import java.io.Serializable;

/**
 * Класс - обертка для отправки данных от клиента к серверу.
 *
 * @author Karpenko Dmitry
 */
public class ClientCommand implements Serializable {

    private static final long serialVersionUID = 8887001020159619356L;
    private Action action;
    private Object object;
    private String oldName, newName;

    /**
     * Перечисление всех возможных команд от клиента к серверу.
     */
    public enum Action {
        SIGNIN, RENAMETASK, SIGNUP, CREATETASK, DELETETASK, SELECTTASK, GETTASKS, TASKSTAT
    }

    /**
     * Конструктор для пересылки экземпляра объектов.
     *
     * @param action Команда, по которой сервер поймет, что делать с объектом.
     * @param object Экземпляр объекта.
     */
    public ClientCommand(Action action, Object object) {
        this.action = action;
        this.object = object;
    }

    /**
     * Конструктор для отправки только команды от клиента к серверу.
     *
     * @param action Команда серверу.
     */
    public ClientCommand(Action action){
        this.action = action;
    }

    /**
     * Конструктор для отправки команды изменения имени (пользователя, задачи...)
     *
     * @param action Команда для сервера.
     * @param oldName Старое имя.
     * @param newName Новое имя.
     */
    public ClientCommand(Action action ,String oldName, String newName){
        this.action=action;
        this.oldName=oldName;
        this.newName=newName;
    }

    /**
     * Метод позволяет получить тип команды.
     * @return Тип команды из перечисления Action.
     */
    public Action getAction() {
        return action;
    }

    /**
     * Метод позволяет получить экземпляр передаваемого объекта.
     *
     * @return Экземпляр объекта.
     */
    public Object getObject() {
        return object;
    }

    /**
     * Метод позволяет получить старое имя, для дальнейшего использования.
     *
     * @return Имя.
     */
    public String getOldName() {
        return oldName;
    }

    /**
     * Метод позволяет получить новое имя, для дальнейшего использования.
     *
     * @return Имя.
     */
    public String getNewName() {
        return newName;
    }
}
