package model;

import java.io.Serializable;
import java.util.UUID;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
	private UUID id;
	private String name;
	private String identificationDocument;

    public Customer(String name, String identificationDocument) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.identificationDocument = identificationDocument;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIdentificationDocument() {
        return identificationDocument;
    }
}



