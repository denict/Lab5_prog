package entity.builders;

import entity.Address;
import entity.Coordinates;
import entity.Organization;
import entity.OrganizationType;
import utility.InputHandler;
import utility.OutputHandler;

import java.util.function.Predicate;


public class OrganizationBuilder extends Builder<Organization> {
    public OrganizationBuilder(InputHandler consoleInput, OutputHandler consoleOutput) {
        super(consoleInput, consoleOutput);
    }

    @Override
    public Organization build() {

        Predicate<String> validateName = (name) -> (name != null && !name.isEmpty());
        Predicate<Coordinates> validateCoordinates = (Coordinates) -> (Coordinates != null);
        Predicate<Double> validateAnnualTurnover = (annualTurnover) -> (annualTurnover != null && annualTurnover > 0);
        Predicate<OrganizationType> validateType = (type) -> (true);
        Predicate<Address> validateOfficialAddress = (officialAddress) -> (true);

        consoleOutput.println("Создание нового объекта Organization");
        String name = askString("Введите поле \"name\"", "Последовательность символов String, не может быть пустой", validateName, "Неверный формат ввода!");
        Coordinates coordinates = new CoordinatesBuilder(consoleInput, consoleOutput).build();
        Double annualTurnover = askDouble("Введите поле \"annualTurnover\"", "Целочисленное значение типа Double", validateAnnualTurnover, "Неверный формат ввода!");
        OrganizationType type = askEnum("Введите поле \"type\"", "Тип организации из \"OrganizationType\", может быть пустым (Enter)", OrganizationType.class, validateType, "Неверный формат ввода!");
        Address officialAddress = new AddressBuilder(consoleInput, consoleOutput).build();
        return new Organization(name, coordinates, annualTurnover, type, officialAddress);

    }

}
