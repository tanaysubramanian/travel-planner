package sol;

import src.IGraph;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation for TravelGraph
 */
public class TravelGraph implements IGraph<City, Transport> {
    private HashSet<City> cities;

    /**
     * Constructor for a TravelGraph
     */
    public TravelGraph() {
        this.cities = new HashSet<>();
    }

    /**
     * Adds a vertex to the graph.
     *
     * @param vertex the vertex
     */
    @Override
    public void addVertex(City vertex) {
        this.cities.add(vertex);
    }

    /**
     * Adds an edge to the graph.
     *
     * @param origin the origin of the edge.
     * @param edge
     */
    @Override
    public void addEdge(City origin, Transport edge) {
        origin.addOut(edge);
    }

    /**
     * Gets a set of vertices in the graph.
     *
     * @return the set of vertices
     */
    @Override
    public Set<City> getVertices() {
        return this.cities;
    }

    /**
     * Gets the source of an edge.
     *
     * @param edge the edge
     * @return the source of the edge
     */
    @Override
    public City getEdgeSource(Transport edge) {
        return edge.getSource();
    }

    /**
     * Gets the target of an edge.
     *
     * @param edge the edge
     * @return the target of the edge
     */
    @Override
    public City getEdgeTarget(Transport edge) {
        return edge.getTarget();
    }

    /**
     * Gets the outgoing edges of a vertex.
     *
     * @param fromVertex the vertex
     * @return the outgoing edges from that vertex
     */
    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        return fromVertex.getOutgoing();
    }

    /**
     * Gets a city from the graph by its name.
     *
     * @param name the name of the city
     * @return the City object with the given name, or null if not found
     */
    public City getCity(String name) {
        for (City city : this.cities) {
            if (city.toString().equals(name)) {
                return city;
            }
        }
        return null;
    }
}