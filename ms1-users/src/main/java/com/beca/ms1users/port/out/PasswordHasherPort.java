package com.beca.ms1users.port.out;

/**
 * Port de saída — contrato para hashing de senhas.
 */
public interface PasswordHasherPort {

    String hash(String rawPassword);

    boolean matches(String rawPassword, String hashedPassword);
}
