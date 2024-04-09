package marmoleriaapp.Invoice.Entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Positive;
import jakarta.persistence.Index;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Table(name = "invoice_details", indexes = {
    @Index(name = "idx_invoice_detail_id", columnList = "id"),
    @Index(name = "idx_invoice_detail_product_id", columnList = "product_id"),
 })
 @Data
 @Entity
 @EqualsAndHashCode(callSuper = true)
public class InvoiceDetail extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive(message = "La cantidad debe ser mayor a 0")
    private double quantity;
    private double price;
    private Long productId;
    @Transient
    private double subTotal;

    public double getSubTotal() {
        if (this.price > 0 && this.quantity > 0 ) {
            //retornar el subtotal con dos decimales
            return Math.round(this.price * this.quantity * 100.0) / 100.0;
            //return this.price * this.quantity;
        }
        return 0;
    }
    
}
