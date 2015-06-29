package org.ethereum.net.shh;

import org.ethereum.crypto.ECKey;
import org.spongycastle.util.encoders.Hex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WhisperForm extends JFrame{
    private JPanel panel1;
    private JButton sendButton;
    private JTextField payloadField;
    private JTextArea textArea1;
    private JTextField topicField;
    private JTextField fromField;
    private JTextField toField;
    private JButton update;
    private JButton newIdentityButton;

    private Whisper whisper;

    public WhisperForm(Whisper whisper) {
        super("Whisper");
        this.whisper = whisper;
//        panel1.setPreferredSize(new Dimension(800, 600));
        setContentPane(panel1);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                whisper.post(
                        fromField.getText(),
                        toField.getText(),
                        topicField.getText().split(","),
                        payloadField.getText(),
                        100,
                        100
                );
                textArea1.setText(messages());
            }
        });

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea1.setText(messages());
            }
        });

        newIdentityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ECKey key = whisper.newIdentity();
                fromField.setText(Hex.toHexString(key.getPubKey()));
            }
        });

        pack();
//        setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private String messages() {
        StringBuilder sb = new StringBuilder();
        for (Message m : whisper.getAllKnownMessages()) {
            sb.append(m.toString());
            sb.append("\n\n");
        }
        return sb.toString();
    }
}
