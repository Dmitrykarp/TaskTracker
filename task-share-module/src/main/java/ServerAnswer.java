import java.io.Serializable;

/**
 * Класс - обертка для отправки данных от сервера к клиентам.
 *
 * @author Karpenko Dmitry
 */
public class ServerAnswer implements Serializable {

    private static final long serialVersionUID = 4168691073568781445L;
    private Type type;
    private String message;
    private Object object;

    /**
     * Перечисление всех возможных ответов.
     */
    public enum Type {
        SUCCESS, FAILURE, EXCEPTION
    }

    /**
     * Конструктор для отправки сообщения клиенту.
     *
     * @param type Тип сообщения
     * @param message Текст сообщения
     */
    private ServerAnswer(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * Конструктор для отправки объекта клиенту.
     *
     * @param object Экземпляр объекта.
     */
    private ServerAnswer(Object object){
        this.object=object;
    }

    /**
     * Метод для упаковки сообщения.
     *
     * @param message Текст сообщения.
     *
     * @return Экземпляр ServerAnswer с упакованным сообщением.
     */
    public static ServerAnswer success(String message) {
        return new ServerAnswer(Type.SUCCESS, message);
    }

    /**
     * Метод для упаковки объектов.
     *
     * @param object Экземпляр объекта.
     *
     * @return Экземпляр ServerAnswer с упакованным объектом.
     */
    public static ServerAnswer success(Object object) { return  new ServerAnswer(object);}

    /**
     * Метод для сообщения об ошибке при работе с данными.
     *
     * @param message Текст ошибки.
     *
     * @return Экземпляр ServerAnswer с упакованным сообщением.
     */
    public static ServerAnswer failure(String message) {
        return new ServerAnswer(Type.FAILURE, message);
    }

    /**
     * Метод для сообщения об ошибке при работе сервера. (внутренние ошибки)
     *
     * @param message Текст сообщения.
     *
     * @return Экземпляр ServerAnswer с упакованным сообщением.
     */
    public static ServerAnswer exception(String message) {
        return new ServerAnswer(Type.EXCEPTION, message);
    }

    /**
     * Метод возвращает тип сообщения.
     *
     * @return Тип из перечисления Type.
     */
    public Type getType() {
        return type;
    }

    /**
     * Метод возвращает сообщение.
     *
     * @return Текст сообзения.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Метод возвращает объект.
     *
     * @return Экземпляр переданного объекта.
     */
    public Object getObject() { return  object;}
}