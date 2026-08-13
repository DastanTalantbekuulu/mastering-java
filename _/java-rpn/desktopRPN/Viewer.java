import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;

public class Viewer {

    private JTextField textField;

    public Viewer() {

        Controller controller = new Controller(this);

        Font fontForButton = new Font("Noto Sans", Font.PLAIN, 20);
        Font fontForTextField = new Font("Noto Sans", Font.PLAIN, 25);

        textField = new JTextField();
        textField.setBounds(Database.startX, Database.startY, Database.textFieldW, Database.textFieldH);
        textField.setFont(fontForTextField);
        textField.setForeground(Color.WHITE);
        textField.setBackground(Color.BLACK);
        textField.setOpaque(true);
        textField.setBorder(null);
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);

        JFrame frame = new JFrame("Calculator RPN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Database.frameW, Database.frameH);
        frame.setLocation(Database.frameX, Database.frameY);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(null);
        frame.add(textField);

        for (int i = 1; i <= Database.buttonLabels.length; i++) {
            JButton button = new JButton("" + Database.buttonLabels[i - 1]);
            button.setActionCommand(Database.actionCommands[i - 1]);

            button.setBackground(new Color(30, 30, 30));
            button.setForeground(Color.WHITE);
            switch (Database.buttonLabels[i - 1]) {
                case "=" -> button.setBackground(new Color(64, 156, 255));
                case "+", "-", "/", "*", "(", ")", "C" -> button.setForeground(new Color(64, 156, 255));
            }

            button.setBounds(Database.buttonX, Database.buttonY, Database.buttonW, Database.buttonH);
            button.setFont(fontForButton);
            button.addActionListener(controller);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            frame.add(button);

            Database.buttonX = Database.buttonX + Database.buttonW + Database.offset;
            if (i % Database.rows == 0) {
                Database.buttonY = Database.buttonY + Database.buttonH + Database.offset;
                Database.buttonX = Database.startX;
            }

        }
        frame.setVisible(true);
    }

    public void update(String value) {
        textField.setText(value);
    }

}
