package br.com.eseg.legaltech.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "localidade")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Localidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String cidade;

    @NotBlank
    @Column(nullable = false, length = 2)
    private String uf;

    @NotBlank
    @Column(nullable = false, length = 20)
    private String regiao;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;
}