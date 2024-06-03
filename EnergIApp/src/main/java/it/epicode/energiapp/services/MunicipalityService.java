package it.epicode.energiapp.services;

import it.epicode.energiapp.entities.Municipality;
import it.epicode.energiapp.exceptions.NotFoundException;
import it.epicode.energiapp.repositories.MunicipalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MunicipalityService {
    
    @Autowired
    private MunicipalityRepository municipalityRepository;

    // GET all

    public Page<Municipality> getAllMunicipalities(Pageable pageable) {
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


}
