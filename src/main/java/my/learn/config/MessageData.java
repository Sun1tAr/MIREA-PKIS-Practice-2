package my.learn.config;

public class MessageData {

    public final static String INPUT_FIND_SUBSTRING = "Введите искомую подстроку";
    public final static String INPUT_PATH_BY_FORMAT = "Введите путь к файлу в формате: C:\\Users\\user\\Desktop\\test.txt";
    public final static String APP_SUCCESSFUL_CLOSED = "Программа успешно завершена";
    public final static String COUNT_OF_WORDS = "Количество слов в файле: ";
    public final static String PATH_SUCCESSFUL_INSTALLED = "Путь к файлу успешно задан";
    public final static String COUNT_OF_SUBSTRING = "Количество вхождений подстроки в файл: ";
    public final static String INCORRECT_DATA = "Некорректные данные - введите число";
    public final static String OPERATION_NOT_SUPPLY = "Такая операция не поддерживается, повторите ввод";
    public final static String FILE_NOT_INSTALLED = "Не выбран";
    public final static String CURRENT_FILE = "Текущий файл: ";
    public final static String FILE_NOT_FOUND = "Файл не найден, повторите ввод";

    public final static String MESSAGE_DELIMITER =
            "-----------------------------------------------";
    public final static String FILE_PATH_DELIMITER = "<>";

    public static final String INPUT_DIR_PATH_OR_FILES_PATHS = "Для анализа файлов одной директории введите в " +
            "консоль путь до директории в формате: C:\\Users\\user\\Desktop\\testDir. \n\n" +
            "Для группового анализа нескольких файлов введите последовательно пути к файлам разделяя их " +
            FILE_PATH_DELIMITER + " в формате:\n" +
            "C:\\Users\\user\\Desktop\\test1.txt " + FILE_PATH_DELIMITER + " C:\\Users\\user\\Desktop\\test2.txt";
    public static final String DIR_NOT_FOUND_OR_EMPTY = "Директория не содержит текстовых файлов или не существует";

    public static final String INPUT_PATH_OF_TEST_DIR = "Введите директорию для создания тестовых файлов в формате: C:\\Users\\user\\Desktop\\test " +
            "\nЛибо нажмите Enter чтобы оставить директорию по умолчанию";
    public static final String FILES_GENERATED_AT_DIR = "Файлы успешно сгенерированы в директории: ";
}
