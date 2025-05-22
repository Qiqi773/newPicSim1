package gui;

import simulatorCode.SimulatorInterface;
import simulatorCode.PicSimulator;


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
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
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

import jCsCodeFromFirstRepo.InstructionFileReader;
import jCsCodeFromFirstRepo.InstructionLine;

public class GUIsim extends JFrame {
	
//	Font smallerFont = new Font();
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	SimulatorInterface simulator=new PicSimulator();

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
		setTitle("PicSim");
		
		//GUI Outer Box Frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//GUI Box content
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JScrollPane showProgScrollPane = new JScrollPane();
		showProgScrollPane.setBounds(10, 389, 606, 314);
		contentPane.add(showProgScrollPane);
		
		JButton buttonRun = new JButton("Run");
		buttonRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		buttonRun.setBounds(10, 340, 116, 39);
		contentPane.add(buttonRun);
		
		JButton buttonStep = new JButton("Step");
		buttonStep.setBounds(136, 340, 116, 39);
		contentPane.add(buttonStep);
		
		JButton buttonReset = new JButton("Reset");
		buttonReset.setBounds(262, 340, 116, 39);
		contentPane.add(buttonReset);
		
		JButton buttonChooseFile = new JButton("Choose File");	//FileChooser to open System Explorer to get .LST-Files
//		btnNewButton_1_2.addActionListener(e ->{
//		    JFileChooser chooser=new JFileChooser();
//		    int result =chooser.showOpenDialog(this);
//		    if(result==JFileChooser.APPROVE_OPTION) {
//		        File file =chooser.getSelectedFile();
//		        List<InstructionLine> lines =InstructionFileReader.readInstructionFile(file.getAbsolutePath());
//		        
//		        simulator.loadInstructions(lines);//pass the parsed instruction data(from file) into the backend simulator
//		        
//		        tableModel.setRowCount()
//		    }
//		})
		
		
		buttonChooseFile.setBounds(10, 10, 116, 39);
		contentPane.add(buttonChooseFile);
		
		//RA - TRIS(A) - PINS ->OUTER BOX
		JPanel panel_RA = new JPanel();
		panel_RA.setBounds(190, 10, 360, 100);
		for (Component comp : panel_RA.getComponents()) {
			if (comp instanceof JComponent) {
				((JComponent)comp).setBorder(BorderFactory.createLineBorder(Color.BLACK));
			}
		}
		contentPane.add(panel_RA);
		panel_RA.setLayout(new GridLayout(3,3));
		
