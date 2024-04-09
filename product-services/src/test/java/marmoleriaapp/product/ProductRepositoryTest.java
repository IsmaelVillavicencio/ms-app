package marmoleriaapp.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import marmoleriaapp.product.Entity.Product;
import marmoleriaapp.product.Entity.Category;
import marmoleriaapp.product.Repository.ICategory;
import marmoleriaapp.product.Repository.IProductRepository;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)// Para que no reemplace la base de datos y se conecte a la base de datos de desarrollo
public class ProductRepositoryTest {
    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ICategory categoryRepository;

    @Test
    public void whenFindByCategory_thenReturnProduct() {

        // given
        // Se debe crear una categoria para poder crear un producto o buscar por el id de una categoria existente
        // Category category = categoryRepository.findById(1L).orElse(null); // Si se desea buscar por id
    
        Category category = Category.builder().id(2L).name(null).description(null).build();
        categoryRepository.save(category);

        Product product = Product.builder()
        .name("Mesa")
        .description("Mesa de marmol")
        .price(100.0)
        .image("mesa.jpg")
        .stock(10)
        .status(true)
        .category(
            category
        )
        .build();

        productRepository.save(product);

        // when
        List<Product> found = productRepository.findByCategory(product.getCategory());

        // then
        Assertions.assertThat(found.size()).isEqualTo(1);
    }
    
}
