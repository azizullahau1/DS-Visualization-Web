package org.example.dsvisualizationweb.servicesimpls;

import com.sun.source.tree.Tree;
import org.example.dsvisualizationweb.dtos.general.TreeNode;
import org.example.dsvisualizationweb.dtos.response.TreeNodeResponse;
import org.example.dsvisualizationweb.dtos.response.TreeResponse;
import org.example.dsvisualizationweb.services.BinaryTreeService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service("bstService")
@SessionScope
public class BSTServiceImpl implements BinaryTreeService {

    private TreeNode root;
    private int size;

    // -------------------------------------------------------------------------
    // INSERT
    // Rule: value < node → go left. value > node → go right. Equal → ignore (no duplicates).
    // -------------------------------------------------------------------------

    @Override
    public TreeResponse insert(int value) {
        // TODO: recursively find correct position and insert

        if(root == null){
            this.root = new TreeNode(value);
            size++;
            return buildResponse("Inserted " + value);
        }

        TreeNode node = root;

        while (true){ // keep iterating untill both childs are null
            if(value < node.value){
                if(node.left == null){
                    node.left = new TreeNode(value);
                    size++;
                    break;
                }
                node = node.left;
            }
            else if(value > node.value){
                if(node.right == null){
                    node.right = new TreeNode(value);
                    size++;
                    break;
                }
                node = node.right;
            }
            else{
                return buildResponse("Value already Exists, No duplicates allwoed!");
            }
        }

        return buildResponse("Inserted " + value);
    }

    // -------------------------------------------------------------------------
    // DELETE
    // Three cases:
    //   1. Node has no children  → just remove it
    //   2. Node has one child    → replace node with its child
    //   3. Node has two children → replace value with inorder successor (smallest in right subtree), delete successor
    // -------------------------------------------------------------------------

    @Override
    public TreeResponse delete(int value) {
        // TODO

        TreeNode tempNode = root;
        TreeNode parentNode = null;
        TreeNode nodeToBeDeleted = null;

        while(tempNode!=null){

            if(tempNode.value == value){
                nodeToBeDeleted = tempNode;
                break;
            }
            else if(value < tempNode.value){
                parentNode = tempNode;
                tempNode = tempNode.left;
            }
            else{
                parentNode = tempNode;
                tempNode = tempNode.right;
            }
        }

        if(nodeToBeDeleted == null){
            return buildResponse("Node not found " + value);
        }

        // case 1 - No Childs
        if(nodeToBeDeleted.left == null && nodeToBeDeleted.right == null){
            if(parentNode == null){
                root = null; // when root is deleted and it has no childs
                size--;
            }
            else{
                if(nodeToBeDeleted.value < parentNode.value){
                    // left child of parent
                    parentNode.left = null;
                    size--;
                }
                else{
                    // right child of parent
                    parentNode.right = null;
                    size--;
                }
            }
            return buildResponse("Deleted " + value);
        }

        // case 2: when of the child is present
        else if( nodeToBeDeleted.left == null || nodeToBeDeleted.right == null){

            if(parentNode == null){
                root = nodeToBeDeleted.left !=null ? nodeToBeDeleted.left : nodeToBeDeleted.right;
                size--;
            }
            else{
                if(nodeToBeDeleted.value<parentNode.value){
                    parentNode.left = nodeToBeDeleted.left != null ? nodeToBeDeleted.left : nodeToBeDeleted.right;
                    size--;
                }
                else{
                    parentNode.right = nodeToBeDeleted.left != null ? nodeToBeDeleted.left : nodeToBeDeleted.right;
                    size--;
                }
            }

            return buildResponse("Deleted " + value);

        }

        // case 3: both childern are present
        else{

            // No left successor
            if(nodeToBeDeleted.right.left == null){
              nodeToBeDeleted.right.left = nodeToBeDeleted.left;

              if(parentNode == null){
                  root = nodeToBeDeleted.right;
              }

              else {
                  if (nodeToBeDeleted.value < parentNode.value) {
                      parentNode.left = nodeToBeDeleted.right;
                  } else {
                      parentNode.right = nodeToBeDeleted.right;
                  }
              }
              size--;
            }
            else{
                // go to right -> left most child
                TreeNode leftMostSuccessor = nodeToBeDeleted.right.left;
                TreeNode leftMostSuccessorParent = nodeToBeDeleted.right;
                while (leftMostSuccessor.left != null){
                    leftMostSuccessorParent = leftMostSuccessor;
                    leftMostSuccessor = leftMostSuccessor.left;
                }

                // Now I have parentNode , NodeToBeDeleted , LeftMostSuccessor


                leftMostSuccessorParent.left = leftMostSuccessor.right;

                TreeNode leftTreeForDeletedNode = nodeToBeDeleted.left;
                TreeNode rightTreeForDeletedNode = nodeToBeDeleted.right;

                leftMostSuccessor.left = leftTreeForDeletedNode;
                leftMostSuccessor.right = rightTreeForDeletedNode;


                if(parentNode == null){
                    root = leftMostSuccessor;
                }
                else {
                    if (nodeToBeDeleted.value < parentNode.value) {
                        parentNode.left = leftMostSuccessor;
                    } else {
                        parentNode.right = leftMostSuccessor;
                    }
                }
                size--;
            }

            return buildResponse("Deleted " + value);
        }
    }

