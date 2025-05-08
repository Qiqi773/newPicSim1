package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class GUIsim extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIsim frame = new GUIsim();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUIsim() {
		
		//GUI Outer Box Frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//GUI Box content
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane progCodeWindow = new JScrollPane();
		progCodeWindow.setBounds(10, 289, 606, 314);
		contentPane.add(progCodeWindow);
		
		JButton buttonRun = new JButton("Run");
		buttonRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonRun.setBounds(10, 240, 116, 39);
		contentPane.add(buttonRun);
		
		JButton btnNewButton_1 = new JButton("Step");
		btnNewButton_1.setBounds(136, 240, 116, 39);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_1_1 = new JButton("Reset");
		btnNewButton_1_1.setBounds(262, 240, 116, 39);
		contentPane.add(btnNewButton_1_1);
		
		JButton btnNewButton_1_2 = new JButton("Load");	//FileChooser to open System Explorer to get .LST-File
		btnNewButton_1_2.setBounds(10, 10, 116, 39);
		contentPane.add(btnNewButton_1_2);
		
		//RA - TRIS(A) - PINS ->OUTER BOX
		JPanel panel = new JPanel();
		panel.setBounds(190, 10, 364, 100);
		for (Component comp : panel.getComponents()) {
			if (comp instanceof JComponent) {
				((JComponent)comp).setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
		}
		contentPane.add(panel);
		panel.setLayout(new GridLayout(3,3));
		
		JLabel lblNewLabel_1 = new JLabel("RA");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_6 = new JLabel("7");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_6);
		
		JLabel lblNewLabel_5 = new JLabel("6");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_5);
		
		JLabel lblNewLabel_2 = new JLabel("5");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("4");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel = new JLabel("3");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_4 = new JLabel("2");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_4);
		
		JLabel lblNewLabel_13 = new JLabel("1");
		lblNewLabel_13.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_13);
		
		JLabel lblNewLabel_7 = new JLabel("0");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_7);
		
		JLabel lblNewLabel_9 = new JLabel("TRIS");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_9);
		
		JLabel lblNewLabel_8 = new JLabel("x");	//need variable char io -> tris at pinX is 1 = input (i) || tris at pinX is 0 = output (o) [freeze toggle]
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_11 = new JLabel("x");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_11);
		
		JLabel lblNewLabel_15 = new JLabel("x");
		lblNewLabel_15.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_15);
		
		JLabel lblNewLabel_12 = new JLabel("x");
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_12);
		
		JLabel lblNewLabel_10 = new JLabel("x");
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_16 = new JLabel("x");
		lblNewLabel_16.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_16);
		
		JLabel lblNewLabel_14 = new JLabel("x");
		lblNewLabel_14.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_14);
		
		JLabel lblNewLabel_18 = new JLabel("x");
		lblNewLabel_18.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_18);
		
		JLabel lblPin = new JLabel("PIN");
		lblPin.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblPin);
		
		JLabel lblNewLabel_16d = new JLabel("-");
		lblNewLabel_16d.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_16d);
		
		JLabel lblNewLabel_14f = new JLabel("-");
		lblNewLabel_14f.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_14f);
		
		JLabel lblNewLabel_18s = new JLabel("-");
		lblNewLabel_18s.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_18s);
		
		//RA - PIN 4
		JToggleButton raPin4TogButt = new JToggleButton("0");
		ActionListener raPin4AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (raPin4TogButt.isSelected()) {
					raPin4TogButt.setText("1");
				} else {
					raPin4TogButt.setText("0");
				}
			}
		};
		raPin4TogButt.addActionListener(raPin4AcLis);
		panel.add(raPin4TogButt);
		
		//RA - PIN 3
		JToggleButton raPin3TogButt = new JToggleButton("0");
		ActionListener raPin3AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (raPin3TogButt.isSelected()) {
					raPin3TogButt.setText("1");
				} else {
					raPin3TogButt.setText("0");
				}
			}
		};
		raPin3TogButt.addActionListener(raPin3AcLis);
		panel.add(raPin3TogButt);
		
		//RA - PIN 2
		JToggleButton raPin2TogButt = new JToggleButton("0");
		ActionListener raPin2AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (raPin2TogButt.isSelected()) {
					raPin2TogButt.setText("1");
				} else {
					raPin2TogButt.setText("0");
				}
			}
		};
		raPin2TogButt.addActionListener(raPin2AcLis);
		panel.add(raPin2TogButt);
		
		//RA - PIN 1
		JToggleButton raPin1TogButt = new JToggleButton("0");
		ActionListener raPin1AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (raPin1TogButt.isSelected()) {
					raPin1TogButt.setText("1");
				} else {
					raPin1TogButt.setText("0");
				}
			}
		};
		raPin1TogButt.addActionListener(raPin1AcLis);
		panel.add(raPin1TogButt);
		
		JToggleButton raPin0TogButt = new JToggleButton("0");
		ActionListener raPin0AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (raPin0TogButt.isSelected()) {
					raPin0TogButt.setText("1");
				} else {
					raPin0TogButt.setText("0");
				}
			}
		};
		raPin0TogButt.addActionListener(raPin0AcLis);
		panel.add(raPin0TogButt);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(594, 10, 364, 100);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(3, 3));
		
		JLabel lblNewLabel_1_1 = new JLabel("RB");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_6_1 = new JLabel("7");
		lblNewLabel_6_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_6_1);
		
		JLabel lblNewLabel_5_1 = new JLabel("6");
		lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("5");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_3_1 = new JLabel("4");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_17 = new JLabel("3");
		lblNewLabel_17.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_17);
		
		JLabel lblNewLabel_4_1 = new JLabel("2");
		lblNewLabel_4_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel_13_1 = new JLabel("1");
		lblNewLabel_13_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_13_1);
		
		JLabel lblNewLabel_7_1 = new JLabel("0");
		lblNewLabel_7_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_7_1);
		
		JLabel lblNewLabel_9_1 = new JLabel("TRIS");
		lblNewLabel_9_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_9_1);
		
		JLabel lblNewLabel_8_1 = new JLabel("x");
		lblNewLabel_8_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_8_1);
		
		JLabel lblNewLabel_11_1 = new JLabel("x");
		lblNewLabel_11_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_11_1);
		
		JLabel lblNewLabel_15_1 = new JLabel("x");
		lblNewLabel_15_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_15_1);
		
		JLabel lblNewLabel_12_1 = new JLabel("x");
		lblNewLabel_12_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_12_1);
		
		JLabel lblNewLabel_10_1 = new JLabel("x");
		lblNewLabel_10_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_10_1);
		
		JLabel lblNewLabel_16_1 = new JLabel("x");
		lblNewLabel_16_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_16_1);
		
		JLabel lblNewLabel_14_1 = new JLabel("x");
		lblNewLabel_14_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_14_1);
		
		JLabel lblNewLabel_18_1 = new JLabel("x");
		lblNewLabel_18_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_18_1);
		
		JLabel lblPin_1 = new JLabel("PIN");
		lblPin_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblPin_1);
		
		JToggleButton raPin4TogButt_1x = new JToggleButton("0");
		panel_1.add(raPin4TogButt_1x);
		
		JToggleButton raPin3TogButt_1x = new JToggleButton("0");
		panel_1.add(raPin3TogButt_1x);
		
		JToggleButton raPin2TogButt_1x = new JToggleButton("0");
		panel_1.add(raPin2TogButt_1x);
		
		JToggleButton raPin4TogButt_1 = new JToggleButton("0");
		panel_1.add(raPin4TogButt_1);
		
		JToggleButton raPin3TogButt_1 = new JToggleButton("0");
		panel_1.add(raPin3TogButt_1);
		
		JToggleButton raPin2TogButt_1 = new JToggleButton("0");
		panel_1.add(raPin2TogButt_1);
		
		JToggleButton raPin1TogButt_1 = new JToggleButton("0");
		panel_1.add(raPin1TogButt_1);
		
		JToggleButton raPin0TogButt_1 = new JToggleButton("0");
		panel_1.add(raPin0TogButt_1);
		

		
		
	}
}
