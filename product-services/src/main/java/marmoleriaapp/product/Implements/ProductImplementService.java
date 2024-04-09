package marmoleriaapp.product.Implements;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import marmoleriaapp.product.Entity.Category;
import marmoleriaapp.product.Entity.Product;
import marmoleriaapp.product.Repository.IProductRepository;
import marmoleriaapp.product.Service.ProductService;
import marmoleriaapp.product.dto.DtoProduct;

@Service
@RequiredArgsConstructor
public class ProductImplementService implements ProductService {


    private final IProductRepository iProductService;

    @Autowired()
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DtoProduct> listAllProduct() {
        List<Product> products = iProductService.findAll();
        return products.stream()
                       .map(this::convertToDto)
                       .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DtoProduct getProduct(Long id) {
        Product product = this.getOriginalProduct(id);
        if (product == null) {
            return null;
        }
        return convertToDto(product);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getOriginalProduct(Long id) {
        return iProductService.findById(id).orElse(null);
    }

    @Override
    @Transactional()
    public DtoProduct createProduct(String transactionId, Product product) {

        Product productExist = iProductService.findByTransactionId(transactionId);
        if (productExist != null) {
            return convertToDto(productExist);
        }

        product.setName(product.getName());
        product.setDescription(product.getDescription());
        product.setPrice(product.getPrice());
        product.setImage(product.getImage());
        product.setStock(product.getStock());
        product.setStatus(product.getStatus());
        product.setCategory(product.getCategory());
        product.setTransactionId(transactionId);
        iProductService.save(product);
        return convertToDto(product);
    }

    @Override
    public DtoProduct updateProduct(Product product) {
        Product productUpdate = this.getOriginalProduct(product.getId());

        if (productUpdate == null) {
            return null;
        }

        productUpdate.setName(product.getName());
        productUpdate.setDescription(product.getDescription());
        productUpdate.setPrice(product.getPrice());
        productUpdate.setImage(product.getImage());
        productUpdate.setStock(product.getStock());
        productUpdate.setStatus(product.getStatus());
        productUpdate.setCategory(product.getCategory());
        iProductService.save(productUpdate);
        return convertToDto(productUpdate);
    }

    @Override
    public Product deleteProduct(Long id) {
        Product productDelete = this.getOriginalProduct(id);

        if (productDelete == null) {
            return null;
        }
        productDelete.setStatus(false);
        return iProductService.save(productDelete);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DtoProduct> findByCategory(Category category) {
        List<Product> products = iProductService.findByCategory(category);
        return products.stream()
                       .map(this::convertToDto)
                       .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DtoProduct updateStock(Long id, Integer quantity) {
        Product productUpdate = this.getOriginalProduct(id);
        
        if (productUpdate == null) {
            return null;
        }

        Integer stock = productUpdate.getStock() + quantity;
        productUpdate.setStock(stock);
        iProductService.save(productUpdate);
        return convertToDto(productUpdate);
    }

    private DtoProduct convertToDto(Product product) {
        DtoProduct dtoProduct = modelMapper.map(product, DtoProduct.class);
        return dtoProduct;
    }
    
}
