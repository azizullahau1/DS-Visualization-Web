package org.example.dsvisualizationweb.servicesimpls;

import org.example.dsvisualizationweb.dtos.response.HeapResponse;
import org.example.dsvisualizationweb.services.HeapService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Service("heapService")
@SessionScope
public class HeapServiceImpl implements HeapService {

    // The heap stored as an array. Index 0 = root.
    // Parent of i  → (i - 1) / 2
    // Left child   → 2i + 1
    // Right child  → 2i + 2
    private final List<Integer> heap = new ArrayList<>();

    // Change to "MAX" and flip the comparison in hasHigherPriority() to get a max-heap
    private static final String TYPE = "MIN";

    // -------------------------------------------------------------------------
    // INSERT
    // Steps:
    //   1. Add value to the end of the array
    //   2. Bubble up: while element has higher priority than its parent, swap and move up
    //   Stop when you reach index 0 (root) or parent already has higher priority
    // -------------------------------------------------------------------------

    @Override
    public HeapResponse insert(int value) {
        // TODO: implement
        return buildResponse("Inserted " + value);
    }

    // -------------------------------------------------------------------------
    // EXTRACT ROOT (extract-min for min-heap, extract-max for max-heap)
    // Steps:
    //   1. Save root value (index 0) to return in the message
    //   2. Move last element to index 0, remove last
    //   3. Bubble down: while element has lower priority than either child, swap with
    //      the higher-priority child and continue down
    //   Stop when no children, or both children have lower priority
    // -------------------------------------------------------------------------

    @Override
    public HeapResponse extractRoot() {
        // TODO: implement
        return buildResponse("Extracted root");
    }

    // -------------------------------------------------------------------------
    // PEEK
    // Just return index 0 without modifying the heap.
    // -------------------------------------------------------------------------

    @Override
    public HeapResponse peek() {
        // TODO: implement
        return buildResponse("Peek");
    }

    // -------------------------------------------------------------------------
    // STATE & CLEAR
    // -------------------------------------------------------------------------

    @Override
    public HeapResponse getState() {
        return buildResponse("Current state");
    }

    @Override
    public HeapResponse clear() {
        heap.clear();
        return buildResponse("Heap cleared");
    }

    // -------------------------------------------------------------------------
    // HELPERS — you may add private helpers (swap, parent index, child index etc.)
    // -------------------------------------------------------------------------

    /**
     * Returns true if the value at index `a` has higher priority than at index `b`.
     * For min-heap: smaller value = higher priority.
     * For max-heap: flip the comparison.
     */
    private boolean hasHigherPriority(int a, int b) {
        return heap.get(a) < heap.get(b);   // min-heap
    }

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // -------------------------------------------------------------------------
    // BUILD RESPONSE
    // -------------------------------------------------------------------------

    private HeapResponse buildResponse(String message) {
        return new HeapResponse(new ArrayList<>(heap), heap.size(), TYPE, message);
    }
}
