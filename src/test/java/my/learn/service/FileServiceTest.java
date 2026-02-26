package my.learn.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование FileService - работа с файловой системой")
class FileServiceTest {

    private final String TEST_FILE_PATH = "./fileService.txt";
    private final Integer TEST_WORDS_MIN_LENGTH = 10;
    private final Integer TEST_WORDS_MAX_LENGTH = 100;
    private String fileContent;
    private FileService fileService;

    private final Random random = new Random();

    @BeforeEach
    @DisplayName("Подготовка тестового окружения: создание файла со случайным содержимым")
    void createTestFile() {
        // Given
        fileService = new FileService();

        // Создаем тестовый файл
        try {
            Files.createFile(Paths.get(TEST_FILE_PATH));
        } catch (IOException e) {
            System.err.println("Error creating file: " + TEST_FILE_PATH);
        }

        // Генерируем случайный контент
        fileContent = generateRandomContent();

        // Записываем контент в файл
        try {
            Files.writeString(Paths.get(TEST_FILE_PATH), fileContent);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + TEST_FILE_PATH);
        }
    }

    @AfterEach
    @DisplayName("Очистка тестового окружения: удаление тестового файла")
    void deleteTestFile() {
        try {
            Files.deleteIfExists(Paths.get(TEST_FILE_PATH));
        } catch (IOException e) {
            System.err.println("Error deleting file: " + TEST_FILE_PATH);
        }
    }

    /**
     * Генерирует случайный текст для тестового файла
     */
    private String generateRandomContent() {
        String[] words = {
                "lorem", "ipsum", "dolor", "sit", "amet", "consectetur",
                "adipiscing", "elit", "sed", "do", "eiusmod", "tempor",
                "incididunt", "ut", "labore", "et", "dolore", "magna", "aliqua"
        };

        StringBuilder content = new StringBuilder();
        int wordCount = random.nextInt(TEST_WORDS_MIN_LENGTH, TEST_WORDS_MAX_LENGTH);

        for (int i = 0; i < wordCount; i++) {
            content.append(words[random.nextInt(words.length)]);
            if (i < wordCount - 1) {
                content.append(" ");
            }
        }

        return content.toString();
    }

    @Test
    @DisplayName("Установка пути: корректный путь успешно сохраняется")
    void setFilePath_ValidPath_SetsFilePath() throws Exception {
        // Given
        String expectedPath = TEST_FILE_PATH;

        // When
        fileService.setFilePath(expectedPath);

        // Then
        assertEquals(expectedPath, fileService.getFilePath());
    }

    @Test
    @DisplayName("Установка пути: некорректный путь вызывает исключение и сбрасывает путь")
    void setFilePath_InvalidPath_ThrowsException() {
        // Given
        String invalidPath = "./non_existent_file.txt";

        // When & Then
        Exception exception = assertThrows(Exception.class, () -> {
            fileService.setFilePath(invalidPath);
        });

        assertEquals("Файл не найден, повторите ввод", exception.getMessage());
        assertNull(fileService.getFilePath());
    }

    @Test
    @DisplayName("Чтение файла: существующий файл возвращает корректное содержимое")
    void getFileText_ValidFile_ReturnsContent() throws Exception {
        // Given
        fileService.setFilePath(TEST_FILE_PATH);

        // When
        String actualContent = fileService.getFileText();

        // Then
        assertEquals(fileContent, actualContent);
    }

    @Test
    @DisplayName("Чтение файла: удалённый файл вызывает исключение")
    void getFileText_InvalidFile_ThrowsException() throws Exception {
        // Given
        fileService.setFilePath(TEST_FILE_PATH);
        Files.delete(Paths.get(TEST_FILE_PATH)); // удаляем файл после установки пути

        // When & Then
        Exception exception = assertThrows(Exception.class, () -> {
            fileService.getFileText();
        });

        assertEquals("Файл не найден, повторите ввод", exception.getMessage());
        assertNull(fileService.getFilePath());
    }

    @Test
    @DisplayName("Валидация пути: существующий файл возвращает true")
    void isValidFilePath_ValidFile_ReturnsTrue() throws Exception {
        // Given
        fileService.setFilePath(TEST_FILE_PATH);

        // When
        boolean isValid = fileService.isValidFilePath();

        // Then
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Валидация пути: удалённый файл возвращает false")
    void isValidFilePath_InvalidFile_ReturnsFalse() throws Exception {
        // Given
        fileService.setFilePath(TEST_FILE_PATH);
        Files.delete(Paths.get(TEST_FILE_PATH));

        // When
        boolean isValid = fileService.isValidFilePath();

        // Then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Валидация пути: null путь возвращает false")
    void isValidFilePath_NullPath_ReturnsFalse() {
        // Given
        // path не установлен

        // When
        boolean isValid = fileService.isValidFilePath();

        // Then
        assertFalse(isValid);
    }

    @Test
    @DisplayName("Подсчёт слов: корректно считает количество слов в файле")
    void getCountOfWords_ValidFile_ReturnsCorrectCount() throws Exception {
        // Given
        fileService.setFilePath(TEST_FILE_PATH);
        int expectedWordCount = fileContent.split(" ").length;

        // When
        int actualWordCount = fileService.getCountOfWords();

        // Then
        assertEquals(expectedWordCount, actualWordCount);
    }

    @Test
    @DisplayName("Подсчёт слов: пустой файл возвращает 0")
    void getCountOfWords_EmptyFile_ReturnsZero() throws Exception {
        // Given
        Files.writeString(Paths.get(TEST_FILE_PATH), ""); // очищаем файл
        fileService.setFilePath(TEST_FILE_PATH);

        // When
        int wordCount = fileService.getCountOfWords();

        // Then
        assertEquals(0, wordCount);
    }

    @Test
    @DisplayName("Подсчёт слов: файл с одним словом возвращает 1")
    void getCountOfWords_FileWithOneWord_ReturnsOne() throws Exception {
        // Given
        String singleWord = "hello";
        Files.writeString(Paths.get(TEST_FILE_PATH), singleWord);
        fileService.setFilePath(TEST_FILE_PATH);

        // When
        int wordCount = fileService.getCountOfWords();

        // Then
        assertEquals(1, wordCount);
    }

    @Test
    @DisplayName("Поиск подстроки: существующая подстрока возвращает корректное количество вхождений")
    void findCountSubstringInFile_ExistingSubstring_ReturnsCorrectCount() throws Exception {
        // Given
        fileService.setFilePath(TEST_FILE_PATH);
        String[] words = fileContent.split(" ");
        String searchWord = words[random.nextInt(words.length)]; // случайное слово из текста

        // Ожидаемое количество вхождений
        int expectedCount = 0;
        for (String word : words) {
            if (word.equals(searchWord) || word.contains(searchWord)) {
                expectedCount++;
            }
        }

        // When
        int actualCount = fileService.findCountSubstringInFile(searchWord);

        // Then
        assertEquals(expectedCount, actualCount);
    }

    @Test
    @DisplayName("Поиск подстроки: несуществующая подстрока возвращает 0")
    void findCountSubstringInFile_NonExistingSubstring_ReturnsZero() throws Exception {
        // Given
        fileService.setFilePath(TEST_FILE_PATH);
        String nonExistingWord = "supercalifragilisticexpialidocious";

        // When
        int count = fileService.findCountSubstringInFile(nonExistingWord);

        // Then
        assertEquals(0, count);
    }

    


    @Test
    @DisplayName("Инвалидация файла: существующий файл инвалидируется и выбрасывает исключение")
    void invalidateFile_ValidFile_InvalidatesAndThrowsException() {
        // Given
        try {
            fileService.setFilePath(TEST_FILE_PATH);
        } catch (Exception e) {
            fail("Should not throw exception");
        }

        // When & Then
        Exception exception = assertThrows(Exception.class, () -> {
            fileService.invalidateFile();
        });

        assertEquals("Файл не найден, повторите ввод", exception.getMessage());
        assertNull(fileService.getFilePath());
    }

    @Test
    @DisplayName("Инвалидация файла: уже инвалидированный файл выбрасывает исключение")
    void invalidateFile_AlreadyInvalid_ThrowsException() {
        // Given
        assertNull(fileService.getFilePath());

        // When & Then
        Exception exception = assertThrows(Exception.class, () -> {
            fileService.invalidateFile();
        });

        assertEquals("Файл не найден, повторите ввод", exception.getMessage());
    }

    @Test
    @DisplayName("Поиск подстроки: подстрока на границах слов считается корректно")
    void findCountSubstringInFile_SubstringAtBoundaries_CountsCorrectly() throws Exception {
        // Given
        String content = "test test test test";
        Files.writeString(Paths.get(TEST_FILE_PATH), content);
        fileService.setFilePath(TEST_FILE_PATH);

        // When
        int count = fileService.findCountSubstringInFile("test");

        // Then
        assertEquals(4, count);
    }
}