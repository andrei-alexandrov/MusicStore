package instruments;

import util.Validation;

import java.util.Objects;

public abstract class Instrument {

    //Characteristics:
    private String name;
    private double price;
    private String type;

    //Constructors:
    public Instrument(String name, double price, String type) {
        if (Validation.textValidation(name)) {
            this.name = name;
        }
        if (price > 0) {
            this.price = price;
        }
        this.type = type;
    }

    //Getters and Setters:
    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getPrice() {
        return price;
    }

    //Override methods:
    @Override
    public String toString() {
        return String.format("Name: %s, %.2f euro", getName(), getPrice());
    }
}
