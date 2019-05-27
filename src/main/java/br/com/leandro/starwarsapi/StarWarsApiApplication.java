package br.com.leandro.starwarsapi;

import br.com.leandro.starwarsapi.info.InfoConfigurationProperties;
import br.com.leandro.starwarsapi.swagger.SwaggerConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@SpringBootApplication
@Import(SwaggerConfiguration.class)
@EnableConfigurationProperties(InfoConfigurationProperties.class)
public class StarWarsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarWarsApiApplication.class, args);
	}

	@Bean
	public Validator validator() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		return factory.getValidator();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
	}

}
