package entity.builders;

import entity.Coordinates;
import utility.ConsoleOutput;
import utility.InputHandler;
import utility.OutputHandler;

import java.util.function.Predicate;

public class CoordinatesBuilder extends Builder<Coordinates> {
    public CoordinatesBuilder(InputHandler consoleInput, OutputHandler consoleOutput) {
        super(consoleInput, consoleOutput);
    }

    @Override
    public Coordinates build() {
        Predicate<Long> validateX = (x) -> (true);
        Predicate<Long> validateY = (y) -> (true);
        consoleOutput.println("Создание нового объекта Coordinates");

        Long x = askLong("Координата x", "Целочисленное число типа Long", validateX, "Неверный формат ввода!");
        Long y = askLong("Координата y", "Целочисленное число типа Long", validateY, "Неверный формат ввода!");
        return new Coordinates(x, y);
    }
}
