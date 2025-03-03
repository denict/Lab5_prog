package command.commands;

import command.Command;
import managers.CommandManager;
import utility.ConsoleOutput;

import java.util.ArrayList;
import java.util.List;

public class History extends Command {
    private CommandManager commandManager;
    private ConsoleOutput consoleOutput;

    public History(CommandManager commandManager, ConsoleOutput consoleOutput) {
        super("history", "Выводит названия пяти последних выполненных команд", 0);
        this.commandManager = commandManager;
        this.consoleOutput = consoleOutput;
    }

    @Override
    public void execute(String[] args) {
//        if (args.length != 0) {
//            consoleOutput.printError("Эта команда не принимает никаких аргументов!");
//            return;
//        }
        List<Command> history = commandManager.getCommandHistory();
        if (history.isEmpty()) {
            consoleOutput.println("В истории хранится 0 команд. Похоже, это первая команда");
            commandManager.addToHistory(this);
            return;
        }

        List<Command> tmpHistory = new ArrayList<>(history.subList(history.size() - Math.min(5, history.size()), history.size()));
        consoleOutput.println("За последнее время были выполнены следующий команды (" + tmpHistory.size()+")");
        int i = 0;
        for (Command h: tmpHistory) {
            consoleOutput.println((i+1) + ". " + history.get(i).toString());
            i++;
        }

    }

}
