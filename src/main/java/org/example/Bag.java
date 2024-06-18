package org.example; //Przykład kompozycji w kodzie

import java.util.ArrayList;
import java.util.List;

class Bag {
    private List<String> items;

    public Bag() {
        items = new ArrayList<>();
    }

    public void addItem(String item) {
        items.add(item);
    }

    public List<String> getItems() {
        return items;
    }
}
