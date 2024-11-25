package br.com.alura.screenmatch.principal;

import br.com.alura.screenmatch.service.OmdbApiConsumer;

import java.util.Scanner;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private OmdbApiConsumer omdbApiConsumer = new OmdbApiConsumer();
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=d1a73fd4";

    public void exibeMenu(){
        System.out.print("Buscar série: ");
        var nomeSerie = scanner.nextLine();
        var json = omdbApiConsumer.fetchData(ENDERECO + nomeSerie.replace(" ", "+") + API_KEY);
    }
}
