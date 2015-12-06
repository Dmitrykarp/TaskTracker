/**
 * Created by Support on 06.12.2015.
 */
public class View {

    View() {
        System.out.println("Введите команду [help] для справки.");
    }

    enum Help{
        CONSOLE,
        USERCREATE,
        ERROR,
        USERACCOUNT,
        TASK
    }

    public void printConsole(Help enume){
        switch (enume){
            case CONSOLE:
                System.out.print(">");
                break;
            case USERACCOUNT:
                System.out.println("Введите номер команды:");
                System.out.println("1) Войти в систему");
                System.out.println("2) Зарегистрироваться");
                break;
            case USERCREATE:
                System.out.println("Введите ID пользователя:");
                break;
            case ERROR:
                System.out.println("Ошибка!");

            default:
                System.out.println("Введите команду [help] для справки.");

        }
    }
}
