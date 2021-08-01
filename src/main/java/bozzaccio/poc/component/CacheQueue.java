package bozzaccio.poc.component;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;

import java.util.Objects;

public abstract class CacheQueue<T> {

    private final HazelcastInstance hazelcastInstance;
    private final String queueName;

    private IQueue<T> queue;

    public CacheQueue(HazelcastInstance hazelcastInstance, String queueName) {
        this.hazelcastInstance = hazelcastInstance;
        this.queueName = queueName;
        this.queue = hazelcastInstance.getQueue(queueName);
    }

    /**
     * Add an element to the queue
     */
    public void push(T element) {

        Objects.requireNonNull(queue, "Queue is null");

        queue.add(element);
    }

    /**
     * Return the head of the queue or returns NULL if the queue is empty
     */
    public T pop() {

        Objects.requireNonNull(queue, "Queue is null");

        return queue.poll();
    }

    /**
     * Remove an element from the queue
     */
    public void remove(T element) {

        Objects.requireNonNull(queue, "Queue is null");

        queue.remove(element);
    }

    /**
     * Return the head of the queue or wait till the element becomes available
     */
    public T getHeadWhenIsAvailable() throws InterruptedException {

        Objects.requireNonNull(queue, "Queue is null");

        return queue.take();
    }
}
