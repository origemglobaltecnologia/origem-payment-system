package tech.origem.payment.auth.model;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
    String title,
    int status,
    String detail,
    Instant timestamp,
    Map<String, String> validations
) {}
