import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

public class Viewer {

    private  JTextField textField;

    public Viewer() {

        Controller controller = new Controller(this);

        Font fontForButton = new Font("Noto Sans", Font.PLAIN, 20);
        Font fontForTextField = new Font("Noto Sans", Font.PLAIN, 25);

        int startX = 10;
        int startY = 10;

        int buttonW = 100;
        int buttonH = 75;

        int offset = 15;

        int buttonsLen = Database.buttonLabels.length;
        int rows = 4;
        int columns = buttonsLen / rows + (buttonsLen % rows > 0 ? 1 : 0);

        int textFieldW = rows * buttonW + (rows - 1) * offset;
        int textFieldH = 75;

        int frameX = 50;
        int frameY = 50;
        int frameW = textFieldW + 2 * offset;
        int frameH = textFieldH + columns * buttonH + (columns + 4) * offset;

        int buttonX = startX;
        int buttonY = textFieldH + offset + startY;
        
        textField = new JTextField();
        textField.setBounds(startX, startY, textFieldW, textFieldH);
        textField.setFont(fontForTextField);
        textField.setForeground(Color.WHITE);
        textField.setBackground(Color.BLACK);
        textField.setOpaque(true);
        textField.setBorder(null);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);

        JFrame frame = new JFrame("Calculator RPN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(frameW, frameH);
        frame.setLocation(frameX, frameY);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(null);
        frame.add(textField);

        for (int i = 1; i <= buttonsLen; i++) {
            JButton button = new JButton("" + Database.buttonLabels[i - 1]);
            button.setActionCommand(Database.actionCommands[i - 1]);

            button.setBackground(new Color(30, 30, 30));
            button.setForeground(Color.WHITE);
            switch (Database.buttonLabels[i - 1]) {
                case "=" -> button.setBackground(new Color(64, 156, 255));
                case "+", "-", "/", "*", "( )", "C", "\u232B" -> button.setForeground(new Color(64, 156, 255));
            }

            button.setBounds(buttonX, buttonY, buttonW, buttonH);
            button.setFont(fontForButton);
            button.addActionListener(controller);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            frame.add(button);

            buttonX = buttonX + buttonW + offset;
            if (i % rows == 0) {
                buttonY = buttonY + buttonH + offset;
                buttonX = startX;
            }

        }
        frame.setVisible(true);
    }

    public void update(String value) {
        textField.setText(value);
    }

}
