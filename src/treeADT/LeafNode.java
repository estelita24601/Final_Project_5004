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
    ArrayList<TreeNode<T>> getChildren() {
        return null;
    }


    @Override //leaf nodes can't have children
    boolean addChild(TreeNode<T> newChild) {
        return false;
    }

    @Override //leaf nodes don't have children
    boolean removeChild(Predicate<T> identifier) {
        return false;
    }

    @Override
    TreeNode<T> deepCopy() {
        return new LeafNode<T>(this.data, this.parent.deepCopy());
    }

    @Override
    TreeNode<T> partialCopy() {
        return new LeafNode<T>(this.data);
    }

    @Override
    TreeNode<T> findNode(Predicate<T> identifier) {
        if(identifier.test(this.data)){
            return this;
        }else{
            return null;
        }
    }

    @Override
    <R> R fold(R initial, BiFunction<R, T, R> combiner) {
        return combiner.apply(initial, this.data);
    }

    @Override
    List<T> filterToList(Predicate<T> filter) {
        List<T> filteredList = new ArrayList<T>();
        if(filter.test(this.data)){
            filteredList.add(this.data);
        }
        return filteredList;
    }

    @Override
    <R> TreeNode<R> map(Function<T, R> converter) {
        return new LeafNode<R>(converter.apply(this.data));
    }

    @Override
    <R> List<R> mapToList(Function<T, R> converter) {
        List<R> mappedList = new ArrayList<R>();
        mappedList.add(converter.apply(this.data));
        return mappedList;
    }
}
