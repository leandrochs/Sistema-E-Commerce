package model;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private final String id;
    private final String name;
    private final String description;
    private final BigDecimal value;

    public Product(String id, String name, String description, BigDecimal value) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}


