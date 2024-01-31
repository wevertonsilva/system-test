package system.test.utils;

import lombok.NoArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class StringUtils {

    public static String encodeSenha(final String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

}