package command.commands;

import command.Command;
import managers.CommandManager;
import managers.DumpManager;
import managers.RunnerManager;
import managers.RunnerScriptManager;
import utility.ConsoleInput;
import utility.ConsoleOutput;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ExecuteScript extends Command {
    private final CommandManager commandManager;
    private final ConsoleInput consoleInput;
    private final ConsoleOutput consoleOutput;
    private final RunnerScriptManager runnerScriptManager;

    public ExecuteScript(CommandManager commandManager, ConsoleInput consoleInput, ConsoleOutput consoleOutput, RunnerScriptManager runnerScriptManager) {
        super("execute_script", "Cчитать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.", 1);
        this.commandManager = commandManager;
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
        this.runnerScriptManager = runnerScriptManager;
    }

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
