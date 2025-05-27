package org.example.ast;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.model.TreeNode;

// JSON 형태의 AST 트리를 재귀적으로 탐색해서 형태를 변환해주는 코드
public class TreeNodeBuilder {
    public static TreeNode fromJson(JsonNode json) {
        String label = json.has("type") ? json.get("type").asText() : "Unknown";
        TreeNode node = new TreeNode(label);

        if (json.has("children")) {
            for (JsonNode child : json.get("children")) {
                node.addChild(fromJson(child));
            }
        }

        return node;
    }
}
