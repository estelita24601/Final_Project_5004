package model.personnel;

import treeADT.*;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class StarfleetCrew implements ICrew {
    private TreeNode<ICrewMember> root;

    @Override
    public int countAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countAll'");
    }

    @Override
    public int countFilter(Predicate filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'countFilter'");
    }

    @Override
    public List membersList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'membersList'");
    }

    @Override
    public List membersList(Predicate filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'membersList'");
    }

    @Override
    public List membersNameList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'membersNameList'");
    }

    @Override
    public List membersNameList(Predicate filter) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'membersNameList'");
    }

    @Override
    public Object getCrewMember(Predicate thisMember) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCrewMember'");
    }

    @Override
    public String getCrewMemberInfo(Predicate thisMember) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCrewMemberInfo'");
    }

    @Override
    public String getCrewMemberInfo(Predicate thisMember, Function convertInfoToStr) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCrewMemberInfo'");
    }

    @Override
    public void editCrewMember(Predicate thisMember, Consumer crewEditor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editCrewMember'");
    }

    @Override
    public void addCrewMember(Object newCrewMember, Predicate newSuperior) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addCrewMember'");
    }

    @Override
    public void removeCrewMember(Predicate identifier) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeCrewMember'");
    }

    @Override
    public void reAssignTo(Predicate thisMember, Predicate newSuperior) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reAssignTo'");
    }

    @Override
    public void putInCommandOf(Predicate thisMember, Predicate newSubordinate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'putInCommandOf'");
    }



}
