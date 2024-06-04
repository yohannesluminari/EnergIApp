package it.epicode.energiapp.repositories;

import it.epicode.energiapp.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> ,PagingAndSortingRepository<Region, Long> {
}
