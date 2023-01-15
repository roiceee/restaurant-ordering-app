package authentication.utility;

public class PasswordSecurityService {
    public String generatePassword(String password) {
       return String.valueOf(password.hashCode() * 1125 / 3);
    }
}
