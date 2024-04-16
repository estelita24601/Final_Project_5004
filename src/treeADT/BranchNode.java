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

    /**
     * goes through everything in the tree in this node and below and counts it if it passes the predicate given
     *
     * @param filter predicate that returns true if you want the current node to be counted
     * @return integer for number of nodes that passed the predicate filter
     */
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

    /**
     * @return integer for the total number of nodes in the tree this node and below
     */
    @Override
    public int countAll() {
        Predicate<T> countAll = (t) -> true;
        return countIf(countAll);
    }

    /**
     * @return a list of all the data in this tree from this node and below
     */
    @Override
    public List<T> toList() {
        Predicate<T> all = (t) -> true;
        return filterToList(all);
    }

    @Override
    public boolean addChild(T newChildData) {
        return this.addChild(newChildData, (data) -> true);
    }

    /**
     * @param identifier predicate that returns true when it finds the desired node
     * @return the first node in the tree that passed the predicate test
     */
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

    /**
     * @param initial  the initial value we are adding to
     * @param combiner bifunction that will convert the data in a node to type <R> and then combine it with the running total
     * @param <R>      whatever data type you want to fold to
     * @return the final product of the folding
     */
    @Override
    public <R> R fold(R initial, BiFunction<R, T, R> combiner) {
        initial = super.fold(initial, combiner);
        for (TreeNode<T> child : this.children) {
            initial = child.fold(initial, combiner);
        }
        return initial;
    }

    /**
     * @param filter predicate that returns true when it finds data it wants to be included in the final list
     * @return list of data taken from the nodes in this tree that passed the predicate filter
     */
    @Override
    public List<T> filterToList(Predicate<T> filter) {
        List<T> result = super.filterToList(filter);
        for (TreeNode<T> child : children) {
            result.addAll(child.filterToList(filter));
        }
        return result;
    }

    /**
     * @param <R>       whatever data type we want our data to be converted to
     * @param converter function that takes the data in the node and transforms it into the desired data type
     * @return a list of all the values in this tree after they've been converted to type <R>
     */
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

    /**
     * moves some children of this node to a new parent node. grandchildren nodes and below will also be affected
     *
     * @param findChildrenToReassign predicate that returns true when it finds a child node that should be moved
     * @param newParent              the node we want to move the children to
     */
    @Override
    public void moveChildren(Predicate<T> findChildrenToReassign, BranchNode<T> newParent) {
        for (TreeNode<T> child : children) {
            if (findChildrenToReassign.test(child.getData())) {
                child.setParent(newParent);
            }
        }
    }

    /**
     * moves all the children of this node to the new parent node
     *
     * @param newParent node we want to move all the children to
     */
    @Override
    public void moveChildren(BranchNode<T> newParent) {
        Predicate<T> allChildren = (t) -> true;
        moveChildren(allChildren, newParent);
    }

    /**
     * add a node as a child of this node
     *
     * @param newChild node that we want to add as a child of this node
     * @return true or false depending on success of the operation
     */
    @Override
    public boolean addChild(TreeNode<T> newChild) {
        if (newChild == null) {
            return false;
        } else {
            this.children.add(newChild); //add the child to the parent's list of children
            newChild.setParent(this); //add the parent to the child //FIXME: INFINITE LOOP
            return true;
        }
    }

    /**
     * given data create a new node as a child of this node
     *
     * @param newChildData     the data that will be going into the child node
     * @param canChildBeBranch predicate that determines if the child node can be a branch node or not
     * @return true or false depending on success of the operation
     */
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

    /**
     * deletes a direct child of this node
     *
     * @param findChildToDelete predicate that returns true when it finds the child node it wants to delete
     * @return true or false depending on success
     */
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

    /**
     * deletes a direct child of this node
     *
     * @param childToDelete the actual node we want to delete
     * @return true if able to delete the child node, false if unable to find the child node to delete
     */
    @Override
    public boolean deleteChild(TreeNode<T> childToDelete) {
        Predicate<T> findChildToDelete = (t) -> t.equals(childToDelete.getData());
        return deleteChild(findChildToDelete);
    }

    /**
     * @return a deep copy of this node that has the same data, children and parent nodes
     */
    @Override
    public TreeNode<T> deepCopy() {
        return new BranchNode<>(this.data, this.parent, this.children);
    }

    /**
     * @param <R>       the data type we want to turn everything into
     * @param converter function that transforms the data in the nodes to the desired type
     * @return TreeNode<R> a tree with the same structure as this one but with all the data converted to the desired type
     */
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
