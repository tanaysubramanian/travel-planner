package sol;

import src.ITravelController;
import src.TransportType;
import src.TravelCSVParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Implementation for TravelController
 */
public class TravelController implements ITravelController<City, Transport> {

    // Why is this field of type TravelGraph and not IGraph?
    // Are there any advantages to declaring a field as a specific type rather than the interface?
    // If this were of type IGraph, could you access methods in TravelGraph not declared in IGraph?
    // Hint: perhaps you need to define a method!
    private TravelGraph graph;
    private Dijkstra<City, Transport> dijkstra;
    private BFS<City, Transport> bfs;

    /**
     * Constructor for TravelController
     */
    public TravelController() {
        this.graph = new TravelGraph();
        this.dijkstra = new Dijkstra<>();
        this.bfs = new BFS<>();
    }

    /**
     * Loads CSVs into the app.
     *
     * @param citiesFile    the filename of the cities csv
     * @param transportFile the filename of the transportations csv
     * @return an informative message to be printed in the REPL
     */
    @Override
    public String load(String citiesFile, String transportFile) {
        this.graph = new TravelGraph();
        TravelCSVParser parser = new TravelCSVParser();

        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));
            return null; // need explicit return null to account for Void type
        };

        Function<Map<String, String>, Void> addEdge = map -> {
            // Extract information from the CSV row
            String originName = map.get("origin");
            String destinationName = map.get("destination");
            TransportType type = TransportType.fromString(map.get("type"));
            double price = Double.parseDouble(map.get("price"));
            double duration = Double.parseDouble(map.get("duration"));

            // Get the origin and destination cities
            City origin = this.graph.getCity(originName);
            City destination = this.graph.getCity(destinationName);

            // Add transport edge to the graph if both cities are found
            if (origin != null && destination != null) {
                Transport transport = new Transport(origin, destination, type, price, duration);
                this.graph.addEdge(origin, transport);
            }
            return null;
        };

        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseLocations(citiesFile, addVertex);
        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }

        try {
            // Load transportation options
            parser.parseTransportation(transportFile, addEdge);
        } catch (IOException e) {
            return "Error parsing transportation file: " + transportFile;
        }

        return "Successfully loaded cities and transportation files.";
    }


    /**
     * Finds the fastest route in between two cities
     *
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the path starting from the source to the destination,
     * or empty if there is none
     */
    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        City sourceCity = this.graph.getCity(source);
        City destinationCity = this.graph.getCity(destination);
        return this.dijkstra.getShortestPath(this.graph, sourceCity, destinationCity, Transport::getMinutes);
    }

    /**
     * Finds the cheapest route in between two cities
     *
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the path starting from the source to the destination,
     * or empty if there is none
     */
    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        City sourceCity = this.graph.getCity(source);
        City destinationCity = this.graph.getCity(destination);
        return this.dijkstra.getShortestPath(this.graph, sourceCity, destinationCity, Transport::getPrice);
    }


    /**
     * Finds the most direct route in between two cities
     *
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return the path starting from the source to the destination,
     * or empty if there is none
     */
    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {
        City sourceCity = this.graph.getCity(source);
        City destinationCity = this.graph.getCity(destination);
        return this.bfs.getPath(this.graph, sourceCity, destinationCity);
    }

    /**
     * Returns the graph stored by the controller
     *
     * NOTE: You __should not__ be using this in your implementation, this is purely meant to be used for the
     * visualizer
     *
     * @return The TravelGraph currently stored in the TravelController
     */
    public TravelGraph getGraph() {
        return this.graph;
    }
}
