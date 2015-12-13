/**
 * Created by Support on 06.12.2015.
 */
import java.io.Serializable;

public class ClientCommand implements Serializable {

    private static final long serialVersionUID = 8887001020159619356L;

    public enum Action {
        SIGNIN, RENAMETASK, SIGNUP, CREATETASK, DELETETASK, SELECTTASK, GETTASKS, TASKACTION, TASKSTAT, STOP
    }

    private Action action;
    private Object object;
    private String oldName, newName;

    public ClientCommand(Action action, Object object) {
        this.action = action;
        this.object = object;
    }

    public ClientCommand(Action action){
        this.action = action;
    }

    public ClientCommand(Action action ,String oldName, String newName){
        this.action=action;
        this.oldName=oldName;
        this.newName=newName;
    }

    public Action getAction() {
        return action;
    }

    public Object getObject() {
        return object;
    }

    public String getOldName() {
        return oldName;
    }

    public String getNewName() {
        return newName;
    }
}
