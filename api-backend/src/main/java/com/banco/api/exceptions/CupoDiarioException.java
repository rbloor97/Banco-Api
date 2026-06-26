package com.banco.api.exceptions;

public class CupoDiarioException extends RuntimeException {
    public CupoDiarioException(){super("Cupo diario Excedido");}
}
