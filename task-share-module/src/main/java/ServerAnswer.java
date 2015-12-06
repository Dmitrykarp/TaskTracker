import java.io.Serializable;

public class ServerAnswer implements Serializable {

    private static final long serialVersionUID = 4168691073568781445L;
    private Type type;
    private String message;

    public enum Type {
        SUCCESS, FAILURE, EXCEPTION
    }

    private ServerAnswer(Type type, String message) {
        this.type = type;
        this.message = message;
    }

    public static ServerAnswer success(String message) {
        return new ServerAnswer(Type.SUCCESS, message);
    }

    public static ServerAnswer failure(String message) {
        return new ServerAnswer(Type.FAILURE, message);
    }

    public static ServerAnswer exception(String message) {
        return new ServerAnswer(Type.EXCEPTION, message);
    }

    public Type getType() {
        return type;
    }
    public String getMessage() {
        return message;
    }



}