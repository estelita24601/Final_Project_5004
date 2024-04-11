public class TerminalCrewView implements ICrewView {
    final Appendable out;

    public TerminalCrewView(Appendable outputStream) {
        this.out = outputStream;
    }
}
