import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {

    public enum Type {
        LOGIN, MESSAGE, EXIT
    }

    private String id;
    private Type mType;
    private String messageBody;
    private ZonedDateTime messageDate;
}
