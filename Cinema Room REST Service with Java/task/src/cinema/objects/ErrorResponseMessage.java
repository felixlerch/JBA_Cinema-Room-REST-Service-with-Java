package cinema.objects;

public class ErrorResponseMessage {

    private final String error;

    public String getError() {
        return error;
    }

    public ErrorResponseMessage(String error) {
        this.error = error;
    }
}
