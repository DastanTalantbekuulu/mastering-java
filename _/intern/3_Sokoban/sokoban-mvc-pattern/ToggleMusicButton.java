public class ToggleMusicButton extends ButtonAbstract {

    public ToggleMusicButton(String name, Mediator mediator) {
        super(name, mediator);
    }

    public void mouseClicked(int button, int x, int y) {
        if (button == 1 && focusOnButton(x, y) && mediator != null) {
            if (mediator != null) {
                if ("Sound off".equals(getName())) {
                    mediator.pauseMusic();
                    setName("Sound on");
                } else {
                    mediator.resumeMusic();
                    setName("Sound off");
                }
                parent.repaint();
            }
        }
    }
}
