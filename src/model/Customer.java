package model;

import java.util.UUID;

public class Customer {

	private String id;
	private String name;
	private String identificationDocument;

    public Customer(String id, String name, String identificationDocument) {
        this.id = id;
        this.name = name;
        this.identificationDocument = identificationDocument;
        }
}



