package it.epicode.energiapp.services;

import it.epicode.energiapp.entities.Invoice;
import it.epicode.energiapp.entities.enumEntities.InvoiceStatus;
import it.epicode.energiapp.exceptions.NotFoundException;
import it.epicode.energiapp.repositories.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    // GET all
    public Page<Invoice> findAllInvoices(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    // GET id
    public Invoice findInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice not found with id: " + id));
    }

    public Page<Invoice> findInvoicesByClient(Long clientId, Pageable pageable) {
        return invoiceRepository.findByClient_Id(clientId, pageable);
    }

    public Page<Invoice> findInvoicesByStatus(InvoiceStatus status, Pageable pageable) {
        return invoiceRepository.findByStatus(status, pageable);
    }

    public Page<Invoice> findInvoicesByDate(LocalDate date, Pageable pageable) {
        return invoiceRepository.findByDate(date, pageable);
    }

    public Page<Invoice> findInvoicesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return invoiceRepository.findByDateBetween(startDate, endDate, pageable);
    }

    public Page<Invoice> findInvoicesByAmountRange(BigDecimal minAmount, BigDecimal maxAmount, Pageable pageable) {
        return invoiceRepository.findByAmountBetween(minAmount, maxAmount, pageable);
    }


    // POST
    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    //PUT
    public Invoice updateInvoice(Long id, Invoice updatedInvoice) {
        if (!invoiceRepository.existsById(id)) {
            throw new NotFoundException("Invoice not found with id: " + id);
        }

        updatedInvoice.setId(id); // Assicura che l'ID dell'invoice sia lo stesso di quello fornito

        return invoiceRepository.save(updatedInvoice);
    }

    // DELETE
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new NotFoundException("Invoice not found with id: " + id);
        }
        invoiceRepository.deleteById(id);
    }
}
