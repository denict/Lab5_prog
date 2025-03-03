package managers;

import java.util.*;

import entity.Organization;

public  class CollectionManager {
    private static Stack<Organization> collection = new Stack<Organization>();
    private static Map<Integer, Organization> organizationMap = new HashMap<Integer, Organization>(); // для быстрого обращения// к элементу коллекции
    private Date initTime;
    private final DumpManager dumpManager;
    private Organization minElement = null;
    public CollectionManager(DumpManager dumpManager) {
        this.initTime = new Date();
        this.dumpManager = dumpManager;
    }

    /**
     * Статический метод для генерации нового id
     * @return минимальный несуществующий id
     */
    public static int generateFreeId() {
        if (collection.isEmpty()) return 1;
        Set<Integer> keys = new HashSet<>(organizationMap.keySet()); // Копируем ключи, чтобы избежать ConcurrentModificationException
        if (keys.isEmpty()) return 1;
        int maxId = Collections.max(keys); // Находим максимальный ID
        for (int i = 1; i < maxId; i++) {
            if (!organizationMap.containsKey(i)) return i;
        }
        return Collections.max(organizationMap.keySet()) + 1;
    }

    public void clearCollection() {
        collection.clear();
        organizationMap.clear();

    }


    public static boolean allIdAreUnique(Collection<Organization> collection) {
        HashSet<Integer> ids = new HashSet<>();
        for (Organization org: collection) {
            if (ids.contains(org.getId())) return false;
            ids.add(org.getId());
        }
        return true;
    }
    public static boolean hasIdInCollection(Integer id) {
        return organizationMap.containsKey(id);
    }

    public Organization getMinElement() {
        return  minElement;
    }

    public void updateMinElement(Organization org) {
        if (minElement == null) {
            minElement = org;
            return;
        }
        if (minElement.compareTo(org) < 0) {
            minElement = org;
        }
    }

    /**
     * Получение типа коллекции
     * @return класс объекта коллекции
     */
    public String getTypeOfCollection() {
        return collection.getClass().getName();
    }

    public int getCollectionSize() {
        return collection.size();
    }


    /**
     * @return  время инициализации.
     */
    public Date getInitTime() {
        return initTime;
    }

    /**
     * @return коллекция.
     */
    public Stack<Organization> getCollection() {
        return collection;
    }
    public Map<Integer, Organization> getOrganizationMap() { return organizationMap; }

    /**
     * @param id ID organization
     * @return Элемент коллекции по ID, если существует, иначе null.
     */
    public Organization byId(Integer id) {
        return organizationMap.get(id);
    }

    /**
     * Проверка, содержит ли коллекция дракона
     *
     * @param organization элемент organization для проверки принадлежности к
     *                     коллекции
     * @return true, если коллекция содержит organization, иначе false.
     */
    public boolean isContain(Organization organization) {
        return organizationMap.containsKey(organization.getId());
    }

    /**
     * Сортировка коллекции collection элементов Organization по умолчанию
     */
    public void updateSort() {
        Collections.sort(collection);
    }

    public void removeFirstElementOfCollection() {
        collection.removeElementAt(0);
    }
    /**
     * Добавляет элемент organization в коллекцию collection
     * Добавляет элемент в HashMap для быстрого поиска
     * Сортирует коллекцию по умолчанию
     * 
     * @param organization добавляемый элемент
     * 
     * @return true, если произошло добавление, иначе false
     */
    public boolean add(Organization organization) {
        if (isContain(organization))
            return false;
        organizationMap.put(organization.getId(), organization);
        collection.push(organization);
        updateSort();
        return true;
    }



    /**
     * По ID organization удаляет элемент из коллекции
     * 
     * @param id ID удаляемого organization
     * @return true, если произошло удаление, иначе false
     */
    public boolean remove(Integer id) {
        Organization organization = byId(id);
        if (organization == null)
            return false;
        organizationMap.remove(id);
        collection.remove(organization);
        return true;
    }




    /**
     * Сохраняет коллекицию в CSV-файл
     *
     */
    public void saveCollection() {
        dumpManager.writeCollection(collection);

    }

    @Override
    public String toString() {
        if (collection.isEmpty())
            return "Коллекция пуста!";
        StringBuilder info = new StringBuilder();

        for (Organization organization : collection) {
            info.append(organization + "\n");
        }

        return info.toString().trim();
    }

}
