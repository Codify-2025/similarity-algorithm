package org.example.ast;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;
import java.util.Map;

public class ASTVectorizer {

    public static Map<String, Integer> buildTypeVector(JsonNode root) {
        Map<String, Integer> vector = new HashMap<>();
        traverse(root, vector);
        return vector;
    }

    // 벡터 정규화
    /*public static Map<String, Double> buildNormalizedTypeVector(JsonNode root) {
        Map<String, Integer> rawVector = new HashMap<>();
        traverse(root, rawVector);

        int total = rawVector.values().stream().mapToInt(Integer::intValue).sum();
        Map<String, Double> normalized = new HashMap<>();
        for (Map.Entry<String, Integer> entry : rawVector.entrySet()) {
            normalized.put(entry.getKey(), entry.getValue() / (double) total);
        }
        return normalized;
    }*/

    private static void traverse(JsonNode node, Map<String, Integer> vector) {
        if (node.has("type")) {
            String type = node.get("type").asText();
            vector.put(type, vector.getOrDefault(type, 0) + 1);
        }

        if (node.has("children")) {
            for (JsonNode child : node.get("children")) {
                traverse(child, vector);
            }
        }
    }
}
