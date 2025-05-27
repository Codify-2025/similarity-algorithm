package org.example.similarity;

import org.example.cost.EditCost;
import org.example.model.TreeNode;

import java.util.List;

// 두 개의 TreeNode를 비교하여 계산
public class TreeEditDistance {
    public static int compute(TreeNode a, TreeNode b) {
        if (a == null && b == null) return 0;
        if (a == null) return costInsertAll(b);
        if (b == null) return costDeleteAll(a);

        // 한 트리를 다른 트리로 바꾸기 위해 드는 비용 계산
        int cost = EditCost.renameCost(a, b);

        List<TreeNode> aChildren = a.children;
        List<TreeNode> bChildren = b.children;

        // dp: 중간 결과를 저장해두는 2차원 배열
        int[][] dp = new int[aChildren.size() + 1][bChildren.size() + 1];

        // dp 테이블 채우기
        for (int i = 0; i <= aChildren.size(); i++) {
            for (int j = 0; j <= bChildren.size(); j++) {
                if (i == 0) { // a가 자식이 없고 b만 있는 경우 → b를 전부 삽입
                    dp[i][j] = sumInsert(bChildren, 0, j);
                } else if (j == 0) { // b가 자식이 없고 a만 있는 경우 → a를 전부 삭제
                    dp[i][j] = sumDelete(aChildren, 0, i);
                } else { // 일반적인 상황
                    int delete = dp[i - 1][j] + costDelete(aChildren.get(i - 1));
                    int insert = dp[i][j - 1] + costInsert(bChildren.get(j - 1));
                    int rename = dp[i - 1][j - 1] + compute(aChildren.get(i - 1), bChildren.get(j - 1));
                    dp[i][j] = Math.min(delete, Math.min(insert, rename)); // 최소 비용을 dp[i][j]에 저장
                }
            }
        }

        return cost + dp[aChildren.size()][bChildren.size()];
    }

    private static int costDelete(TreeNode node) {
        return EditCost.deleteCost(node) + costDeleteAll(node);
    }

    private static int costInsert(TreeNode node) {
        return EditCost.insertCost(node) + costInsertAll(node);
    }

    private static int costDeleteAll(TreeNode node) {
        int cost = 0;
        for (TreeNode child : node.children) {
            cost += costDelete(child);
        }
        return cost;
    }

    private static int costInsertAll(TreeNode node) {
        int cost = 0;
        for (TreeNode child : node.children) {
            cost += costInsert(child);
        }
        return cost;
    }

    private static int sumInsert(List<TreeNode> nodes, int from, int to) {
        int cost = 0;
        for (int i = from; i < to; i++) {
            cost += costInsert(nodes.get(i));
        }
        return cost;
    }

    private static int sumDelete(List<TreeNode> nodes, int from, int to) {
        int cost = 0;
        for (int i = from; i < to; i++) {
            cost += costDelete(nodes.get(i));
        }
        return cost;
    }
}
