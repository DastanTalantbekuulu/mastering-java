public class StartButton extends ButtonAbstract {

    public StartButton(String name, Mediator mediator) {
        super(name, mediator);
    }

    public void mouseClicked(int button, int x, int y) {
        if (button == 1 && focusOnButton(x, y) && mediator != null) {
          if (mediator != null) {
              if ("Start".equals(getName())) {
                  mediator.startGame();
                  setName("Restart");
              } else {
                  mediator.restartGame();
              }
              parent.repaint();
          }
        }
    }
}
