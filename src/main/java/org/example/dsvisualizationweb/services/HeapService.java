package org.example.dsvisualizationweb.services;

import org.example.dsvisualizationweb.dtos.response.HeapResponse;

public interface HeapService {

    // --- Modify ---
    HeapResponse insert(int value);   // add to end, bubble up
    HeapResponse extractRoot();       // remove min (or max), replace with last, bubble down
    HeapResponse clear();

    // --- Query ---
    HeapResponse peek();              // return root value without removing
    HeapResponse getState();
}
