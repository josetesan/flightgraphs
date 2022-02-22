package com.edreamsodigeo.travelling.salesman.service;

import com.edreamsodigeo.travellingsalesman.model.Flight;
import com.edreamsodigeo.travellingsalesman.model.FlightDetails;
import com.edreamsodigeo.travellingsalesman.store.FlightStore;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class RouteCalculator {

    @Inject
    private FlightStore flightStore;

    public RouteCalculator(FlightStore flightStore) {
        this.flightStore = flightStore;
    }

    public List<Flight> calculate(String originCity, String destinationCity) {

        List<Flight> originFlights = flightStore.getFlights(originCity,destinationCity);
        var simpleDirectedWeightedGraph = GraphCreator.from(originFlights);
        printGraph(simpleDirectedWeightedGraph);
        var dijkstraShortestPath = new DijkstraShortestPath<>(simpleDirectedWeightedGraph);
        var path  = dijkstraShortestPath.getPath(originCity,destinationCity);
        return GraphCreator.toFlightList(path);

    }

    private void printGraph(SimpleDirectedWeightedGraph graph) {
        JGraphXAdapter<String, FlightDetails> graphAdapter =
                new JGraphXAdapter<String, FlightDetails>(graph);
        mxIGraphLayout layout = new mxHierarchicalLayout(graphAdapter);
        layout.execute(graphAdapter.getDefaultParent());

        BufferedImage image =
                mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        File imgFile = new File("src/test/resources/graph.png");
        try {
            ImageIO.write(image, "PNG", imgFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
