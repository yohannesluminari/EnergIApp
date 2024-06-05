package it.epicode.energiapp.controllers;

import it.epicode.energiapp.entities.Municipality;
import it.epicode.energiapp.entities.Province;
import it.epicode.energiapp.exceptions.NoContentException;
import it.epicode.energiapp.services.MunicipalityService;
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
@RequestMapping("/api/municipalities")
public class MunicipalityController {

    @Autowired
    private MunicipalityService municipalityService;

    @Value("${csv_comuni}")
    private String municipalityFile;

    // GET http://localhost:8080/api/municipalities

    @GetMapping
    public ResponseEntity<Page<Municipality>> getAllProvinces(Pageable pageable) {
        Page<Municipality> municipalities = municipalityService.getAllMunicipalities(pageable);
        if (municipalities.isEmpty()) {
            throw new NoContentException("No municipalities were found.");
        } else {
            ResponseEntity<Page<Municipality>> responseEntity = new ResponseEntity<>(municipalities, HttpStatus.OK);
            return responseEntity;
        }
    }

    // GET http://localhost:8080/api/municipalities/{id}

    @GetMapping("/{id}")
    public ResponseEntity<Municipality> getMunicipalityById(@PathVariable Long id) {
        Municipality municipality = municipalityService.getMunicipalityById(id);
        return ResponseEntity.ok(municipality);
    }

    // POST http://localhost:8080/api/municipalities

    @PostMapping
    public ResponseEntity<Municipality> createMunicipality(@RequestBody Municipality municipality) {
        Municipality createdMunicipality = municipalityService.saveMunicipality(municipality);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMunicipality);
    }

    // PUT http://localhost:8080/api/municipalities/{id}

    @PutMapping("/{id}")
    public ResponseEntity<Municipality> updateMunicipality(@PathVariable Long id, @RequestBody Municipality municipality) {
        Municipality updatedMunicipality = municipalityService.updateMunicipality(id, municipality);
        return ResponseEntity.ok(updatedMunicipality);
    }

    // DELETE http://localhost:8080/api/municipalities/{id}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMunicipality(@PathVariable Long id) {
        municipalityService.deleteMunicipality(id);
        return ResponseEntity.noContent().build();
    }

    // POST http://localhost:8080/api/municipalities/load-municipality

    @PostMapping("/load-municipality")
    public String importMunicipality() throws IOException {
        municipalityService.uploadMunicipality(Path.of(municipalityFile));
        return "Municipalities successfully imported from file";
    }
}

