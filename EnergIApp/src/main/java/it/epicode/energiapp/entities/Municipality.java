package it.epicode.energiapp.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "municipalities")
@Data
@Builder(setterPrefix = "with")
public class Municipality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private String code; // Campo aggiunto per il codice del comune

    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    // Costruttore aggiunto per accettare nome e provincia
    public Municipality(String name, String code, Province province) {
        this.name = name;
        this.code = code;
        this.province = province;
    }
}
