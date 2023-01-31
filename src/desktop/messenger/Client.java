/**
 *
 * @author AmitGiri
 */
package desktop.messenger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*; //actionListener
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.swing.border.*; //emptyBoder
import javax.xml.validation.Validator;

import org.w3c.dom.Text;

import java.util.*;
import java.text.*;
import java.net.*;

public class Client implements ActionListener {

    JTextField TextZone;
    static JPanel msgArea;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();

    Client() {
        f.setLayout(null);

        f.setSize(450, 600);
        f.setLocation(800, 50);
        f.getContentPane().setBackground(Color.WHITE);

        // greeen header
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);

        // back icon
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        // back icon exits the screen
        back.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent ae) {
                System.exit(0);
            }

        });

        // profile pic of first
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel dp1 = new JLabel(i6);
        dp1.setBounds(40, 10, 50, 50);
        p1.add(dp1);

        // video call icon
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);

        // phone icon
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(350, 20, 35, 30);
        p1.add(phone);

        // more options icon
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(15, 30, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel options = new JLabel(i15);
        options.setBounds(400, 20, 15, 30);
        p1.add(options);

        // profile name
        JLabel name = new JLabel("Hitanshu");
        name.setBounds(110, 15, 100, 15);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        // active status
        JLabel status = new JLabel("Active now");
        status.setBounds(110, 38, 100, 12);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 12));
        p1.add(status);

        // message read area
        msgArea = new JPanel();
        msgArea.setBounds(5, 75, 440, 570);
        f.setUndecorated(true);
        f.add(msgArea);

        // text msg area
        TextZone = new JTextField();
        TextZone.setBounds(5, 555, 310, 40);
        TextZone.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(TextZone);

        JButton send = new JButton("Send");
        send.setBounds(320, 555, 120, 40);
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        send.addActionListener(this);
        f.add(send);

        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {

        try {

            String outPut = TextZone.getText();
            // JLabel outPut1 = new JLabel(outPut);
            JPanel p2 = formatLabel(outPut);
            // p2.add(outPut1);

            msgArea.setLayout(new BorderLayout()); // it places the element to top,bottom,left,right,center

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END); // it will not take a string as argument
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            msgArea.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(outPut);

            TextZone.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static JPanel formatLabel(String outPut) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel outPut1 = new JLabel(outPut);
        outPut1.setFont(new Font("Tahoma", Font.PLAIN, 16));
        outPut1.setBackground(new Color(37, 211, 102));
        outPut1.setOpaque(true);
        outPut1.setBorder(new EmptyBorder(15, 15, 15, 50));// left top bottom right

        panel.add(outPut1);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }

    public static void main(String args[]) {
        new Client();

        try {
            // ServerSocket skt = new ServerSocket(6001);

            while (true) {
                Socket s = new Socket("127.0.0.1", 6001);
                DataInputStream din = new DataInputStream(s.getInputStream());
                dout = new DataOutputStream(s.getOutputStream());

                while (true) {
                    msgArea.setLayout(new BorderLayout());

                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);

                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);

                    vertical.add(left);

                    vertical.add(Box.createVerticalStrut(15));

                    msgArea.add(vertical, BorderLayout.PAGE_START);

                    f.validate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
