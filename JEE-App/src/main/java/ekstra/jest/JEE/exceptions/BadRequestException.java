package ekstra.jest.JEE.exceptions;

public class BadRequestException extends HttpRequestException {
    private static final int RESPONSE_CODE = 400;
    public BadRequestException() {
        super(RESPONSE_CODE);
    }

    public BadRequestException(String message) {
        super(message, RESPONSE_CODE);
    }
}
