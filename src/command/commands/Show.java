package command.commands;

import command.Command;
import entity.Organization;
import managers.CollectionManager;
import utility.ConsoleOutput;

/**
 * Команда "show".
 * Описание команды: выводит в стандартный поток вывода все элементы коллекции в строковом представлении
 */
public class Show extends Command {
    private CollectionManager collectionManager;
    private ConsoleOutput consoleOutput;

    public Show(CollectionManager collectionManager, ConsoleOutput consoleOutput) {
        super("show", "выводит в стандартный поток вывода все элементы коллекции в строковом представлении", 0, "");
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
//        if (args.length > 0) {
//            consoleOutput.printError("Команда не принимает аргументов!");
//            return;
//        }

        if (collectionManager.getCollectionSize() == 0) {
            consoleOutput.println("Коллекции пустая! Добавьте элементы.");
            return;
        }

        consoleOutput.println("Количество элементов: " + collectionManager.getCollectionSize());
        int cou = 1;
        // получаем коллекцию для вывода
        for (Organization org : collectionManager.getCollection()) {
            consoleOutput.println(cou++ + ": " + org.toString());
        }
    }
}
