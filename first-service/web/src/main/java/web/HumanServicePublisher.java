package web;

import jakarta.xml.ws.Endpoint;

public class HumanServicePublisher {
    public static void main(String[] args) {
        Endpoint.publish(
                "http://localhost:8080/human-service/api/humans",
                new HumanWebServiceImpl()
        );
    }
}
