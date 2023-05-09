package com.example.projeto.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator {
    public static boolean isValidEmail(String email) {
        // Expressão regular para validar endereços de e-mail
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        // Compila a expressão regular em um objeto Pattern
        Pattern pattern = Pattern.compile(regex);

        // Usa o objeto Pattern para criar um objeto Matcher e verifica se o endereço de e-mail corresponde à expressão regular
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
