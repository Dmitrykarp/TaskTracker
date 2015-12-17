import java.io.Serializable;

/**
 * Класс объекта "пользователь".
 *
 * @author Karpenko Dmitry
 */
public class User implements Serializable {
    private static final long serialVersionUID = 4168691073568781123L;
    private int id;

    /**
     * Конструктору необходимо указать номер пользователя.
     *
     * @param id Номер пользователя.
     */
    public User (int id){
        this.id=id;
    }

    /**
     * Метод позволяет получить идентификатор пользователя.
     *
     * @return Номер пользователя.
     */
    public int getId() {
        return id;
    }

    /**
     * Метод позволяет установить номер пользователя.
     *
     * @param id Номер пользователя.
     */
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
