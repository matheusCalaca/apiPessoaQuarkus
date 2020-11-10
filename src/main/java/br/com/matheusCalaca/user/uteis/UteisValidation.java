package br.com.matheusCalaca.user.uteis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UteisValidation {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$");

    public static boolean isValidEmail(String emailStr) {
        return validateRegex(VALID_EMAIL_ADDRESS_REGEX, emailStr);
    }

    public static boolean isValidPassword(String input) {
        return validateRegex(VALID_PASSWORD_REGEX, input);
    }

    private static boolean validateRegex(Pattern regex,String input) {
        Matcher matcher = regex.matcher(input);
        return matcher.find();
    }

}
