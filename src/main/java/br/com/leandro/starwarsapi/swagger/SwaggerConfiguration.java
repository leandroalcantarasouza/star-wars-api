package br.com.leandro.starwarsapi.swagger;

import br.com.leandro.starwarsapi.info.InfoConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Autowired
    private InfoConfigurationProperties infoConfigurationProperties;

    @Bean
    public Docket petApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.leandro.starwarsapi"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false);
    }

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("Documentacao da api de star wars v1")
                .version(infoConfigurationProperties.getVersion())
                .description("Gerado em " + infoConfigurationProperties.getDateFormatted())
                .build();
    }

}
