package it.epicode.energiapp.runners;

import it.epicode.energiapp.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ProvinceRunner implements CommandLineRunner {

    @Autowired
    private ProvinceService provinceService;

    @Override
    public void run(String... args) throws Exception {
        // Carica tutte le province dal file CSV al momento dell'avvio dell'applicazione
        provinceService.uploadProvince(ProvinceService.filePath);
        System.out.println("Provinces successfully loaded from file");
    }
}
