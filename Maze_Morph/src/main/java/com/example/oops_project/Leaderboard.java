package com.example.oops_project;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Leaderboard {

    public static class Entry {
        String name;
        int score;

        public Entry(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String getName() { return name; }
        public int getScore() { return score; }
    }

    private static final String FILE_NAME = "leaderboard.txt";
    private static ArrayList<Entry> scores = new ArrayList<>();

    // Load scores from file (run once at game startup)
    public static void loadScores() {
        scores.clear(); // Start fresh
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    scores.add(new Entry(name, score));
                }
            }
        } catch (IOException e) {
            System.out.println("Leaderboard file not found. A new one will be created.");
        }
    }

    private static void saveScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Entry e : scores) {
                writer.write(e.getName() + "," + e.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing leaderboard file: " + e.getMessage());
        }
    }

    // Add a new score and update file
    public static void addScore(String name, int score) {
        scores.add(new Entry(name, score));
        scores.sort(Comparator.comparingInt(Entry::getScore).reversed());
        if (scores.size() > 10) {
            scores = new ArrayList<>(scores.subList(0, 10));
        }
        saveScores();
    }

    public static ArrayList<Entry> getTopScores() {
        return scores;
    }
}
