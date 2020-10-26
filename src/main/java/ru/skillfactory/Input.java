package ru.skillfactory;

/**
 * Простой интерфейс для ввода.
 */
public interface Input {

    /**
     * @param question печатаем вопрос и возвращаем ответ
     * @return возвращает ответ типом String.
     */
    String askStr(String question);

    String askUsername(String question);

    /**
     * @param question печатаем вопрос и возвращаем ответ
     * @return возвращает ответ типом int.
     */
    int askInt(String question);

    /**
     * @param question печатаем вопрос и возвращаем ответ
     * @return возвращает ответ типом long.
     */
    long askLong(String question);
}
