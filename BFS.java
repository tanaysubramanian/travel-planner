package sol;

import src.IBFS;
import src.IEdge;
import src.IGraph;
import src.IVertex;

import java.util.*;

/**
 * Implementation for BFS, implements IBFS interface
 * @param <V> The type of the vertices
 * @param <E> The type of the edges
 */
public class BFS<V extends IVertex<E>, E extends IEdge<V>> implements IBFS<V, E> {
    
    /**
     * Returns the path from start to end.
     *
     * @param graph the graph including the vertices
     * @param start the start vertex
     * @param end   the end vertex
     * @return a list of edges starting from the start to the end
     */
    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        // Return an empty list if start and end are the same, as no path is needed
        if (start.equals(end)) {
            return Collections.emptyList();
        }

        // Initialize data structures for BFS
        HashSet<V> visited = new HashSet<>();
        LinkedList<V> toCheck = new LinkedList<>();
        HashMap<V, E> cameFrom = new HashMap<>();

        // Start the BFS from the start vertex
        toCheck.add(start);
        visited.add(start);

        while (!toCheck.isEmpty()) {
            V currentVertex = toCheck.removeFirst();

            // Explore all edges connected to the current vertex
            for (E edge : currentVertex.getOutgoing()) {
                V neighbor = edge.getTarget();

                // If the neighbor hasn't been visited, mark it as visited and add it to the queue
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    toCheck.add(neighbor);
                    cameFrom.put(neighbor, edge);

                    // If the end vertex is reached, backtrace and return the path
                    if (end.equals(neighbor)) {
                        return this.backtracePath(cameFrom, start, end);
                    }
                }
            }
        }
        // Return an empty list if no path is found
        return Collections.emptyList();
    }

    /**
     * Backtraces the path from the end vertex to the start vertex using the cameFrom map.
     *
     * @param cameFrom the map of vertices to the edges that connect them in the BFS tree
     * @param start     the start vertex
     * @param end       the end vertex
     * @return a list of edges representing the path from start to end
     */
    public List<E> backtracePath(HashMap<V, E> cameFrom, V start, V end) {
        LinkedList<E> path = new LinkedList<>();

        // Start from the end vertex
        V currentVertex = end;

        // Continue backtracking until the start vertex is reached
        while (!currentVertex.equals(start)) {
            // Get the edge that led to the current vertex during BFS traversal
            E edgeToCurrentVertex = cameFrom.get(currentVertex);

            // Add this edge to the front of the path list
            path.addFirst(edgeToCurrentVertex);

            // Move to the previous vertex in the path
            currentVertex = edgeToCurrentVertex.getSource();
        }
        return path;
    }
}
