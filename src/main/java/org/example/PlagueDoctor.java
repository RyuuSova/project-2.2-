package org.example;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;


// Klasa PlagueDoctor dziedziczy po klasie Person (Dziedziczenie)
class PlagueDoctor extends Person {

    private Bag bag;
    private static final double TREATMENT_SUCCESS_PROBABILITY = 0.95; // Prawdopodobieństwo skutecznego leczenia
    private static final double INFECTION_PROBABILITY = 0.05; // Prawdopodobieństwo infekcji podczas leczenia
    private static final double RAT_KILL_PROBABILITY = 0.05; // Prawdopodobieństwo zabicia szczura przez doktora

    public PlagueDoctor(String name, int x, int y) {
        super(name, x, y);// Wywołanie konstruktora klasy bazowej (Dziedziczenie)
        this.bag = new Bag();
    }

    public Bag getBag() {
        return bag;
    }

    public void addBagItem(String item) {
        bag.addItem(item);
    }

    @Override
    public void setInfected(boolean infected) {
        super.setInfected(infected); // Wywołanie metody klasy bazowej (Polimorfizm)
    }

    // Metoda leczenia osoby przez doktora
    public void treat(Person person) {
        Random random = new Random();
        if (!person.isInfected()) {
            return;
        }
        if (random.nextDouble() < TREATMENT_SUCCESS_PROBABILITY) {
            person.setCured(true);
            person.setInfected(false);
        }
        if (random.nextDouble() < INFECTION_PROBABILITY) {
            this.setInfected(true);
        }
    }

    // Metoda zabijania szczura przez doktora
    public void killRat(Rat rat, List<Entity> entities) {
        Random random = new Random();
        if (random.nextDouble() < RAT_KILL_PROBABILITY) {
            rat.setDead(true);
        }
    }
}
