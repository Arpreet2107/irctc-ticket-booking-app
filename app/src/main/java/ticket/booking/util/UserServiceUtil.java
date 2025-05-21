package ticket.booking.util;

import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil {

    private UserServiceUtil() {
        // Private constructor to prevent instantiation.  This is a utility class.
    }

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    public static void main(String[] args) {
        // Example usage (optional, for testing)
        String password = "mySecretPassword";
        String hashedPassword = UserServiceUtil.hashPassword(password);
        System.out.println("Hashed Password: " + hashedPassword);

        boolean passwordMatch = UserServiceUtil.checkPassword(password, hashedPassword);
        System.out.println("Password Match: " + passwordMatch);

        boolean wrongPasswordMatch = UserServiceUtil.checkPassword("wrongPassword", hashedPassword);
        System.out.println("Wrong Password Match: " + wrongPasswordMatch);
    }
}
