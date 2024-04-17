package treeADT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface ITree<T> {
    T getData();

    void setData(T newData);

    void editData(Consumer<T> editor);

    TreeNode<T> getParent();

    void setParent(TreeNode<T> newParent);

    ArrayList<TreeNode<T>> getChildren();

    void moveChildren(Predicate<T> findChildrenToReassign, BranchNode<T> newParent);

    void moveChildren(BranchNode<T> newParent);

    int countIf(Predicate<T> filter);

    int countAll();

    List<T> toList();

    boolean addChild(TreeNode<T> newChild);

    boolean addChild(T newChildData, Predicate<T> canBeBranch);

    boolean addChild(T newChildData);

    boolean deleteChild(Predicate<T> findChildToDelete);

    boolean deleteChild(TreeNode<T> childToDelete);

    TreeNode<T> deepCopy();

    TreeNode<T> findNode(Predicate<T> identifier);

    <R> R fold(R initial, BiFunction<R, T, R> combiner);

    List<T> filterToList(Predicate<T> filter);

    <R> TreeNode<R> map(Function<T, R> converter);

    <R> List<R> mapToList(Function<T, R> converter);
}
