package system.test.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import system.test.config.exception.BadRequestException;
import system.test.entity.Usuario;
import system.test.enuns.EnumCategoriaSenha;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isLetterOrDigit;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static system.test.enuns.EnumCategoriaSenha.BOA;
import static system.test.enuns.EnumCategoriaSenha.FORTE;
import static system.test.enuns.EnumCategoriaSenha.FRACA;
import static system.test.enuns.EnumCategoriaSenha.MEDIANA;
import static system.test.enuns.EnumCategoriaSenha.MUITO_FRACA;
import static system.test.utils.StringUtils.encodeSenha;

@Slf4j
@Getter
@Service
@RequiredArgsConstructor
public class SenhaService implements ISenhaService {

    private static final Integer VALOR_INICIAL = 0;
    private static final Integer QTDE_MINIMA_CARACTERES_SENHA = 8;
    private static final BigDecimal VALOR_MAXIMO = BigDecimal.valueOf(100);
    private static final MathContext MATH_CONTEXT = new MathContext(10);
    private static final Integer QTDE_REQUERIMENTOS = 4;
    private static final String CARACTERES_LETRAS_ESPECIAIS = "âàãáÁÂÀÃéêÉÊíÍóôõÓÔÕüúÜÚÇç";

    public void apply(final Usuario usuario) throws BadRequestException {
        validarSenha(usuario);
        determinarForcaSenha(usuario);
        atualizarCategoria(usuario);
        criptografarSenha(usuario);
    }

    private void validarSenha(final Usuario usuario) throws BadRequestException {
        if (usuario.getSenha().length() < QTDE_MINIMA_CARACTERES_SENHA) {
            throw new BadRequestException("usuario.senha.insuficiente");
        }
    }

    public void determinarForcaSenha(final Usuario usuario) {
        final Integer score = obterScore(usuario.getSenha());
        final BigDecimal forcaSenha = BigDecimal.valueOf(score).min(VALOR_MAXIMO);
        usuario.setForcaSenhaPercentual(forcaSenha);
    }

    private Integer obterScore(final String senha) {
        final int length = senha.length();
        final AtomicInteger score = new AtomicInteger(VALOR_INICIAL);

        score.addAndGet(length * 4);
        score.addAndGet(countUppercaseLetters(senha) * 2);
        score.addAndGet(countLowercaseLetters(senha) * 2);
        score.addAndGet(countNumbers(senha) * 4);
        score.addAndGet(countSymbols(senha) * 6);
        score.addAndGet(countMiddleNumbersOrSymbols(senha) * 2);
        score.addAndGet(countRequirements(senha) * 2);

        if (isLettersOnly(senha)) {
            score.addAndGet(-length);
        }

        if (isNumbersOnly(senha)) {
            score.addAndGet(-length);
        }

        score.addAndGet(-countRepeatCharacters(senha));
        score.addAndGet(-countConsecutiveUppercaseLetters(senha) * 2);
        score.addAndGet(-countConsecutiveLowercaseLetters(senha) * 2);
        score.addAndGet(-countConsecutiveNumbers(senha) * 2);
        score.addAndGet(-countSequentialLetters(senha) * 3);
        score.addAndGet(-countSequentialNumbers(senha) * 3);
        score.addAndGet(-countSequentialSymbols(senha) * 3);

        return Math.max(0, score.get());
    }

    private static int countUppercaseLetters(final String senha) {
        final AtomicInteger count = new AtomicInteger(VALOR_INICIAL);

        for (final char c : senha.toCharArray()) {
            if (isUpperCase(c) && isFalse(CARACTERES_LETRAS_ESPECIAIS.contains(String.valueOf(c)))) {
                count.incrementAndGet();
            }
        }

        if (count.get() > VALOR_INICIAL) {
            return senha.length() - count.get();
        }

        return VALOR_INICIAL;
    }

    private static int countLowercaseLetters(final String senha) {
        final AtomicInteger count = new AtomicInteger(VALOR_INICIAL);

        for (final char c : senha.toCharArray()) {
            if (isLowerCase(c) && isFalse(CARACTERES_LETRAS_ESPECIAIS.contains(String.valueOf(c)))) {
                count.incrementAndGet();
            }
        }

        if (count.get() > VALOR_INICIAL) {
            return senha.length() - count.get();
        }

        return VALOR_INICIAL;
    }

    private static int countNumbers(final String senha) {
        final AtomicInteger count = new AtomicInteger(VALOR_INICIAL);

        for (final char c : senha.toCharArray()) {
            if (isNumeric(String.valueOf(c))) {
                count.incrementAndGet();
            }
        }

        return count.get();
    }

    private static int countSymbols(final String password) {
        final AtomicInteger count = new AtomicInteger(VALOR_INICIAL);

        for (final char c : password.toCharArray()) {
            if (isFalse(isLetterOrDigit(c)) || CARACTERES_LETRAS_ESPECIAIS.contains(String.valueOf(c))) {
                count.incrementAndGet();
            }
        }

        return count.get();
    }

    private static int countMiddleNumbersOrSymbols(final String password) {
        final AtomicInteger count = new AtomicInteger(VALOR_INICIAL);

        for (int i = 1; i < password.length() - 1; i++) {
            final char c = password.charAt(i);

            if ((isFalse(isLetter(c)) || CARACTERES_LETRAS_ESPECIAIS.contains(String.valueOf(c)))
                    && isFalse(Objects.equals(i, 1))
                    && isFalse(Objects.equals(i, password.length()))) {
                count.incrementAndGet();
            }
        }

        return count.get();
    }

