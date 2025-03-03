package command.commands;

import command.Command;
import entity.Organization;
import entity.builders.OrganizationBuilder;
import managers.CollectionManager;
import utility.ConsoleInput;
import utility.ConsoleOutput;

public class Add extends Command {

    private CollectionManager collectionManager;
    private ConsoleInput consoleInput;
    private ConsoleOutput consoleOutput;

    public Add(CollectionManager collectionManager, ConsoleInput consoleInput, ConsoleOutput consoleOutput) {
        super("add", "Добавить новый элемент в коллекцию", 0);
        this.collectionManager = collectionManager;
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void execute(String[] args) {
//        if (args.length > 0) {
//            consoleOutput.printError("Команда не принимает аргументов!");
//            return;
//        }
        consoleOutput.println("Добавление нового элемента в коллекцию. Введите необходимые данные");
            collectionManager.add(new OrganizationBuilder(consoleInput, consoleOutput).build());
            consoleOutput.println("Элемент успешно добавлен в коллекцию!");
    }
}
