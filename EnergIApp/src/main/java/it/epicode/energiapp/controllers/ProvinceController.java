package it.epicode.energiapp.controllers;

import it.epicode.energiapp.entities.Province;
import it.epicode.energiapp.exceptions.NoContentException;
import it.epicode.energiapp.services.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Path;

@RestController
@RequestMapping("/api/provinces")
public class ProvinceController {

    @Autowired
    private ProvinceService provinceService;

    @Value("${csv_province}")
    private String provinceFile;

    // GET http://localhost:8080/api/provinces

    @GetMapping
    public ResponseEntity<Page<Province>> getAllProvinces(Pageable pageable) {
        Page<Province> provinces = provinceService.getAllProvinces(pageable);
        if (provinces.isEmpty()) {
            throw new NoContentException("No provinces were found.");
        } else {
            ResponseEntity<Page<Province>> responseEntity = new ResponseEntity<>(provinces, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET http://localhost:8080/api/provinces/{id}

    @GetMapping("/{id}")
    public ResponseEntity<Province> getProvinceById(@PathVariable Long id) {
        Province province = provinceService.getProvinceById(id);
        return ResponseEntity.ok(province);
    }

    // POST http://localhost:8080/api/provinces

    @PostMapping
    public ResponseEntity<Province> createProvince(@RequestBody Province province) {
        Province createdProvince = provinceService.saveProvince(province);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProvince);
    }

    // PUT http://localhost:8080/api/provinces/{id}

    @PutMapping("/{id}")
    public ResponseEntity<Province> updateProvince(@PathVariable Long id, @RequestBody Province province) {
        Province updatedProvince = provinceService.updateProvince(id, province);
        return ResponseEntity.ok(updatedProvince);
    }

    // DELETE http://localhost:8080/api/provinces/{id}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProvince(@PathVariable Long id) {
        provinceService.deleteProvince(id);
        return ResponseEntity.noContent().build();
    }

    //  // POST http://localhost:8080/api/provinces/load-province

    @PostMapping("/load-province")
    public String importProvince() throws IOException {
        provinceService.uploadProvince(Path.of(provinceFile));
        return "Provinces successfully imported from file";
    }
}

