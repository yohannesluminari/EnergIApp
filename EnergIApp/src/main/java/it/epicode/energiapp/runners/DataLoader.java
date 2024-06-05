package it.epicode.energiapp.runners;

import it.epicode.energiapp.csvManager.ProvinceCommunesCsvManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProvinceCommunesCsvManager csvManager;

    @Override
    public void run(String... args) throws Exception {
        ClassPathResource municipalitiesCsvResource = new ClassPathResource("comuni & province/comuni-italiani.csv");
        ClassPathResource provincesCsvResource = new ClassPathResource("comuni & province/province-italiane.csv");

        String municipalitiesCsvPath = municipalitiesCsvResource.getFile().getPath();
        String provincesCsvPath = provincesCsvResource.getFile().getPath();

        csvManager.processCsvFiles(municipalitiesCsvPath, provincesCsvPath);
    }
}
