package com.solvd.citytransportationsystemproject.algorithimRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.solvd.citytransportationsystemproject.models.Station;

public class ShortestPath {
    private static final int INFINITY = Integer.MAX_VALUE;
    private List<Station> stations;
    private int[][] graph;

    public ShortestPath(List<Station> stations, int[][] graph) {
        this.stations = stations;
        this.graph = graph;
    }

    public List<String> findShortestPath(String start, String end) {
        int startIndex = getStationIndex(start);
        int endIndex = getStationIndex(end);
        int[] distances = new int[stations.size()];
        int[] previous = new int[stations.size()];
        boolean[] visited = new boolean[stations.size()];
        Arrays.fill(distances, INFINITY);
        Arrays.fill(previous, -1);
        Arrays.fill(visited, false);
        distances[startIndex] = 0;

        for (int i = 0; i < stations.size(); i++) {
            int current = getNextStation(distances, visited);
            if (current == -1) {
                break;
            }
            visited[current] = true;
            for (int j = 0; j < stations.size(); j++) {
                if (!visited[j] && graph[current][j] != INFINITY) {
                    int newDistance = distances[current] + graph[current][j];
                    if (newDistance < distances[j]) {
                        distances[j] = newDistance;
                        previous[j] = current;
                    }
                }
            }
        }

        List<String> path = new ArrayList<>();
        int current = endIndex;
        while (current != startIndex) {
            path.add(0, stations.get(current).getName());
            current = previous[current];
        }
        path.add(0, start);
        return path;
    }

    private int getNextStation(int[] distances, boolean[] visited) {
        int current = -1;
        int min = INFINITY;
        for (int i = 0; i < stations.size(); i++) {
            if (!visited[i] && distances[i] < min) {
                current = i;
                min = distances[i];
            }
        }
        return current;
    }

    private int getStationIndex(String name) {
        for (int i = 0; i < stations.size(); i++) {
            if (stations.get(i).getName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
}
