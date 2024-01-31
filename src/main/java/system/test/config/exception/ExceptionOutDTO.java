package system.test.config.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import system.test.utils.Message;

import java.time.LocalDateTime;

import static io.micrometer.common.util.StringUtils.isNotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ExceptionOutDTO {

    private HttpStatus status;
    private String message;
    private LocalDateTime dateTime;
    private String stacktrace;

    public ExceptionOutDTO(final HttpStatus status, final String message, final LocalDateTime dateTime, final String stacktrace) {
        this.status = status;
        this.message = message;
        this.dateTime = dateTime;
        this.stacktrace = stacktrace;

        if (isNotBlank(message)) {
            this.message = Message.getMessage(message);
        }
    }

}