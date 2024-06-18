package org.example;

import java.util.Random;
import java.util.List;

// Klasa Person dziedziczy po klasie Entity (Dziedziczenie)
class Person extends Entity {
    private boolean isInfected; // Pole określające, czy osoba jest zainfekowana
    private boolean isCured; // Pole określające, czy osoba jest wyleczona
    private boolean isDead; // Pole określające, czy osoba jest martwa

    public Person(String name, int x, int y) {
        super(name, x, y); // Wywołanie konstruktora klasy bazowej (Dziedziczenie)
        this.isInfected = false;
        this.isCured = false;
        this.isDead = false;
    }

    // Gettery i settery dla pól (Hermetyzacja metod)
    public boolean isInfected() {
        return isInfected;
    }

    public void setInfected(boolean infected) {
        isInfected = infected;
    }

    public boolean isCured() {
        return isCured;
    }

    public void setCured(boolean cured) {
        isCured = cured;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    // Metoda interakcji osoby z innym bytem (Polimorfizm)
    public void interact(Entity entity) {
        if (entity instanceof Rat rat && rat.spreadPlague()) {
            this.setInfected(true);
        }
    }

    // Metoda przemieszczania osoby losowo po polu
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
}



