package tech.origem.payment.auth.model;

/**
 * DTO para captura de credenciais via JSON
 */
public record LoginRequest(String user, String pass) {}
