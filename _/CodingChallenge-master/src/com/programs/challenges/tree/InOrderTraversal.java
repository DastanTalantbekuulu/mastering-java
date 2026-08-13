package com.programs.challenges.tree;

import java.util.Stack;

public class InOrderTraversal {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(50);
        root.insert(41);
        root.insert(38);
        root.insert(30);
        root.insert(66);
        root.insert(77);
        root.insert(61);

        System.out.println("=========Iterative Traversal============");
        traverseIterative(root);

        System.out.println("\n=========Recursive Traversal============");
        traverseRecursive(root);

    }

    static void traverseIterative(TreeNode root){
        if(root == null)
            return;

        TreeNode current = root;

        Stack<TreeNode> stack = new Stack<>();

        while(!stack.isEmpty()|| current != null){

            if(current != null){

                stack.push(current);
                current = current.left;

            }else{
                current = stack.pop();
                System.out.print(current.data+" ");
                current = current.right;
            }

        }

    }

    static void traverseRecursive(TreeNode root){
        if(root == null)
            return;

        traverseRecursive(root.left);

        System.out.print(root.data+" ");

        traverseRecursive(root.right);

    }


}
