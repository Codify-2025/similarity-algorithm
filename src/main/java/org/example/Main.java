package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ast.ASTVectorizer;
import org.example.ast.TreeNodeBuilder;
import org.example.model.TreeNode;
import org.example.similarity.CosineSimilarity;
import org.example.similarity.TreeEditDistance;

import java.io.File;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // 비교할 JSON 파일 리스트
        List<String> filenames = List.of(
                "test1.json",
                "test1-1.json",
                "test2.json",
                "test2-1.json"
                // 필요한 만큼 추가
        );

        // 모든 (i, j) 쌍 비교
        for (int i = 0; i < filenames.size(); i++) {
            for (int j = i + 1; j < filenames.size(); j++) {
                String file1 = filenames.get(i);
                String file2 = filenames.get(j);

                JsonNode ast1 = mapper.readTree(new File("src/main/resources/" + file1));
                JsonNode ast2 = mapper.readTree(new File("src/main/resources/" + file2));

                // 1차: AST 벡터화 및 코사인 유사도 계산
                var vec1 = ASTVectorizer.buildTypeVector(ast1);
                var vec2 = ASTVectorizer.buildTypeVector(ast2);
                double cosineSim = CosineSimilarity.calculate(vec1, vec2);
                /*Map<String, Double> vec1 = ASTVectorizer.buildNormalizedTypeVector(ast1);
                Map<String, Double> vec2 = ASTVectorizer.buildNormalizedTypeVector(ast2);
                double cosineSim = CosineSimilarity.calculate(vec1, vec2);*/

                System.out.printf("[%s vs %s] Cosine Similarity: %.3f\n", file1, file2, cosineSim);

                // 0.6 이상일 때만 2차 분석 수행
                if (cosineSim >= 0.6) {
                    TreeNode tree1 = TreeNodeBuilder.fromJson(ast1);
                    TreeNode tree2 = TreeNodeBuilder.fromJson(ast2);
                    int ted = TreeEditDistance.compute(tree1, tree2);
                    int maxSize = Math.max(countNodes(tree1), countNodes(tree2));
                    double normalizedSim = 1.0 - ((double) ted / maxSize);

                    System.out.printf("Tree Edit Distance: %d\n", ted);
                    System.out.printf("Normalized Similarity: %.3f\n", normalizedSim);
                } else {
                    System.out.println("Skipped Tree Edit Distance (cosine < 0.6)");
                }
                System.out.println(); // 구분선
            }
        }
    }

    // 트리 전체 노드 수 세기 (TED 정규화용)
    private static int countNodes(TreeNode node) {
        if (node == null) return 0;
        int count = 1;
        for (TreeNode child : node.children) {
            count += countNodes(child);
        }
        return count;
    }
}
