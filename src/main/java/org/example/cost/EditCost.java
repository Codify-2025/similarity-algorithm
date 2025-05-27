package org.example.cost;

import org.example.model.TreeNode;

// TED에서 사용하는 편집 연산에 대한 비용, 현재는 고정 비용 사용
public class EditCost {
    public static int insertCost(TreeNode node) {
        return 1;
    }

    public static int deleteCost(TreeNode node) {
        return 1;
    }

    public static int renameCost(TreeNode a, TreeNode b) {
        return a.label.equals(b.label) ? 0 : 1;
    }
}
