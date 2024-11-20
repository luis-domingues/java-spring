package br.com.alura.screenmatch;

import br.com.alura.screenmatch.service.OmdbApiConsumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		var apiConsumer = new OmdbApiConsumer();
		var json = apiConsumer.fetchData("https://www.omdbapi.com/?t=the+last+of+us&season=1&apikey=d1a73fd4");
		System.out.println(json);
	}
}
