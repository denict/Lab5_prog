import command.commands.*;
import entity.Organization;
import entity.builders.OrganizationBuilder;
import managers.*;
import utility.ConsoleInput;
import utility.ConsoleOutput;

import java.io.File;


public class Main {
    public static void main(String[] args) {

        String filePath = System.getenv("MY_FILE_PATH");
        ConsoleInput consoleInput = new ConsoleInput();
        ConsoleOutput consoleOutput = new ConsoleOutput();
        if (filePath == null || filePath.split(";").length == 0) {
            consoleOutput.println("Введите имя загружаемого файла в переменную среды. Пример: MY_FILE_PATH=\"C:\\Users\\user\\Desktop\\input.txt\"");
                return;
        } else if (filePath.split(";").length > 1) {
            consoleOutput.printError("В переменной среды должен храниться 1 аргумент.");
            return;
        }
        DumpManager dumpManager = new DumpManager(new File(filePath), consoleOutput);
        CollectionManager collectionManager = new CollectionManager(dumpManager);
        dumpManager.readCollection(collectionManager);
        RunnerScriptManager runnerScriptManager = new RunnerScriptManager();
        CommandManager commandManager = new CommandManager() {{
            register(new Help(this, consoleOutput));
            register(new Info(collectionManager, consoleOutput));
            register(new Show(collectionManager, consoleOutput));
            register(new Add(collectionManager, consoleInput, consoleOutput));
            register(new UpdateByID(collectionManager, consoleInput, consoleOutput));
            register(new RemoveByID(collectionManager, consoleOutput));
            register(new Clear(collectionManager, consoleOutput));
            register(new Save(collectionManager, dumpManager, consoleOutput));
            register(new ExecuteScript(this, consoleInput, consoleOutput, runnerScriptManager));
            register(new Exit(consoleOutput));
            register(new RemoveFirst(collectionManager, consoleOutput));
            register(new AddIfMin(collectionManager, consoleInput, consoleOutput));
            register(new History(this, consoleOutput));
            register(new CountByOfficialAddress(collectionManager, consoleOutput));
            register(new FilterByAnnualTurnover(collectionManager, consoleOutput));
            register(new PrintUniqueAnnualTurnover(collectionManager, consoleOutput));
        }};

        RunnerManager runnerManager = new RunnerManager(consoleInput, consoleOutput, commandManager, dumpManager);
        runnerManager.run();

    }
}
