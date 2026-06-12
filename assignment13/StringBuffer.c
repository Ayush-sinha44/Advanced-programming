/*
 * Assignment 13: Dynamic String Buffer in C
 * Implements a heap-allocated, auto-growing string buffer.
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

/* ── Struct Definition ─────────────────────────────────────────── */

typedef struct {
    char   *data;       /* heap-allocated character buffer          */
    size_t  length;     /* current string length (excl. '\0')       */
    size_t  capacity;   /* total allocated bytes (incl. '\0' slot)  */
} StringBuffer;

/* ── sb_init ────────────────────────────────────────────────────── */
/*
 * Allocates a StringBuffer on the heap and initialises its internal
 * data buffer to `initial_capacity` bytes.
 *
 * Returns a pointer to the new StringBuffer, or NULL on failure.
 */
StringBuffer *sb_init(size_t initial_capacity) {
    /* Guard: sensible minimum so we always have room for '\0' */
    if (initial_capacity == 0) initial_capacity = 1;

    StringBuffer *sb = (StringBuffer *)malloc(sizeof(StringBuffer));
    if (sb == NULL) {
        fprintf(stderr, "sb_init: failed to allocate StringBuffer struct\n");
        return NULL;
    }

    sb->data = (char *)malloc(initial_capacity);
    if (sb->data == NULL) {
        fprintf(stderr, "sb_init: failed to allocate data buffer\n");
        free(sb);           /* don't leak the struct */
        return NULL;
    }

    sb->data[0]  = '\0';
    sb->length   = 0;
    sb->capacity = initial_capacity;
    return sb;
}

/* ── sb_append ──────────────────────────────────────────────────── */
/*
 * Appends `str` to the buffer, growing it (doubling) as needed.
 *
 * Returns  0 on success.
 * Returns -1 on failure (realloc refused); the buffer is left intact.
 */
int sb_append(StringBuffer *sb, const char *str) {
    if (sb == NULL || str == NULL) return -1;

    size_t str_len     = strlen(str);
    size_t needed      = sb->length + str_len + 1; /* +1 for '\0' */

    /* ── Grow loop: double until we fit ── */
    if (needed > sb->capacity) {
        size_t new_cap = sb->capacity;
        while (new_cap < needed) new_cap *= 2;

        /* Safe realloc: use a temp pointer so we never clobber sb->data */
        char *tmp = (char *)realloc(sb->data, new_cap);
        if (tmp == NULL) {
            fprintf(stderr,
                "sb_append: realloc failed (needed %zu bytes) – buffer unchanged\n",
                new_cap);
            return -1;              /* original buffer still valid */
        }

        printf("  [GROW] capacity %zu → %zu bytes\n", sb->capacity, new_cap);
        sb->data     = tmp;
        sb->capacity = new_cap;
    }

    /* Append and update length */
    memcpy(sb->data + sb->length, str, str_len + 1); /* +1 copies '\0' */
    sb->length += str_len;
    return 0;
}

/* ── sb_free ────────────────────────────────────────────────────── */
/*
 * Destructor: frees the internal data buffer, then the struct itself.
 * Sets the caller's pointer to NULL via double-pointer to prevent
 * use-after-free.
 */
void sb_free(StringBuffer **sb) {
    if (sb == NULL || *sb == NULL) return;
    free((*sb)->data);   /* 1. free the character buffer */
    free(*sb);           /* 2. free the struct           */
    *sb = NULL;          /* 3. null out the caller's ptr */
}

/* ── Helper: print buffer state ─────────────────────────────────── */
static void sb_print_state(const StringBuffer *sb, const char *label) {
    printf("\n── %s ──\n", label);
    printf("  data     : \"%s\"\n",  sb->data);
    printf("  length   : %zu\n",    sb->length);
    printf("  capacity : %zu\n",    sb->capacity);
}

/* ── main: demonstration ────────────────────────────────────────── */
int main(void) {
    printf("╔══════════════════════════════════════╗\n");
    printf("║   Dynamic String Buffer — Demo       ║\n");
    printf("╚══════════════════════════════════════╝\n\n");

    /* Start with a tiny capacity so we can watch it grow */
    StringBuffer *sb = sb_init(8);
    if (sb == NULL) return EXIT_FAILURE;

    sb_print_state(sb, "After sb_init(8)");

    /* ── Append 1: fits within initial capacity ── */
    sb_append(sb, "Hello");
    sb_print_state(sb, "After append(\"Hello\")");

    /* ── Append 2: triggers FIRST grow (8 → 16) ── */
    sb_append(sb, ", World");
    sb_print_state(sb, "After append(\", World\")  [GROW #1]");

    /* ── Append 3: triggers SECOND grow (16 → 32) ── */
    sb_append(sb, "! This is a longer sentence.");
    sb_print_state(sb, "After append(\"! This is a longer sentence.\")  [GROW #2]");

    /* ── Append 4: still fits in current capacity ── */
    sb_append(sb, " -- done.");
    sb_print_state(sb, "After append(\" -- done.\")");

    /* ── Free everything ── */
    printf("\n── Freeing StringBuffer ──\n");
    sb_free(&sb);
    printf("  sb is now %s\n", sb == NULL ? "NULL (safe)" : "NON-NULL (leak!)");

    printf("\nAll memory freed. No leaks.\n");
    return EXIT_SUCCESS;
}