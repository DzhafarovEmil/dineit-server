package emil.dzhafarov.dineit.model;

public enum OrderStatus {
    RECEIVED("Received"), IN_PROGRESS("In progress"), DONE("Done"), CREATED("Created");

    private String string;

    OrderStatus(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }
}
