package marmoleriaapp.Invoice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import marmoleriaapp.Invoice.Dto.DtoApiResponse;
import marmoleriaapp.Invoice.Dto.DtoInvoice;
import marmoleriaapp.Invoice.Entity.Invoice;
import marmoleriaapp.Invoice.Exception.ResourceNotFoundException;
import marmoleriaapp.Invoice.Service.InvoiceService;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<?> getInvoices() {
        return buildResponse(invoiceService.findInvoiceAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoice(@PathVariable(name = "id") Long id) {
        DtoInvoice invoice = invoiceService.getInvoice(id);
        if (invoice == null) {
            throw new ResourceNotFoundException("No se encontro el recurso con el id: " + id);
        }
        return buildResponse(invoice);
    }

    @PostMapping
    public ResponseEntity<?> createInvoice(@Valid @RequestHeader("transactionId") String transactionId, @Valid @RequestBody Invoice invoice, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResourceNotFoundException("Error en la validacion de los datos");
        }
        return buildResponse(invoiceService.createInvoice(transactionId, invoice));
    }

    private ResponseEntity<DtoApiResponse<?>> buildResponse(Object data) {
        return ResponseEntity.ok(new DtoApiResponse<>("success", data));
    }
}
