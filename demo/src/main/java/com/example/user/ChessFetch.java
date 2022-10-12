package com.example.user;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ChessFetch {

    private final String BASE_API_URL = "https://api.chess.com/pub/player/";
    private final String BASE_FETCH_URL;
    private final String username;
    private HashMap<int[], MonthlyGames> monthlyGamesMap;

    ChessFetch(String username) {
        this.username = username;
        this.BASE_FETCH_URL = BASE_API_URL + this.username;
        this.monthlyGamesMap = new HashMap<int[], MonthlyGames>();
    }

    public class MonthlyGames {
        private ArrayList<ChessGame> games;

        MonthlyGames(ArrayList<ChessGame> games) {
            this.games = games;
        }

        public class PlayerResult {
            private long rating;
            private String result;
            private String username;

            PlayerResult(long rating, String result, String username) {
                this.rating = rating;
                this.result = result;
                this.username = username;
            }
        }

        public class ChessGame {
            private long end_time;
            private String rules;
            private String time_class;
            private String time_control;
            private PlayerResult white;
            private PlayerResult black;

            ChessGame(long end_time, String rules, String time_class, String time_control, PlayerResult white, PlayerResult black) {
                this.end_time = end_time;
                this.rules = rules;
                this.time_class = time_class;
                this.time_control = time_control;
                this.white = white;
                this.black = black;
            }
        }
    }

    private static String formFetchUrl(String baseUrl, int year, int month) {
        String monthString = month > 10 ? Integer.toString(month) : String.format("0%d", month);
        String stringUrl = String.format("%s/games/%d/%s", baseUrl, year, monthString);
        return stringUrl;
    }

    void fetchGamesFromMonth(int year, int month) {
        int[] monthKey = {year, month};
        String stringUrl = formFetchUrl(BASE_FETCH_URL, year, month);
        try {
            URL url = new URL(stringUrl);
            MonthlyGames monthlyGames = Fetch.getGjsonClassResponse(url, MonthlyGames.class);
            this.monthlyGamesMap.put(monthKey, monthlyGames);
        } catch (MalformedURLException e) {
            System.out.printf("Unable to form url: %s", stringUrl);
        } catch (IOException e) {
            System.out.printf("Unable to fetch games: %s", stringUrl);
        }
    }

    public static void main(String... args) {
        ChessFetch chessUser = new ChessFetch("steakbacon");
        chessUser.fetchGamesFromMonth(2019, 1);
    }
}
