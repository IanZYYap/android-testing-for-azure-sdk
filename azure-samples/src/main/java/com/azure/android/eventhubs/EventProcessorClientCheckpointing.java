// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.android.eventhubs;

import android.util.Log;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.models.EventBatchContext;
import com.azure.messaging.eventhubs.models.EventContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * This originally contained sample code to demonstrate checkpointing after processing some number
 * of events. However, the main method has been refactored into an instrumented test.
 */
public class EventProcessorClientCheckpointing {
    private static final Map<String, SamplePartitionProcessor> SAMPLE_PARTITION_PROCESSOR_MAP = new HashMap<>();

    /**
     * Creates or gets and delegates a {@link SamplePartitionProcessor} to take care of processing and updating the
     * checkpoint if needed.
     *
     * @param batchContext Events to process.
     * @param numberOfEventsBeforeCheckpointing Number of events to process before checkpointing.
     */
    public static void onEventBatchReceived(EventBatchContext batchContext, int numberOfEventsBeforeCheckpointing) {
        final String partitionId = batchContext.getPartitionContext().getPartitionId();

        final SamplePartitionProcessor samplePartitionProcessor = SAMPLE_PARTITION_PROCESSOR_MAP.computeIfAbsent(
            partitionId, key -> new SamplePartitionProcessor(key, numberOfEventsBeforeCheckpointing));

        samplePartitionProcessor.processEventBatch(batchContext);
    }

    /**
     * Class keeps track of the number of events processed for each partition and checkpoints when at least
     * {@code numberOfEventsBeforeCheckpointing} has been processed.
     *
     * In practice, only {@link #processEvent(EventContext)} OR {@link #processEventBatch(EventBatchContext)} will be
     * used.
     */
    public static final class SamplePartitionProcessor {
        private final Logger logger;
        private final String partitionId;
        private final int numberOfEventsBeforeCheckpointing;

        private int eventsProcessed;

        public SamplePartitionProcessor(String partitionId, int numberOfEventsBeforeCheckpointing) {
            this.partitionId = partitionId;
            this.numberOfEventsBeforeCheckpointing = numberOfEventsBeforeCheckpointing;

            final String loggerName = SamplePartitionProcessor.class + partitionId;
            this.logger = LoggerFactory.getLogger(loggerName);
        }

        /**
         * Processes some events and checkpointing after at least {@link #numberOfEventsBeforeCheckpointing} events have
         * been processed.
         *
         * @param eventBatchContext Batch of events to process.
         */
        private void processEventBatch(EventBatchContext eventBatchContext) {

            // There's nothing to process.
            if (eventBatchContext.getEvents().isEmpty()) {
                return;
            }

            final String partitionId = eventBatchContext.getPartitionContext().getPartitionId();

            for (EventData event : eventBatchContext.getEvents()) {
                /*Log.i(TAG, String.format("Processing event: Partition id = %s; sequence number = %d; body = %s",
                    partitionId, event.getSequenceNumber(), event.getBodyAsString()));*/
            }

            eventsProcessed = eventsProcessed + eventBatchContext.getEvents().size();

            // We have processed at least the minimum number of events. If so, then checkpoint and reset the counter.
            if (eventsProcessed >= numberOfEventsBeforeCheckpointing) {
                eventBatchContext.updateCheckpoint();

                eventsProcessed = 0;
            }
        }

        /**
         * Processes an event and checkpoints if {@link #numberOfEventsBeforeCheckpointing} events have been processed.
         *
         * @param eventContext Event to process.
         */
        public void processEvent(EventContext eventContext) {

            final EventData event = eventContext.getEventData();

            // It is possible to have no event if maxWaitTime was specified.  This means that no event was received
            // during that interval.
            if (event == null) {
                return;
            }

            /*Log.i(TAG, String.format("Processing event: Partition id = %s; sequence number = %d; body = %s",
                partitionId, event.getSequenceNumber(), event.getBodyAsString()));*/

            // We have processed the number of events required. If so, then checkpoint and reset the counter.
            if (++eventsProcessed % numberOfEventsBeforeCheckpointing == 0) {
                eventContext.updateCheckpoint();

                eventsProcessed = 0;
            }
        }
    }
}