    private static int countRequirements(final String password) {
        if (countUppercaseLetters(password) > VALOR_INICIAL && countLowercaseLetters(password) > VALOR_INICIAL
                && countNumbers(password) > VALOR_INICIAL && countSymbols(password) > VALOR_INICIAL) {
            return QTDE_REQUERIMENTOS;
        }

        return VALOR_INICIAL;
    }

    private static boolean isLettersOnly(final String password) {
        for (final char c : password.toCharArray()) {
            if (isFalse(isLetter(c)) || CARACTERES_LETRAS_ESPECIAIS.contains(String.valueOf(c))) {
                return FALSE;
            }
        }

        return TRUE;
    }

    private static boolean isNumbersOnly(final String password) {
        for (final char c : password.toCharArray()) {
            if (isFalse(isDigit(c))) {
                return FALSE;
            }
        }

        return TRUE;
    }

    private static int countRepeatCharacters(final String password) {
        final AtomicInteger repeatCount = new AtomicInteger(VALOR_INICIAL);

        for (int i = 0; i < password.length(); i++) {
            final char currentChar = password.charAt(i);

            for (int j = i + 1; j < password.length(); j++) {
                if (Objects.equals(password.charAt(j), currentChar)) {
                    repeatCount.incrementAndGet();
                    break;
                }
            }
        }

        return repeatCount.get();
    }

    private static int countConsecutiveUppercaseLetters(final String password) {
        final AtomicInteger consecutiveCount = new AtomicInteger(VALOR_INICIAL);
        char previousChar = 0;

        for (int i = 0; i < password.length() - 1; i++) {
            final char currentChar = password.charAt(i);

            if (isUpperCase(currentChar) && isUpperCase(previousChar)
                    && isFalse(CARACTERES_LETRAS_ESPECIAIS.contains(String.valueOf(previousChar)))) {
                consecutiveCount.incrementAndGet();
            }

            previousChar = password.charAt(i);
        }

        return consecutiveCount.get();
    }

    private static int countConsecutiveLowercaseLetters(final String password) {
        final AtomicInteger consecutiveCount = new AtomicInteger(VALOR_INICIAL);
        char previousChar = 0;

        for (int i = 0; i < password.length() - 1; i++) {
            final char currentChar = password.charAt(i);

            if (isLowerCase(currentChar) && isLowerCase(previousChar)
                    && isFalse(CARACTERES_LETRAS_ESPECIAIS.contains(String.valueOf(previousChar)))) {
                consecutiveCount.incrementAndGet();
            }

            previousChar = password.charAt(i);
        }

        return consecutiveCount.get();
    }

    private static int countConsecutiveNumbers(final String password) {
        final AtomicInteger consecutiveCount = new AtomicInteger(VALOR_INICIAL);

        for (int i = 0; i < password.length() - 1; i++) {
            final char currentChar = password.charAt(i);
            final char nextChar = password.charAt(i + 1);

            if (isDigit(currentChar) && isDigit(nextChar)) {
                consecutiveCount.incrementAndGet();
            }
        }

        return consecutiveCount.get();
    }

    private static int countSequentialLetters(final String password) {
        final AtomicInteger sequentialCount = new AtomicInteger(VALOR_INICIAL);

        for (int i = 0; i < password.length() - 2; i++) {
            if (isLetter(password.charAt(i))
                    && Objects.equals(password.charAt(i) + 1, password.charAt(i + 1))
                    && Objects.equals(password.charAt(i) + 2, password.charAt(i + 2))) {
                sequentialCount.incrementAndGet();
            }
        }

        return sequentialCount.get();
    }

    private static int countSequentialNumbers(final String password) {
        final AtomicInteger sequentialCount = new AtomicInteger(VALOR_INICIAL);

        for (int i = 0; i < password.length() - 2; i++) {
            if (isDigit(password.charAt(i))
                    && password.charAt(i) + 1 == password.charAt(i + 1)
                    && password.charAt(i) + 2 == password.charAt(i + 2)) {
                sequentialCount.incrementAndGet();
            }
        }

        return sequentialCount.get();
    }

    private static int countSequentialSymbols(final String password) {
        final AtomicInteger sequentialCount = new AtomicInteger(VALOR_INICIAL);

        for (int i = 0; i < password.length() - 2; i++) {
            if (isFalse(isLetterOrDigit(password.charAt(i)))
                    && Objects.equals(password.charAt(i) + 1, password.charAt(i + 1))
                    && Objects.equals(password.charAt(i) + 2, password.charAt(i + 2))) {
                sequentialCount.incrementAndGet();
            }
        }

        return sequentialCount.get();
    }

    public void atualizarCategoria(final Usuario usuario) {
        final int forcaSenha = usuario.getForcaSenhaPercentual().intValue();
        final AtomicReference<EnumCategoriaSenha> categoriaSenha = new AtomicReference<>(MUITO_FRACA);

        if (forcaSenha > 0 && forcaSenha < 20) {
            categoriaSenha.set(MUITO_FRACA);
        } else if (forcaSenha >= 20 && forcaSenha < 40) {
            categoriaSenha.set(FRACA);
        } else if (forcaSenha >= 40 && forcaSenha < 60) {
            categoriaSenha.set(MEDIANA);
        } else if (forcaSenha >= 60 && forcaSenha < 80) {
            categoriaSenha.set(BOA);
        } else if (forcaSenha >= 80) {
            categoriaSenha.set(FORTE);
        } else {
            categoriaSenha.set(MUITO_FRACA);
        }

        usuario.setCategoriaSenha(categoriaSenha.get());
        usuario.setCategoriaSenhaFormatada(categoriaSenha.get().getDescricao());
    }

    public void criptografarSenha(final Usuario usuario) {
        usuario.setSenha(encodeSenha(usuario.getSenha()));
    }

}
