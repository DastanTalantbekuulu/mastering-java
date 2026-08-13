public class PrevLevelButton extends ButtonAbstract {

    public PrevLevelButton(String name, Mediator mediator) {
        super(name, mediator);
    }

    public void mouseClicked(int button, int x, int y) {
        if (button == 1 && focusOnButton(x, y) && mediator != null) {
            mediator.prevLevel();
        }
    }
}