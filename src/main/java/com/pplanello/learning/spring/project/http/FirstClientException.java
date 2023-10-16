package com.pplanello.learning.spring.project.http;

public class FirstClientException extends RuntimeException {

    public String getHttpStatusCode() {
        return httpStatusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public FirstClientException(String httpStatusCode, String responseBody) {
        super(getMessage(httpStatusCode, responseBody));
        this.httpStatusCode = httpStatusCode;
        this.responseBody = responseBody;
    }


    private static String getMessage(String httpStatusCode, String responseBody) {
        return httpStatusCode.concat("-").concat(responseBody);
    }

    private final String httpStatusCode;
    private final String responseBody;
}
