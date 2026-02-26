package my.learn.constatnts;

import my.learn.model.Operation;

import java.util.List;

public class Constants {


    public final static List<Operation> operations = List.of(
            Operation.CLOSE_PROGRAM,
            Operation.INSERT_PATH,
            Operation.WRITE_TO_CONSOLE,
            Operation.FIND_SUBSTRING_COUNT,
            Operation.FIND_COUNT_OF_WORDS
    );

    public final static String DELIMITER =
            "-----------------------------------------------";

}
