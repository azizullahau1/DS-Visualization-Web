package org.example.dsvisualizationweb.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeapResponse {
    private List<Integer> heap;   // the array — index 0 = root, UI renders this as a tree
    private int size;
    private String type;          // "MIN" or "MAX"
    private String message;
}
