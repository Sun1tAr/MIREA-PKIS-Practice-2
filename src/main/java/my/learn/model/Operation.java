package my.learn.model;

import lombok.Getter;

public enum Operation {

    CLOSE_PROGRAM(0, "Завершить работу", false, false),
    INSERT_PATH(1, "Ввести путь к файлу", true, false),
    WRITE_TO_CONSOLE(2, "Вывести текст в консоль", false, true),
    FIND_SUBSTRING_COUNT(3, "Найти подстроку в файле", true, true),
    FIND_COUNT_OF_WORDS(4, "Посчитать количество слов в файле", false, true),
    GET_FILES_ANALYSIS(5, "Провести анализ набора файлов", true, false),
    GENERATE_TEST_FILES(6, "Сгенерировать случайный набор файлов", true, false);


    @Getter
    private final int value;
    @Getter
    private final String message;
    @Getter
    private final boolean isNeedSomeData;
    @Getter
    private final boolean isNeedFile;



    Operation(int value, String message, boolean isNeedSomeData, boolean isNeedFile) {
        this.value = value;
        this.message = message;
        this.isNeedSomeData = isNeedSomeData;
        this.isNeedFile = isNeedFile;
    }


    @Override
    public String toString() {
        return value + " - " +  message;
    }
}
