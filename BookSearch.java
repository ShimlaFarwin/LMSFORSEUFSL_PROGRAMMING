public class BookSearch {

    public static void main(String[] args) {

        String[][] books = {
            {"Java Programming", "James Gosling", "9780134685991", "Programming"},
            {"Database Systems", "Thomas Connolly", "9780132943260", "Database"},
            {"Web Development", "Jon Duckett", "9781118907443", "Web"},
            {"Computer Networks", "Andrew Tanenbaum", "9780132126953", "Networking"}
        };

        java.util.Scanner input = new java.util.Scanner(System.in);

        System.out.print("Enter book title, author, ISBN or category: ");
        String keyword = input.nextLine();

        if (keyword.isEmpty()) {

            System.out.println("Please enter a search keyword");

        } else {

            boolean found = false;

            for (int i = 0; i < books.length; i++) {

                if (books[i][0].toLowerCase().contains(keyword.toLowerCase())
                    || books[i][1].toLowerCase().contains(keyword.toLowerCase())
                    || books[i][2].contains(keyword)
                    || books[i][3].toLowerCase().contains(keyword.toLowerCase())) {

                    System.out.println("\nBook Found!");
                    System.out.println("Title: " + books[i][0]);
                    System.out.println("Author: " + books[i][1]);
                    System.out.println("ISBN: " + books[i][2]);
                    System.out.println("Category: " + books[i][3]);

                    found = true;
                }
            }

            if (!found) {
                System.out.println("No books found");
            }
        }

        input.close();
    }
}