    // -------------------------------------------------------------------------
    // SEARCH
    // -------------------------------------------------------------------------

    @Override
    public TreeResponse search(int value) {
        // TODO: traverse left/right based on comparison, return found/not found

        if(root == null){
            return buildResponse("Empty Tree!");
        }

        TreeNode tempNode = root;

        while(tempNode != null){
            if(value == tempNode.value){
                return buildResponse("Node found");
            }

            if(value < tempNode.value){
                tempNode = tempNode.left;
            }
            else{
                tempNode = tempNode.right;
            }
        }

        return buildResponse("Node not fond: " + value);
    }

    // -------------------------------------------------------------------------
    // TRAVERSALS
    // -------------------------------------------------------------------------

    @Override
    public TreeResponse inorder() {
        // TODO: Left → Root → Right (produces sorted output for BST)
        List<Integer> result = new ArrayList<>();
        inorderTraversal(root, result);
        TreeResponse response = buildResponse("Inorder traversal");
        response.setInorder(result);
        return response;
    }

    private void inorderTraversal(TreeNode node, List<Integer> result) {

        if(node == null)
            return;

        inorderTraversal(node.left,result);
        result.add(node.value);
        inorderTraversal(node.right,result);
    }

    @Override
    public TreeResponse preorder() {
        // TODO: Root → Left → Right
        List<Integer> result = new ArrayList<>();
        preorderTraversal(root, result);
        TreeResponse response = buildResponse("Preorder traversal");
        response.setPreorder(result);
        return response;
    }

    private void preorderTraversal(TreeNode node, List<Integer> result) {
        // TODO

        if(node == null)
            return;

        result.add(node.value);
        preorderTraversal(node.left,result);
        preorderTraversal(node.right,result);
    }

    @Override
    public TreeResponse postorder() {
        // TODO: Left → Right → Root
        List<Integer> result = new ArrayList<>();
        postorderTraversal(root, result);
        TreeResponse response = buildResponse("Postorder traversal");
        response.setPostorder(result);
        return response;
    }

    private void postorderTraversal(TreeNode node, List<Integer> result) {
        // TODO

        if(node == null)
            return ;

        postorderTraversal(node.left,result);
        postorderTraversal(node.right,result);
        result.add(node.value);
    }

    @Override
    public TreeResponse levelorder() {
        // TODO: BFS — use a Queue. Poll node, add value, enqueue left and right children.
        List<Integer> result = new ArrayList<>();
        TreeResponse response = buildResponse("Level-order traversal");
        response.setLevelorder(result);
        return response;
    }

    // -------------------------------------------------------------------------
    // STATE & CLEAR
    // -------------------------------------------------------------------------

    @Override
    public TreeResponse getState() {
        return buildResponse("Current state");
    }

    @Override
    public TreeResponse clear() {
        root = null;
        size = 0;
        return buildResponse("Tree cleared");
    }

    // -------------------------------------------------------------------------
    // HELPER — builds TreeResponse from current tree state
    // -------------------------------------------------------------------------

    private TreeResponse buildResponse(String message) {
        TreeResponse response = new TreeResponse();
        response.setRoot(toResponseNode(root));
        response.setSize(size);
        response.setHeight(computeHeight(root));
        response.setMessage(message);
        return response;
    }

    // Converts the internal TreeNode tree into a TreeNodeResponse tree (for UI)
    private TreeNodeResponse toResponseNode(TreeNode node) {
        if (node == null) return null;
        TreeNodeResponse res = new TreeNodeResponse();
        res.setValue(node.value);
        res.setLeft(toResponseNode(node.left));
        res.setRight(toResponseNode(node.right));
        return res;
    }

    private int computeHeight(TreeNode node) {
        if (node == null) return 0;
        return 1 + Math.max(computeHeight(node.left), computeHeight(node.right));
    }
}
