package treeADT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class BranchNode<T> extends LeafNode<T> {
    public BranchNode(T initData) {
        super(initData);
    }

    public BranchNode(T initData, TreeNode<T> initParent) {
        super(initData, initParent);
    }

    @Override
    ArrayList<TreeNode<T>> getChildren() {
        return this.children;
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
    boolean addChild(TreeNode<T> newChild) {
        if(newChild == null){
            return false;
        }else{
            this.children.add(newChild);
            return true;
        }
    }

    @Override
    boolean removeChild(Predicate<T> identifier) {
        for(TreeNode<T> child : children){
            if(identifier.test(child.getData())){
                this.children.remove(child);
                return true;
            }
        }
        return false; //wasn't able to find child to remove
    }

    @Override
    TreeNode<T> deepCopy() {
        return new BranchNode<T>(this.data, this.parent, this.children);
    }

    @Override
    TreeNode<T> findNode(Predicate<T> identifier) {
        for(TreeNode<T> child : this.children){
            if(identifier.test(child.getData())){
                return child;
            }
        }
        return null;
    }

    @Override
    <R> R fold(R initial, BiFunction<R, T, R> combiner) {
        initial = super.fold(initial, combiner);
        for(TreeNode<T> child: this.children){
            initial = child.fold(initial, combiner);
        }
        return initial;
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

    @Override
    public List<T> filterToList(Predicate<T> filter) {
        List<T> result = super.filterToList(filter);
        for (TreeNode<T> child : children) {
            result.addAll(child.filterToList(filter));
        }
        return result;
    }
}
