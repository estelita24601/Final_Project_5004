import model.ICrewMember;
import model.ICrewModel;

public class StarfleetCommand implements ICrewController {
    final Readable in;

    public StarfleetCommand(Readable inputStream) {
        this.in = inputStream;
    }

    @Override
    public void go(ICrewModel<ICrewMember> model, ICrewView view) {

    }
}
