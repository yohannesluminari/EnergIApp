package it.epicode.energiapp.services;

import it.epicode.energiapp.entities.Province;
import it.epicode.energiapp.exceptions.NotFoundException;
import it.epicode.energiapp.repositories.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProvinceService {
    @Autowired
    private ProvinceRepository provinceRepository;

    // GET all

    public Page<Province> getAllProvinces(Pageable pageable) {
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

}
