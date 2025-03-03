package managers;

import com.opencsv.*;
import entity.Organization;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Objects;
import java.util.Stack;

import utility.ConsoleInput;
import utility.ConsoleOutput;


/**
 * Менеджер для работы с файлами
 */
public class DumpManager implements DumpReader, DumpWriter {

    private final File file;
    private final ConsoleOutput consoleOutput;

    public DumpManager(File file, ConsoleOutput consoleOutput) {
        this.file = file;
        this.consoleOutput = consoleOutput;
    }

    public File getFile() {
        return file;
    }



    /**
     * Преобразует коллекцию в CSV-строку.
     * 
     * @param collection
     * @return CSV-строка
     */

    @Override
    public String fromCollectionToCSV(Collection<Organization> collection) {
        try {
            StringWriter sw = new StringWriter(); // поток символов, который помещается в bufferedWriter, потом используется
                                                  // для создания строки
            CSVWriter csvWriter = new CSVWriter(sw);
            for (var el : collection) {
                csvWriter.writeNext(Organization.toArray(el));
            }

            String csv = sw.toString(); // превращаем в строку buffer
            return csv;
        } catch (Exception e) {
            consoleOutput.printError("Ошибка сериализации");
            return null;
        }
    }


    /**
     * Записывает коллекцию в файл.
     * 
     * @param collection коллекция
     */

    public void writeCollection(Collection<Organization> collection) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                                                                                             // преобразует байты в
                                                                                             // символы
            String csv = fromCollectionToCSV(collection);
            if (csv == null)
                return;
            try {
                bw.write(csv); // кладёт данные в буфер, но необязательно записывает в файл, накапливает данные
                               // в буфере
                bw.flush(); // flush гарантирует запись всех данных из буфера в файл
                 bw.close();
                consoleOutput.println("Коллекция успешна сохранена в файл!");
            } catch (IOException e) {
                consoleOutput.printError("Ошибка во время сохранения" + e.getMessage());
            }
        } catch (FileNotFoundException | NullPointerException e) {
            consoleOutput.printError("Файл не найден");
        } catch (IOException e) {
            consoleOutput.printError("Ошибка во время сохранения" + e.getMessage());
        }
    }

    /*
     * Считывает CSV-строку в коллекцию.
     * 
     * @param CSV-строка типа String
     * 
     * @return коллекция
     */
    public Stack<Organization> fromCSVToCollection(String s) {
        try {
            StringReader sr = new StringReader(s);
            CSVReader csvReader = new CSVReader(sr);
            Stack<Organization> organizations = new Stack<Organization>();
            String[] record;

            while ((record = csvReader.readNext()) != null) {

                Organization org = Organization.fromArray(record, consoleOutput);
                if (org != null && org.validate()) { // валидация
                    organizations.push(org);
                } else {
                    consoleOutput.printError("Строка с экземпляром коллекции содержит не валидные данные");
                }
            }
            csvReader.close();
            return organizations;
        } catch (Exception e) {
            consoleOutput.print("Ошибка десериализации" + e.getMessage());
            return null;
        }
    }

/**
 * Читает коллекцию из файла.
 *
 * @param collection Коллекция, в которую будут загружены элементы из файла
 */

    // 
    public void readCollection(CollectionManager collection) {
        if (file.getName() != null && !file.getName().isEmpty()) {
            try  (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) { // для проверки, есть ли слеюущая строка
                var s = new StringBuilder("");
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    s.append(line);
                    s.append("\n");
                }
                collection.clearCollection(); // очистка коллекции
                for (Organization e: fromCSVToCollection(s.toString())) {
                    if (!collection.getOrganizationMap().containsKey(e.getId()))
                        collection.add(e); // проверка, есть ли уже элемент в коллекции
                    if (e != null)
                        consoleOutput.println("Экземпляр коллекции успешно загружена из файла!");
                }
            } catch (FileNotFoundException | NullPointerException e) {
                consoleOutput.println("Загрузочный файл не найден!");
            } catch(IllegalStateException e) {
                consoleOutput.println("Непредвиденная ошибка!" + e.getMessage());
                System.exit(0);
            } catch (IOException e) {
                consoleOutput.println("Ошибка чтения из файла!" + e.getMessage());
            } 
        } else {
            consoleOutput.println("Введите в командную строку имя непустого загружаемого файла");
        }
    }


    public boolean validate() {
        if (!file.exists()) {
            consoleOutput.printError("Файл, введённый в качестве параметра через переменную окружения не существует. Попробуйте поменять файл в переменной окружения.");
            return false;
        }
        if (!file.canRead()) {
            consoleOutput.printError("У файла недостаточно прав для чтения.");
            return false;
        }
        if (!file.canWrite()) {
            consoleOutput.printError("У файла недостаточно прав для записи.");
            return false;
        }
        return true;
    }
}
