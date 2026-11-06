import java.util.Scanner;

public class UserLogin {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String correctUsername = "admin";
        String correctPassword = "12345";

        System.out.print("Enter Username: ");
        String username = input.nextLine();

        System.out.print("Enter Password: ");
        String password = input.nextLine();

        if (username.isEmpty() || password.isEmpty()) {

            System.out.println("Please enter username and password");

        } else if (username.equals(correctUsername)
                && password.equals(correctPassword)) {

            System.out.println("Login Successful");
            System.out.println("Welcome to the User Dashboard");

        } else {

            System.out.println("Invalid username or password");
        }

        input.close();
    }
}