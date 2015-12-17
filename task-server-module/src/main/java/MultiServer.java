import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * Класс, работающий с подключенными клиентами. Позволяет обрабатывать команды от клиента
 * в отдельном потоке.
 *
 * @author Karpenko Dmitry
 */
public class MultiServer extends Thread {
    private Socket socket;
    // Флаг пользователя
    private User user;
    private Model model;
    // Флаг корневой задачи
    private Task taskUP = new Task("PARENT",-1);
    // Флаг задачи, в которой находится пользователь в данный момент времени.
    private Task userInTask;
    // Таймер, для отчета времени между переключениями задач.
    private Timer tikTak;

    /**
     * Конструктору необходимо явно указать сокет и модель для дальнейшей работы.
     *
     * @param socket Ссылка на сокет.
     * @param model  Ссылка на модель.
     *
     * @throws IOException Возникает при ошибках Ввода\Вывода.
     */
    public MultiServer(Socket socket, Model model) throws IOException{
        this.socket = socket;
        this.model = model;

        start();
    }

    /**
     * Метод реализует основную логику работы многопоточного сервера.
     * Принимает команды от клиента, обрабатывает модель и
     * отправляет ответ на клиент.
     */
    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;
        tikTak = new Timer();

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());


            while (true) {
                try {
                    Object readed = ois.readObject();
                    if (readed instanceof ClientCommand) {
                        ClientCommand command = (ClientCommand) readed;
                        switch (command.getAction()) {
                            //Вход пользователя
                            case SIGNIN:
                                user = (User) command.getObject();
                                if (model.findEqualsUser(user)) {
                                    oos.writeObject(ServerAnswer.success("Вход выполнен!"));
                                    oos.reset();
                                    userInTask = taskUP;
                                } else {
                                    oos.writeObject(ServerAnswer.failure("Пользователь не найден!"));
                                    oos.reset();
                                }
                                break;
                            // Создание нового пользователя
                            case SIGNUP:
                                User tempUser = (User) command.getObject();
                                if (model.findEqualsUser(tempUser)) {
                                    oos.writeObject(ServerAnswer.failure("Такой пользователь существует!"));
                                    oos.reset();
                                } else {
                                    oos.writeObject(ServerAnswer.success("Пользователь создан!"));
                                    oos.reset();
                                    user = tempUser;
                                    model.addUser(user);
                                    userInTask = taskUP;
                                }
                                break;
                            // Получение списка задач
                            case GETTASKS:
                                if (userInTask.equals(taskUP)) {
                                    oos.writeObject(ServerAnswer.success(model.getTasks()));
                                    oos.reset();
                                } else {
                                    oos.writeObject(ServerAnswer.success(userInTask.getTasks()));
                                    oos.reset();
                                }
                                break;
                            // Создание задачи
                            case CREATETASK:
                                Task newTask = (Task) command.getObject();
                                if(model.findTask(newTask.getName(), userInTask)){
                                    oos.writeObject(ServerAnswer.failure("Задача с таким именем уже существует!"));
                                    oos.reset();
                                } else {
                                    int i = model.findMaxId(model.getTasks());
                                    newTask.setId(i +1);
                                    if (userInTask.equals(taskUP)) {
                                        model.addTask(newTask);
                                    } else {
                                        userInTask.addTask(newTask);
                                    }
                                    oos.writeObject(ServerAnswer.success("Задача успешно создана!"));
                                    oos.reset();
                                }
                                break;
                            // Выбор родительской задачи
                            case SELECTTASK:
                                newTask = (Task) command.getObject();
                                if (userInTask.equals(taskUP)){
                                    tikTak.setEndDate();
                                    tikTak.setLongDate();
                                    model.getTasks().get(0).setOneHistory(user, tikTak);
                                }

                                if("UP".equals(newTask.getName()) && !taskUP.getName().equals(newTask.getName())){
                                    tikTak.setEndDate();
                                    tikTak.setLongDate();
                                    userInTask.setOneHistory(user,tikTak);
                                    userInTask =taskUP;
                                    oos.writeObject(ServerAnswer.success("Вы находитесь в корне всех задач!"));
                                    oos.reset();
                                    tikTak = new Timer();
                                    break;
                                }
                                if(model.findTask(newTask.getName(), userInTask)){
                                    tikTak.setEndDate();
                                    tikTak.setLongDate();
                                    userInTask.setOneHistory(user,tikTak);
                                    userInTask =model.getFindTask(newTask.getName(), userInTask);
                                    oos.writeObject(ServerAnswer.success("Вход в задачу " +newTask.getName() +" выполнен!"));
                                    oos.reset();
                                    tikTak = new Timer();
                                } else {
                                    oos.writeObject(ServerAnswer.failure("Задача не найдена!"));
                                    oos.reset();
                                }
                                break;
                            // Переименование задачи.
                            case RENAMETASK:
                                String oldName = command.getOldName();
                                String newName = command.getNewName();
                                if(model.findTask(oldName, userInTask)){
                                    Task task = model.getFindTask(oldName, userInTask);
                                    if (task.equals(model.getFindTask("Не работа", userInTask))){
                                        oos.writeObject(ServerAnswer.failure("Данную задачу нельзя переименовать или удалить!"));
                                        oos.reset();
                                    }else {
                                        task.setName(newName);
                                        oos.writeObject(ServerAnswer.success("Задача переименована"));
                                        oos.reset();
                                    }
                                }else{
                                    oos.writeObject(ServerAnswer.failure("Задача не найдена"));
                                    oos.reset();
                                }
                                break;
                            // Удаление задачи и всех ее подзадач.
                            case DELETETASK:
                                Task t = (Task) command.getObject();
                                if (model.findTask(t.getName(), userInTask)){
                                    Task task = model.getFindTask(t.getName(), userInTask);
                                    if (task.equals(model.getFindTask("Не работа", userInTask))){
                                        oos.writeObject(ServerAnswer.failure("Данную задачу нельзя переименовать или удалить!"));
                                        oos.reset();
                                    }else if (userInTask.equals(taskUP)){
                                        model.dellTask(task);
                                        oos.writeObject(ServerAnswer.success("Задача удалена"));
                                        oos.reset();
                                    }else {
                                        userInTask.dellTask(task);
                                        oos.writeObject(ServerAnswer.success("Задача удалена"));
                                        oos.reset();
                                    }

                                }else{
                                    oos.writeObject(ServerAnswer.failure("Задача не найдена"));
                                    oos.reset();
                                }
                                break;
                            // Получение статистики
                            case TASKSTAT:
                                newTask = (Task) command.getObject();
                                if(model.findTask(newTask.getName(), userInTask)){
                                    Task task = model.getFindTask(newTask.getName(), userInTask);
                                    task.updateAllTime(user);
                                    task.updateParentAllTime(user);
                                    oos.writeObject(ServerAnswer.success(task));
                                    oos.reset();
                                } else {
                                    oos.writeObject(ServerAnswer.failure("Задача не найдена!"));
                                    oos.reset();
                                }
                                break;
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (EOFException e){
                    throw new SocketException();
                }
            }

        }catch (SocketException e) {
            System.out.println("Client disconnected. IP " + socket.getInetAddress());

        }catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                new Utils().saveModel(model);
                socket.close();
            } catch (IOException e) {
                System.out.println("Socket not closed");
            }
        }
    }
}
