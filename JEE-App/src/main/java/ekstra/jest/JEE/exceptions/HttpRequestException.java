package ekstra.jest.JEE.exceptions;

import lombok.Getter;

@Getter
public class HttpRequestException extends RuntimeException {
  private final int responseCode;

  public HttpRequestException(int responseCode) {
    this.responseCode = responseCode;
  }

  public HttpRequestException(String message, int responseCode) {
    super(message);
    this.responseCode = responseCode;
  }
}
