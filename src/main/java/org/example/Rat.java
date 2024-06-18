package org.example;

import java.util.Random;

// Klasa Rat dziedziczy po klasie Entity (Dziedziczenie)
class Rat extends Entity {
    private static final double INFECTION_PROBABILITY = 0.45; // Prawdopodobieństwo infekcji
    private boolean isDead; // Pole określające, czy szczur jest martwy

    public Rat(String name, int x, int y) {
        super(name, x, y); // Wywołanie konstruktora klasy bazowej (Dziedziczenie)
        this.isDead = false;
    }

    // Metoda określająca, czy szczur rozprzestrzenia zarazę (Polimorfizm)
    public boolean spreadPlague() {
        Random random = new Random();
        return random.nextDouble() < INFECTION_PROBABILITY;
    }

    // Metoda przemieszczania szczura losowo po polu
    public void moveRandomly(int fieldSize) {
        if (!isDead) {
            Random random = new Random();
            int newX = getX() + random.nextInt(3) - 1;
            int newY = getY() + random.nextInt(3) - 1;
            newX = Math.max(0, Math.min(newX, fieldSize - 1));
            newY = Math.max(0, Math.min(newY, fieldSize - 1));
            setPosition(newX, newY);
        }
    }

    // Getter i setter dla pola isDead
    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }
}
