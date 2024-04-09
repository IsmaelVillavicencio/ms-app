package marmoleriaapp.Invoice.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.persistence.Index;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "invoices", indexes = {
    @Index(name = "idx_invoice_id", columnList = "id"),
    @Index(name = "idx_invoice_customer_id", columnList = "customer_id"),
    @Index(name = "idx_invoice_status", columnList = "status"),
})
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Invoice extends BaseEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoiceNumber;
    private String description;
    private Long customerId;
    private Boolean status;
    private String transactionId;

    @Valid
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "invoice_id")
    private List<InvoiceDetail> details;

    public Invoice(){
        details = new ArrayList<>();
    }
}
