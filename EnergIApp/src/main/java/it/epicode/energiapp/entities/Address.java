package it.epicode.energiapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(setterPrefix = "with")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String street;

    @Column(nullable = false, length = 10)
    private String streetNumber;

    @Column(nullable = false, length = 255)
    private String locality;

    @Column(nullable = false, length = 10)
    private String zipCode;



    @ManyToOne
    private Municipality municipality;

    @ManyToOne
    private Client client;
}
