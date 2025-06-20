package br.com.tosin.models;

import java.math.BigInteger;

public record User(BigInteger salt, BigInteger verifier) {
}