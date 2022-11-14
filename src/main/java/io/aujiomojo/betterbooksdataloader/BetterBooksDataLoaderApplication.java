package io.aujiomojo.betterbooksdataloader;

import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import connection.DataStaxAstraProperties;
import io.aujiomojo.betterbooksdataloader.author.Author;
import io.aujiomojo.betterbooksdataloader.author.AuthorRepository;

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class BetterBooksDataLoaderApplication {

	@Autowired
	private AuthorRepository authorRepository;

	public static void main(String[] args) {
		SpringApplication.run(BetterBooksDataLoaderApplication.class, args);
	}


	@PostConstruct
	public void start() {
		System.out.println("Application Started");
		Author author = new Author();
		author.setId("id");
		author.setName("Name");
		author.setPersonalName("personalName");
		authorRepository.save(author);
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties properties) {
		Path bundle = properties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}

}
