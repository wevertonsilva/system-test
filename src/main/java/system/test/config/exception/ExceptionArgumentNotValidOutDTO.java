package system.test.config.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionArgumentNotValidOutDTO {

    private HttpStatus status;
    private String message;
    private LocalDateTime dateTime;
    private List<ExceptionField> errors;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExceptionField {

        private String message;

    }

}