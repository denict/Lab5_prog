package entity.builders;

import managers.RunnerScriptManager;
import utility.ConsoleInput;
import utility.ConsoleOutput;
import utility.InputHandler;
import utility.OutputHandler;

import java.util.Arrays;
import java.util.function.Predicate;

public abstract class Builder<T> {
    protected final InputHandler consoleInput;
    protected final OutputHandler consoleOutput;

    public Builder(InputHandler consoleInput, OutputHandler consoleOutput) {
        this.consoleInput = ConsoleInput.isFileMode() ? new RunnerScriptManager() : consoleInput;
        this.consoleOutput = consoleOutput;
    }


    /**
     * \
     * @return объект, построенный на основе полученных данных
     */
    public abstract T build();

    public String askString(String valueName, String valueInfo, Predicate<String> validateRule, String errorMessage) {
        while (true) {
            consoleOutput.print(valueName + " (" + valueInfo + ")\n> ");
            String value = consoleInput.readLine();
            if (value != null) value = value.trim();
            if (validateRule.test(value)) return value;
            consoleOutput.printError("Введенное значение не удовлетворяет одному или нескольким условиям валидации поля \"" + valueName + "\". " + errorMessage);
            dropIfFileMode();
        }
    }

    public Integer askInteger(String valueName, String valueInfo, Predicate<Integer> validateRule, String errorMessage) {
        while (true) {
            consoleOutput.print(valueName + " (" + valueInfo + ")\n> ");
            try {
                Integer value = Integer.parseInt(consoleInput.readLine());
                if (validateRule.test(value)) return value;
                consoleOutput.printError("Введенное значение не удовлетворяет одному или нескольким условиям валидации поля \"" + valueName + "\". " + errorMessage);
            }
            catch (NumberFormatException e) {
                consoleOutput.printError("Введите число!");
                dropIfFileMode();
            }
        }
    }

    public <T extends Enum<T>> T askEnum(String valueName, String valueInfo, Class<T> enumClass, Predicate<T> validateRule, String errorMessage) {
        while (true) {
            consoleOutput.print(valueName + " (" + valueInfo + ")\n" + Arrays.toString(enumClass.getEnumConstants()) + "\n> ");
            try {
                String input = consoleInput.readLine().trim();
                if (input.isBlank()) return null;
                T value = Enum.valueOf(enumClass, input.toUpperCase());
                if (validateRule.test(value)) return value;
                consoleOutput.printError("Введенное значение не удовлетворяет одному или нескольким условиям валидации поля \"" + valueName + "\". " + errorMessage);

            }
            catch (IllegalArgumentException e) {
                consoleOutput.printError("Выберите одно из корректных значений из Enum'а!");
                dropIfFileMode();
            }
        }
    }

    public Double askDouble(String valueName, String valueInfo, Predicate<Double> validateRule, String errorMessage) {
        while (true) {
            consoleOutput.print(valueName + " (" + valueInfo + ")\n> ");
            try {
                String input = consoleInput.readLine();
                if (input != null) input = input.trim();
                if (input.isBlank()) throw new NumberFormatException();
                Double value = Double.parseDouble(input);
                if (validateRule.test(value)) return value;
                consoleOutput.printError("Введенное значение не удовлетворяет одному или нескольким условиям валидации поля \"" + valueName + "\". " + errorMessage);
                dropIfFileMode();
            }
            catch (NumberFormatException e) {
                consoleOutput.printError("Введите число с плавающей точкой типа Double!");
                dropIfFileMode();
            }
        }
    }



    public Long askLong(String valueName, String valueInfo, Predicate<Long> validateRule, String errorMessage) {
        while (true) {
            consoleOutput.print(valueName + " (" + valueInfo + ")\n> ");
            try {
                String input = consoleInput.readLine();
                if (input != null) input = input.trim();
                if (input.isBlank()) throw new NumberFormatException();
                Long value = Long.parseLong(input);
                if (validateRule.test(value)) return value;
                consoleOutput.printError("Введенное значение не удовлетворяет одному или нескольким условиям валидации поля \"" + valueName + "\". " + errorMessage);
            }
            catch (NumberFormatException e) {
                consoleOutput.printError("Введите целочисленное число типа Long!");
                dropIfFileMode();
            }
        }
    }


    private void dropIfFileMode() {
        if (ConsoleInput.isFileMode()) {
            consoleOutput.printError("Неверный ввод. Завершение программы");
            System.exit(-1);
        }
    }

}


