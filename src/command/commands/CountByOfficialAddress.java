package command.commands;

import command.Command;
import entity.Organization;
import managers.CollectionManager;
import utility.ConsoleOutput;

public class CountByOfficialAddress extends Command {
    private CollectionManager collectionManager;
    private ConsoleOutput consoleOutput;

    public CountByOfficialAddress(CollectionManager collectionManager, ConsoleOutput consoleOutput) {
        super("count_by_official_address", "Вывести количество элементов, у которых значение поля officialAddress равно заданному", 2);
        this.collectionManager = collectionManager;
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void execute(String[] args) {
//        if (args.length != 2) {
//            consoleOutput.printError("Команда принимает два аргумента - поле \"street\" и поле \"zipCode\" для подсчёта количество элементов c фиксированным officialAddress");
//            return;
//        }
        if (collectionManager.getCollectionSize() == 0) {
            consoleOutput.println("Коллекция пустая, количество элементов с фиксированным officialAddress равно 0");
            return;
        }
        int count = 0;
            String street = args[0];
            String zipCode = args[1];
            for (Organization org : collectionManager.getCollection()) {
                if (org.getOfficialAddress().getStreet().equals(street) && org.getOfficialAddress().getZipCode().equals(zipCode)) {
                    count++;
                }
            }
            consoleOutput.println("Количество элементов, у которых значение поля officialAddress (\"street\" = " + street + "; \"zipCode\" = " + zipCode + ") равно " + count);


    }


}
