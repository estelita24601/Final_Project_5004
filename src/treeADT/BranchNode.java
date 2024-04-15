package treeADT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class BranchNode<T> extends TreeNode<T> {
    public BranchNode(T initData) {
        super(initData);
        this.children = new ArrayList<>();
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
    public int countIf(Predicate<T> filter) {
        BiFunction<Integer, T, Integer> counter = (number, t) -> {
            if (filter.test(t)) {
                return number + 1;
            }
            return number;
        };

        int runningTotal = counter.apply(0, this.data);
        for (TreeNode<T> child : this.children) {
            runningTotal += child.countIf(filter);
        }
        return runningTotal;
    }

    @Override
    public int countAll() {
        Predicate<T> countAll = (t) -> true;
        return countIf(countAll);
    }

    @Override
    public List<T> toList() {
        Predicate<T> all = (t) -> true;
        return filterToList(all);
    }

    @Override
    public TreeNode<T> findNode(Predicate<T> identifier) {
        //first check if the current node is the one we're looking for
        if (super.findNode(identifier) != null) {
            return this;
        }

        //then search all the children
        for (TreeNode<T> child : this.children) {
            //if the child checked itself and all it's children and found the node then return it
            if (child.findNode(identifier) != null) {
                return child.findNode(identifier);
            }
        }

        //if that didn't work then unable to find the node
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
    public List<T> filterToList(Predicate<T> filter) {
        List<T> result = super.filterToList(filter);
        for (TreeNode<T> child : children) {
            result.addAll(child.filterToList(filter));
        }
        return result;
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
    public String toString() {
        String initial = "";
        BiFunction<String, T, String> foldToString;
        foldToString = (currentStr, thisNode) -> {
            return String.format("%s\n%s", currentStr, thisNode);
        };
        return fold("", foldToString);
    }

    @Override
    public void moveChildren(Predicate<T> findChildrenToReassign, BranchNode<T> newParent) {
        for (TreeNode<T> child : children) {
            if (findChildrenToReassign.test(child.getData())) {
                child.setParent(newParent);
            }
        }
    }

    @Override
    public void moveChildren(BranchNode<T> newParent) {
        Predicate<T> allChildren = (t) -> true;
        moveChildren(allChildren, newParent);
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
    public boolean addChild(T newChildData, Predicate<T> canChildBeBranch) {
        TreeNode<T> newChild;
        //decide if we're making a branch node or leaf node
        if (canChildBeBranch.test(newChildData)) {
            newChild = new BranchNode<>(newChildData);
        } else {
            newChild = new LeafNode<>(newChildData);
        }
        addChild(newChild);
        return true;
    }

    @Override
    public boolean addChild(T newChildData) {
        return this.addChild(newChildData, (data) -> true);
    }

    @Override
    public boolean deleteChild(Predicate<T> findChildToDelete) {
        for (TreeNode<T> child : children) {
            if (findChildToDelete.test(child.getData())) {
                this.children.remove(child);
                return true;
            }
        }
        return false; //wasn't able to find child to remove
    }

    @Override
    public boolean deleteChild(TreeNode<T> childToDelete) {
        Predicate<T> findChildToDelete = (t) -> t.equals(childToDelete.getData());
        return deleteChild(findChildToDelete);
    }

    @Override
    public TreeNode<T> deepCopy() {
        return new BranchNode<>(this.data, this.parent, this.children);
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
}
