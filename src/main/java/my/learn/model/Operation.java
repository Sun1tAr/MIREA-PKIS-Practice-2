package my.learn.model;

import lombok.Getter;

public enum Operation {

    CLOSE_PROGRAM(0, "Завершить работу"),
    INSERT_PATH(1, "Ввести путь к файлу"),
    WRITE_TO_CONSOLE(2, "Вывести текст в консоль"),
    FIND_SUBSTRING_COUNT(3, "Найти подстроку в файле"),
    FIND_COUNT_OF_WORDS(4, "Посчитать количество слов в файле");


    @Getter
    private final int value;
    @Getter
    private final String message;

    Operation(int value, String message) {
        this.value = value;
        this.message = message;
    }


    @Override
    public String toString() {
        return value + " - " +  message;
    }
}
