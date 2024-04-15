package treeADT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class TreeNode<T> implements ITree<T> {
    protected TreeNode<T> parent;
    protected T data;
    protected ArrayList<TreeNode<T>> children;

    public TreeNode(T initData) {
        this.data = initData;
    }

    public TreeNode(T initData, TreeNode<T> initParent) {
        this(initData);
        this.parent = initParent;
    }


    @Override
    public T getData() {
        return this.data;
    }

    @Override
    public void setData(T newData) {
        this.data = newData;
    }

    @Override
    public void editData(Consumer<T> editor) {
        editor.accept(this.data);
    }

    @Override
    public TreeNode<T> getParent() {
        return this.parent;
    }

    @Override
    public void setParent(TreeNode<T> newParent) {
        Predicate<T> findChildToDelete = (t) -> t.equals(this);
        this.parent.deleteChild(findChildToDelete); // remove ourselves from the parent's list of children
        this.parent = newParent; //change our parent
        this.parent.addChild(this); //add ourselves to new parent's list of children
    }

    @Override
    public ArrayList<TreeNode<T>> getChildren() {
        return this.children;
    }

    @Override
    public int countIf(Predicate<T> filter) {
        if (filter.test(this.data)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int countAll() {
        Predicate<T> getAll = (t) -> true;
        return countIf(getAll);
    }

    @Override
    public List<T> toList() {
        Predicate<T> getAll = (t) -> true;
        return filterToList(getAll);
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
        List<T> filteredList = new ArrayList<>();
        if (filter.test(this.data)) {
            filteredList.add(this.data);
        }
        return filteredList;
    }

    @Override
    public <R> List<R> mapToList(Function<T, R> converter) {
        List<R> mappedList = new ArrayList<>();
        mappedList.add(converter.apply(this.data));
        return mappedList;
    }

    @Override
    public String toString() {
        return String.format("%s\n", this.data);
    }

    public abstract boolean addChild(T newChildData);
}
