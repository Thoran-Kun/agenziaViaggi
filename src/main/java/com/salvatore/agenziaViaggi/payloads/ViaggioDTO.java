package com.salvatore.agenziaViaggi.payloads;

import com.salvatore.agenziaViaggi.entities.StatoViaggio;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ViaggioDTO(
        @NotBlank(message = "la destinazione è obbligatoria")
        String destinazione,

        @NotNull(message = "la data è obbligatoria")
        @FutureOrPresent(message = "la data non può essere nel passato")
        LocalDate dataViaggio,

        @NotNull(message = "lo stato è obbligatorio")
        StatoViaggio statoViaggio
) {
}
