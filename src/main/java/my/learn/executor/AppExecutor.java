package my.learn.executor;

import lombok.RequiredArgsConstructor;
import my.learn.console.ConsoleSender;
import my.learn.model.message.DescriptionMessage;
import my.learn.model.message.OperationMessage;
import my.learn.model.Operation;
import my.learn.model.message.ResultMessage;
import my.learn.service.FileService;

import java.util.Scanner;

import static my.learn.config.MessageData.MESSAGE_DELIMITER;

@RequiredArgsConstructor
public class AppExecutor {

    private final Scanner scanner;
    private final ConsoleSender sender;
    private final OperationExecutor operationExecutor;
    private final FileService fileService;


    public void execute() throws Exception {

        sender.sendMessage(
                new OperationMessage(fileService.getFilePath(), MESSAGE_DELIMITER)
        );

        String userInput = scanner.nextLine();
        Operation curOper = operationExecutor.getOperationFromString(userInput);

        operationExecutor.validateFilePathFor(curOper);

        ResultMessage result;
        if (curOper.isNeedSomeData()) {
            DescriptionMessage msg = operationExecutor.getDescriptionForOperation(curOper);
            sender.sendMessage(msg);

            String nextUserInput = scanner.nextLine();
            result = operationExecutor.execute(curOper, nextUserInput);
        } else {
            result = operationExecutor.execute(curOper);
        }

        sender.sendMessage(result);

    }

    public static void run() {
        Scanner scanner = new Scanner(System.in);
        FileService fileService = new FileService();
        OperationExecutor executor = new OperationExecutor(fileService);
        ConsoleSender sender = new ConsoleSender();

        AppExecutor appExecutor = new AppExecutor(scanner, sender, executor, fileService);

        while (executor.isReady()) {
            try {
                appExecutor.execute();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }







}
