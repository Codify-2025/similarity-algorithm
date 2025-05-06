package org.example;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.ast.ASTVectorizer;
import org.example.similarity.CosineSimilarity;

import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode ast1 = mapper.readTree(new File("src/main/resources/remove1.json"));
        JsonNode ast2 = mapper.readTree(new File("src/main/resources/remove2.json"));

        var vec1 = ASTVectorizer.buildTypeVector(ast1);
        var vec2 = ASTVectorizer.buildTypeVector(ast2);

        double similarity = CosineSimilarity.calculate(vec1, vec2);
        System.out.println("Cosine Similarity: " + similarity);
    }
}