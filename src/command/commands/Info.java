package command.commands;

import command.Command;
import managers.CollectionManager;
import utility.ConsoleOutput;


public class Info extends Command {

    private CollectionManager collectionManager;
    private ConsoleOutput consoleOutput;

    public Info(CollectionManager collectionManager, ConsoleOutput consoleOutput) {
        super("info", "вывод в стандартный поток вывода информации о коллекции (тип, дата инициализации, количество элементов и т.д.)", 0);
        this.collectionManager = collectionManager;
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void execute(String[] args) {
//        if (args.length > 0) {
//            consoleOutput.printError("Команда не принимает аргументов!");
//            return;
//        }
        consoleOutput.println("Информация о коллекции:");
        consoleOutput.println(String.format(
                "Тип: %s\n" +
                "Дата инициализации: %s\n" +
                "Количество элементов: %d",
                collectionManager.getTypeOfCollection(),
                collectionManager.getInitTime(),
                collectionManager.getCollectionSize()
        ));
    }
}
