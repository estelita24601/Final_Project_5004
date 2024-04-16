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
    public boolean addChild(T newChildData) {
        return false; //have no children so can't be successful in adding a new child
    }

    @Override
    public void moveChildren(Predicate<T> findChildrenToReassign, BranchNode<T> newParent) {
        //have no children so do nothing
    }

    @Override
    public void moveChildren(BranchNode<T> newParent) {
        //have no children so do nothing
    }

    @Override
    public boolean addChild(TreeNode<T> newChild) {
        return false; //leaf nodes can't have children
    }

    @Override
    public boolean addChild(T newChildData, Predicate<T> canBeBranch) {
        return false; //leaf nodes can't have children
    }

    @Override
    public boolean deleteChild(Predicate<T> findChildToDelete) {
        return false; //leaf nodes don't have children
    }

    @Override
    public boolean deleteChild(TreeNode<T> childToDelete) {
        return false; //leaf nodes don't have children
    }

    @Override
    public TreeNode<T> deepCopy() {
        return new LeafNode<>(this.data, this.parent.deepCopy());
    }

    /**
     * one of the base cases for our map function
     *
     * @param <R>       the data type we want to map to
     * @param converter function that converts data to the desired type
     * @return a leaf node with the data mapped to the desired data type
     */
    @Override
    public <R> TreeNode<R> map(Function<T, R> converter) {
        return new LeafNode<>(converter.apply(this.data));
    }


}
