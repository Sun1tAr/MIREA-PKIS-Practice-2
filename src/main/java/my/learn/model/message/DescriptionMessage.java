package my.learn.model.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class DescriptionMessage implements Message {

    private final String data;
    private final String delimiter;

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(delimiter).append("\n");
        sb.append(data).append("\n");;
        sb.append(delimiter).append("\n");
        return sb.toString();
    }
}
