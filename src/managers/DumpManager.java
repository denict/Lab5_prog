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
 * Класс для управления загрузкой и сохранением коллекции в файл.
 * Реализует преобразование данных в CSV-формат и обратно.
 */
public class DumpManager implements DumpReader, DumpWriter {

    private final File file;
    private final ConsoleOutput consoleOutput;

    /**
     * Создает экземпляр менеджера загрузки.
     *
     * @param file          Файл для сохранения и загрузки коллекции.
     * @param consoleOutput Объект для вывода сообщений в консоль.
     */
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
     * @param collection Коллекция, которую нужно преобразовать.
     * @return CSV-строка, содержащая данные коллекции, либо {@code null} в случае ошибки.
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
     * @param collection Коллекция, которую нужно сохранить.
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

    /**
     * Преобразует CSV-строку в коллекцию объектов.
     *
     * @param s CSV-строка с данными.
     * @return Коллекция объектов типа {@link Organization}, либо {@code null} в случае ошибки.
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
     * Читает коллекцию из файла и загружает её в {@link CollectionManager}.
     *
     * @param collection Менеджер коллекции, в который будут загружены данные.
     */
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


    /**
     * Проверяет существование файла и наличие у него прав на чтение и запись.
     *
     * @return {@code true}, если файл существует и доступен для чтения и записи, иначе {@code false}.
     */
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
