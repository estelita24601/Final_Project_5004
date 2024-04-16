package controller;

import model.ICrewMember;
import model.ICrewModel;
import view.ICrewView;

public interface ICrewController {
    public void go(ICrewModel<ICrewMember> model, ICrewView view);
}
