package it.epicode.energiapp.runners;



import com.github.javafaker.Faker;
import it.epicode.energiapp.entities.Client;
import it.epicode.energiapp.entities.Invoice;
import it.epicode.energiapp.entities.enumEntities.InvoiceStatus;
import it.epicode.energiapp.services.ClientService;
import it.epicode.energiapp.services.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


import java.math.BigDecimal;

import java.util.List;
import java.util.Random;

@Component
@Order(5)
public class InvoicesRunner implements CommandLineRunner {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ClientService clientService;

    @Override
    public void run(String... args) throws Exception {

        List<Client> clients = clientService.findAllClients(PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        Faker faker = new Faker();
        Random random = new Random();

        for (Client client : clients) {
            for (int j = 0; j < 10; j++) {
                Invoice invoice = Invoice.builder()
                        .withDate(faker.date().past(365, java.util.concurrent.TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                        .withAmount(BigDecimal.valueOf(faker.number().randomDouble(2, 100, 1000)))
                        .withClient(client)
                        .withStatus(faker.options().option(InvoiceStatus.class))
                        .build();

                invoiceService.saveInvoice(invoice);
            }
        }
    }
}
