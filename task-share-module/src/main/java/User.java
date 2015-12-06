/**
 * Created by Support on 06.12.2015.
 */
public class User {
    private int id;

    User (int id){
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
}
