package system.test.config.exception;

import system.test.utils.Message;

public class BadRequestException extends Exception {

    public BadRequestException(final String message) {
        super(Message.getMessage(message));
    }

    public BadRequestException(final String message, final Object... args) {
        super(Message.getMessage(message, args));
    }

}