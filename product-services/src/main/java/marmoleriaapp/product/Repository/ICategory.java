package marmoleriaapp.product.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import marmoleriaapp.product.Entity.Category;

public interface ICategory extends JpaRepository<Category, Long>{
    
}
