package com.salvatore.agenziaViaggi.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="prenotazioni")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    private LocalDate dataPrenotazione;
    private String note;

    @ManyToOne
    @JoinColumn(name="viaggio_id")
    private Viaggio viaggio;

    @ManyToOne
    @JoinColumn(name="dipendente_id")
    private Dipendente dipendente;

    public Prenotazione(LocalDate dataPrenotazione, String note, Viaggio viaggio, Dipendente dipendente) {
        this.dataPrenotazione = dataPrenotazione;
        this.note = note;
        this.viaggio = viaggio;
        this.dipendente = dipendente;
    }
}
