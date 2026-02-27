package my.learn.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class FileAnalysis {

    private final String fileName;
    private final boolean isExists;
    private final Long countOfWords;
    private final Long countOfChars;

    @Override
    public String toString() {
        return fileName + ": " +
                (!isExists? "Не найден": (
                            countOfWords + " слов, " +
                            countOfChars + " символов"
                        )
                );
    }
}
