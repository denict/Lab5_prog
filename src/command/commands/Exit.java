package command.commands;

import command.Command;
import utility.ConsoleOutput;

/**
 * Команда "exit".
 * Описание команды: завершить программу (без сохранения в файл)
 */
public class Exit extends Command {
    private final ConsoleOutput consoleOutput;

    public Exit(ConsoleOutput consoleOutput) {
        super("exit", "завершить программу (без сохранения в файл)", 0, "");
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
        consoleOutput.close();
        System.exit(0);
    }
}
