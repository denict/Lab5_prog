package entity.builders;

import entity.Address;
import utility.InputHandler;
import utility.OutputHandler;

import java.util.function.Predicate;

public class AddressBuilder extends Builder<Address>{
    public AddressBuilder(InputHandler consoleInput, OutputHandler consoleOutput) {
        super(consoleInput, consoleOutput);
    }

    @Override
    public Address build() {
        Predicate<String> validateStreet = (street) -> (street != null);
        Predicate<String> validateZipCode = (zipCode) -> (true);
        consoleOutput.println("Создание поля Address (типа Address)");
        consoleOutput.println("Создание нового объекта Address");
        String street = askString("Введите поле street", "Последовательность символов String, не может быть пустой", validateStreet, "Неверный формат ввода!");
        String zipCode = askString("Введите поле zipCode", "Последовательность символов String, может быть пустой", validateZipCode, "Неверный формат ввода!");

        return new Address(street, zipCode);
    }
}
