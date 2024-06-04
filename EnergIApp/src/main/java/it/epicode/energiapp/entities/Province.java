package it.epicode.energiapp.entities;

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

    @Column(nullable = false, length = 10)
    private String code;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

  //@OneToMany(mappedBy = "province", cascade = CascadeType.ALL, orphanRemoval = true)
  //private List<Municipality> municipalities;
}
