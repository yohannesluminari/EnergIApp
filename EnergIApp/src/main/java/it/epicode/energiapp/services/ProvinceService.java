package it.epicode.energiapp.services;

import it.epicode.energiapp.entities.Province;
import it.epicode.energiapp.exceptions.NotFoundException;
import it.epicode.energiapp.repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    public static final Path filePath = Path.of("C:\\Users\\simon\\Desktop\\Buildweek5\\EnergIApp\\src\\main\\resources\\data\\province-italiane.csv");


    // GET all
    public Page<Province> getAllProvinces(Pageable pageable) {
        if (pageable == null) {
            pageable = PageRequest.of(0, Integer.MAX_VALUE); // Default Pageable
        }
        return provinceRepository.findAll(pageable);
    }

    // GET id
    public Province getProvinceById(long id) {
        return provinceRepository.findById(id).orElseThrow(() -> new NotFoundException("Province not found with id: " + id));
    }

    public Province getProvinceByCode(String code) {
        return provinceRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("Province not found with code: " + code));
    }

    // POST
    public Province saveProvince(Province province) {
        return provinceRepository.save(province);
    }

    // PUT
    public Province updateProvince(long id, Province updatedProvince) {
        Province provinceToBeUpdated = this.getProvinceById(id);
        provinceToBeUpdated.setName(updatedProvince.getName());
        provinceToBeUpdated.setCode(updatedProvince.getCode());
        return provinceRepository.save(provinceToBeUpdated);
    }

    // DELETE
    public void deleteProvince(long id) {
        provinceRepository.deleteById(id);
    }

    // Save all provinces
    public void saveAllProvinces(List<Province> provinces) {
        provinceRepository.saveAll(provinces);
    }

    // LOAD PROVINCES
    public List<Province> uploadProvince(Path filePath) throws IOException {
        List<Province> provinces;
        try {
            provinces = Files.lines(filePath, StandardCharsets.ISO_8859_1)
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
        } catch (IOException err) {
            throw err;
        }
        return provinces;
    }

}
