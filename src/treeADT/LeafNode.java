package treeADT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class LeafNode<T> extends TreeNode<T> {
    public LeafNode(T initData) {
        super(initData);
        this.children = null;
    }

    public LeafNode(T initData, TreeNode<T> initParent) {
        super(initData, initParent);
        this.children = null;
    }

    @Override //leaf nodes don't have children
    public ArrayList<TreeNode<T>> getChildren() {
        return null;
    }

    @Override
    public int countIf(Predicate<T> filter) {
        if(filter.test(this.data)){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public int countAll() {
        return 1;
    }

    @Override
    public List<T> toList() {
        Predicate<T> getAll = (t) -> true;
        return filterToList(getAll);
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
    public void moveChildren(Predicate<T> findChildrenToReassign, BranchNode<T> newParent){
        return;
    }

    @Override
    public void moveChildren(BranchNode<T> newParent) {

    }

    @Override
    public TreeNode<T> deepCopy() {
        return new LeafNode<T>(this.data, this.parent.deepCopy());
    }

    @Override
    public TreeNode<T> findNode(Predicate<T> identifier) {
        if (identifier.test(this.data)) {
            return this;
        } else {
            return null;
        }
    }

    @Override
    public <R> R fold(R initial, BiFunction<R, T, R> combiner) {
        return combiner.apply(initial, this.data);
    }

    @Override
    public List<T> filterToList(Predicate<T> filter) {
        List<T> filteredList = new ArrayList<T>();
        if (filter.test(this.data)) {
            filteredList.add(this.data);
        }
        return filteredList;
    }

    @Override
    public <R> TreeNode<R> map(Function<T, R> converter) {
        return new LeafNode<R>(converter.apply(this.data));
    }

    @Override
    public <R> List<R> mapToList(Function<T, R> converter) {
        List<R> mappedList = new ArrayList<R>();
        mappedList.add(converter.apply(this.data));
        return mappedList;
    }
}
