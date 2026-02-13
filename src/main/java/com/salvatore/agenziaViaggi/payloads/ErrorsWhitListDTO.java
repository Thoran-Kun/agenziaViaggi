package com.salvatore.agenziaViaggi.payloads;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorsWhitListDTO(String message, LocalDateTime timestamp, List<String> errors) {
}
