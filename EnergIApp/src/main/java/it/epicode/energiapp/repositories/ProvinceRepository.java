package it.epicode.energiapp.repositories;

import it.epicode.energiapp.controllers.ProvinceController;
import it.epicode.energiapp.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long>, PagingAndSortingRepository<Province, Long> {
}