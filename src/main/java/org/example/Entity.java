package org.example;

// Definicja klasy bazowej Entity. (Definiowanie klas, hermetyzacja danych i metod)
class Entity {
    private String name; // Pole prywatne (Hermetyzacja danych)
    private int x, y; // Pola prywatne (Hermetyzacja danych)

    public Entity(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    // Gettery i settery (Hermetyzacja metod)

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
