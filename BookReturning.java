import java.util.Scanner;
import java.time.LocalDate;

public class BookReturning {

    public static void main(String[] args) {

        String[] loanMemberIDs = {"M001", "M002"};
        String[] loanBookIDs = {"B101", "B102"};

        boolean[] activeBorrowings = {true, true};
        boolean[] available = {false, false};

        Scanner input = new Scanner(System.in);

        System.out.print("Enter Member ID: ");
        String memberID = input.nextLine();

        System.out.print("Enter Book ID: ");
        String bookID = input.nextLine();

        if (memberID.isEmpty() || bookID.isEmpty()) {

            System.out.println("Please enter Member ID and Book ID");

        } else {

            boolean recordFound = false;

            for (int i = 0; i < loanMemberIDs.length; i++) {

                if (loanMemberIDs[i].equals(memberID)
                        && loanBookIDs[i].equals(bookID)
                        && activeBorrowings[i]) {

                    activeBorrowings[i] = false;
                    available[i] = true;

                    String returnDate = LocalDate.now().toString();

                    System.out.println("Book Return Successful");
                    System.out.println("Member ID: " + memberID);
                    System.out.println("Book ID: " + bookID);
                    System.out.println("Return Date: " + returnDate);
                    System.out.println("Borrowing Status: Returned");
                    System.out.println("Book Availability: Available");

                    recordFound = true;
                    break;
                }
            }

            if (!recordFound) {
                System.out.println("No active borrowing record found");
            }
        }

        input.close();
    }
}