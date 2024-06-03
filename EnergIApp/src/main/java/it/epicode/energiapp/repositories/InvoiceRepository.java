package it.epicode.energiapp.repositories;

import it.epicode.energiapp.entities.Invoice;
import it.epicode.energiapp.entities.enumEntities.InvoiceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    Page<Invoice> findByClient_Id(Long clientId, Pageable pageable);
    Page<Invoice> findByStatus(InvoiceStatus status, Pageable pageable);
    Page<Invoice> findByDate(LocalDate date, Pageable pageable);
    Page<Invoice> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
    Page<Invoice> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable);
}
