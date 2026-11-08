class Book {

    private String title;

    public Book(String title) {
        this.title = title;
    }

    public boolean matches(String keyword) {
        return title.toLowerCase()
                .contains(keyword.toLowerCase());
    }

    public void display() {
        System.out.println("Book Found: " + title);
    }
}

public class OOPBookSearch {

    public static void main(String[] args) {

        Book[] books = {
            new Book("Java Programming"),
            new Book("Database Systems"),
            new Book("Web Development")
        };

        String keyword = "Java";
        boolean found = false;

        for (Book book : books) {

            if (book.matches(keyword)) {
                book.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found");
        }
    }
}