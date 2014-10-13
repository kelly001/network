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

    public void createGUI() {
        setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);


        Dimension labelSize = new Dimension(50, 20);
        Border solidBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        ipLabel = new JLabel("Адрес (IP):");
        ipLabel.setPreferredSize(new Dimension(55, 20));
        ipLabel.setHorizontalAlignment(JLabel.LEFT);
        panel.add(ipLabel);

        maskLabel = new JLabel("Битов или маска:");
        maskLabel.setPreferredSize(labelSize);
        //maskLabel.setHorizontalAlignment(JLabel.LEFT);
        panel.add(maskLabel);
        panel.add(spacer = new JLabel(" "),"span, grow");

        ipTextField = new JTextField();
        ipTextField.setColumns(16);
        ipTextField.setPreferredSize(labelSize);
        ipTextField.setHorizontalAlignment(JLabel.LEFT);
        panel.add(ipTextField);
        panel.add(new JLabel("/"));

        maskTextField = new JTextField();
        maskTextField.setColumns(20);
        maskTextField.setPreferredSize(labelSize);
        //maskTextField.setHorizontalAlignment(JLabel.LEFT);
        panel.add(maskTextField);

        maskButton = new JButton("Get mask");
        maskButton.setHorizontalAlignment(JLabel.RIGHT);
        maskButton.setActionCommand("Mask");
        ActionListener actionListener = new MaskActionListener();
        maskButton.addActionListener(actionListener);
        panel.add(maskButton);
        panel.add(spacer = new JLabel(" "),"span, grow");

        infoButton = new JButton("Информация");
        //infoButton.setHorizontalAlignment(JLabel.LEFT);
        infoButton.setActionCommand("Info");
        ActionListener infoActionListener = new InfoActionListener();
        infoButton.addActionListener(infoActionListener);
        panel.add(infoButton);
        panel.add(spacer = new JLabel(" "),"span, grow");

        resultLabel = new JLabel();
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
                frame.pack();
                frame.setLocationRelativeTo(null);
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
