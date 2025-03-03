package managers;

import java.util.Collection;

import entity.Organization;

public interface DumpWriter {

   String fromCollectionToCSV(Collection<Organization> collection);
   void writeCollection(Collection<Organization> collection);



}
