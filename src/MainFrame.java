/**
 * Created by new_name on 13.10.2014.
 */

import javax.swing.*;

import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.border.Border;

public final class MainFrame extends JFrame{

    private JTextField ipTextField;
    private JLabel ipLabel;
    private JTextField portTextField;
    private JLabel portLabel;
    private JButton maskButton;
    private JLabel resultLabel;

    public MainFrame() {
        super("Network App");
        createGUI();
    }

    public void createGUI() {
        setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);


        Dimension labelSize = new Dimension(300, 20);
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        ipLabel = new JLabel("Введите IP");
        ipLabel.setPreferredSize(labelSize);
        ipLabel.setHorizontalAlignment(JLabel.LEFT);
        panel.add(ipLabel);

        ipTextField = new JTextField();
        ipTextField.setColumns(23);
        ipTextField.setPreferredSize(labelSize);
        ipTextField.setHorizontalAlignment(JLabel.LEFT);
        panel.add(ipTextField);

        maskButton = new JButton("Get mask");
        maskButton.setHorizontalAlignment(JLabel.LEFT);
        maskButton.setActionCommand("Mask");
        ActionListener actionListener = new MaskActionListener();
        maskButton.addActionListener(actionListener);
        panel.add(maskButton);

        resultLabel = new JLabel("");
        resultLabel.setVerticalAlignment(JLabel.BOTTOM);
        resultLabel.setHorizontalAlignment(JLabel.CENTER);
        //resultLabel.setPreferredSize(new Dimension(300));
        resultLabel.setBorder(solidBorder);
        panel.add(resultLabel);

        getContentPane().add(panel);
        setPreferredSize(new Dimension(320, 240));
    }

    public class MaskActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String result = e.getActionCommand() + ": ";

            // mask?
            String ip = ipTextField.getText();

            result = result + ip;
            resultLabel.setText(result);
        }
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                MainFrame frame = new MainFrame();
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}
