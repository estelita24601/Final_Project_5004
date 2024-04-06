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

    T getData() {
        return this.data;
    }

    void setData(T newData) {
        this.data = newData;
    }

    void editData(Consumer<T> editor) {
        editor.accept(this.data);
    }

    TreeNode<T> getParent() {
        if (this.parent != null) {
            return this.parent.deepCopy();
        } else {
            return this.parent;
        }
    }

    void setParent(TreeNode<T> newParent) {
        this.parent = newParent.deepCopy();
    }

    ArrayList<TreeNode<T>> getChildren() {
        return this.children;
    }

    int countIf(Predicate<T> filter) {
        BiFunction<Integer, T, Integer> counter = (i, t) -> {
            if (filter.test(t)) {
                return i + 1;
            } else {
                return i;
            }
        };
        return this.fold(0, counter);
    }

    int countAll() {
        Predicate<T> all = (t) -> true;
        return this.countIf(all);
    }

    List<T> toList() {
        Predicate<T> all = (t) -> true;
        return this.filterToList(all);
    }

    //ABSTRACT METHODS//////////////////////////////////////////////////////////////////

    abstract boolean addChild(TreeNode<T> newChild);

    abstract boolean removeChild(Predicate<T> identifier);

    abstract TreeNode<T> deepCopy();

    abstract TreeNode<T> findNode(Predicate<T> identifier);

    abstract <R> R fold(R initial, BiFunction<R, T, R> combiner);

    abstract List<T> filterToList(Predicate<T> filter);

    abstract <R> TreeNode<R> map(Function<T, R> converter);

    abstract <R> List<R> mapToList(Function<T, R> converter);
}
