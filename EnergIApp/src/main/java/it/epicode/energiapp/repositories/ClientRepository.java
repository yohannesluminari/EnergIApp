package it.epicode.energiapp.repositories;

import it.epicode.energiapp.entities.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByCompanyNameContaining(String businessName);

    Page<Client> findByAnnualRevenue(BigDecimal annualRevenue, Pageable pageable);
    Page<Client> findByDateAdded(LocalDate dateAdded, Pageable pageable);
    Page<Client> findByLastContactDate(LocalDate lastContactDate, Pageable pageable);
    Page<Client> findByBusinessNameContaining(String businessName, Pageable pageable);

    // Sorting methods
    Page<Client> findAllByOrderByBusinessName(Pageable pageable);
    Page<Client> findAllByOrderByAnnualRevenue(Pageable pageable);
    Page<Client> findAllByOrderByDateAdded(Pageable pageable);
    Page<Client> findAllByOrderByLastContactDate(Pageable pageable);
}
