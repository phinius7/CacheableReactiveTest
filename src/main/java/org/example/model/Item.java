package org.example.model;

public class Item {

    public final String id;
    public final String name;
    public final double price;

    private Item(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public double getPrice() {
        return price;
    }

    public static final class Builder {
        private String id;
        private String name;
        private double price;

        private Builder() {
        }

        public static Builder anItem() {
            return new Builder();
        }

        public Builder withId(final String id) {
            this.id = id;
            return this;
        }

        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withPrice(double price) {
            this.price = price;
            return this;
        }

        public Item build() {
            return new Item(id, name, price);
        }
    }
}
