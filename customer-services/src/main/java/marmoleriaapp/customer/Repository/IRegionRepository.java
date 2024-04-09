package marmoleriaapp.customer.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import marmoleriaapp.customer.Entity.Region;

public interface IRegionRepository extends JpaRepository<Region, Long>{
    
}
