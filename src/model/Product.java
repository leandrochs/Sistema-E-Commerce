package model;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

    private final UUID id;
    private final String name;
    private final String description;
    private final BigDecimal value;

    public Product(UUID id, String name, String description, BigDecimal value) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.value = value;
    }
}

