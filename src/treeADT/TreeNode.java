package treeADT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class TreeNode<T> {
    TreeNode<T> parent;
    T data;
    ArrayList<TreeNode<T>> children;

    public TreeNode(T initData) {
        this.data = initData;
    }

    public TreeNode(T initData, TreeNode<T> initParent) {
        this(initData);
        this.parent = initParent;
    }

    //CONCRETE METHODS////////////////////////////////////////////////////////////////////////

    public T getData() {
        return this.data;
    }

    public void setData(T newData) {
        this.data = newData;
    }

    public void editData(Consumer<T> editor) {
        editor.accept(this.data);
    }

    public TreeNode<T> getParent() {
        if (this.parent != null) {
            return this.parent.deepCopy();
        } else {
            return this.parent;
        }
    }

    public void setParent(TreeNode<T> newParent) {
        this.parent = newParent.deepCopy();
    }

    public ArrayList<TreeNode<T>> getChildren() {
        return this.children;
    }


    //ABSTRACT METHODS//////////////////////////////////////////////////////////////////

    public abstract int countIf(Predicate<T> filter);

    public abstract int countAll();

    protected abstract List<T> toList();

    protected abstract boolean addChild(TreeNode<T> newChild);

    protected abstract boolean removeChild(Predicate<T> identifier);

    protected abstract TreeNode<T> deepCopy();

    protected abstract TreeNode<T> findNode(Predicate<T> identifier);

    protected abstract <R> R fold(R initial, BiFunction<R, T, R> combiner);

    protected abstract List<T> filterToList(Predicate<T> filter);

    protected abstract <R> TreeNode<R> map(Function<T, R> converter);

    protected abstract <R> List<R> mapToList(Function<T, R> converter);
}
