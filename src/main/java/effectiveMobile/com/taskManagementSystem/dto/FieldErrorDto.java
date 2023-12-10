package effectiveMobile.com.taskManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldErrorDto {
    private final String objectName;
    private final String field;
    private final String message;
}
