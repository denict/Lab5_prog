package command.commands;

import command.Command;
import managers.CollectionManager;
import utility.ConsoleOutput;

/**
 * Команда "remove_by_id".
 * Описание команды: удалить элемент из коллекции по его id
 */
public class RemoveByID extends Command {
    private CollectionManager collectionManager;
    private ConsoleOutput consoleOutput;

    public RemoveByID(CollectionManager collectionManager, ConsoleOutput consoleOutput) {
        super("remove_by_id", "удалить элемент из коллекции по его id", 1, "\"id\"");
        this.collectionManager = collectionManager;
        this.consoleOutput = consoleOutput;
    }

    /**
     * Выполнение команды.
     *
     * @param args аргументы
     */
    @Override
    public void execute(String[] args) {
//        if (args.length != 1) {
//            consoleOutput.printError("Команда принимает один целочисленный аргумент - id элемента коллекции");
//            return;
//        }
        try {
            int id = Integer.parseInt(args[0]);
            if (collectionManager.remove(id)) {
                consoleOutput.println("Элемент с id=" + id + " был успешно удален!");
                return;
            } else{
             consoleOutput.printError("Элемента с id=" + id + " не был удален! Его просто нет в коллекции.");
                 return;
            }
        } catch (NumberFormatException e) {
            consoleOutput.printError("Введите целочисленное число");
        }
    }
}
