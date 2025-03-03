package utility;

/*
 * Интерфейс методов вывода информации
 */
public interface OutputHandler {

    void print(String s);
    void println(String s);
    void printError(String s);
    void close();

}
