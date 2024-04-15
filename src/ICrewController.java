import model.ICrewMember;
import model.ICrewModel;

public interface ICrewController {
    public void go(ICrewModel<ICrewMember> model, ICrewView view);
}
