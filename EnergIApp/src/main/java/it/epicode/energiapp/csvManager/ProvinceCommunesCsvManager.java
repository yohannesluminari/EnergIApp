package it.epicode.energiapp.csvManager;

import it.epicode.energiapp.entities.Municipality;
import it.epicode.energiapp.entities.Province;
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

    public void processCsvFiles(String provincesCsvFile, String regionsCsvFile, String municipalitiesCsvFile) {
        // Carica le regioni e le associa alle province
        Map<String, String> regionMap = loadRegions(regionsCsvFile);

        // Carica i comuni e li associa alle province
        loadMunicipalities(provincesCsvFile, municipalitiesCsvFile, regionMap);
    }

    private void loadMunicipalities(String provincesCsvFile, String municipalitiesCsvFile, Map<String, String> regionMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader(municipalitiesCsvFile))) {
            String line;
            // Salta la riga dell'intestazione
            reader.readLine();

            // Legge ogni riga del file CSV dei comuni
            while ((line = reader.readLine()) != null) {
                // Divide i dati della riga utilizzando il separatore ";" e li mette in un array
                String[] data = line.split(";");
                // Ottiene il codice della provincia (posizione 0 nell'array)
                String provinceCode = data[0];
                // Ottiene il nome del comune (posizione 2 nell'array)
                String municipalityName = data[2];

                // Trova la provincia corrispondente utilizzando il codice della provincia
                Province province = provinceService.getProvinceByCode(provinceCode);

                // Crea un nuovo oggetto Municipality per il comune
                Municipality municipality = new Municipality();
                municipality.setName(municipalityName);
                municipality.setProvince(province);

                // Salva il comune utilizzando il servizio MunicipalityService
                municipalityService.saveMunicipality(municipality);
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file CSV dei comuni: " + e.getMessage());
        }
    }

    private Map<String, String> loadRegions(String regionsCsvFile) {
        Map<String, String> regionMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(regionsCsvFile))) {
            String line;
            // Salta la riga dell'intestazione
            reader.readLine();

            // Legge ogni riga del file CSV delle regioni
            while ((line = reader.readLine()) != null) {
                // Divide i dati della riga utilizzando il separatore ";" e li mette in un array
                String[] data = line.split(";");
                // Ottiene il codice della provincia (posizione 0 nell'array)
                String provinceCode = data[0];
                // Ottiene il nome della regione (posizione 1 nell'array)
                String regionName = data[1];

                // Associa il nome della regione al codice della provincia
                regionMap.put(provinceCode, regionName);
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file CSV delle regioni: " + e.getMessage());
        }

        return regionMap;
    }
}
