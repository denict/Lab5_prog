package command.commands;

import command.Command;
import managers.CommandManager;
import utility.ConsoleOutput;

import java.util.Map;

/**
 * Команда "help".
 * Описание команды: вывод справки о доступных командах.
 */
public class Help extends Command {
    private CommandManager commandManager;
    private ConsoleOutput consoleOutput;

    public Help(CommandManager commandManager, ConsoleOutput consoleOutput) {
        super("help", "вывод справки о доступных командах", 0, "");
        this.commandManager = commandManager;
        this.consoleOutput = consoleOutput;
    }

    /**
     * Выполнение команды.
     *
     * @param args аргументы
     */
    public void execute(String[] args) {

        consoleOutput.println("Краткая справка по всем доступным командам.");
        for (Map.Entry<String, Command> entry : commandManager.getCommands().entrySet()) {
            String commandName = entry.getKey();
            Command command = entry.getValue();
            consoleOutput.println(commandName + " - " + command.getDescription());
        }
    }
}

