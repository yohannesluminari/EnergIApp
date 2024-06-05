package it.epicode.energiapp.runners;

import it.epicode.energiapp.csvManager.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ProvinceRunner implements CommandLineRunner {

    @Autowired
    private CsvService csvService;

    @Override
    public void run(String... args) throws Exception {
        String provinceCsvFilePath = "D:/BackendBWTeam3/EnergIApp/src/main/resources/data/province-italiane.csv";
        csvService.loadProvincesFromCsv(provinceCsvFilePath);
    }
}
