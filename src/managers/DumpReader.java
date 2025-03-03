package managers;

import java.util.Collection;
import java.util.Stack;
import entity.Organization;

public interface DumpReader {

    void readCollection(CollectionManager collectionManager);
    Stack<Organization> fromCSVToCollection(String s);
    
}
