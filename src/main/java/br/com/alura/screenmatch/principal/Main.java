package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.model.DataEpisode;
import br.com.alura.screenmatch.model.DataSeason;
import br.com.alura.screenmatch.model.DataSeries;
import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.service.DataConverter;
import br.com.alura.screenmatch.service.OmdbApiConsumer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private Scanner scanner = new Scanner(System.in);
    private OmdbApiConsumer omdbApiConsumer = new OmdbApiConsumer();
    private DataConverter converter = new DataConverter();
    private final String BASE_URL = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=d1a73fd4";

    public void displayMenu() {
        System.out.print("Search for a series: ");
        var seriesName = scanner.nextLine();
        var json = omdbApiConsumer.fetchData(BASE_URL + seriesName.replace(" ", "+") + API_KEY);
        DataSeries data = converter.convertData(json, DataSeries.class);
        System.out.println(data);
		List<DataSeason> seasonsList = new ArrayList<>();

		for(int i = 1; i <= data.totalSeasons(); i++){
			json = omdbApiConsumer.fetchData(BASE_URL + seriesName.replace(" ", "+") + "&season=" + i + API_KEY);
			DataSeason dataSeason = converter.convertData(json, DataSeason.class);
			seasonsList.add(dataSeason);
		}
		seasonsList.forEach(System.out::println);

        seasonsList.forEach(s -> s.episodeList().forEach(e -> System.out.println(e.title())));

        List<DataEpisode> episodes = seasonsList.stream()
                .flatMap(s -> s.episodeList().stream())
                .collect(Collectors.toList());

        System.out.println("\nTop 5 Episodes 🔥");
        episodes.stream()
                .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DataEpisode::rating).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episode> episodeList = seasonsList.stream()
                .flatMap(s -> s.episodeList().stream()
                        .map(d -> new Episode(s.seasonNumber(), d))
                ).collect(Collectors.toList());

        episodeList.forEach(System.out::println);

        System.out.println("From which year would you like to see the episodes?");
        var year = scanner.nextInt();
        scanner.nextLine();

        LocalDate searchDate  = LocalDate.of(year, 1, 1);
        DateTimeFormatter dateFormatter  = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodeList.stream()
                .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate ))
                .forEach(e -> System.out.println(
                        "Temporada: " + e.getSeason() + ", episódio " + e.getEpisodeNumber() + " - Data de lançamento: " + e.getReleaseDate().format(dateFormatter)
                ));
    }
}