package it.epicode.energiapp.entities;

import it.epicode.energiapp.controllers.MunicipalityController;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="provinces")
@Data
@Builder(setterPrefix = "with")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private String code;

   //@OneToMany(mappedBy = "province", cascade = CascadeType.ALL, orphanRemoval = true)
   //private List<Municipality> municipalities;

}
