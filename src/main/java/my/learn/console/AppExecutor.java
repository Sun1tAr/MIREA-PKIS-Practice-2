package my.learn.console;

import lombok.RequiredArgsConstructor;
import my.learn.model.message.DescriptionMessage;
import my.learn.model.message.OperationMessage;
import my.learn.model.Operation;
import my.learn.model.message.ResultMessage;
import my.learn.service.FileService;
import my.learn.service.OperationsService;

import java.util.Scanner;

import static my.learn.constatnts.Constants.DELIMITER;

@RequiredArgsConstructor
public class AppExecutor {

    private final Scanner scanner;
    private final ConsoleSender sender;
    private final OperationsService opSer;
    private final FileService fileService;


    public void execute() throws Exception {

        sender.sendMessage(
                new OperationMessage(fileService.getFilePath(), DELIMITER)
        );

        String userInput = scanner.nextLine();
        Operation curOper = opSer.getOperFromString(userInput);

        opSer.validateFilePathFor(curOper);

        ResultMessage result;
        if (opSer.mustBeInputFor(curOper)) {
            DescriptionMessage msg = opSer.getDescForOper(curOper);
            sender.sendMessage(msg);

            String nextUserInput = scanner.nextLine();
            result = opSer.execute(curOper, nextUserInput);
        } else {
            result = opSer.execute(curOper);
        }

        sender.sendMessage(result);

    }







}
