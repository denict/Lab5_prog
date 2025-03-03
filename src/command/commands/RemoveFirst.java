package command.commands;

import command.Command;
import managers.CollectionManager;
import utility.ConsoleOutput;

public class RemoveFirst extends Command {
    private final CollectionManager collectionManager;
    private final ConsoleOutput consoleOutput;

    public RemoveFirst(CollectionManager collectionManager, ConsoleOutput consoleOutput) {
        super("remove_first", "удалить первый элемент из коллекции", 0);
        this.collectionManager = collectionManager;
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void execute(String[] args) {
//        if (args.length > 0) {
//            consoleOutput.printError("Команда не принимает аргументов!");
//            return;
//        }
        if (collectionManager.getCollectionSize() == 0) {
            consoleOutput.printError("Коллекция пуста. Нельзя удалить первый элемент.");
            return;
        }
        collectionManager.removeFirstElementOfCollection();
        consoleOutput.println("Первый элемент коллекции был успешно удален!");
    }
}
