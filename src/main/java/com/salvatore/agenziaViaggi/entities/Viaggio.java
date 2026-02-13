package com.salvatore.agenziaViaggi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="viaggi")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Viaggio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private String destinazione;
    private LocalDate dataViaggio;
    private StatoViaggio statoViaggio;

    public Viaggio(String destinazione, LocalDate dataViaggio, StatoViaggio statoViaggio) {
        this.destinazione = destinazione;
        this.dataViaggio = dataViaggio;
        this.statoViaggio = statoViaggio;
    }
}
