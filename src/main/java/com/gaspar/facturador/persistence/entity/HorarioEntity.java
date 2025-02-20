package com.gaspar.facturador.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Entity
@Table(name = "horarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HorarioEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String panadero;
    private Long idPanadero;
    private LocalTime horaEntrada;
    private LocalTime horaSalida;
    private LocalDate fechaEntrada;
    private LocalDate fechaSalida;
    @ElementCollection
    @CollectionTable(name = "dias_horario", joinColumns = @JoinColumn(name = "horario_id"))
    @Column(name = "dia")
    private List<String> dias;
}
