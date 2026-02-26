package my.learn.model.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ResultMessage implements Message {

    private final String data;

    @Override
    public String getMessage() {
        return data;
    }
}
