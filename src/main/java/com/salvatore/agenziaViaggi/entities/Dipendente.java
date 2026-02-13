package com.salvatore.agenziaViaggi.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="dipendenti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private long id;
    @Column(unique = true)
    private String username;
    private String nome;
    private String cognome;
    private String email;

    public Dipendente(String username, String nome, String cognome, String email) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }
}
