package marmoleriaapp.Invoice.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import marmoleriaapp.Invoice.Entity.Invoice;

public interface IInvoiceRepository extends JpaRepository<Invoice, Long>{
    public List<Invoice> findByCustomerId(Long customerId);
    public Invoice findByInvoiceNumber(String invoiceNumber);
    public Invoice findByTransactionId(String transactionId);
}
