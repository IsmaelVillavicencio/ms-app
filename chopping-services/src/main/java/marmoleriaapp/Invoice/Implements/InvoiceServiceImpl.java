package marmoleriaapp.Invoice.Implements;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import marmoleriaapp.Invoice.Dto.DtoInvoice;
//import lombok.extern.slf4j.Slf4j;
import marmoleriaapp.Invoice.Entity.Invoice;
import marmoleriaapp.Invoice.Repository.IInvoiceRepository;
import marmoleriaapp.Invoice.Service.InvoiceService;

@Service
//@Slf4j
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final IInvoiceRepository iInvoiceRepository;

    @Autowired()
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DtoInvoice> findInvoiceAll() {
       List<Invoice> invoices = iInvoiceRepository.findAll();
       return invoices.stream().map(this::convertToDto)
                       .collect(Collectors.toList());
    }

    @Override
    @Transactional()
    public DtoInvoice createInvoice(String transactionId, Invoice invoice) {
       Invoice invoiceCreated = iInvoiceRepository.findByTransactionId(transactionId);
       if (invoiceCreated != null) {
           return convertToDto(invoiceCreated);
       }
        invoice.setTransactionId(transactionId);
        iInvoiceRepository.save(invoice);
        return convertToDto(invoice);
    }

    @Override
    public Invoice updateInvoice(Invoice invoice) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateInvoice'");
    }

    @Override
    public Invoice deleteInvoice(Invoice invoice) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteInvoice'");
    }

    @Override
    public DtoInvoice getInvoice(Long id) {
        Invoice invoice = this.getOriginalInvoice(id);
        if (invoice == null) {
            return null;
        }
        return convertToDto(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public Invoice getOriginalInvoice(Long id) {
        return iInvoiceRepository.findById(id).orElse(null);
    }

    private DtoInvoice convertToDto(Invoice invoice) {
        DtoInvoice dtoInvoice = modelMapper.map(invoice, DtoInvoice.class);
        return dtoInvoice;
    }
    
}
