package command;

/*
 * Интерфейс для Command
 */
public interface CommandInterface {

    /*
     * Запуск команды
     * @param args массив аргументов функции
     */
    void execute(String[] args);

}
