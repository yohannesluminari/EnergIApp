package it.epicode.energiapp.services;

import it.epicode.energiapp.entities.Municipality;
import it.epicode.energiapp.entities.Province;
import it.epicode.energiapp.exceptions.NotFoundException;
import it.epicode.energiapp.repositories.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MunicipalityService {

    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Autowired
    private ProvinceService provinceService;

    public static final Path FILE_PATH = Paths.get("C:\\Users\\simon\\Desktop\\Buildweek5\\EnergIApp\\src\\main\\resources\\data\\comuni-italiani.csv");

    public static Path getFilePath() {
        return FILE_PATH;
    }


    // GET all
    public Page<Municipality> getAllMunicipalities(Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, Integer.MAX_VALUE); // Default Pageable
        }
        return municipalityRepository.findAll(pageable);
    }

    // GET id
    public Municipality getMunicipalityById(long id) {
        return municipalityRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    // POST
    public Municipality saveMunicipality(Municipality municipality) {
        return municipalityRepository.save(municipality);
    }

    // PUT
    public Municipality updateMunicipality(long id, Municipality updatedMunicipality) {
        Municipality municipalityToBeUpdated = this.getMunicipalityById(id);
        municipalityToBeUpdated.setName(updatedMunicipality.getName());
        municipalityToBeUpdated.setProvince(updatedMunicipality.getProvince());
        return municipalityRepository.save(municipalityToBeUpdated);
    }

    // DELETE
    public void deleteMunicipality(long id) {
        municipalityRepository.deleteById(id);
    }

    // Save all municipalities
    public void saveAllMunicipalities(List<Municipality> municipalities) {
        for (Municipality municipality : municipalities) {
            if (municipality.getProvince() == null) {
                throw new IllegalArgumentException("Province cannot be null");
            }
            municipalityRepository.save(municipality);
        }
    }

    // UPLOAD MUNICIPALITIES
    public List<Municipality> uploadMunicipality() throws IOException {
        List<Municipality> municipalities;
        try {
            Page<Province> provinces = provinceService.getAllProvinces(PageRequest.of(0, Integer.MAX_VALUE));
            Map<String, Province> provinceMap = provinces.stream()
                    .collect(Collectors.toMap(Province::getCode, province -> province));

            municipalities = Files.lines(FILE_PATH, StandardCharsets.ISO_8859_1)
                    .skip(1)
                    .map(line -> line.split(";"))
                    .map(columns -> {
                        String municipalityCode = columns[0];
                        String municipalityName = columns[2];
                        String provinceCode = columns[3];
                        Province province = provinceMap.get(provinceCode);
                        if (province != null) {
                            return new Municipality(municipalityCode, municipalityName, province);
                        } else {
                            throw new RuntimeException("Province not found for code: " + provinceCode);
                        }
                    })
                    .collect(Collectors.toList());


            municipalityRepository.saveAll(municipalities);
        } catch (IOException err) {
            throw err;
        }
        return municipalities;
    }
}
