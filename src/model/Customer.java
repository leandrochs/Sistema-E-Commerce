package model;

public class Customer {

	private String id;
	private String name;
	private String identificationDocument;

    public Customer(String id, String name, String identificationDocument) {
        this.id = id;
        this.name = name;
        this.identificationDocument = identificationDocument;
        }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIdentificationDocument() {
        return identificationDocument;
    }
}
