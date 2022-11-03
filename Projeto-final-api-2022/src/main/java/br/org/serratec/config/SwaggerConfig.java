package br.org.serratec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.org.serratec.model.Categoria;
import br.org.serratec.model.Cliente;
import br.org.serratec.model.Endereco;
import br.org.serratec.model.ItemPedido;
import br.org.serratec.model.Pedido;
import br.org.serratec.model.Produto;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("br.org.serratec.controller")).paths(PathSelectors.any())
				.build().ignoredParameterTypes(Cliente.class, Endereco.class, Categoria.class, ItemPedido.class,
						Pedido.class, Produto.class)
				.apiInfo(apiInfo());
	}

	public ApiInfo apiInfo() {
		ApiInfo apiInfo = new ApiInfoBuilder().title("API DO SERRATEC")
				.description("Essa API foi desenvolvida pelos alunos Anderson, Graziela, Priscila, Quézia e Vinícius")
				.license("Apache License 2.0").licenseUrl("http://www.apache.org/license")
				.termsOfServiceUrl("/service.html").version("1.0.1")
				.contact(new Contact("Serratec", "www.serratec.org", "serratec@serratec.org")).build();
		return apiInfo;
	}

}