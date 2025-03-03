package command.commands;

import command.Command;
import entity.Organization;
import managers.CollectionManager;
import utility.ConsoleOutput;

import java.util.HashSet;

public class PrintUniqueAnnualTurnover extends Command {

    private CollectionManager collectionManager;
    private ConsoleOutput consoleOutput;

    public PrintUniqueAnnualTurnover(CollectionManager collectionManager, ConsoleOutput consoleOutput) {
        super("print_unique_annual_turnover", "Вывести уникальные значения поля annualTurnover всех элементов в коллекции", 0);
        this.collectionManager = collectionManager;
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void execute(String[] args) {
        if (collectionManager.getCollectionSize() == 0) {
            consoleOutput.println("Коллекция пустая! Элементов с уникальными annualTurnover нет!");
            return;
        }
        HashSet<Double> uniqueAnnualTurnover = new HashSet<Double>();
        consoleOutput.print("Уникальные значения annualTurnover элементов коллекции: ");
        for (Organization org : collectionManager.getCollection()) {
            if (!uniqueAnnualTurnover.contains(org.getAnnualTurnover())) {
                uniqueAnnualTurnover.add(org.getAnnualTurnover());
                consoleOutput.print(org.getAnnualTurnover().toString() + " ");
            }
        }
    }
}
