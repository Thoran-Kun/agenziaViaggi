package com.salvatore.agenziaViaggi.payloads;

import jakarta.validation.constraints.NotNull;

public record PrenotazioneDTO(
        @NotNull(message = "l'id del viaggio è obbligatorio")
        Long viaggioId,

        @NotNull(message = "l'id del dipendente è obbligatorio")
        Long dipendenteId,
        String note
) {
}
