package managers;

import java.util.Collection;

import entity.Organization;

/**
 * Интерфейс, определяющий методы для записи коллекции в файл.
 */
public interface DumpWriter {

    String fromCollectionToCSV(Collection<Organization> collection);

    void writeCollection(Collection<Organization> collection);


}
