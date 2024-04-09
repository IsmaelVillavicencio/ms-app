package marmoleriaapp.product;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


//import java.util.logging.Logger;

@SpringBootApplication
public class ProductServicesApplication {

	//private static final Logger logger = Logger.getLogger(ProductServicesApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(ProductServicesApplication.class, args);
		//logger.info("Este es un mensaje de INFORMACIÓN");
		//logger.warning("Este es un mensaje de ADVERTENCIA");
		//logger.severe("Este es un mensaje SEVERO");
	}

	@Bean
    public ModelMapper modelMapper() {
		return new ModelMapper();
    }
	

}
