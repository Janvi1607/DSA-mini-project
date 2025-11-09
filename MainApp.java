import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        // Create the Graph (Model)
        SocialNetworkGraph graph = new SocialNetworkGraph();
       
        // Start the GUI on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new FriendSuggestionGUI(graph);
        });
    }
}  
