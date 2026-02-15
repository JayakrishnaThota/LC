import java.util.*;

/**
 * PHASE 1: Simple Key-Value Store
 * Goal: Implement a basic in-memory store with O(1) set, get, and delete.
 */
class SimpleStore {
    private final Map<String, String> store = new HashMap<>();

    // TC: O(1), SC: O(1)
    public void set(String key, String value) {
        store.put(key, value);
    }

    // TC: O(1), SC: O(1)
    public String get(String key) {
        return store.get(key);
    }

    // TC: O(1), SC: O(1)
    public void delete(String key) {
        store.remove(key);
    }
}

/**
 * PHASE 2: Single Transaction Support
 * Goal: Support atomic begin, commit, and rollback for one level of changes. 
 * Use a buffer to avoid copying the entire store and a sentinel to track deletions.
 */
class SingleTransactionStore {
    private final Map<String, String> globalStore = new HashMap<>();
    private Map<String, String> buffer = null;
    private static final String DELETE_SENTINEL = "___DELETED___";

    // TC: O(1), SC: O(1)
    public void begin() {
        if (buffer != null) throw new IllegalStateException("Transaction already active");
        buffer = new HashMap<>();
    }

    // TC: O(1), SC: O(1) - Checks buffer first
    public String get(String key) {
        if (buffer != null && buffer.containsKey(key)) {
            String val = buffer.get(key);
            return DELETE_SENTINEL.equals(val) ? null : val;
        }
        return globalStore.get(key);
    }

    // TC: O(1), SC: O(1)
    public void set(String key, String value) {
        if (buffer != null) buffer.put(key, value);
        else globalStore.put(key, value);
    }

    // TC: O(1), SC: O(1)
    public void delete(String key) {
        if (buffer != null) buffer.put(key, DELETE_SENTINEL);
        else globalStore.remove(key);
    }

    // TC: O(K) where K is number of changes in buffer, SC: O(1)
    public void commit() {
        if (buffer == null) throw new IllegalStateException("No active transaction");
        buffer.forEach((k, v) -> {
            if (DELETE_SENTINEL.equals(v)) globalStore.remove(k);
            else globalStore.put(k, v);
        });
        buffer = null;
    }

    // TC: O(1), SC: O(1)
    public void rollback() {
        buffer = null;
    }
}

/**
 * PHASE 3: Nested Transactions Support
 * Goal: Support multiple active transactions using a stack. 
 * Child commits merge into parent; parent rollbacks discard all child changes.
 */
class NestedTransactionStore {
    private final Map<String, String> globalStore = new HashMap<>();
    private final Deque<Map<String, String>> stack = new ArrayDeque<>();
    private static final String DELETE_SENTINEL = "___DELETED___";

    // TC: O(1), SC: O(1)
    public void begin() {
        stack.push(new HashMap<>());
    }

    // TC: O(D) where D is stack depth, SC: O(1)
    public String get(String key) {
        for (Map<String, String> layer : stack) {
            if (layer.containsKey(key)) {
                String val = layer.get(key);
                return DELETE_SENTINEL.equals(val) ? null : val;
            }
        }
        return globalStore.get(key);
    }

    // TC: O(1), SC: O(1)
    public void set(String key, String value) {
        if (stack.isEmpty()) globalStore.put(key, value);
        else stack.peek().put(key, value);
    }

    // TC: O(1), SC: O(1)
    public void delete(String key) {
        if (stack.isEmpty()) globalStore.remove(key);
        else stack.peek().put(key, DELETE_SENTINEL);
    }

    // TC: O(K) where K is changes in top layer, SC: O(1)
    public void commit() {
        if (stack.isEmpty()) throw new IllegalStateException();
        Map<String, String> top = stack.pop();
        Map<String, String> target = stack.isEmpty() ? globalStore : stack.peek();
        
        top.forEach((k, v) -> {
            if (DELETE_SENTINEL.equals(v)) target.remove(k);
            else target.put(k, v);
        });
    }

    // TC: O(1), SC: O(1)
    public void rollback() {
        if (stack.isEmpty()) throw new IllegalStateException();
        stack.pop();
    }
}
