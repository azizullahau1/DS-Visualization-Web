package org.example.dsvisualizationweb.leetcode.bst;

import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode 98 — Validate Binary Search Tree (Medium)
 *
 * Given the root of a binary tree, determine if it is a valid BST.
 *
 * A valid BST:
 *   - Left subtree contains only nodes with values LESS THAN the node's value
 *   - Right subtree contains only nodes with values GREATER THAN the node's value
 *   - Both subtrees must also be valid BSTs
 *
 * Example 1:
 *       2
 *      / \
 *     1   3
 * Output: true
 *
 * Example 2:
 *       5
 *      / \
 *     1   4
 *        / \
 *       3   6
 * Output: false (4 is in right subtree of 5 but 4 < 5)
 *
 * Approach: Recursive with min/max bounds.
 *   - Every node must fall within a valid range (min, max)
 *   - Going left  → update max to current node's value
 *   - Going right → update min to current node's value
 */
public class ValidateBST {

    List<TreeNode> invalidTrees = new ArrayList<>();

    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int val) { this.val = val; }
    }

    public boolean validate(TreeNode root,long min, long max) {
        // TODO: implement

        if (root == null){
            return true;
        }

        if(root.val < min || root.val > max){
            return false;
        }

        boolean leftSubTreeRes = validate(root.left,min,root.val);
        boolean rightSubTreeRes = validate(root.right,root.val,max);

        return leftSubTreeRes && rightSubTreeRes;
    }




    // -------------------------------------------------------------------------
    // Test
    // -------------------------------------------------------------------------
    public static void main(String[] args) {
        ValidateBST solution = new ValidateBST();

        // Test 1: valid BST → expected true
        TreeNode root1 = new TreeNode(2);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(3);
        System.out.println("Test 1: " + solution.validate(root1,Integer.MIN_VALUE,Integer.MAX_VALUE)); // true

//        // Test 2: invalid BST → expected false
        TreeNode root2 = new TreeNode(5);
        root2.left = new TreeNode(1);
        root2.right = new TreeNode(4);
        root2.right.left = new TreeNode(3);
        root2.right.right = new TreeNode(6);
        System.out.println("Test 2: " + solution.validate(root2,Integer.MIN_VALUE,Integer.MAX_VALUE)); // false

        // Test 3: tricky — locally valid but globally invalid → expected false
        //       10
        //      /  \
        //     5   15
        //        /  \
        //       6   20
        TreeNode root3 = new TreeNode(10);
        root3.left = new TreeNode(5);
        root3.right = new TreeNode(15);
        root3.right.left = new TreeNode(6);   // 6 < 10, invalid!
        root3.right.right = new TreeNode(20);
        System.out.println("Test 3: " + solution.validate(root3,Integer.MIN_VALUE,Integer.MAX_VALUE)); // false
    }
}
