package sol;

import src.IDijkstra;
import src.IEdge;
import src.IGraph;
import src.IVertex;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.PriorityQueue;
import java.util.*;

/**
 * Implementation for Dijkstra's algorithm
 *
 * @param <V> the type of the vertices
 * @param <E> the type of the edges
 */
public class Dijkstra<V extends IVertex<E>, E extends IEdge<V>> implements IDijkstra<V, E> {

    /**
     * Finds the lowest cost path from source to destination.
     *
     * @param graph       the graph including the vertices
     * @param source      the source vertex
     * @param destination the destination vertex
     * @param edgeWeight  the weight of an edge
     * @return a list of edges from source to destination
     */
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {

        // Initialize data structures for Dijkstra
        HashMap<V, Double> routeDist = new HashMap<>();
        HashSet<V> visited = new HashSet<>();
        HashMap<V, E> cameFrom = new HashMap<>();

        Comparator<V> shortestDistanceFirst = (city1, city2) -> {
            return Double.compare(routeDist.get(city1), routeDist.get(city2));
        };
        PriorityQueue<V> toCheckQueue = new PriorityQueue<>(shortestDistanceFirst);


        // Initialize distances to infinity and source to 0
        for (V vertex : graph.getVertices()) {
            routeDist.put(vertex, Double.POSITIVE_INFINITY);
        }
        routeDist.put(source, 0.0);

        // Add source to priority queue
        toCheckQueue.add(source);

        while (!toCheckQueue.isEmpty()) {
            V currentVertex = toCheckQueue.poll();      // removes and returns first element of priority queue

            if (currentVertex.equals(destination)) {
                return this.backtracePath(graph, source, destination, cameFrom);
            }

            if (!visited.contains(currentVertex)) {
                // Explore all outgoing edges from the current vertex
                this.evaluateNeighbors(graph, currentVertex, edgeWeight, routeDist, cameFrom, toCheckQueue);
                // Add current vertex to visited set
                visited.add(currentVertex);
            }
        }
        // Return an empty list if no path is found
        return Collections.emptyList();
    }

    /**
     * Evaluates the neighboring vertices of the current vertex and updates the shortest paths.
     *
     * @param graph        the graph including the vertices
     * @param currentVertex the current vertex
     * @param edgeWeight   a function to compute the weight of an edge
     * @param routeDist    a map storing the shortest distances from source to each vertex
     * @param cameFrom     a map storing the edge that leads to each vertex
     * @param toCheckQueue the priority queue used in Dijkstra's algorithm
     */
    public void evaluateNeighbors(IGraph<V, E> graph, V currentVertex, Function<E, Double> edgeWeight,
                                      HashMap<V, Double> routeDist, HashMap<V, E> cameFrom,
                                  PriorityQueue<V> toCheckQueue) {
        for (E edge : graph.getOutgoingEdges(currentVertex)) {
            V targetVertex = graph.getEdgeTarget(edge);
            double edgeCost = edgeWeight.apply(edge);
            double newDist = routeDist.get(currentVertex) + edgeCost;

            // Update the shortest path if a shorter path is found
            if (newDist < routeDist.get(targetVertex)) {
                routeDist.put(targetVertex, newDist);
                cameFrom.put(targetVertex, edge);

                // Update the priority of the target vertex in the priority queue
                toCheckQueue.remove(targetVertex);
                toCheckQueue.add(targetVertex);
            }
        }
    }

    /**
     * Backtraces the path from destination to source using the information stored in cameFrom map.
     *
     * @param graph        the graph including the vertices
     * @param source       the source vertex
     * @param destination  the destination vertex
     * @param cameFrom     a map storing the edge that leads to each vertex
     * @return a list of edges forming the shortest path from source to destination,
     * or an empty list if no path exists
     */
    public List<E> backtracePath(IGraph<V, E> graph, V source, V destination, HashMap<V, E> cameFrom) {
        // Reconstruct the path from source to destination
        LinkedList<E> path = new LinkedList<>();

        V currentVertex = destination;

        while (!currentVertex.equals(source)) {
            // Get the edge that led to the current vertex during Dijkstra's traversal
            E edgeToCurrentVertex = cameFrom.get(currentVertex);

            path.addFirst(edgeToCurrentVertex);
            // Move to the previous vertex in the path
            currentVertex = graph.getEdgeSource(edgeToCurrentVertex);
        }
        return path;
    }
}
