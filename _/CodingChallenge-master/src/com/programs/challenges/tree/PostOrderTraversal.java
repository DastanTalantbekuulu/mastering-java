package com.programs.challenges.tree;

import java.util.Stack;

public class PostOrderTraversal {

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
        if(root == null){
            return;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        Stack<Integer> out = new Stack<>();

        while(!stack.isEmpty()){

            TreeNode current = stack.pop();
            out.push(current.data);

            if(current.left != null){
                stack.push(current.left);
            }
            if(current.right != null){
                stack.push(current.right);
            }
        }

        while (!out.isEmpty())
            System.out.print(out.pop()+" ");

    }

    static void traverseRecursive(TreeNode root){

        if(root == null)
            return;

        traverseRecursive(root.left);

        traverseRecursive(root.right);

        System.out.print(root.data+" ");

    }
}
