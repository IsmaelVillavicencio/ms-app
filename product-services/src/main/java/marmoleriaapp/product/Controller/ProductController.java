package marmoleriaapp.product.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.Valid;
import marmoleriaapp.product.Entity.Category;
import marmoleriaapp.product.Entity.Product;
import marmoleriaapp.product.Exception.ResourceNotFoundException;
import marmoleriaapp.product.Service.ProductService;
import marmoleriaapp.product.dto.DtoApiResponse;
import marmoleriaapp.product.dto.DtoFieldResponseError;
import marmoleriaapp.product.dto.DtoProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PutMapping;



@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<?> listAllProduct(
            @RequestParam(name = "categoryId", required = false) Long categoryId) {
        List<DtoProduct> products = new ArrayList<>();
        if (categoryId == null) {
            products = productService.listAllProduct();
        } else {
            products = productService.findByCategory(Category.builder().id(categoryId).build());
        }
        return buildResponse(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable(name = "id") Long id) {
        
        DtoProduct product = productService.getProduct(id);
        if (product == null) {
            throw new ResourceNotFoundException("No se encontro el recurso con el id: " + id);
        }
        return buildResponse(product);
    }

    @PostMapping()
    public ResponseEntity<?> createProduct(@Valid @RequestHeader("transactionId") String transactionId, @Valid @RequestBody Product product, BindingResult result){
   
        if (result.hasErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, getErrorMessage(result));
        }
       DtoProduct newProduct = productService.createProduct(transactionId, product);
        return buildResponse(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editProduct(@PathVariable String id, @RequestBody Product productToUpdate) {
        productToUpdate.setId(Long.parseLong(id));
        Product product = productService.getOriginalProduct(productToUpdate.getId());
        if (product == null) {
            throw new ResourceNotFoundException("No se encontro el recurso con el id: " + id);
        }
        DtoProduct updatedProduct = productService.updateProduct(productToUpdate);
        return buildResponse(updatedProduct);
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<?> updateStock(@PathVariable String id, @RequestParam(name = "quantity") Integer quantity) {
        Product product = productService.getOriginalProduct(Long.parseLong(id));
        if (product == null) {
            throw new ResourceNotFoundException("No se encontro el recurso con el id: " + id);
        }
        DtoProduct updatedProduct = productService.updateStock(Long.parseLong(id), quantity);
        return buildResponse(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        Product product = productService.getOriginalProduct(Long.parseLong(id));
        if (product == null) {
            throw new ResourceNotFoundException("No se encontro el recurso con el id: " + id);
        }
        productService.deleteProduct(Long.parseLong(id));
        DtoApiResponse<String> apiResponse = new DtoApiResponse<>("success", "Producto eliminado");
        return ResponseEntity.ok(apiResponse);
    }

    private ResponseEntity<DtoApiResponse<?>> buildResponse(Object data) {
        return ResponseEntity.ok(new DtoApiResponse<>("success", data));
    }

    private String getErrorMessage(BindingResult result) {
        ObjectMapper mapper = new ObjectMapper();
        List<DtoFieldResponseError> fieldErrors = result.getFieldErrors().stream()
                .map(error -> new DtoFieldResponseError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        try {
            return mapper.writeValueAsString(fieldErrors);
        } catch (Exception e) {
            e.printStackTrace();
            return ""; 
        }
    }

}