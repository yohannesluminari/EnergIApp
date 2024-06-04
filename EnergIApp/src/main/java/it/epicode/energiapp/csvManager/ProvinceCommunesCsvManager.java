package it.epicode.energiapp.csvManager;

import it.epicode.energiapp.entities.Municipality;
import it.epicode.energiapp.entities.Province;
import it.epicode.energiapp.entities.Region;
import it.epicode.energiapp.services.MunicipalityService;
import it.epicode.energiapp.services.ProvinceService;
import it.epicode.energiapp.services.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ProvinceCommunesCsvManager {

    @Autowired
    private RegionService regionService;

    @Autowired
    private MunicipalityService municipalityService;

    @Autowired
    private ProvinceService provinceService;

    // Metodo principale per processare i file CSV
    public void processCsvFiles(String municipalitiesCsvFile, String provincesCsvFile) {
        // Carica e salva le province e le regioni, restituendo una mappa delle province
        Map<String, Province> provinceMap = loadProvinces(provincesCsvFile);

        // Carica e salva i comuni, associandoli alle province
        loadMunicipalities(municipalitiesCsvFile, provinceMap);
    }

    // Metodo per caricare le province dal file CSV
    private Map<String, Province> loadProvinces(String provincesCsvFile) {
        // Mappa per memorizzare le province caricate
        Map<String, Province> provinceMap = new HashMap<>();

        // Apertura del file CSV e lettura delle righe
        try (BufferedReader reader = new BufferedReader(new FileReader(provincesCsvFile))) {
            String line;
            reader.readLine(); // Salta la riga dell'intestazione

            // Legge ogni riga del file CSV
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";"); // Divide la riga in base al separatore ";"
                String provinceCode = data[0].trim(); // Codice della provincia
                String provinceName = data[1].trim(); // Nome della provincia
                String regionName = data[2].trim(); // Nome della regione

                // Cerca la regione nel database, se non esiste, la crea e la salva
                Region region = regionService.getRegionByName(regionName);
                if (region == null) {
                    region = new Region();
                    region.setName(regionName);
                    region = regionService.saveRegion(region); // Salva la regione
                }

                // Crea e salva la provincia nel database
                Province province = new Province();
                province.setCode(provinceCode);
                province.setName(provinceName);
                province.setRegion(region);

                province = provinceService.saveProvince(province); // Salva la provincia
                provinceMap.put(provinceCode, province); // Aggiunge la provincia alla mappa
            }
        } catch (IOException e) {
            // Gestione degli errori di lettura del file CSV
            System.err.println("Errore nella lettura del file CSV delle province: " + e.getMessage());
        }

        return provinceMap; // Restituisce la mappa delle province
    }

    // Metodo per caricare i comuni dal file CSV
    private void loadMunicipalities(String municipalitiesCsvFile, Map<String, Province> provinceMap) {
        // Apertura del file CSV e lettura delle righe
        try (BufferedReader reader = new BufferedReader(new FileReader(municipalitiesCsvFile))) {
            String line;
            reader.readLine(); // Salta la riga dell'intestazione

            // Legge ogni riga del file CSV
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(";"); // Divide la riga in base al separatore ";"
                String provinceCode = data[0].trim(); // Codice della provincia
                String municipalityName = data[2].trim(); // Nome del comune

                // Trova la provincia corrispondente nella mappa
                Province province = provinceMap.get(provinceCode);

                // Verifica se la provincia esiste nella mappa
                if (province != null) {
                    // Crea e salva il comune nel database
                    Municipality municipality = new Municipality();
                    municipality.setName(municipalityName);
                    municipality.setProvince(province);

                    municipalityService.saveMunicipality(municipality); // Salva il comune
                } else {
                    // Gestisci il caso in cui la provincia non è trovata
                    System.err.println("Provincia con codice " + provinceCode + " non trovata per il comune " + municipalityName);
                }
            }
        } catch (IOException e) {
            // Gestione degli errori di lettura del file CSV
            System.err.println("Errore nella lettura del file CSV dei comuni: " + e.getMessage());
        }
    }
}
//LUCAAAAAAAAAAAAAAAAA TU LO SAI DOVE DEVI ANDARE!
//METTI IL CAPPELLO