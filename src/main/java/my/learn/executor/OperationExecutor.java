package my.learn.executor;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import my.learn.config.AppConfig;
import my.learn.model.FileAnalysis;
import my.learn.config.MessageData;
import my.learn.model.Operation;
import my.learn.model.message.DescriptionMessage;
import my.learn.model.message.ResultMessage;
import my.learn.service.FileService;
import my.learn.util.FileAnalyzer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
public class OperationExecutor {

    private final FileService fileService;

    @Getter
    private boolean ready = true;

    public Operation getOperationFromString(String userInput) throws Exception {
        int i;
        try {
            i = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            throw new Exception(MessageData.INCORRECT_DATA);
        }

        for (Operation operation : Operation.values()) {
            if (operation.getValue() == i) {
                return operation;
            }
        }
        throw new Exception(MessageData.OPERATION_NOT_SUPPLY);
    }


    public ResultMessage execute(Operation operation,
                                 String userInput) throws Exception {
        String data = null;
        switch (operation) {
            case INSERT_PATH -> {
                fileService.setAndValidateFilePath(userInput);
                data = MessageData.PATH_SUCCESSFUL_INSTALLED;
            }
            case FIND_SUBSTRING_COUNT -> {
                data = MessageData.COUNT_OF_SUBSTRING +
                        fileService.findCountSubstringInFile(userInput);
            } case GET_FILES_ANALYSIS -> {
                data = analyzeFiles(userInput);
            } case GENERATE_TEST_FILES -> {
                data = generateTestFiles(userInput);
            }
        }
        return new ResultMessage(data);
    }

    private String generateTestFiles(String userInput) {

        String dirPath = userInput;

        if (userInput == null || userInput.isBlank()) {
            dirPath = AppConfig.DEFAULT_TEST_DIR;
        }

        for (int i = 0; i < AppConfig.COUNT_OF_TEST_FILES; i++) {
            fileService.createRandomDataFile(
                    dirPath,
                    "test_file_"+(i+1)+".txt"
            );
        }
        return MessageData.FILES_GENERATED_AT_DIR + dirPath;
    }


    public DescriptionMessage getDescriptionForOperation(Operation operation) {
        String data = null;
        switch (operation) {
            case INSERT_PATH -> {
                data = MessageData.INPUT_PATH_BY_FORMAT;
            }
            case FIND_SUBSTRING_COUNT -> {
                data = MessageData.INPUT_FIND_SUBSTRING;
            }
            case GET_FILES_ANALYSIS -> {
                data = MessageData.INPUT_DIR_PATH_OR_FILES_PATHS;
            }
            case GENERATE_TEST_FILES -> {
                data = MessageData.INPUT_PATH_OF_TEST_DIR;
            }
        }
        return new DescriptionMessage(data, MessageData.MESSAGE_DELIMITER);
    }

    public ResultMessage execute(Operation operation) throws Exception {
        String data = null;

        switch (operation) {
            case CLOSE_PROGRAM -> {
                ready = false;
                data = MessageData.APP_SUCCESSFUL_CLOSED;
            }
            case WRITE_TO_CONSOLE -> {
                data = fileService.getFileText();
            }
            case FIND_COUNT_OF_WORDS -> {
                data = MessageData.COUNT_OF_WORDS + fileService.getCountOfWords();
            }
        }
        return new ResultMessage(data);
    }

    public void validateFilePathFor(Operation operation) throws Exception {
        if (!operation.isNeedFile() || fileService.isValidFilePath()) {
            return;
        }
        fileService.invalidateFile();
    }

    private String analyzeFiles(String userInput) {
        List<String> paths;
        if (!userInput.contains(MessageData.FILE_PATH_DELIMITER)) {
            paths = fileService.getFilesPathsFromDir(userInput);
            if (paths.isEmpty()) {
                return MessageData.DIR_NOT_FOUND_OR_EMPTY;
            }
        } else {
            paths = new ArrayList<>(Arrays.asList(userInput.split(MessageData.FILE_PATH_DELIMITER)));
        }

        List<FileAnalysis> analyses = FileAnalyzer.analyzeSomeFiles(paths);
        StringBuilder data = new StringBuilder();
        int sumOfWords = 0;
        int sumOfChars = 0;
        for (int i = 0; i < analyses.size(); i++) {
            data.append(i+1).append(". ").append(analyses.get(i).toString()).append("; \n");
            sumOfWords += analyses.get(i).getCountOfWords();
            sumOfChars += analyses.get(i).getCountOfChars();
        }
        data.append("\n");
        data.append("Итого: ").append(sumOfWords).append(" слов, ").append(sumOfChars).append(" символов");

        return data.toString();
    }




}
