package it.epicode.energiapp.entities;

import it.epicode.energiapp.entities.enumEntities.InvoiceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="invoices")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix="with")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private BigDecimal amount;

    @ManyToOne
    private Client client;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;
}
