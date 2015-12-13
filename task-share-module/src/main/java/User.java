import java.io.Serializable;

/**
 * Created by Support on 06.12.2015.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 4168691073568781123L;
    private int id;

    public User (int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if(id>0){
            this.id = id;
        }
        else throw new IllegalArgumentException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return id == user.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
