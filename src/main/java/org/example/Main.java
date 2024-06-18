package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    private static final int FIELD_SIZE = 10;
    private static final int NUM_PEOPLE = 10;
    private static final int NUM_RATS = 3;
    private static final int NUM_DOCTORS = 4;
    private static final int STEP_DELAY = 1000; // Opóźnienie między krokami w milisekundach

    public static void main(String[] args) {
        List<Entity> entities = new ArrayList<>();

        // Dodajemy osoby do symulacji
        for (int i = 0; i < NUM_PEOPLE; i++) {
            entities.add(new Person("Person" + i, randomCoordinate(), randomCoordinate()));
        }

        // Dodajemy szczury do symulacji
        for (int i = 0; i < NUM_RATS; i++) {
            entities.add(new Rat("Rat" + i, randomCoordinate(), randomCoordinate()));
        }

        // Dodajemy doktorów do symulacji
        for (int i = 0; i < NUM_DOCTORS; i++) {
            entities.add(new PlagueDoctor("Doctor" + i, randomCoordinate(), randomCoordinate()));
        }

        while (true) {
            displayField(entities);
            moveEntities(entities);
            interactEntities(entities);
            updateStatus(entities);

            // Sprawdzenie zakończenia symulacji
            boolean allPeopleDead = entities.stream()
                    .filter(entity -> entity instanceof Person)
                    .map(entity -> (Person) entity)
                    .allMatch(Person::isDead);

            boolean allRatsDead = entities.stream()
                    .filter(entity -> entity instanceof Rat)
                    .map(entity -> (Rat) entity)
                    .allMatch(Rat::isDead);

            if (allPeopleDead || allRatsDead) {
                displaySummary(entities);
                if (allPeopleDead) {
                    System.out.println("All people are dead. Simulation stops.");
                }
                if (allRatsDead) {
                    System.out.println("All rats are dead. Simulation stops.");
                }
                break;
            }

            // Opóźnienie między krokami symulacji
            try {
                Thread.sleep(STEP_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Wyczyść ekran
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    private static int randomCoordinate() {
        Random random = new Random();
        return random.nextInt(FIELD_SIZE);
    }

    private static void moveEntities(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof Person person && !person.isDead()) {
                person.moveRandomly(FIELD_SIZE);
            } else if (entity instanceof Rat rat && !rat.isDead()) {
                rat.moveRandomly(FIELD_SIZE);
            }
        }
    }

    private static void interactEntities(List<Entity> entities) {
        for (Entity entity : entities) {
            for (Entity other : entities) {
                if (entity != other) {
                    if (entity instanceof Person person && other instanceof Rat rat && !person.isDead() && !rat.isDead()) {
                        if (Math.abs(person.getX() - rat.getX()) <= 1 && Math.abs(person.getY() - rat.getY()) <= 1) {
                            person.setInfected(true);
                        }
                    }

                     if (entity instanceof Person person && other instanceof PlagueDoctor doctor && !person.isDead() && !doctor.isDead()) {
                        if (Math.abs(person.getX() - doctor.getX()) <= 1 && Math.abs(person.getY() - doctor.getY()) <= 1) {
                            doctor.treat(person);
                        }

                    } else if (entity instanceof PlagueDoctor doctor && other instanceof Rat rat && !doctor.isDead()) {
                        if (Math.abs(doctor.getX() - rat.getX()) <= 1 && Math.abs(doctor.getY() - rat.getY()) <= 1) {
                            doctor.killRat(rat, entities);
                        }
                    }
                }
            }
        }
        entities.removeIf(entity -> entity instanceof Rat && ((Rat) entity).isDead());
    }

    private static void updateStatus(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity instanceof Person person && person.isInfected() && !person.isDead()) {
                Random random = new Random();
                if (random.nextDouble() < 0.15) { // 10% probability of dying from infection
                    person.setDead(true);
                }
            }
        }
    }

    private static void displayField(List<Entity> entities) {
        char[][] field = new char[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                field[i][j] = '-';
            }
        }
        for (Entity entity : entities) {
            if (entity instanceof Person person) {
                if (person.isDead()) {
                    field[person.getX()][person.getY()] = 'X'; // Display dead people with 'X'
                } else if (person instanceof PlagueDoctor) {
                    field[person.getX()][person.getY()] = 'D'; // Display doctors with 'D'
                } else if (person.isInfected()) {
                    field[person.getX()][person.getY()] = 'I'; // Display infected people with 'I'
                } else if (person.isCured()) {
                    field[person.getX()][person.getY()] = 'C'; // Display cured people with 'C'
                } else {
                    field[person.getX()][person.getY()] = 'P'; // Display healthy people with 'P'
                }
            } else if (entity instanceof Rat rat) {
                if (rat.isDead()) {
                    field[rat.getX()][rat.getY()] = 'X'; // Display dead rats with 'X'
                } else {
                    field[rat.getX()][rat.getY()] = 'R'; // Display rats with 'R'
                }
            }
        }
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                System.out.print(" " + field[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void displaySummary(List<Entity> entities) {
        long deadPeopleCount = entities.stream()
                .filter(entity -> entity instanceof Person)
                .map(entity -> (Person) entity)
                .filter(Person::isDead)
                .count();

        long curedPeopleCount = entities.stream()
                .filter(entity -> entity instanceof Person)
                .map(entity -> (Person) entity)
                .filter(Person::isCured)
                .count();

        long infectedPeopleCount = entities.stream()
                .filter(entity -> entity instanceof Person)
                .map(entity -> (Person) entity)
                .filter(Person::isInfected)
                .count();

        long totalRatsCount = entities.stream()
                .filter(entity -> entity instanceof Rat)
                .count();

        long deadRatsCount = entities.stream()
                .filter(entity -> entity instanceof Rat)
                .map(entity -> (Rat) entity)
                .filter(Rat::isDead)
                .count();

        long aliveRatsCount = totalRatsCount - deadRatsCount;

        System.out.println("Simulation Summary:");
        System.out.println("Total People: " + entities.stream().filter(entity -> entity instanceof Person).count());
        System.out.println("Dead People: " + deadPeopleCount);
        System.out.println("Cured People: " + curedPeopleCount);
        System.out.println("Infected People: " + infectedPeopleCount);
        System.out.println("Alive Rats: " + aliveRatsCount);


    }
}
