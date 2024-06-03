package it.epicode.energiapp.repositories;

import it.epicode.energiapp.entities.Invoice;
import it.epicode.energiapp.entities.Municipality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MunicipalityRepository extends JpaRepository<Municipality, Long>, PagingAndSortingRepository<Municipality, Long> {
}