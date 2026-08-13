import java.awt.Color;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class Viewer {
    private JFrame frame;
    private HashMap<String,Canvas> containerCanvas;
    private HashMap<String,Integer> canvasDimensions;
    private HashMap<String,Integer> buttonCoordinates;
    private List<Button> buttons;
    private int buttonHeightOffset;
    private int buttonWidthOffset;
    private int buttonWidth;
    private int buttonHeight;
    private int buttonMargin;

    public Viewer() {
        frame = new JFrame("Sokoban Game MVC Pattern");
        frame.setSize(816, 739);
        frame.setLocation(200, 0);
        canvasDimensions = new HashMap<>();
        initCanvasDimensions(frame);

        Controller controller = new Controller(this);
        Model model = controller.getModel();

        Canvas fieldCanvas = new GameCanvas(model);
        fieldCanvas.setBounds(0,canvasDimensions.get("panelCanvasY"),canvasDimensions.get("fieldCanvasX"),canvasDimensions.get("fieldCanvasY"));
        fieldCanvas.addMouseListener(controller);
        fieldCanvas.addMouseWheelListener(controller);

        StatsCanvas statsCanvas = new StatsCanvas(model);
        model.setStatsCanvas(statsCanvas);
        statsCanvas.setBounds(0,canvasDimensions.get("panelCanvasY") + canvasDimensions.get("fieldCanvasY") ,
                              canvasDimensions.get("statsCanvasX"),canvasDimensions.get("statsCanvasY"));

        PanelCanvas panelCanvas = new PanelCanvas(model);
        panelCanvas.setBounds(0,0,canvasDimensions.get("panelCanvasX"), canvasDimensions.get("panelCanvasY"));

        buttons = new ArrayList<>();
        Button startButton = new StartButton("Start", model);
        buttons.add(startButton);

        Button prevLevelButton = new PrevLevelButton("Prev Level", model);
        buttons.add(prevLevelButton);

        Button nextLevelButton = new NextLevelButton("Next Level", model);
        buttons.add(nextLevelButton);

        Button undoButton = new UndoButton("Undo", model);
        buttons.add(undoButton);

        Button redoButton = new RedoButton("Redo", model);
        buttons.add(redoButton);

        Button toggleMusicButton = new ToggleMusicButton("Sound off", model);
        buttons.add(toggleMusicButton);

        buttonWidthOffset = canvasDimensions.get("panelCanvasX") / 16;
        buttonHeightOffset = canvasDimensions.get("panelCanvasY") / 4;
        buttonMargin = canvasDimensions.get("panelCanvasX") / 40;
        buttonWidth = (canvasDimensions.get("panelCanvasX") -  buttonWidthOffset * 2 - buttonMargin * (buttons.size() - 1)) / buttons.size();
        buttonHeight = (canvasDimensions.get("panelCanvasY") - buttonHeightOffset * 2);

        for(Button button : buttons) {
            button.setBounds(buttonWidthOffset, buttonHeightOffset, buttonWidth, buttonHeight);
            buttonWidthOffset = buttonWidthOffset + buttonWidth + buttonMargin;
        }

        panelCanvas.addButton(nextLevelButton);
        panelCanvas.addButton(prevLevelButton);
        panelCanvas.addButton(startButton);
        panelCanvas.addButton(undoButton);
        panelCanvas.addButton(redoButton);
        panelCanvas.addButton(toggleMusicButton);

        JPanel panelCenter = new JPanel();
        panelCenter.setBackground(Color.WHITE);
        panelCenter.setLayout(null);

        containerCanvas = new HashMap<>();
        containerCanvas.put("fieldCanvas",fieldCanvas);
        containerCanvas.put("panelCanvas",panelCanvas);
        containerCanvas.put("statsCanvas",statsCanvas);

        for (Canvas canvas : containerCanvas.values()) {
            panelCenter.add(canvas);
        }


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add("Center", panelCenter);

        frame.setVisible(true);
        frame.setResizable(false);
        frame.addKeyListener(controller);

    }

    private void initCanvasDimensions(JFrame frame) {
        int x = frame.getWidth() - 16;
        int y = (frame.getHeight() - 39) / 7;
        int statsY = y;

        if (y * 7 < frame.getHeight() - 39) {
           statsY = statsY + ((frame.getHeight() - 39) - y * 7);
        }

        canvasDimensions.put("fieldCanvasX", x);
        canvasDimensions.put("fieldCanvasY", y * 5);
        canvasDimensions.put("panelCanvasX", x);
        canvasDimensions.put("panelCanvasY", y);
        canvasDimensions.put("statsCanvasX", x);
        canvasDimensions.put("statsCanvasY", statsY);
    }

    public int getGameFieldWidth() {
        return (int)canvasDimensions.get("fieldCanvasX");
    }

    public int getGameFieldHeight() {
        return (int)canvasDimensions.get("fieldCanvasY");
    }

    public void updateAll() {
        for (Canvas canvas : containerCanvas.values()) {
            canvas.repaint();
        }
    }

    public void updateGameField() {
        containerCanvas.get("fieldCanvas").repaint();
    }

    public void showWonDialog(int countOfSteps, int minute, int second) {
        String timeFormat = String.format("%02d:%02d", minute, second);
        JOptionPane.showMessageDialog(null,
                "Congratulations , you have successfully passed the level!" +
                        "\nSteps taken: " + countOfSteps + "\nTime taken: " + timeFormat);
    }

    public void showGameOverDialog() {
        JOptionPane.showMessageDialog(
            null,
            "Game Over! You've been defeated. Better luck next time!",
            "Game Over",
            JOptionPane.INFORMATION_MESSAGE
            );
        }

    public HashMap<String, Canvas> getContainerCanvas(){
        return containerCanvas;
    }
}
