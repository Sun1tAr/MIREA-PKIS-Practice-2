package my.learn.service;

import lombok.Getter;
import my.learn.config.AppConfig;
import my.learn.config.MessageData;
import my.learn.util.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FileService {

    @Getter
    private String filePath;


    public void setAndValidateFilePath(String filePath) throws Exception {
        this.filePath = filePath;
        if (!isValidFilePath()) {
            invalidateFile();
        }
    }


    public String getFileText() throws Exception {
        String content = "";
        try {
            Path path = Paths.get(filePath);
            content = Files.readString(path);
        } catch (Exception e) {
            invalidateFile();
        }
        return content;
    }

    public boolean isValidFilePath() {
        try {
            Path path = Paths.get(filePath);
            Files.readString(path);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public int getCountOfWords() throws Exception {
        String fileText = getFileText().trim();
        char[] charArray = fileText.toCharArray();
        int count = 1;
        for (char c : charArray) {
            if (c == ' ') {
                count++;
            }
        }
        if  (count == 1 && charArray.length == 0) {
            return 0;
        }
        return count;
    }

    public int findCountSubstringInFile(String userInput) throws Exception {
        String fileText = getFileText().trim();
        String[] words = fileText.split(" ");
        int count = 0;
        for (String word : words) {
            if (word.equals(userInput) || word.contains(userInput)) {
                count++;
            }
        }
        return count;
    }


    public void invalidateFile() throws Exception {
        filePath = null;
        throw new Exception(MessageData.FILE_NOT_FOUND);
    }

    public List<String> getFilesPathsFromDir(String dirPath) {
        File directory = new File(dirPath);

        List<String> filesPaths = new ArrayList<>();

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().contains("txt")) {
                        filesPaths.add(file.getPath());
                    }
                }
            }
        }
        return filesPaths;
    }

    public int getCountOfChars() throws Exception {
        return getFileText().length();
    }

    public void createRandomDataFile(String fileDir, String fileName) {

        String fullPath = fileDir + "/" + fileName;


        try {
            Files.createFile(Paths.get(fullPath));
        } catch (IOException e) {
            try {
                Files.createDirectory(Paths.get(fileDir));
                Files.createFile(Paths.get(fullPath));
            } catch (IOException ex) {
                Logger.error("Error creating directory: " + fullPath + ": " + e.getMessage());
            }
        }

        String fileContent = generateRandomContent();

        try {
            Files.writeString(Paths.get(fullPath), fileContent);
        } catch (IOException e) {
            Logger.error("Error writing to file: " + fullPath + ": " + e.getMessage());
        }
    }

    private String generateRandomContent() {
        String[] words = {
                "lorem", "ipsum", "dolor", "sit", "amet", "consectetur",
                "adipiscing", "elit", "sed", "do", "eiusmod", "tempor",
                "incididunt", "ut", "labore", "et", "dolore", "magna", "aliqua"
        };

        Random random = new Random();

        StringBuilder content = new StringBuilder();
        int wordCount = random.nextInt(AppConfig.COUNT_OF_TEST_WORDS_MIN, AppConfig.COUNT_OF_TEST_WORDS_MAX);

        for (int i = 0; i < wordCount; i++) {
            content.append(words[random.nextInt(words.length)]);
            if (i < wordCount - 1) {
                content.append(" ");
            }
        }

        return content.toString();
    }

}
