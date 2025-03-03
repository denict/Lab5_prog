package utility;

import java.io.PrintStream;

/*
 * Класс для потока контроля вывода
 */
public class ConsoleOutput implements OutputHandler {

    private final PrintStream printStream = System.out;

    @Override
    public void print(String s) {
        printStream.print(s);
    }

    @Override
    public void println(String s) {
        printStream.println(s);
    }

    @Override
    public void printError(String s) {
        printStream.print("Ошибка: " + s + "\n");
    }

    @Override
    public void close(){
        printStream.close();
    }

}

