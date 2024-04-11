package treeADT;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class TreeNode<T> implements ITree<T> {
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
        if (this.parent != null) {
            return this.parent;
        } else {
            return this.parent;
        }
    }

    @Override
    public void setParent(TreeNode<T> newParent) {
        Predicate<T> findChildToDelete = (t) -> {return t.equals(this);};
        this.parent.deleteChild(findChildToDelete); // remove ourselves from the parent's list of children
        this.parent = newParent; //change our parent
        this.parent.addChild(this); //add ourselves to new parent's list of children
    }

    @Override
    public ArrayList<TreeNode<T>> getChildren() {
        return this.children;
    }


}
