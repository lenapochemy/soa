package org.example.secondservice.configuration;

import org.example.secondservice.exceptions.ClientErrorException;
import org.example.secondservice.exceptions.ServerErrorException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class ErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        System.out.println("Error on request " + response.getStatusCode() + response.getBody());

        if (response.getStatusCode().is4xxClientError()) throw new ClientErrorException();
        if (response.getStatusCode().is5xxServerError()) throw new ServerErrorException();

    }
}