package command.commands;

import command.Command;
import entity.Organization;
import entity.builders.OrganizationBuilder;
import managers.CollectionManager;
import utility.ConsoleInput;
import utility.ConsoleOutput;

/**
 * Команда "update_by_id".
 * Описание команды: обновить значение элемента коллекции, id которого равен заданному
 */
public class UpdateByID extends Command {
    private CollectionManager collectionManager;
    private ConsoleInput consoleInput;
    private ConsoleOutput consoleOutput;


    public UpdateByID(CollectionManager collectionManager, ConsoleInput consoleInput, ConsoleOutput consoleOutput) {
        super("update_by_id", "обновить значение элемента коллекции, id которого равен заданному", 1, "\"id\"");
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
//        if (args.length != 1) {
//            consoleOutput.printError("Команда принимает один целочисленный аргумент - id элемента коллекции");
//            return;
//        }
                int id = Integer.parseInt(args[0]);
                if (collectionManager.remove(id)) {
                    Organization newOrganization = new OrganizationBuilder(consoleInput, consoleOutput).build();
                    if (newOrganization == null) {
                        consoleOutput.printError("После изменения элемента с id=" + id + "получился null\n" +
                                "Попробуйте ещё раз!");
                        return;
                    }
                    newOrganization.setId(id);
                    collectionManager.add(newOrganization);
                    consoleOutput.println("Элемент с id=" + id + " был успешно изменен!");
                    return;
                }
                consoleOutput.printError("Элемента с id=" + id + " не существует");
                return;
    }
}
