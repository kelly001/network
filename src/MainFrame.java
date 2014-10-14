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
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;

import javax.swing.JFrame;
import javax.swing.border.Border;

public final class MainFrame extends JFrame{

    private JTextField ipTextField;
    private JLabel ipLabel;
    private JTextField maskTextField;
    private JLabel maskLabel;
    private JButton maskButton;
    private JButton infoButton;
    private JLabel resultLabel;
    private JLabel spacer;

    public MainFrame() {
        super("Network App");
        createGUI();
    }

    public static void addComponentsToPane(Container pane) {
        pane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    }

    public void createGUI() {
        setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);


        Dimension labelSize = new Dimension(100, 20);
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        ipLabel = new JLabel("Адрес (IP):");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(ipLabel, c);

        maskLabel = new JLabel("Маска:");
        c.weightx = 0;
        c.gridx = 1;
        c.gridy = 0;
        panel.add(maskLabel,c);


        ipTextField = new JTextField();
        /*ipTextField.setPreferredSize(labelSize);
        ipTextField.setHorizontalAlignment(JLabel.LEFT);
        panel.add(spacer = new JLabel(" "),"span, grow");*/
        c.gridx = 0;
        c.gridy = 1;
        panel.add(ipTextField,c);

        maskTextField = new JTextField();
        c.gridx = 1;
        c.gridy = 1;
        panel.add(maskTextField,c);

        maskButton = new JButton("??");
        maskButton.setActionCommand("Mask");
        ActionListener actionListener = new MaskActionListener();
        maskButton.addActionListener(actionListener);
        c.gridx = 2;
        c.gridy = 1;
        panel.add(maskButton,c);

        infoButton = new JButton("Рассчитать");
        infoButton.setActionCommand("Info");
        ActionListener infoActionListener = new InfoActionListener();
        infoButton.addActionListener(infoActionListener);
        c.ipady = 5;      //make this component tall
        c.gridwidth = 3;
        c.gridx = 0;
        c.gridy = 2;
        panel.add(infoButton,c);


        resultLabel = new JLabel();
        c.ipady = 10;      //make this component tall
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 3;
        resultLabel.setBorder(solidBorder);
        panel.add(resultLabel,c);

        getContentPane().add(panel);
        //setPreferredSize(new Dimension(320, 240));
    }

    public class MaskActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String result = "";

            // check mask
            try {
                InetAddress localHost = Inet4Address.getLocalHost();
                NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);
                Short address = networkInterface.getInterfaceAddresses().get(0).getNetworkPrefixLength();
                result += address;

            }   catch(Exception networkExp){networkExp.printStackTrace();
            }

            maskTextField.setText(result);
        }
    }

    public class InfoActionListener implements ActionListener {
        public void actionPerformed (ActionEvent e ) {
            // mask?
            String ip = ipTextField.getText();
            String mask = maskTextField.getText();
            String CIDR =  ip + "/" + mask;

            SubnetUtils utils = new SubnetUtils(CIDR);
            SubnetUtils.SubnetInfo info = utils.getInfo();
            String result = "<html> Netmask: " + info.getNetmask() + "<br>";
            result += "Network" + info.getNetworkAddress() + "<br></html>";
            resultLabel.setText(result);
        }
    }

    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame.setDefaultLookAndFeelDecorated(true);
                MainFrame frame = new MainFrame();
                //Set up the content pane.
                addComponentsToPane(frame.getContentPane());
                frame.pack();
                frame.setVisible(true);

                // check mask
                try {
                    InetAddress localHost = Inet4Address.getLocalHost();
                    NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);

                    for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                        System.out.println(address.getNetworkPrefixLength());
                        System.out.println(address.getAddress().toString());
                        System.out.println(address.getBroadcast().toString());
                    }
                }   catch(Exception e){e.printStackTrace();
                }

            }
        });
    }
}
