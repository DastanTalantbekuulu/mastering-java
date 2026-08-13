public class RedoButton extends ButtonAbstract {

    public RedoButton(String name, Mediator mediator) {
        super(name, mediator);
    }

    public void mouseClicked(int button, int x, int y) {
        if (button == 1 && focusOnButton(x, y) && mediator != null) {
            mediator.redo();
        }
    }
}
