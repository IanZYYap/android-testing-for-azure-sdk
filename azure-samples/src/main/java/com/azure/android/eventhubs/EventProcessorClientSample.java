// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.android.eventhubs;

import android.util.Log;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventProcessorClient;
import com.azure.messaging.eventhubs.EventProcessorClientBuilder;
import com.azure.messaging.eventhubs.models.ErrorContext;
import com.azure.messaging.eventhubs.models.EventContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Sample code to demonstrate how a customer might use {@link EventProcessorClient}.
 */
public class EventProcessorClientSample {
    private static final String TAG = "EventProcessorClientSample";
    /**
     * Main method to demonstrate starting and stopping a {@link EventProcessorClient}.
     *
     * @param args The input arguments to this executable.
     * @throws Exception If there are any errors while running the {@link EventProcessorClient}.
     */
    public static void main(String[] args, ClientSecretCredential credential) throws Exception {

        Logger logger = LoggerFactory.getLogger(EventProcessorClientSample.class);
        Consumer<EventContext> processEvent = eventContext -> {
            Log.i(TAG, String.format("Processing event: Event Hub name = %s; consumer group name = %s; partition id = %s; sequence number = %s",
                    eventContext.getPartitionContext().getEventHubName(),
                    eventContext.getPartitionContext().getConsumerGroup(),
                    eventContext.getPartitionContext().getPartitionId(),
                    eventContext.getEventData().getSequenceNumber()));
            /*logger.info(
                "Processing event: Event Hub name = {}; consumer group name = {}; partition id = {}; sequence number = {}",
                eventContext.getPartitionContext().getEventHubName(),
                eventContext.getPartitionContext().getConsumerGroup(),
                eventContext.getPartitionContext().getPartitionId(),
                eventContext.getEventData().getSequenceNumber());*/

            eventContext.updateCheckpoint();
        };

        // This error handler logs the error that occurred and keeps the processor running. If the error occurred in
        // a specific partition and had to be closed, the ownership of the partition will be given up and will allow
        // other processors to claim ownership of the partition.
        Consumer<ErrorContext> processError = errorContext -> {
            Log.e(TAG, String.format("Error while processing %s, %s, %s, %s",
                            errorContext.getPartitionContext().getEventHubName(),
                            errorContext.getPartitionContext().getConsumerGroup(),
                            errorContext.getPartitionContext().getPartitionId(),
                            errorContext.getThrowable().getMessage()));
            /*logger.error("Error while processing {}, {}, {}, {}", errorContext.getPartitionContext().getEventHubName(),
                errorContext.getPartitionContext().getConsumerGroup(),
                errorContext.getPartitionContext().getPartitionId(),
                errorContext.getThrowable().getMessage());*/
        };

        // Create a processor client.
        //
        // "<<fully-qualified-namespace>>" will look similar to "{your-namespace}.servicebus.windows.net"
        // "<<event-hub-name>>" will be the name of the Event Hub instance you created inside the Event Hubs namespace.
        EventProcessorClientBuilder eventProcessorClientBuilder = new EventProcessorClientBuilder()
            .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
            .credential(args[0], args[1],
                    credential)
            .processEvent(processEvent)
            .processError(processError)
            .checkpointStore(new SampleCheckpointStore());

        EventProcessorClient eventProcessorClient = eventProcessorClientBuilder.buildEventProcessorClient();
        Log.i(TAG, "Starting event processor");
        eventProcessorClient.start();
        eventProcessorClient.start(); // should be a no-op

        // Continue to perform other tasks while the processor is running in the background.
        Thread.sleep(TimeUnit.MINUTES.toMillis(1));

        Log.i(TAG, "Stopping event processor");
        eventProcessorClient.stop();

        Thread.sleep(TimeUnit.SECONDS.toMillis(40));
        Log.i(TAG, "Starting a new instance of event processor");
        eventProcessorClient = eventProcessorClientBuilder.buildEventProcessorClient();
        eventProcessorClient.start();
        // Continue to perform other tasks while the processor is running in the background.
        Thread.sleep(TimeUnit.MINUTES.toMillis(1));
        Log.i(TAG, "Stopping event processor");
        eventProcessorClient.stop();
        Log.i(TAG, "Exiting process");
    }
}
