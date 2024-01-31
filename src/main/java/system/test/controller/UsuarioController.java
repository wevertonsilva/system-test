package system.test.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.test.config.exception.BadRequestException;
import system.test.dto.UsuarioInDTO;
import system.test.dto.UsuarioMapper;
import system.test.dto.UsuarioOutDTO;
import system.test.dto.UsuarioOutFilhosDTO;
import system.test.service.IUsuarioService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuarioService service;
    private final UsuarioMapper mapper;

    @PostMapping
    public UsuarioOutDTO create(@RequestBody @Valid final UsuarioInDTO usuarioInDTO) throws BadRequestException {
        return mapper.toOutDto(service.create(mapper.fromInDto(usuarioInDTO)));
    }

    @GetMapping("/listas/nomes")
    public List<UsuarioOutFilhosDTO> findListaUsuarios() {
        return mapper.toFilhosOutDto(service.findListaUsuarios());
    }

}