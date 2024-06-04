package it.epicode.energiapp.csvManager;

import it.epicode.energiapp.entities.Province;
import it.epicode.energiapp.services.ProvinceService; // Import ProvinceService

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProvinceCommunesCsvManager {

    private final ProvinceService provinceService; // Inject ProvinceService

    public ProvinceCommunesCsvManager(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    public void processCsvFile(String csvFile) {
        List<Province> provinces = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            // Skip the header line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String provinceSigla = data[0];
                String provinceName = data.length > 1 ? data[1] : ""; // Handle potential missing data
                String region = data.length > 2 ? data[2] : ""; // Handle potential missing data

                // Assuming id is auto-generated (remove id from constructor)
                Province province = new Province(provinceName, region);
                provinces.add(province);
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        // Save provinces to database using ProvinceService
        for (Province province : provinces) {
            provinceService.saveProvince(province);
        }

        System.out.println("Successfully processed CSV file and saved provinces to database.");
    }
}
