package it.epicode.energiapp.services;

import it.epicode.energiapp.entities.Region;
import it.epicode.energiapp.exceptions.NotFoundException;
import it.epicode.energiapp.repositories.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    // GET all

    public Page<Region> getAllRegion(Pageable pageable) {
        return regionRepository.findAll(pageable);
    }

    // GET id

    public Region getRegionById(long id) {
        return regionRepository.findById(id).orElseThrow(() -> new NotFoundException("Region not found with id: " + id));
    }

    // POST

    public Region saveRegion(Region region) {
        return regionRepository.save(region);
    }


}
