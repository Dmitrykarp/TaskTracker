import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Support on 06.12.2015.
 */
public class View {
    SimpleDateFormat format = new SimpleDateFormat("YYYY.MM.dd");
    View() {

    }


    enum Help{
        CONSOLE,
        USERCREATE,
        ERROR,
        USERACCOUNT,
        WORKMENU,
        TASKCREATE,
        TASKRENAME,
        GOTOUP,
        TASKACTION,

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
            case TASKACTION:
                System.out.println("Введите <STOP> для остановки задачи!");
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
        System.out.println("===============");
        for (Task task: tasks){
            System.out.println(task.getName());
            }
        System.out.println("===============");
    }

    public void printStat(Task taskStat) {
        System.out.printf("| %10s | %10s | %10s | \n","NAME","DATE","TIMER");
        System.out.printf("| %10s | %10s | %10s | \n",taskStat.getName(),format.format(taskStat.getCalendar().getTime()),taskStat.getHistory());


    }

}
