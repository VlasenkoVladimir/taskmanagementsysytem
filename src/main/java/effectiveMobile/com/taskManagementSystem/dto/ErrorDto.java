package effectiveMobile.com.taskManagementSystem.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorDto {
    private final String message;
    private final String description;
    private List<FieldErrorDto> fieldErrors;
    
    public ErrorDto(String message) {
        this(message, null);
    }
    
    public ErrorDto(String message,
                    String description) {
        this.message = message;
        this.description = description;
    }
    
    public void add(String objectName,
                    String field,
                    String message) {
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }
        fieldErrors.add(new FieldErrorDto(objectName, field, message));
        
    }
}
