package managers;

import utility.InputHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.HashSet;

/**
 *  Менеджер для корректного контроля выполнения исполняемых скриптов
 */
public class RunnerScriptManager implements InputHandler {
    // хранение запущенных на момент исполнения файлов
    private static final HashSet<String> setFiles = new HashSet<>();
    private static final ArrayDeque<BufferedReader> readers = new ArrayDeque<>();
    private static BufferedReader br;

    /**
     * Метод для проверки, был ли запущен файл повторно (рекурсивно)
     * @param fileName проверяемый файл
     * @return был или не был запущен
     */
    public static boolean checkIfFileInStack (String fileName) {
        return setFiles.contains(fileName);
    }
    /**
     * Добавляет файл в список запущенных
     * @param fileName файл
     */
    public static void addFile(String fileName) throws FileNotFoundException {
        setFiles.add(fileName);
        readers.add(br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8)));
    }

    /**
     * Удаляет файл из списка запущенных
     * @param fileName файл
     */
    public static void removeFile(String fileName) {
        setFiles.remove(fileName);
    }

    /**
     * Метод для чтения перенаправленного потока ввода на файл
     *
     */
    @Override
    public String readLine() {
        try {
            return readers.getLast().readLine();
        } catch (IOException e) {
            return "Не найден файл";
        }
    }

    /**
     * Метод для закрытия потока перенаправленного потока ввода
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        br.close();
    }
}
