package command;

/**
 * Интерфейс для Command, реализующий метод execute
 */
public interface CommandInterface {

    /**
     * Запуск команды
     * @param args массив аргументов функции
     */
    void execute(String[] args);

}
