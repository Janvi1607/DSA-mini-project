import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Set;

public class FriendSuggestionGUI extends JFrame {
    private final SocialNetworkGraph graph;
    private JComboBox<String> userComboBox;
    private JTextArea resultArea;

    public FriendSuggestionGUI(SocialNetworkGraph graph) {
        this.graph = graph;
       
        // 1. Setup the Frame
        setTitle("Social Network Friend Suggestion");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLayout(new BorderLayout(10, 10)); // Use BorderLayout

        // 2. Center Panel (Controls)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
       
        controlPanel.add(new JLabel("Select User:"));
       
        // Populate ComboBox
        Set<String> users = graph.getAllUsers();
        userComboBox = new JComboBox<>(users.toArray(new String[0]));
        userComboBox.setPreferredSize(new Dimension(100, 25));
        controlPanel.add(userComboBox);
       
        JButton suggestButton = new JButton("Find Suggestions (BFS)");
        suggestButton.addActionListener(this::onSuggestButtonClick);
        controlPanel.add(suggestButton);

        // 3. Result Area
        resultArea = new JTextArea("Suggestions will appear here...", 10, 35);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
       
        // 4. Add components to Frame
        add(controlPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    private void onSuggestButtonClick(ActionEvent e) {
        String selectedUser = (String) userComboBox.getSelectedItem();
       
        if (selectedUser == null) {
            resultArea.setText("Please select a user.");
            return;
        }

        // Call the BFS core logic
        Set<String> suggestions = graph.findSuggestionsBFS(selectedUser);
       
        // Display results
        resultArea.setText("--- Suggestions for " + selectedUser + " ---\n\n");
        if (suggestions.isEmpty()) {
            resultArea.append("No 'Friends of Friends' suggestions found.");
        } else {
            resultArea.append(String.join(", ", suggestions));
        }
    }
}   
