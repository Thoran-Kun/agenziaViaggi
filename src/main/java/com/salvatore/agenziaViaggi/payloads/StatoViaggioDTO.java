package com.salvatore.agenziaViaggi.payloads;

import com.salvatore.agenziaViaggi.entities.StatoViaggio;
import jakarta.validation.constraints.NotNull;

public record StatoViaggioDTO(
        @NotNull(message = "lo stato del viaggio è obbligatorio")
        StatoViaggio statoViaggio
) {
}
