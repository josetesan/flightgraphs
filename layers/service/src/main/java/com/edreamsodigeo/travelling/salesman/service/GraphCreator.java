package com.edreamsodigeo.travelling.salesman.service;

import com.edreamsodigeo.travellingsalesman.model.Flight;
import com.edreamsodigeo.travellingsalesman.model.FlightImplementation;
import com.edreamsodigeo.travellingsalesman.model.Weight;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.ArrayList;
import java.util.List;

public class GraphCreator {

    /**
     *
     * @param flights the list of flights
     * @return a graph with String ( city name ) as vertes, and edges as Weight
     *
     *  Vertex --  Edge -->  Vertex
     *    BCN  --   W1  -->   MAD
     *
     */
    public static SimpleDirectedWeightedGraph from(List<Flight> flights) {

        SimpleDirectedWeightedGraph<String,Weight> simpleDirectedWeightedGraph = new SimpleDirectedWeightedGraph<>(Weight.class);


        for (Flight flight : flights) {

            if (!simpleDirectedWeightedGraph.containsVertex(flight.getSource())) {
                simpleDirectedWeightedGraph.addVertex(flight.getSource());
            }

            if (!simpleDirectedWeightedGraph.containsVertex(flight.getDestination())) {
                simpleDirectedWeightedGraph.addVertex(flight.getDestination());
            }

            simpleDirectedWeightedGraph.addEdge(flight.getSource(),
                                                flight.getDestination(),
                                                new Weight(flight.getPrice(),flight.getDepartureTime(),flight.getArrivalTime()));

        }

        return simpleDirectedWeightedGraph;

    }

    public static List<Flight> toFlightList(GraphPath<String,Weight> path) {
        List<Flight> flightList = new ArrayList<>(path.getEdgeList().size());

        // vertex size is always going to be edges_size + 1
        // A -> B -> C, 3 vertices, 2 edges

        var vertices = path.getVertexList();
        var edges = path.getEdgeList();

        int i = 0,j = 0;
        while (i < vertices.size() && j < edges.size()) {

            flightList.add(new FlightImplementation(
                    vertices.get(i),  // source
                    vertices.get(i+1), // destination
                    edges.get(j).getPrice(), // price
                    edges.get(j).getDepartureTime(), // departure time
                    edges.get(j).getArrivalTime() // arrival time
            ));

            i++;
            j++;

        }


        return flightList;
    }
}
