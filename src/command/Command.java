package command;

/*
 * Абстрактный класс для всех команд, их имена и описание
 */
public abstract class Command implements CommandInterface {

    private final String name;
    private final String description;
    private final int argsCount;

    public Command(String name, String description, int argsCount) {
        this.name = name;
        this.description = description;
        this.argsCount = argsCount;

    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getArgsCount() {return argsCount;}
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Command command = (Command) obj;
        return name.equals(command.name) && description.equals(command.description);
    }


    @Override
    public int hashCode() {
        return name.hashCode() + description.hashCode();
    }

    @Override
    public String toString() {
//        return "Команда " + name + ": " + description;
        return String.format(": %-1s | %s", name, description);
    }

}
