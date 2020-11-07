package com.dapr.client;

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.HttpExtension;

import java.io.IOException;

/**
 * @author Zhang_Xiang
 * @since 2020/11/7 17:30:26
 */
public class ClientA {
    /**
     * Identifier in Dapr for the service this client will invoke.
     */
    private static final String SERVICE_APP_ID = "java-service-b";

    /**
     * Starts the invoke client.
     *
     * @param args Messages to be sent as request for the invoke API.
     */
    public static void main(String[] args) throws IOException {
        try (DaprClient client = (new DaprClientBuilder()).build()) {
            for (String message : args) {
                byte[] response = client.invokeService(SERVICE_APP_ID, "say", message, HttpExtension.POST, null,
                        byte[].class).block();
                System.out.println(new String(response));
            }

            // This is an example, so for simplicity we are just exiting here.
            // Normally a dapr app would be a web service and not exit main.
            System.out.println("Done");
        }
    }
}