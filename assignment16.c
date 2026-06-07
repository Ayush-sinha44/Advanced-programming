/*
Producer-Consumer Visualization using POSIX Threads and Semaphores

This version prints the entire buffer after every produce and consume
operation so you can visually see how items move through the circular buffer.

Compile:
    gcc assignment16_visual.c -o assignment16_visual -pthread

Run:
    ./assignment16_visual
*/

#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>

#define BUFFER_SIZE 5
#define ITEMS_TO_PRODUCE 10

int buffer[BUFFER_SIZE];
int in = 0;
int out = 0;

sem_t empty;
sem_t full;
pthread_mutex_t mutex;

/* Print current buffer state */
void print_buffer()
{
    printf("\nBuffer State:\n[ ");

    for (int i = 0; i < BUFFER_SIZE; i++)
    {
        if (buffer[i] == 0)
            printf("_ ");
        else
            printf("%d ", buffer[i]);
    }

    printf("]\n");
    printf("   ");
    for (int i = 0; i < BUFFER_SIZE; i++)
        printf("%d ", i);
    printf("\n");

    printf("in  = %d\n", in);
    printf("out = %d\n", out);

    printf("--------------------------------------\n");
}

/* Producer Thread */
void *producer(void *arg)
{
    for (int item = 1; item <= ITEMS_TO_PRODUCE; item++)
    {
        sleep(1);  // Simulate production time

        sem_wait(&empty);              // Wait for empty slot
        pthread_mutex_lock(&mutex);    // Enter critical section

        buffer[in] = item;
        printf("\nProducer produced: %d at position %d\n", item, in);

        in = (in + 1) % BUFFER_SIZE;

        print_buffer();

        pthread_mutex_unlock(&mutex);  // Exit critical section
        sem_post(&full);               // Signal item available
    }

    pthread_exit(NULL);
}

/* Consumer Thread */
void *consumer(void *arg)
{
    int item;

    for (int i = 1; i <= ITEMS_TO_PRODUCE; i++)
    {
        sem_wait(&full);               // Wait for available item
        pthread_mutex_lock(&mutex);    // Enter critical section

        item = buffer[out];
        buffer[out] = 0;               // Mark slot as empty

        printf("\nConsumer consumed: %d from position %d\n", item, out);

        out = (out + 1) % BUFFER_SIZE;

        print_buffer();

        pthread_mutex_unlock(&mutex);  // Exit critical section
        sem_post(&empty);              // Signal empty slot available

        sleep(2);  // Simulate slower consumption
    }

    pthread_exit(NULL);
}

int main()
{
    pthread_t producer_thread, consumer_thread;

    /* Initialize buffer with zeros */
    for (int i = 0; i < BUFFER_SIZE; i++)
        buffer[i] = 0;

    printf("Initial Buffer State:\n");
    print_buffer();

    /* Initialize semaphores */
    sem_init(&empty, 0, BUFFER_SIZE);  // 5 empty slots
    sem_init(&full, 0, 0);             // 0 full slots

    /* Initialize mutex */
    pthread_mutex_init(&mutex, NULL);

    /* Create threads */
    pthread_create(&producer_thread, NULL, producer, NULL);
    pthread_create(&consumer_thread, NULL, consumer, NULL);

    /* Wait for threads to finish */
    pthread_join(producer_thread, NULL);
    pthread_join(consumer_thread, NULL);

    /* Cleanup */
    sem_destroy(&empty);
    sem_destroy(&full);
    pthread_mutex_destroy(&mutex);

    printf("\nAll items produced and consumed successfully.\n");

    return 0;
}