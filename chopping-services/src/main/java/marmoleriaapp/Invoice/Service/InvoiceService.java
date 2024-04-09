package marmoleriaapp.Invoice.Service;

import java.util.List;

import marmoleriaapp.Invoice.Dto.DtoInvoice;
import marmoleriaapp.Invoice.Entity.Invoice;

public interface InvoiceService {

    public List<DtoInvoice> findInvoiceAll();
    public DtoInvoice createInvoice(String transactionId, Invoice invoice);
    public Invoice updateInvoice(Invoice invoice);
    public Invoice deleteInvoice(Invoice invoice);
    public Invoice getOriginalInvoice(Long id);
    public DtoInvoice getInvoice(Long id);
}
