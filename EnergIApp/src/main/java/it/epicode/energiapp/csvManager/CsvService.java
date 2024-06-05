package it.epicode.energiapp.csvManager;

import it.epicode.energiapp.entities.Municipality;
import it.epicode.energiapp.entities.Province;
import it.epicode.energiapp.repositories.MunicipalityRepository;
import it.epicode.energiapp.repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private MunicipalityRepository municipalityRepository;

    public void loadProvincesFromCsv(String filePath) throws IOException {
        List<Province> provinces = Files.lines(Path.of(filePath), StandardCharsets.ISO_8859_1)
                .skip(1)
                .map(line -> line.split(";"))
                .map(columns -> {
                    Province province = new Province();
                    province.setName(columns[0]);
                    province.setCode(columns[1]);
                    return province;
                })
                .collect(Collectors.toList());
        provinceRepository.saveAll(provinces);
    }

    public void loadMunicipalitiesFromCsv(String filePath) throws IOException {
        List<Municipality> municipalities = Files.lines(Path.of(filePath), StandardCharsets.ISO_8859_1)
                .skip(1)
                .map(line -> line.split(";"))
                .map(columns -> {
                    Municipality municipality = new Municipality();
                    municipality.setName(columns[0]);
                    // Set other attributes if necessary
                    return municipality;
                })
                .collect(Collectors.toList());
        municipalityRepository.saveAll(municipalities);
    }
}
