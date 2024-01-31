package system.test.service;

import system.test.config.exception.BadRequestException;
import system.test.entity.Usuario;

public interface ISenhaService {

    void apply(final Usuario usuario) throws BadRequestException;

}
