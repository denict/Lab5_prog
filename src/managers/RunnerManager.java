package managers;

import command.Command;
import utility.ConsoleInput;
import utility.ConsoleOutput;
import utility.InputHandler;
import utility.OutputHandler;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class RunnerManager implements Runnable{
    private final InputHandler consoleInput;
    private final OutputHandler consoleOutput;
    private final CommandManager commandManager;
    private final DumpManager dumpManager;

    public RunnerManager(InputHandler consoleInput, OutputHandler consoleOutput, CommandManager commandManager, DumpManager dumpManager) {
        this.consoleInput = consoleInput;
        this.consoleOutput = consoleOutput;
        this.commandManager = commandManager;
        this.dumpManager = dumpManager;
    }

    /*
     * Проверка корректности введённой команды и ее запуск
     */
    public static void runCommand(String[] queryParts, CommandManager commandManager, OutputHandler consoleOutput) {
        String qCommandName = queryParts[0].toLowerCase();
        if (qCommandName.isBlank()) {
            consoleOutput.println("Вы ничего не ввели. Воспользуйтесь командой \"help\" для просморта доступных команд");
            return;

        }
        String[] qCommandArgs = Arrays.copyOfRange(queryParts, 1, queryParts.length);
        if (!commandManager.getCommands().containsKey(qCommandName)) {
            consoleOutput.printError("Команда \"" + qCommandName + "\" не найдена. Воспользуйтесь командой \"help\" для просморта доступных команд");
            return;
        }

        try {
            Command command = commandManager.getCommands().get(qCommandName);
            if (command.getArgsCount() != qCommandArgs.length) {
                consoleOutput.printError("Команда " + qCommandName + " принимает " + command.getArgsCount() + " аргумент(а)");
                return;
            }
            command.execute(qCommandArgs);
            commandManager.addToHistory(commandManager.getCommands().get(qCommandName));
        } catch (NoSuchElementException e) {
            consoleOutput.println("^D");
        } catch (Exception e) {
            consoleOutput.printError("Произошла ошибка во время выполнения команды \"" + qCommandName + "\": " + e.getMessage());
        }
    }


    /*
     * Запуск программы
     */
    @Override
    public void run() {

        // При завершении программы срабатывает addShutdownHook;
        // Создаётся новый поток, который будет выполнен.
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            consoleOutput.println("Завершение работы программы. До свидания!");
        }));

        consoleOutput.println("Здравствуйте! Для справки по доступным командам, нажмите \"help\".");

        consoleOutput.println("Работа с файлом: " + dumpManager.getFile().getName());

        while (true) {
            try {
                String query = consoleInput.readLine().trim();
                String[] queryParts = query.split(" ");
                runCommand(queryParts, commandManager, consoleOutput);
            } catch (NoSuchElementException e) {
                consoleOutput.println("Конец ввода."); // Ctrl+D
                break;
            } catch (Exception e) {
                consoleOutput.printError("Ошибка во время выполнения: " + e.getMessage());
                break;
            }
        }
    }


}
