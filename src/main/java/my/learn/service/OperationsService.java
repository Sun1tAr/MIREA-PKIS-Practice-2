package my.learn.service;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import my.learn.constatnts.Constants;
import my.learn.constatnts.MessageData;
import my.learn.model.Operation;
import my.learn.model.message.DescriptionMessage;
import my.learn.model.message.ResultMessage;

import static my.learn.constatnts.Constants.DELIMITER;

@RequiredArgsConstructor
public class OperationsService {

    private final FileService fileService;

    @Getter
    private boolean ready = true;

    public Operation getOperFromString(String userInput) throws Exception {
        int i;
        try {
            i = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            throw new Exception(MessageData.INCORRECT_DATA);
        }

        for (Operation operation : Constants.operations) {
            if (operation.getValue() == i) {
                return operation;
            }
        }
        throw new Exception(MessageData.OPERATION_NOT_SUPPLY);
    }


    public boolean mustBeInputFor(Operation operation) {
        switch (operation) {
            case CLOSE_PROGRAM,
                 WRITE_TO_CONSOLE,
                 FIND_COUNT_OF_WORDS -> {
                return false;
            }
            default -> {
                return true;
            }
        }
    }

    public ResultMessage execute(Operation operation,
                                 String userInput) throws Exception {
        String data = null;
        switch (operation) {
            case INSERT_PATH -> {
                fileService.setFilePath(userInput);
                data = MessageData.PATH_SUCCESSFUL_INSTALLED;
            }
            case FIND_SUBSTRING_COUNT -> {
                data = MessageData.COUNT_OF_SUBSTRING +
                        fileService.findCountSubstringInFile(userInput);
            }
        }
        return new ResultMessage(data);
    }

    public DescriptionMessage getDescForOper(Operation operation) throws Exception {
        String data = null;
        switch (operation) {
            case INSERT_PATH -> {
                data = MessageData.INPUT_PATH_BY_FORMAT;
            }
            case FIND_SUBSTRING_COUNT -> {
                data = MessageData.INPUT_FIND_SUBSTRING;
            }
        }
        return new DescriptionMessage(data, DELIMITER);
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
        if (operation == Operation.CLOSE_PROGRAM ||
                operation == Operation.INSERT_PATH ||
                fileService.isValidFilePath()) {
            return;
        }
        fileService.invalidateFile();
    }




}
