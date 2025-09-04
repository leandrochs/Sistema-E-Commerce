package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getValue() {
        return value;
    }
}


