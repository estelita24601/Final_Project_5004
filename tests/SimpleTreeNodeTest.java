import treeADT.BranchNode;
import treeADT.TreeNode;

import java.util.ArrayList;

public class SimpleTreeNodeTest {
    public static void main(String[] args) {
        BranchNode<Integer> intTree = new BranchNode<Integer>(24);
        intTree.addChild(new BranchNode<>(5));
        intTree.addChild(new BranchNode<>(6));
        intTree.addChild(new BranchNode<>(7));
        intTree.addChild(new BranchNode<>(8));
        intTree.addChild(new BranchNode<>(9));

        ArrayList<TreeNode<Integer>> intTreeList  = intTree.getChildren();
        for(TreeNode<Integer> child: intTreeList){
            System.out.println(child.getData());
        }
    }

}
