package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ast.TreeNodeBuilder;
import org.example.model.TreeNode;
import org.example.similarity.TreeEditDistance;

import java.io.File;

public class MainTED {
    /*public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        JsonNode ast1 = mapper.readTree(new File("src/main/resources/test1.json"));
        JsonNode ast2 = mapper.readTree(new File("src/main/resources/test2.json"));

        // 변환
        TreeNode tree1 = TreeNodeBuilder.fromJson(ast1);
        TreeNode tree2 = TreeNodeBuilder.fromJson(ast2);

        // 편집 거리 계산
        int ted = TreeEditDistance.compute(tree1, tree2);
        int maxSize = Math.max(countNodes(tree1), countNodes(tree2));
        double similarity = 1.0 - ((double) ted / maxSize); // 정규화

        System.out.println("Tree Edit Distance: " + ted);
        System.out.println("Normalized Similarity: " + similarity);
    }

    // 정규화를 위해 트리 내 총 노드 수 세기
    private static int countNodes(TreeNode node) {
        if (node == null) return 0;
        int count = 1;
        for (TreeNode child : node.children) {
            count += countNodes(child);
        }
        return count;
    }*/
}
