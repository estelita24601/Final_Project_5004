package treeADT;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

public class LeafNode<T> extends TreeNode<T> {
    public LeafNode(T initData) {
        super(initData);
        this.children = new ArrayList<>();
    }

    public LeafNode(T initData, TreeNode<T> initParent) {
        super(initData, initParent);
        this.children = new ArrayList<>();
    }

    @Override
    public void moveChildren(Predicate<T> findChildrenToReassign, BranchNode<T> newParent) {
    }

    @Override
    public void moveChildren(BranchNode<T> newParent) {

    }

    @Override //leaf nodes can't have children
    public boolean addChild(TreeNode<T> newChild) {
        return false;
    }

    @Override
    public boolean addChild(T newChildData, Predicate<T> canBeBranch) {
        return false;
    }

    @Override //leaf nodes don't have children
    public boolean deleteChild(Predicate<T> findChildToDelete) {
        return false;
    }

    @Override
    public boolean deleteChild(TreeNode<T> childToDelete) {
        return false;
    }

    @Override
    public TreeNode<T> deepCopy() {
        return new LeafNode<>(this.data, this.parent.deepCopy());
    }

    @Override
    public <R> TreeNode<R> map(Function<T, R> converter) {
        return new LeafNode<>(converter.apply(this.data));
    }
}
