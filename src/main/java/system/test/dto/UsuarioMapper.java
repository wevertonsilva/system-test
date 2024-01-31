package system.test.dto;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import system.test.entity.Usuario;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario fromInDto(final UsuarioInDTO inDto);

    UsuarioOutDTO toOutDto(final Usuario entity);

    List<UsuarioOutFilhosDTO> toFilhosOutDto(final List<Usuario> entities);

}