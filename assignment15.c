#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#define NUM_THREADS 4
#define INCREMENTS 1000000

long long counter = 0;
pthread_mutex_t lock;

/* ------------------ Without Mutex ------------------ */
void *increment_without_mutex(void *arg)
{
    for (long long i = 0; i < INCREMENTS; i++)
    {
        counter++;   // Race condition occurs here
    }
    return NULL;
}

/* ------------------ With Mutex ------------------ */
void *increment_with_mutex(void *arg)
{
    for (long long i = 0; i < INCREMENTS; i++)
    {
        pthread_mutex_lock(&lock);
        counter++;
        pthread_mutex_unlock(&lock);
    }
    return NULL;
}

int main()
{
    pthread_t threads[NUM_THREADS];

    /* ==========================================
       PART 1: Without Synchronization
       ========================================== */
    counter = 0;

    printf("Running without mutex...\n");

    for (int i = 0; i < NUM_THREADS; i++)
    {
        pthread_create(&threads[i], NULL, increment_without_mutex, NULL);
    }

    for (int i = 0; i < NUM_THREADS; i++)
    {
        pthread_join(threads[i], NULL);
    }

    printf("Expected Counter Value : %lld\n",
           (long long)NUM_THREADS * INCREMENTS);
    printf("Actual Counter Value   : %lld\n\n", counter);

    /* ==========================================
       PART 2: With Mutex Synchronization
       ========================================== */
    counter = 0;

    pthread_mutex_init(&lock, NULL);

    printf("Running with mutex...\n");

    for (int i = 0; i < NUM_THREADS; i++)
    {
        pthread_create(&threads[i], NULL, increment_with_mutex, NULL);
    }

    for (int i = 0; i < NUM_THREADS; i++)
    {
        pthread_join(threads[i], NULL);
    }

    printf("Expected Counter Value : %lld\n",
           (long long)NUM_THREADS * INCREMENTS);
    printf("Actual Counter Value   : %lld\n", counter);

    pthread_mutex_destroy(&lock);

    return 0;
}
