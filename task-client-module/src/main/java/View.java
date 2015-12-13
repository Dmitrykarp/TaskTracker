import java.util.ArrayList;

/**
 * Created by Support on 06.12.2015.
 */
public class View {

    View() {

    }


    enum Help{
        CONSOLE,
        USERCREATE,
        ERROR,
        USERACCOUNT,
        WORKMENU,
        TASKCREATE,
        TASKRENAME, GOTOUP,

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
                System.out.println("3) Выход");
                break;
            case USERCREATE:
                System.out.println("Введите ID пользователя:");
                break;
            case TASKCREATE:
                System.out.println("Введите название задачи:");
                break;
            case TASKRENAME:
                System.out.println("Введите новое название:");
                break;
            case GOTOUP:
                System.out.println("(введите UP для выхода в корень всех задач)");
                break;
            case WORKMENU:
                System.out.println("Введите номер команды:");
                System.out.println("1) Получить список задач");
                System.out.println("2) Выбрать корневую задачу");
                System.out.println("3) Создать задачу");
                System.out.println("4) Переименовать задачу");
                System.out.println("5) Удалить задачу");
                System.out.println("6) Активировать задачу");
                System.out.println("7) Статистика");
                System.out.println("8) Выход");
                break;
            case ERROR:
                System.out.println("Ошибка!");

            default:
                System.out.println("Введите команду [help] для справки.");

        }
    }

    public void printTask(ArrayList<Task> tasks) {
        for (Task task: tasks){
            System.out.println(task.getId() +" " +task.getName());
            }
    }

}
