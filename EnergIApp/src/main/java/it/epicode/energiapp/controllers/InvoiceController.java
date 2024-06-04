package it.epicode.energiapp.controllers;

import it.epicode.energiapp.entities.Address;
import it.epicode.energiapp.entities.Invoice;
import it.epicode.energiapp.entities.enumEntities.InvoiceStatus;
import it.epicode.energiapp.exceptions.NoContentException;
import it.epicode.energiapp.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // GET http://localhost:8080/api/invoices

    @GetMapping
    public ResponseEntity<Page<Invoice>> getAllInvoices(Pageable pageable) {
        Page<Invoice> invoices = invoiceService.findAllInvoices(pageable);
        if (invoices.isEmpty()) {
            throw new NoContentException("No invoices were found.");
        } else {
            ResponseEntity<Page<Invoice>> responseEntity = new ResponseEntity<>(invoices, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET http://localhost:8080/api/invoices/{id}

    @GetMapping("/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.findInvoiceById(id));
    }

    // GET http://localhost:8080/api/invoices/client/{clientId}

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Page<Invoice>> getInvoicesByClient(@PathVariable Long clientId, Pageable pageable) {
        return ResponseEntity.ok(invoiceService.findInvoicesByClient(clientId, pageable));
    }

    // GET http://localhost:8080/api/invoices/status/{status}

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<Invoice>> getInvoicesByStatus(@PathVariable InvoiceStatus status, Pageable pageable) {
        return ResponseEntity.ok(invoiceService.findInvoicesByStatus(status, pageable));
    }

    // GET http://localhost:8080/api/invoices/date/{date}

    @GetMapping("/date/{date}")
    public ResponseEntity<Page<Invoice>> getInvoicesByDate(@PathVariable LocalDate date, Pageable pageable) {
        return ResponseEntity.ok(invoiceService.findInvoicesByDate(date, pageable));
    }

    // GET http://localhost:8080/api/invoices/date-range?startDate={startDate}&endDate={endDate}

    @GetMapping("/date-range")
    public ResponseEntity<Page<Invoice>> getInvoicesByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate, Pageable pageable) {
        return ResponseEntity.ok(invoiceService.findInvoicesByDateRange(startDate, endDate, pageable));
    }

    // GET http://localhost:8080/api/invoices/amount-range?minAmount={minAmount}&maxAmount={maxAmount}

    @GetMapping("/amount-range")
    public ResponseEntity<Page<Invoice>> getInvoicesByAmountRange(@RequestParam BigDecimal minAmount, @RequestParam BigDecimal maxAmount, Pageable pageable) {
        return ResponseEntity.ok(invoiceService.findInvoicesByAmountRange(minAmount, maxAmount, pageable));
    }

    // POST http://localhost:8080/api/invoices

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.saveInvoice(invoice));
    }

    // PUT http://localhost:8080/api/invoices/{id}

    @PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice updatedInvoice) {
        return ResponseEntity.ok(invoiceService.updateInvoice(id, updatedInvoice));
    }

    // DELETE http://localhost:8080/api/invoices/{id}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}
