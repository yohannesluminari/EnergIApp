package it.epicode.energiapp.runners;

import it.epicode.energiapp.csvManager.ProvinceCommunesCsvManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProvinceCommunesCsvManager csvManager;

    @Override
    public void run(String... args) throws Exception {
        csvManager.processCsvFiles(
                "src/main/java/it/epicode/energiapp/csv/comuni & province/comuni-italiani.csv",
                "src/main/java/it/epicode/energiapp/csv/comuni & province/province-italiane.csv"
        );
    }
}
