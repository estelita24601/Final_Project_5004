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
            return this.parent;
        } else {
            return this.parent;
        }
    }

    public void setParent(TreeNode<T> newParent) {
        Predicate<T> findChildToDelete = (t) -> {return t.equals(this);};
        this.parent.deleteChild(findChildToDelete); // remove ourselves from the parent's list of children
        this.parent = newParent; //change our parent
        this.parent.addChild(this); //add ourselves to new parent's list of children
    }

    public ArrayList<TreeNode<T>> getChildren() {
        return this.children;
    }


    //ABSTRACT METHODS//////////////////////////////////////////////////////////////////
    public abstract void moveChildren(Predicate<T> findChildrenToReassign, BranchNode<T> newParent);
    public abstract void moveChildren(BranchNode<T> newParent);

    public abstract int countIf(Predicate<T> filter);

    public abstract int countAll();

    public abstract List<T> toList();

    public abstract boolean addChild(TreeNode<T> newChild);
    public abstract boolean addChild(T newChildData, Predicate<T> canBeBranch);

    public abstract boolean deleteChild(Predicate<T> findChildToDelete);
    public abstract boolean deleteChild(TreeNode<T> childToDelete);

    public abstract TreeNode<T> deepCopy();

    public abstract TreeNode<T> findNode(Predicate<T> identifier);

    public abstract <R> R fold(R initial, BiFunction<R, T, R> combiner);

    public abstract List<T> filterToList(Predicate<T> filter);

    public abstract <R> TreeNode<R> map(Function<T, R> converter);

    public abstract <R> List<R> mapToList(Function<T, R> converter);
}
