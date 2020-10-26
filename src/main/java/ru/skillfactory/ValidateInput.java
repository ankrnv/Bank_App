package ru.skillfactory;

/**
 * Главная задача данного класса решить проблему ошибок ввода пользователем.
 */
public class ValidateInput extends ConsoleInput {

    @Override
    public String askStr(String question) {

        return super.askStr(question);
    }

    // Редактируем введенный пользователем логин, удаляем лишние пробелы, делаем с заглавной буквы

    public String askUsername(String question) {
        System.out.print(question);
        String username = scanner.nextLine();

        username = username.replaceAll(" ", "");

        try {
            username = username.substring(0, 1).toUpperCase() + username.substring(1);

        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("Неправильный логин");
            username = askUsername("Ваш логин: ");
            return username;
        }
        return username;
    }

    // Редактируем введенные пользователем числовые значения, удаляем лишние пробелы

    @Override
    public int askInt(String question) {

        String checkInt = askStr(question);
        checkInt = checkInt.replaceAll(" ", "");
        int parse;

        try {
            parse = Integer.parseInt(checkInt);

        } catch (NumberFormatException e) {
            System.out.println("Неправильный пункт меню");
            parse = askInt("Выберите пункт меню: ");
            return parse;
        }

        return parse;
    }

    // Редактируем введенные пользователем числовые значения, удаляем лишние пробелы

    @Override
    public long askLong(String question) {

        String checkLong = askStr(question);
        checkLong = checkLong.replaceAll(" ", "");
        long parse;

        try {
            parse = Long.parseLong(checkLong);

        } catch (NumberFormatException e) {
            System.out.println("Некоректное значение");
            parse = askLong("Повторите ввод: ");
            return parse;
        }
        return parse;
    }
}
