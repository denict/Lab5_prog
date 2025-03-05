package command.commands;

import command.Command;
import managers.CommandManager;
import managers.DumpManager;
import managers.RunnerManager;
import managers.RunnerScriptManager;
import utility.ConsoleInput;
import utility.ConsoleOutput;

import java.io.*;

/**
 * Команда "execute_script".
 * Описание команды: считать и исполнить скрипт из указанного файла.
 */
public class ExecuteScript extends Command {
    private final CommandManager commandManager;
    private final ConsoleOutput consoleOutput;
    private final RunnerScriptManager runnerScriptManager;

    public ExecuteScript(CommandManager commandManager, ConsoleOutput consoleOutput, RunnerScriptManager runnerScriptManager) {
        super("execute_script", "считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.", 1, "\"file_script\"");
        this.commandManager = commandManager;
        this.consoleOutput = consoleOutput;
        this.runnerScriptManager = runnerScriptManager;
    }

    /**
     * Выполнение команды.
     *
     * @param args аргументы
     */
    public void execute(String[] args) {
//        if (args.length != 1) {
//            consoleOutput.printError("Команда принимает один целочисленный аргумент - filename");
//            return;
//        }

        try {
            File scriptFile = new File(args[0]);
            if (!scriptFile.exists()) {
                throw new FileNotFoundException();
            }

            consoleOutput.println("Начало исполнения файла " + args[0]);


            DumpManager dumpManager = new DumpManager(scriptFile, consoleOutput);
            if (RunnerScriptManager.checkIfFileInStack(args[0])) {
                consoleOutput.printError("Файл " + args[0] + " вызывается рекурсивно. Уберите рекурсию исполняемых файлов!");
                return;
            }


            ConsoleInput.setFileMode(true);
            RunnerScriptManager.addFile(scriptFile.getName());

            for (String line = runnerScriptManager.readLine(); line != null; line = runnerScriptManager.readLine()) {
                RunnerManager.runCommand(line.split(" "), commandManager, consoleOutput);

            }


            consoleOutput.println("Завершение исполнения файла " + args[0]);
            
            RunnerScriptManager.removeFile(args[0]);

            ConsoleInput.setFileMode(false);
        } catch (FileNotFoundException e) {
            consoleOutput.printError("Файл \"" + args[0] + "\" не найден");
        }


    }
}
