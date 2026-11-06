public class BookBorrowing {

    public static void main(String[] args) {

        String[] members = {"M001", "M002", "M003"};

        String[] bookIDs = {"B101", "B102", "B103"};

        boolean[] available = {true, false, true};

        java.util.Scanner input = new java.util.Scanner(System.in);

        System.out.print("Enter Member ID: ");
        String memberID = input.nextLine();

        System.out.print("Enter Book ID: ");
        String bookID = input.nextLine();

        if (memberID.isEmpty() || bookID.isEmpty()) {

            System.out.println("Please enter Member ID and Book ID");

        } else {

            boolean validMember = false;
            int bookIndex = -1;

            for (int i = 0; i < members.length; i++) {

                if (members[i].equals(memberID)) {
                    validMember = true;
                    break;
                }
            }

            for (int i = 0; i < bookIDs.length; i++) {

                if (bookIDs[i].equals(bookID)) {
                    bookIndex = i;
                    break;
                }
            }

            if (validMember && bookIndex != -1
                    && available[bookIndex]) {

                available[bookIndex] = false;

                System.out.println("Borrowing Details Recorded");
                System.out.println("Member ID: " + memberID);
                System.out.println("Book ID: " + bookID);
                System.out.println("Book borrowed successfully");

            } else {

                System.out.println("Book borrowing failed");
            }
        }

        input.close();
    }
}