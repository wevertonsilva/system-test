package system.test.config.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.time.LocalDateTime.now;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static system.test.utils.Message.getMessage;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionOutDTO> badRequestException(final BadRequestException exception) {
        logException(exception);

        return ResponseEntity.status(BAD_REQUEST).body(
                ExceptionOutDTO.builder()
                        .status(BAD_REQUEST)
                        .message(exception.getMessage())
                        .dateTime(now())
                        .stacktrace(getStacktrace(exception))
                        .build());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ResponseEntity<ExceptionOutDTO> handleGlobalException(final Exception exception) {
        logException(exception);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(
                ExceptionOutDTO.builder()
                        .status(INTERNAL_SERVER_ERROR)
                        .message(exception.getMessage())
                        .dateTime(now())
                        .stacktrace(getStacktrace(exception))
                        .build());
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
                                                                  final @NotNull HttpHeaders headers,
                                                                  final @NotNull HttpStatusCode status,
                                                                  final @NotNull WebRequest request) {
        final HttpStatus httpStatus = BAD_REQUEST;

        final ExceptionArgumentNotValidOutDTO exception = ExceptionArgumentNotValidOutDTO.builder()
                .status(httpStatus)
                .message(getMessage("error.validation-failed"))
                .dateTime(now())
                .errors(ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> ExceptionArgumentNotValidOutDTO.ExceptionField.builder()
                                .message(getMessage(error.getDefaultMessage()))
                                .build())
                        .toList())
                .build();

        return new ResponseEntity<>(exception, headers, httpStatus);
    }

    private void logException(final Exception exception) {
        log.error(getStacktrace(exception));
    }

    private String getStacktrace(final Exception exception) {
        return getStackTrace(exception);
    }

}