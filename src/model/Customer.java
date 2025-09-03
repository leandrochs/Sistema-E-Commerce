package model;

import java.util.UUID;

public class Customer {

	private UUID id;
	private String name;
	private String identificationDocument;

    public Customer(String name, String identificationDocument) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.identificationDocument = identificationDocument;
        }
}



