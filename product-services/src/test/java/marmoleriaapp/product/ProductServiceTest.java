package marmoleriaapp.product;

import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import marmoleriaapp.product.Entity.Category;
import marmoleriaapp.product.Entity.Product;
import marmoleriaapp.product.Implements.ProductImplementService;
import marmoleriaapp.product.Repository.IProductRepository;
import marmoleriaapp.product.Service.ProductService;
import marmoleriaapp.product.dto.DtoProduct;

@SpringBootTest
public class ProductServiceTest {

    @Mock //Un mock es un objeto simulado que se utiliza para probar el comportamiento de otros objetos.
    private IProductRepository iProductRepository;

    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductImplementService(iProductRepository);
        Product product = Product.builder()
            .id(1L)
            .name("Marmol")
            .description("Marmol de Carrara")
            .price(1000.0)
            .image("marmol.jpg")
            .stock(10)
            .status(true)
            .category(
                Category.builder()
                    .id(1L)
                    .name("Marmol")
                    .description("Marmol de Carrara")
                    .build()
            )
            .build();
        
        String idempotencyKey = UUID.randomUUID().toString();


        productService.createProduct( idempotencyKey, product);

        Mockito.when(iProductRepository.findById(1L))
            .thenReturn(Optional.of(product));

        Mockito.when(iProductRepository.save(product)).thenReturn(product); //Cuando se llame al método save, se devolverá el objeto product actualizado
    }

    @Test
    public void whenValidId_thenProductShouldBeFound() {
        Long id = 1L;
        DtoProduct found = productService.getProduct(id);

        //Assertions.assertThat(found.getId()).isEqualTo(id);

        // by name
        Assertions.assertThat(found.getName()).isEqualTo("Marmol");
    }

    @Test //prueba para actualizar el stock del producto
    public void whenValidId_thenProductShouldBeUpdated() {
        DtoProduct found = productService.updateStock(1L, 20);
        Assertions.assertThat(found.getStock()).isEqualTo(30);
    }
}