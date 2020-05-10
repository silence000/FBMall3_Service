package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class ResponseJsonBody implements Serializable {
    private Integer code;
    private String msg;
    private Object data;
    private Object extra;
}
