package command.commands;

import command.Command;
import entity.Organization;
import entity.builders.OrganizationBuilder;
import managers.CollectionManager;
import utility.ConsoleInput;
import utility.ConsoleOutput;

/**
 * Команда "add_if_min".
 * Описание команды: добавить новый элемент, если его значение меньше, чем у наименьшего элемента коллекции.
 */
public class AddIfMin extends Command {

    private CollectionManager collectionManager;
    private ConsoleInput consoleInput;
    private ConsoleOutput consoleOutput;

    public AddIfMin(CollectionManager collectionManager, ConsoleInput consoleInput, ConsoleOutput consoleOutput) {
        super("add_if_min", "добавить элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции", 0, "");
        this.collectionManager = collectionManager;
        this.consoleInput = consoleInput;
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
            consoleOutput.println("Коллекция пустая. Введите элемент для добавления в коллекцию");
            Organization org = new OrganizationBuilder(consoleInput, consoleOutput).build();
            if (org == null) {
                consoleOutput.println("Вы ввели пустой элемент null");
                return;
            }
            collectionManager.add(org);
            collectionManager.updateMinElement(org);
            consoleOutput.println("Элемент успешно добавлен в коллекцию! Минимальный элемент коллекции обновлён.");
        } else {
            consoleOutput.println("Введите элемент для сравнения с наименьшим элементом в коллекции");
            Organization org = new OrganizationBuilder(consoleInput, consoleOutput).build();
            if (org == null) {
                consoleOutput.println("Вы ввели пустой элемент null");
                return;
            }
            if (collectionManager.getMinElement().compareTo(org) > 0) {
                collectionManager.add(org);
                consoleOutput.println("Элемент успешно добавлен в коллекцию! Минимальный элемент коллекции обновлён.");
            } else {
                consoleOutput.println("Элемент не добавлен в коллекцию, потому он больше минимального элемента этой коллекции:" + collectionManager.getMinElement());
            }
        }
    }
}
