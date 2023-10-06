// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.android.eventhubs;

import com.azure.identity.ClientSecretCredential;
import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;

import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

import android.util.Log;

/**
 * Sample demonstrates how to send an {@link EventDataBatch} to an Azure Event Hub using Azure Identity.
 */
public class PublishEventsWithAzureIdentity {
    private static final String TAG = "PublishEventsWithAzureIdentity";
    /**
     * Main method to invoke this demo on how to send an {@link EventDataBatch} to an Azure Event Hub.
     *
     * @param args Unused arguments to the program.
     */
    public static void main(String[] args, ClientSecretCredential credential) {
        List<EventData> telemetryEvents = Arrays.asList(
            new EventData("Roast beef".getBytes(UTF_8)),
            new EventData("Cheese".getBytes(UTF_8)),
            new EventData("Tofu".getBytes(UTF_8)),
            new EventData("Turkey".getBytes(UTF_8)));

        // Create a producer.
        //
        // "<<fully-qualified-namespace>>" will look similar to "{your-namespace}.servicebus.windows.net"
        // "<<event-hub-name>>" will be the name of the Event Hub instance you created inside the Event Hubs namespace.
        EventHubProducerClient producer = new EventHubClientBuilder()
            .credential(
                    args[0],
                    args[1],
                credential)
            .buildProducerClient();

        // Creates an EventDataBatch where the Event Hubs service will automatically load balance the events between all
        // available partitions.
        EventDataBatch currentBatch = producer.createBatch();

        // We try to add as many events as a batch can fit based on the event size and send to Event Hub when
        // the batch can hold no more events. Create a new batch for next set of events and repeat until all events
        // are sent.
        for (EventData event : telemetryEvents) {
            if (currentBatch.tryAdd(event)) {
                continue;
            }

            // The batch is full, so we create a new batch and send the batch.
            producer.send(currentBatch);
            currentBatch = producer.createBatch();

            // Add that event that we couldn't before.
            if (!currentBatch.tryAdd(event)) {
                Log.e(TAG, String.format("Event is too large for an empty batch. Skipping. Max size: %s. Event: %s%n",
                    currentBatch.getMaxSizeInBytes(), event.getBodyAsString()));
            }
        }

        producer.send(currentBatch);
    }
}
