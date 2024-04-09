package marmoleriaapp.product.Service;

import java.util.List;

import marmoleriaapp.product.Entity.Category;
import marmoleriaapp.product.Entity.Product;
import marmoleriaapp.product.dto.DtoProduct;

public interface ProductService {

    public List<DtoProduct> listAllProduct();
    public DtoProduct getProduct(Long id);
    public DtoProduct createProduct(String transactionId, Product product);
    public DtoProduct updateProduct(Product product);
    public Product deleteProduct(Long id);
    public Product getOriginalProduct(Long id);
    public List<DtoProduct> findByCategory(Category category);
    public DtoProduct updateStock(Long id, Integer quantity);
}
