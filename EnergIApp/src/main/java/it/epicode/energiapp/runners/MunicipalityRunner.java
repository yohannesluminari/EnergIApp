package it.epicode.energiapp.runners;

import it.epicode.energiapp.services.MunicipalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class MunicipalityRunner implements CommandLineRunner {

    @Autowired
    private MunicipalityService municipalityService;

    @Override
    public void run(String... args) throws Exception {
        // Carica tutti i comuni dal file CSV al momento dell'avvio dell'applicazione
        municipalityService.uploadMunicipality();
        System.out.println("Municipalities successfully loaded from file");
    }

}
