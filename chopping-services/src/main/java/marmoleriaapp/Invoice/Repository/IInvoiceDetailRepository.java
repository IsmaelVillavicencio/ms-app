package marmoleriaapp.Invoice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import marmoleriaapp.Invoice.Entity.InvoiceDetail;

public interface IInvoiceDetailRepository extends JpaRepository<InvoiceDetail, Long>{
    
}
