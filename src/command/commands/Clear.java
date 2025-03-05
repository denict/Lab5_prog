package command.commands;

import command.Command;
import managers.CollectionManager;
import utility.ConsoleOutput;

/**
 * Команда "clear".
 * Описание команды: очистить коллекцию.
 */
public class Clear extends Command {
    private final CollectionManager collectionManager;
    private final ConsoleOutput consoleOutput;

    public Clear(CollectionManager collectionManager, ConsoleOutput consoleOutput) {
        super("clear", "очистить коллекцию", 0, "");
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
            consoleOutput.println("Коллекция пустая");
            return;
        }

        collectionManager.clearCollection();
        consoleOutput.println("Коллекция успешно очищена.");
    }
}
