package sol;

import src.IEdge;
import src.IVertex;

import java.util.HashSet;
import java.util.Set;

/**
 * A City class representing the vertex of a travel graph
 */
public class City implements IVertex<Transport> {
    private String name;
    private HashSet<Transport> outgoingEdges;

    /**
     * Constructor for a City
     * @param name The name of the city
     */
    public City(String name) {
        this.name = name;
        this.outgoingEdges = new HashSet<>();
    }

    /**
     * Gets outgoing edges from this vertex
     *
     * @return Set of outgoing edges
     */
    @Override
    public Set<Transport> getOutgoing() {
        return this.outgoingEdges;
    }

    /**
     * Adds an outgoing edge to this vertex
     *
     * @param outEdge the outgoing edge
     */
    @Override
    public void addOut(Transport outEdge) {
        this.outgoingEdges.add(outEdge);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
