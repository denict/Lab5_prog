package utility;

import java.util.Scanner;

import managers.InputManager;

/*
 * Контроль пользовательского ввода
 */
public class ConsoleInput implements InputHandler {

    private static final Scanner scanner = InputManager.scanner;
    private static boolean fileMode = false;
    public static boolean isFileMode() {
        return fileMode;
    }

    public static void setFileMode(boolean fileMode) {
        ConsoleInput.fileMode = fileMode;
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public void close() {
        scanner.close();
    }

}
