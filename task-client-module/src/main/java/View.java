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
        DISCONNECT,
        NUMBERFORMAT

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
                System.out.println("6) Статистика");
                System.out.println("7) Выход");
                break;
            case ERROR:
                System.out.println("Ошибка!");
                break;
            case NUMBERFORMAT:
                System.out.println("ID должен состоять только из цифр, максимальное значение: " +Integer.MAX_VALUE);
                break;
            case DISCONNECT:
                System.out.println("Сервер разорвал соединение.");
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

    public void printStat(User u,Task taskStat) {
       if (taskStat == null){
           System.out.println("Задача не найдена.");
       }else {
           ArrayList<Timer> temp = taskStat.getHistory().get(u);
           if (temp == null){
               System.out.println("Нет данных для вывода.");
           }else {
               System.out.println("=======================================================");
               System.out.printf("| %15s | %15s | %15s | \n","NAME","DATE","TIMER");

               for (Timer t: temp){
                   long[] time = new long[3];
                   time[0] = t.getLongDate() / 86400000; //Days
                   time[1] = (t.getLongDate() / 3600000) - time[0]*24 ; //Hours
                   time[2] = t.getLongDate() / 60000 - time[1]*60; //Minutes
                   String timer = time[0] +"д. " +time[1] +"ч. " +time[2] +"м.";
                   System.out.printf("| %15s | %15s | %15s | \n"
                           ,taskStat.getName()
                           ,format.format(t.getStartDate().getTime())
                           ,timer);
               }
               System.out.println("=======================================================");
           }
       }
    }

    public void printAllStat(Task taskStat){
        if (taskStat == null){
            System.out.println("Задача не найдена.");
        }else {
                System.out.println("==========================================");
                System.out.printf("| %15s | %20s | \n","NAME","TIMER");
                printChildrenTime(taskStat);
            System.out.println("==========================================");
        }
    }

    private void printChildrenTime(Task task){
        long[] time = new long[4];
        time[0] = task.getAllTime() / 86400000; //Days
        time[1] = (task.getAllTime() / 3600000) - time[0]*24 ; //Hours
        time[2] = task.getAllTime() / 60000 - time[1]*60; //Minutes
        time[3] = task.getAllTime() / 1000  - time[2]*60;
        String timer = time[0] +"д. " +time[1] +"ч. " +time[2] +"м. " + time[3] +"c.";
        if (task.getTasks().size() == 0) {

            System.out.printf("| %15s | %20s |  \n",task.getName(),timer);
        }else{
            System.out.printf("| %15s | %20s | \n",task.getName(), timer);
            for (Task t: task.getTasks()){
                printChildrenTime(t);
            }
        }
    }

}
