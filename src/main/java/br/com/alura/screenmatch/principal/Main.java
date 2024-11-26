package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DataSeason;
import br.com.alura.screenmatch.model.DataSeries;
import br.com.alura.screenmatch.service.DataConverter;
import br.com.alura.screenmatch.service.OmdbApiConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private OmdbApiConsumer omdbApiConsumer = new OmdbApiConsumer();
    private DataConverter converter = new DataConverter();
    private final String BASE_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=d1a73fd4";
    private List<DataSeries> dataSeriesList = new ArrayList<>();

    public void displayMenu() {
        var option = -1;
        while (option != 0) {
            var menu = """
                    1 - search for series
                    2 - search for episodes
                                
                    0 - exit
                    """;

            System.out.println(menu);
            scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    searchSeries();
                    break;
                case 2:
                    searchEpisodeBySeries();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private void searchSeries() {
        DataSeries dataSeries = getDataSeries();
        System.out.println(dataSeries);
    }

    private DataSeries getDataSeries() {
        System.out.print("Enter the series name to search: ");
        var seriesName = scanner.nextLine();
        var json = omdbApiConsumer.fetchData(BASE_URL + seriesName.replace(" ", "+" + API_KEY));
        DataSeries dataSeries = converter.convertData(json, DataSeries.class);
        return dataSeries;
    }

    private void searchEpisodeBySeries() {
        DataSeries dataSeries = getDataSeries();
        List<DataSeason> dataSeasonList = new ArrayList<>();

        for (int i = 1; i <= dataSeries.totalSeasons(); i++) {
            var json = omdbApiConsumer.fetchData(BASE_URL + dataSeries.title().replace(" ", "+") + "&season=" + i + API_KEY);
            DataSeason dataSeason = converter.convertData(json, DataSeason.class);
            dataSeasonList.add(dataSeason);
        }

        dataSeasonList.forEach(System.out::println);
    }
}