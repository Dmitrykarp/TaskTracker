/**
 * Created by Support on 06.12.2015.
 */
import java.io.Serializable;

public class ClientCommand implements Serializable {

    private static final long serialVersionUID = 8887001020159619356L;

    public enum Action {
        SIGNIN, SIGNOUT, SIGNUP
    }

    private Action action;
    private Object object;

    public ClientCommand(Action action, Object object) {
        this.action = action;
        this.object = object;
    }

    public Action getAction() {
        return action;
    }

    public Object getObject() {
        return object;
    }

}
