package it.epicode.energiapp.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="clients")
@Data
@Builder(setterPrefix = "with")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String businessName;

    private String vatNumber;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false)
    private LocalDate dateAdded;

    private LocalDate lastContactDate;

    private BigDecimal annualRevenue;

    private String pec;

    @Column(nullable = false, length = 20)
    private String phone;

    private String contactEmail;

    private String contactFirstName;

    private String contactLastName;

    @Column(nullable = false, length = 20)
    private String contactPhone;

    private String companyLogo;


    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Address> addresses;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Invoice> invoices;

    @Enumerated(EnumType.STRING)
    private RedisProperties.ClientType clientType;
    // Getters, Setters, Constructors, equals, hashCode
}
