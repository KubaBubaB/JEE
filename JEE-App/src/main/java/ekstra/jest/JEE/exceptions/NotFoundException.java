package ekstra.jest.JEE.exceptions;

public class NotFoundException extends HttpRequestException {
    private static final int RESPONSE_CODE = 404;
    public NotFoundException() {
        super(RESPONSE_CODE);
    }

    public NotFoundException(String message) {
        super(message, RESPONSE_CODE);
    }
}
