package treeADT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class BranchNode<T> extends LeafNode<T> {
    public BranchNode(T initData) {
        super(initData);
        this.children = new ArrayList<TreeNode<T>>();
    }

    public BranchNode(T initData, TreeNode<T> initParent) {
        this(initData);
        this.parent = initParent;
    }

    public BranchNode(T initData, TreeNode<T> initParent, ArrayList<TreeNode<T>> initChildren) {
        this(initData, initParent);
        this.children = initChildren;
    }

    public BranchNode(T initData, ArrayList<TreeNode<T>> initChildren) {
        this(initData);
        this.children = initChildren;
    }

    @Override
    public ArrayList<TreeNode<T>> getChildren() {
        return this.children;
    }

    @Override
    public boolean addChild(TreeNode<T> newChild) {
        if (newChild == null) {
            return false;
        } else {
            this.children.add(newChild);
            return true;
        }
    }

    @Override
    public boolean removeChild(Predicate<T> identifier) {
        for (TreeNode<T> child : children) {
            if (identifier.test(child.getData())) {
                this.children.remove(child);
                return true;
            }
        }
        return false; //wasn't able to find child to remove
    }

    @Override
    public TreeNode<T> deepCopy() {
        return new BranchNode<T>(this.data, this.parent, this.children);
    }

    @Override
    public TreeNode<T> findNode(Predicate<T> identifier) {
        for (TreeNode<T> child : this.children) {
            if (identifier.test(child.getData())) {
                return child;
            }
        }
        return null;
    }

    @Override
    public <R> R fold(R initial, BiFunction<R, T, R> combiner) {
        initial = super.fold(initial, combiner);
        for (TreeNode<T> child : this.children) {
            initial = child.fold(initial, combiner);
        }
        return initial;
    }

    @Override
    public int countIf(Predicate<T> filter){
        BiFunction<Integer, T, Integer> counter = (number, t) -> {
            if(filter.test(t)){
                return number + 1;
            }
            return number;
        };

        int runningTotal = counter.apply(0, this.data);
        for(TreeNode<T> child : this.children){
            runningTotal += child.countIf(filter);
        }
        return runningTotal;
    }

    @Override
    public int countAll(){
        Predicate<T> countAll = (t) -> true;
        return countIf(countAll);
    }

    @Override
    public List<T> toList() {
        Predicate<T> all = (t) -> true;
        return filterToList(all);
    }

    @Override
    public List<T> filterToList(Predicate<T> filter) {
        List<T> result = super.filterToList(filter);
        for (TreeNode<T> child : children) {
            result.addAll(child.filterToList(filter));
        }
        return result;
    }

    @Override
    public <R> TreeNode<R> map(Function<T, R> converter) {
        R mappedData = converter.apply(this.data);
        BranchNode<R> mappedNode = new BranchNode<>(mappedData);
        for (TreeNode<T> child : children) {
            mappedNode.children.add(child.map(converter));
        }

        return mappedNode;
    }

    @Override
    public <R> List<R> mapToList(Function<T, R> converter) {
        List<R> result = super.mapToList(converter);
        for (TreeNode<T> child : children) {
            result.addAll(child.mapToList(converter));
        }
        return result;
    }
}
