package tech.origem.payment.auth.model;

/**
 * Resposta estruturada contendo o Token JWT
 */
public record AuthResponse(String token, String type, long expiresAt) {}
