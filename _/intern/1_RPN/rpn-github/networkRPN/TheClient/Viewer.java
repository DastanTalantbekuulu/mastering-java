import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

/**
 * Viewer class for creating a graphical calculator interface with RPN functionality.
 * This class manages the layout and components such as the display and buttons.
 *
 * @author Argen Azanov
 */
public class Viewer {
    private JTextField textField;
    private static final Color DARK_GRAY = new Color(30, 30, 30);
    private static final Color LIGHT_BLUE = new Color(64, 156, 255);

    /**
     * Constructs the viewer by creating a JFrame with a display panel and a button panel.
     * It also initializes the controller that handles button interactions.
     */
    public Viewer() {
        Controller controller = new Controller(this);

        JFrame frame = new JFrame("Calculator with RPN");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 450);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.BLACK);

        JPanel displayPanel = createDisplayPanel();
        JPanel buttonPanel = createButtonPanel(controller);

        frame.add(displayPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Creates the display panel for the calculator, which contains a text field for the input
     * and a label to display the postfix expression.
     *
     * @return a JPanel containing the display components.
     */
    private JPanel createDisplayPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.setBackground(Color.BLACK);

        textField = new JTextField();
        textField.setFont(new Font("Arial", Font.BOLD, 26));
        textField.setForeground(Color.WHITE);
        textField.setBackground(Color.BLACK);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));

        panel.add(textField);

        return panel;
    }

    /**
     * Creates a panel with calculator buttons, using a grid layout to arrange them.
     *
     * @param controller the Controller handling button actions.
     * @return a JPanel containing the buttons.
     */
    private JPanel createButtonPanel(Controller controller) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        for (int row = 0; row < Database.buttonLabels.length; row++) {
            for (int col = 0; col < Database.buttonLabels[row].length; col++) {
                gbc.gridx = col;
                gbc.gridy = row;
                gbc.gridwidth = 1;

                JButton button = createStyledButton(Database.buttonLabels[row][col], controller);
                button.setActionCommand(Database.actionCommands[row][col]);
                panel.add(button, gbc);
            }
        }

        return panel;
    }

    /**
     * Creates a JButton with specific design.
     *
     * @param label the label for the button.
     * @param controller the Controller that handles button actions.
     * @return a JButton.
     */
    private JButton createStyledButton(String label, Controller controller) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(DARK_GRAY);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        switch(label) {
          case "=" -> button.setBackground(LIGHT_BLUE);
          case "+", "-", "/", "*", "(", ")", "C" -> button.setForeground(LIGHT_BLUE);
        }

        button.addActionListener(controller);

        return button;
    }

    /**
     * Updates the text field with the given value.
     *
     * @param value the value to be displayed in the text field.
     */
    public void update(String value) {
        textField.setText(value);
    }

}
