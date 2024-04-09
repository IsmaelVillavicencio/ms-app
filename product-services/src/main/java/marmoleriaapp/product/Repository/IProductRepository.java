package marmoleriaapp.product.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import marmoleriaapp.product.Entity.Category;
import marmoleriaapp.product.Entity.Product;
import java.util.List;


public interface IProductRepository extends JpaRepository<Product, Long>{
    public List<Product> findByCategory(Category category);
    public Product findByName(String name);
    public Product findByTransactionId(String transactionId);
}
