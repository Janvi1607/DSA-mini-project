import java.util.*;

public class SocialNetworkGraph {
    // Adjacency List: Key=User, Value=Set of Friends
    private final Map<String, Set<String>> network = new HashMap<>();

    public SocialNetworkGraph() {
        // Initialize Sample Data
        addConnection("Alice", "Bob");
        addConnection("Alice", "Charlie");
        addConnection("Bob", "David");
        addConnection("Charlie", "David");
        addConnection("David", "Emily");
        addConnection("Emily", "Frank");
        addConnection("Frank", "George");
        addConnection("Charlie", "George");
        // Add symmetric connections (undirected graph)
    }

    private void addConnection(String user1, String user2) {
        network.computeIfAbsent(user1, k -> new HashSet<>()).add(user2);
        network.computeIfAbsent(user2, k -> new HashSet<>()).add(user1);
    }

    public Set<String> getAllUsers() {
        return network.keySet();
    }

    /**
     * Finds friend suggestions (Friends of Friends) using BFS.
     * @param startUser The user for whom suggestions are needed.
     * @return A set of suggested user names.
     */
    public Set<String> findSuggestionsBFS(String startUser) {
        if (!network.containsKey(startUser)) {
            return Collections.emptySet();
        }

        Set<String> suggestions = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        Map<String, Integer> distance = new HashMap<>();

        // Initialization
        queue.add(startUser);
        distance.put(startUser, 0);

        // BFS traversal
        while (!queue.isEmpty()) {
            String currentUser = queue.poll();
            int dist = distance.get(currentUser);

            if (dist == 2) {
                // At distance 2 (Friends of Friends), check if they are not direct friends
                Set<String> directFriends = network.getOrDefault(startUser, Collections.emptySet());
               
                // Exclude self and direct friends
                if (dist > 0 && !directFriends.contains(currentUser) && !currentUser.equals(startUser)) {
                    suggestions.add(currentUser);
                }
                continue; // Stop exploring deeper from this path
            }
           
            if (dist < 2) {
                for (String neighbor : network.getOrDefault(currentUser, Collections.emptySet())) {
                    if (!distance.containsKey(neighbor)) {
                        distance.put(neighbor, dist + 1);
                        queue.add(neighbor);
                    }
                }
            }
        }
        return suggestions;
    }
}
