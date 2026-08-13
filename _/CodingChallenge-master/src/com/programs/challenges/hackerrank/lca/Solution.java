/**
 */
package com.programs.challenges.hackerrank.lca;

public class Solution {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		
		TreeNode lca = null;
		WrapperNode LCA = new WrapperNode(lca);
		
		
		if(isNodePresent(root, p) && isNodePresent(root, q)) {
			findLCA(root,LCA,p,q);
			lca = LCA.node;
		}
		
		if(lca == null) {
			System.out.println("Lowest Common Ancistor not found\n");
		} else {
			System.out.println("LCA is :"+lca.data);
		}
		
		return lca;
		
		
		
	}
	
	public static class WrapperNode{
		TreeNode node;
		WrapperNode(TreeNode node){
			this.node = node;
		}
	}
	
	private static boolean findLCA(TreeNode root, WrapperNode lca, TreeNode p, TreeNode q) {
		
		if(root == null) {
			return false;
		}
		
		if(root == p || root == q) {
			lca.node = root;
			return true;
		}
		
		boolean left = findLCA(root.left, lca, p, q);
		boolean right = findLCA(root.right, lca, p, q);
		
		if(left && right) {
			lca.node = root;
		}
		
		return left || right;
		
	}
	
	private static boolean isNodePresent(TreeNode root, TreeNode node) {
		if(root == null) {
			return false;
		}
		
		if(root == node) {
			return true;
		}
		
		return isNodePresent(root.left, node) || isNodePresent(root.right,node);
	}

		
	
	public class TreeNode{
		int data;
		TreeNode left = null;
		TreeNode right = null;
		
		TreeNode(int data){
			this.data = data;
		}
	}

}
