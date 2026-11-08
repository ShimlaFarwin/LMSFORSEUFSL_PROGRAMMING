import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EventDrivenBookSearch extends JFrame {

    private JTextField searchField;
    private JTextArea resultArea;

    private String[] books = {
        "Java Programming",
        "Database Systems",
        "Web Development"
    };

    public EventDrivenBookSearch() {

        setTitle("Library Book Search");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Enter Book Title:");

        searchField = new JTextField(20);

        JButton searchButton = new JButton("Search");

        resultArea = new JTextArea(8, 35);
        resultArea.setEditable(false);

        searchButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                String keyword = searchField.getText();
                boolean found = false;

                resultArea.setText("");

                if (keyword.trim().isEmpty()) {
                    resultArea.setText(
                        "Please enter a search keyword"
                    );
                } else {

                    for (String book : books) {

                        if (book.toLowerCase().contains(
                                keyword.toLowerCase())) {

                            resultArea.append(
                                "Book Found: " + book + "\n"
                            );

                            found = true;
                        }
                    }

                    if (!found) {
                        resultArea.setText("No books found");
                    }
                }
            }
        });

        add(label);
        add(searchField);
        add(searchButton);
        add(new JScrollPane(resultArea));

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new EventDrivenBookSearch();
    }
}