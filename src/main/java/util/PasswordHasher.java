package util;

public class PasswordHasher {
    public static String hashPassword(String password) {
       return String.valueOf(password.hashCode() * 1125 / 3);
    }
}
