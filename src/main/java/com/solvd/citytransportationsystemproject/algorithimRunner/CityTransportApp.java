
package com.solvd.citytransportationsystemproject.algorithimRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import com.solvd.citytransportationsystemproject.models.Station;
import com.solvd.citytransportationsystemproject.models.StationConnection;
import com.solvd.citytransportationsystemproject.utils.Parser;

public class CityTransportApp {
    private static final Logger LOGGER = Logger.getLogger(CityTransportApp.class.getName());
    private static final int INFINITY = Integer.MAX_VALUE;
    private List<Station> stations;
    private int[][] graph;

    // The main method of the application where user input and program execution happens
    public static void main(String[] args) {
        CityTransportApp cityTransportation = new CityTransportApp();
        cityTransportation.initializeStations();
        cityTransportation.initializeGraph();
        Scanner scanner = new Scanner(System.in);
        LOGGER.info("Enter start location: ");
        String start = scanner.nextLine();
        LOGGER.info("Enter end location: ");
        String end = scanner.nextLine();
        ShortestPath shortestPath = new ShortestPath(cityTransportation.stations, cityTransportation.graph);
        List<String> path = shortestPath.findShortestPath(start, end);
        if (path.isEmpty()) {
            LOGGER.info("There is no path between " + start + " and " + end + ".");
            LOGGER.info("Would you like us to call a taxi to your location? (yes/no)");
            String response = scanner.nextLine();
            if (response.equals("yes")) {
                LOGGER.info("A taxi has been dispatched to your location.");
            } else {
                LOGGER.info("Thank you for using our transportation system.");
            }
        } else {
            LOGGER.info("The shortest path from " + start + " to " + end + " is: " + path);
        }
    }

    // Initializes the stations with their names and indices
    private void initializeStations() {
        Parser parser = new Parser();
        List<Station> stationList = parser.parseXML("stationMapper.xml");
        stations = new ArrayList<>();
        for (Station station : stationList) {
            stations.add(station);
        }
    }

    // Initializes the graph representing the connections between stations
    private void initializeGraph() {
        graph = new int[stations.size()][stations.size()];
        for (int i = 0; i < stations.size(); i++) {
            for (int j = 0; j < stations.size(); j++) {
                if (i == j) {
                    graph[i][j] = 0;
                } else {
                    Station station1 = stations.get(i);
                    Station station2 = stations.get(j);
                    int distance = getDistanceBetween(station1, station2);
                    if (distance != -1) {
                        graph[i][j] = distance;
                        StationConnection neighbor = new StationConnection(
                            getNextId(),  // use a method to get the next id
                            station1.getId(),
                            station2.getId(),
                            distance
                        );
                        station1.addStationNeighbor(neighbor);
                        // Also add the reverse neighbor to the other station
                        StationConnection reverseNeighbor = new StationConnection(
                            getNextId(),
                            station2.getId(),
                            station1.getId(),
                            distance
                        );
                        station2.addStationNeighbor(reverseNeighbor);
                    } else {
                        graph[i][j] = INFINITY;
                    }
                }
            }
        }
    }



    private long getNextId() {
		// TODO Auto-generated method stub
		return 0;
	}

	private int getDistanceBetween(Station station1, Station station2) {
        for (StationConnection neighbor : station1.getNeighbors()) {
            if (neighbor.getStation1Id() == station1.getId() && neighbor.getStation2Id() == station2.getId()) {
                return neighbor.getDistance();
            } else if (neighbor.getStation1Id() == station2.getId() && neighbor.getStation2Id() == station1.getId()) {
                return neighbor.getDistance();
            }
        }
        return -1;
    }


    // Checks if two stations are connected to each other
    private boolean isConnected(Station station1, Station station2) {
        List<Station> neighbors = station1.getNeighbors();
        for (Station neighbor : neighbors) {
            if (neighbor.getName().equals(station2.getName())) {
                return true;
            }
        }
        return false;
    }
}

/* For Reference:
  A --5-- B
  |       |
  10      4
  |       |
  E --3-- C
  |
  5
  |
  D       F
  
  
    // Initializes the graph representing the connections between stations
    private void initializeGraph() {
        graph = new Integer[][]{
        	{0, 5, INFINITY, INFINITY, 10, INFINITY}, 
        	{5, 0, 4, INFINITY, INFINITY, INFINITY},
        	{INFINITY, 4, 0, INFINITY, INFINITY, INFINITY}, 
        	{INFINITY, INFINITY, INFINITY, 0, 5, INFINITY}, 
        	{10, INFINITY, 3, 5, 0, INFINITY},
        	{INFINITY, INFINITY, INFINITY,INFINITY, INFINITY, 0}};
    }

*/