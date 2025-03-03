package command.commands;

import command.Command;
import entity.Organization;
import managers.CollectionManager;
import utility.ConsoleOutput;

public class FilterByAnnualTurnover extends Command {

    private CollectionManager collectionManager;
    private ConsoleOutput consoleOutput;

    public FilterByAnnualTurnover(CollectionManager collectionManager, ConsoleOutput consoleOutput) {
        super("filter_by_annual_turnover", "Вывести элементы, значение поля annualTurnover которых равно заданному", 1);
        this.collectionManager = collectionManager;
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void execute(String[] args) {
//        if (args.length != 1) {
//            consoleOutput.printError("Команда принимает один целочисленный аргумент - annualTurnover");
//            return;
//        }
        if (collectionManager.getCollectionSize() == 0) {
            consoleOutput.println("Коллекция пустая! Элементов с фиксированным annualTurnover нет!");
            return;
        }

        try {
            Double annualTurnover = Double.parseDouble(args[0]);
            int count = 0;
            for (Organization org : collectionManager.getCollection()) {
                if (org.getAnnualTurnover().equals(annualTurnover)) {
                    consoleOutput.println(org.toString());
                    count++;
                }
            }
            if (count == 0) {
                consoleOutput.println("Нет элементов, значение поля annualTurnover которых равно " + annualTurnover);
            }
        } catch (NumberFormatException e) {
            consoleOutput.printError("Введите число типа Double");
        }
    }
}