		JLabel raRALabel = new JLabel("RA");
		raRALabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raRALabel);
		
		JLabel raPin7Label = new JLabel("7");
		raPin7Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raPin7Label);
		
		JLabel raPin6Label = new JLabel("6");
		raPin6Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raPin6Label);
		
		JLabel raPin5Label = new JLabel("5");
		raPin5Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raPin5Label);
		
		JLabel raPin4Label = new JLabel("4");
		raPin4Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raPin4Label);
		
		JLabel raPin3Label = new JLabel("3");
		raPin3Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raPin3Label);
		
		JLabel raPin2Label = new JLabel("2");
		raPin2Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raPin2Label);
		
		JLabel raPin1Label = new JLabel("1");
		raPin1Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raPin1Label);
		
		JLabel raPin0Label = new JLabel("0");
		raPin0Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raPin0Label);
		
		JLabel raTRISLabel = new JLabel("TRIS");
		raTRISLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTRISLabel);
		
		JLabel raTrisPin7Label = new JLabel("x");	//need variable char io -> tris at pinX is 1 = input (i) || tris at pinX is 0 = output (o) [freeze toggle]
		raTrisPin7Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin7Label);
		
		JLabel raTrisPin6Label = new JLabel("x");
		raTrisPin6Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin6Label);
		
		JLabel raTrisPin5Label = new JLabel("x");
		raTrisPin5Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin5Label);
		
		JLabel raTrisPin4Label = new JLabel("x");
		raTrisPin4Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin4Label);
		
		JLabel raTrisPin3Label = new JLabel("x");
		raTrisPin3Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin3Label);
		
		JLabel raTrisPin2Label = new JLabel("x");
		raTrisPin2Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin2Label);
		
		JLabel raTrisPin1Label = new JLabel("x");
		raTrisPin1Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin1Label);
		
		JLabel raTrisPin0Label = new JLabel("x");
		raTrisPin0Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin0Label);
		
		JLabel raPINLabel = new JLabel("PIN");
		raPINLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raPINLabel);
		
		JLabel raPin7ValueLabel = new JLabel("-");
		raPin7ValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raPin7ValueLabel);
		
		JLabel raPin6ValueLabel = new JLabel("-");
		raPin6ValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raPin6ValueLabel);
		
		JLabel raPin5ValueLabel = new JLabel("-");
		raPin5ValueLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raPin5ValueLabel);
		
		//RA - PIN 4
		JToggleButton raPin4ValueTogButt = new JToggleButton("0");
		ActionListener raPin4AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (raPin4ValueTogButt.isSelected()) {
					raPin4ValueTogButt.setText("1");
				} else {
					raPin4ValueTogButt.setText("0");
				}
			}
		};
		raPin4ValueTogButt.addActionListener(raPin4AcLis);
		panel_RA.add(raPin4ValueTogButt);
	
		
		//RA - PIN 3
		JToggleButton raPin3ValueTogButt = new JToggleButton("0");
		ActionListener raPin3AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (raPin3ValueTogButt.isSelected()) {
					raPin3ValueTogButt.setText("1");
				} else {
					raPin3ValueTogButt.setText("0");
				}
			}
		};
		raPin3ValueTogButt.addActionListener(raPin3AcLis);
		panel_RA.add(raPin3ValueTogButt);
		
		//RA - PIN 2
		JToggleButton raPin2ValueTogButt = new JToggleButton("0");
		ActionListener raPin2AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (raPin2ValueTogButt.isSelected()) {
					raPin2ValueTogButt.setText("1");
				} else {
					raPin2ValueTogButt.setText("0");
				}
			}
		};
		raPin2ValueTogButt.addActionListener(raPin2AcLis);
		panel_RA.add(raPin2ValueTogButt);
		
		//RA - PIN 1
		JToggleButton raPin1ValueTogButt = new JToggleButton("0");
		ActionListener raPin1AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (raPin1ValueTogButt.isSelected()) {
					raPin1ValueTogButt.setText("1");
				} else {
					raPin1ValueTogButt.setText("0");
				}
			}
		};
		raPin1ValueTogButt.addActionListener(raPin1AcLis);
		panel_RA.add(raPin1ValueTogButt);
		
		JToggleButton raPin0ValueTogButt = new JToggleButton("0");
		ActionListener raPin0AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (raPin0ValueTogButt.isSelected()) {
					raPin0ValueTogButt.setText("1");
				} else {
					raPin0ValueTogButt.setText("0");
				}
			}
		};
		raPin0ValueTogButt.addActionListener(raPin0AcLis);
		panel_RA.add(raPin0ValueTogButt);
		
		JPanel panel_RB = new JPanel();
		panel_RB.setBounds(553, 10, 360, 100);
		contentPane.add(panel_RB);
		panel_RB.setLayout(new GridLayout(3, 3));
		
		JLabel lblNewLabel_1_1 = new JLabel("RB");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_6_1 = new JLabel("7");
		lblNewLabel_6_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_6_1);
		
		JLabel lblNewLabel_5_1 = new JLabel("6");
		lblNewLabel_5_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_5_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("5");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_3_1 = new JLabel("4");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel_17 = new JLabel("3");
		lblNewLabel_17.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_17);
		
		JLabel lblNewLabel_4_1 = new JLabel("2");
		lblNewLabel_4_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_4_1);
		
		JLabel lblNewLabel_13_1 = new JLabel("1");
		lblNewLabel_13_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_13_1);
		
		JLabel lblNewLabel_7_1 = new JLabel("0");
		lblNewLabel_7_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_7_1);
		
		JLabel lblNewLabel_9_1 = new JLabel("TRIS");
		lblNewLabel_9_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_9_1);
		
		JLabel lblNewLabel_8_1 = new JLabel("x");
		lblNewLabel_8_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_8_1);
		
		JLabel lblNewLabel_11_1 = new JLabel("x");
		lblNewLabel_11_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_11_1);
		
		JLabel lblNewLabel_15_1 = new JLabel("x");
		lblNewLabel_15_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_15_1);
		
		JLabel lblNewLabel_12_1 = new JLabel("x");
		lblNewLabel_12_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_12_1);
		
		JLabel lblNewLabel_10_1 = new JLabel("x");
		lblNewLabel_10_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_10_1);
		
		JLabel lblNewLabel_16_1 = new JLabel("x");
		lblNewLabel_16_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_16_1);
		
		JLabel lblNewLabel_14_1 = new JLabel("x");
		lblNewLabel_14_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_14_1);
		
		JLabel lblNewLabel_18_1 = new JLabel("x");
		lblNewLabel_18_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblNewLabel_18_1);
		
		JLabel lblPin_1 = new JLabel("PIN");
		lblPin_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(lblPin_1);
		
		JToggleButton raPin4TogButt_1x = new JToggleButton("0");
		panel_RB.add(raPin4TogButt_1x);
		
		JToggleButton raPin3TogButt_1x = new JToggleButton("0");
		panel_RB.add(raPin3TogButt_1x);
		
		JToggleButton raPin2TogButt_1x = new JToggleButton("0");
		panel_RB.add(raPin2TogButt_1x);
		
		JToggleButton raPin4TogButt_1 = new JToggleButton("0");
		panel_RB.add(raPin4TogButt_1);
		
		JToggleButton raPin3TogButt_1 = new JToggleButton("0");
		panel_RB.add(raPin3TogButt_1);
		
		JToggleButton raPin2TogButt_1 = new JToggleButton("0");
		panel_RB.add(raPin2TogButt_1);
		
		JToggleButton raPin1TogButt_1 = new JToggleButton("0");
		panel_RB.add(raPin1TogButt_1);
		
		JToggleButton raPin0TogButt_1 = new JToggleButton("0");
		panel_RB.add(raPin0TogButt_1);
		

		
		
	}
}
