package gui;

import simulatorCode.SimulatorInterface;

import simulatorCode.PicSimulator;
import simulatorCode.InstructionExcutor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import jCsCodeFromFirstRepo.InstructionFileReader;
import jCsCodeFromFirstRepo.InstructionLine;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.JInternalFrame;
import javax.swing.JComboBox;

public class GUIsim extends JFrame {

//	Font smallerFont = new Font();

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	SimulatorInterface simulator = new PicSimulator();
	private JTable table;
	private JTable table_1;
	private List<JPanel> instructionRows = new ArrayList<>();
	private Timer runTimer;
	private int startTickCount = 0;
	private double tickDuration = 1.0;
	int currentLine = 0;
	private List<JCheckBox> breakpoints = new ArrayList();
	private JTextField wField;
	private JTextField pcField;
	private boolean isRunning = true;
	private JButton btnStop;
	JToggleButton zTogButt;
	JToggleButton dcTogButt;
	JToggleButton cTogButt;
	private RamTablePanel ramPanel;

	// global PinValue Variables
	JToggleButton rbPin0ValueTogButt;
	private JTextField tickField;

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

		// Colours
		Color lightLavender = new Color(230, 218, 237);
		Color darkLavender = new Color(185, 164, 189);

		// GUI Outer Box Frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1400, 750);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 204));
		contentPane.setLayout(null);

		// GUI Box content
		setContentPane(contentPane);
		contentPane.setLayout(null);

//------SHOW LOADED TESTFILE---------------------------------------------------------------------------------------------------------
		JScrollPane showProgScrollPane = new JScrollPane();
		showProgScrollPane.setBounds(10, 301, 735, 402);
		contentPane.add(showProgScrollPane);

//------CHOOSE FILE------------------------------------------------------------------------------------------------------------------
		JButton buttonChooseFile = new JButton("Choose File"); // FileChooser to open System Explorer to get .LST-Files
		buttonChooseFile.setBackground(new Color(255, 250, 240));
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
		// 1. 创建一个用于显示指令的面板，加入 ScrollPane 中（放在 GUI 初始化中）
		JPanel progPanel = new JPanel();
		progPanel.setBackground(new Color(255, 250, 240));
		progPanel.setLayout(new BoxLayout(progPanel, BoxLayout.Y_AXIS));
		showProgScrollPane.setViewportView(progPanel); // << 放入 Scroll 面板

		// 2. 添加 Choose File 按钮的逻辑
		buttonChooseFile.addActionListener(e -> {
			JFileChooser chooser = new JFileChooser();
			int result = chooser.showOpenDialog(null); // 用 null 代表当前窗口

			if (result == JFileChooser.APPROVE_OPTION) {
				File file = chooser.getSelectedFile();

				try {
					// read file
					List<InstructionLine> lines = InstructionFileReader.readInstructionFile(file.getAbsolutePath());

					// send to backend
					simulator.loadInstructions(lines);

					// clear the previous content
					progPanel.removeAll();

					JPanel header = new JPanel(new GridLayout(1, 5)); // 5 columns
					JLabel label = new JLabel("BP");
					label.setBackground(new Color(200, 230, 240));
					header.add(label); // Breakpoint
					header.add(new JLabel("Address"));
					header.add(new JLabel("Machine Code"));
					header.add(new JLabel("Instruction"));
					header.add(new JLabel("Comment"));
					progPanel.add(header); // add to the head

					// line by line show the content
					for (InstructionLine line : lines) {
						JPanel row = new JPanel(new GridLayout(1, 5));
						row.setBackground(new Color(255, 250, 240));

						JCheckBox breakpointBox = new JCheckBox();
						breakpointBox.setBackground(new Color(255, 250, 240));
						row.add(breakpointBox);

						breakpoints.add(breakpointBox);

						row.add(new JLabel(String.format("%04X", line.getAddress())));
						row.add(new JLabel(String.format("%04X", line.getMachineCode())));
						row.add(new JLabel(line.getInstruction()));
						row.add(new JLabel(line.getComment()));

						progPanel.add(row);
						instructionRows.add(row);
					}

					progPanel.revalidate();
					progPanel.repaint();

				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "File read failed.");
				}
			}
		});
		buttonChooseFile.setBounds(10, 10, 116, 39);
		contentPane.add(buttonChooseFile);

//------RUN-----------------------------------------------------------------------------------------------------------------
		JButton buttonRun = new JButton("Run");
		buttonRun.setBackground(new Color(255, 250, 240));
		buttonRun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Run-Button
				// simulator.runProgram();
//                new Thread(() -> {
//                    while (!simulator.isHalted()) {
//                        simulator.step();
//                        currentLine = simulator.getPC() / 2;
//                        highlightLine(currentLine);
//                        try {
//                            Thread.sleep(300); // 控制执行速度
//                        } catch (InterruptedException ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//                }).start();
				if (!isRunning) {
					isRunning = true;
				}
				new Thread(() -> {

					while (!InstructionExcutor.isHalted() && isRunning) {
						int pc = simulator.getPC();

						// if current pc has BP, stop
						if (isBreakpointAt(pc)) {
							break;
						}

						simulator.step();
						updateLaufzeit();
						updateRegisters();
						updateFlags();
						ramPanel.updateRam(simulator.getMemory().getRam());
						simulator.caHInterrupt();
						highlightLine(simulator.getPC());
						// wField.setText(String.valueOf(simulator.getW()));

						try {
							Thread.sleep(100); //
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
					// clearTicktime();
					updateLaufzeit();
					isRunning = false;
				}).start();
			}
//

		});
		buttonRun.setBounds(755, 426, 116, 39);
		contentPane.add(buttonRun);

//------STEP------------------------------------------------------------------------------------------------------------------
		JButton buttonStep = new JButton("Step");
		buttonStep.setBackground(new Color(255, 250, 240));
		buttonStep.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Step-Button
				simulator.step();

				int currentPC = simulator.getPC();
				currentLine = currentPC;
				highlightLine(currentLine);
				updateRegisters();
				updateFlags();
				ramPanel.updateRam(simulator.getMemory().getRam());
				updateLaufzeit();
			}
		});
		buttonStep.setBounds(755, 363, 116, 39);
		contentPane.add(buttonStep);

//------RESET-------------------------------------------------------------------------------------------------------------------
		JButton buttonReset = new JButton("Reset");
		buttonReset.setBackground(new Color(255, 250, 240));
		buttonReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Reset-Button
				isRunning = false;
				simulator.reset();
				currentLine = 0;
				resetLaufZeit();
				updateFlags();
				updateRegisters();
				ramPanel.updateRam(simulator.getMemory().getRam());
				updateLaufzeit();

				highlightLine(currentLine);
			}
		});
		buttonReset.setBounds(755, 301, 116, 39);
		contentPane.add(buttonReset);

//------PORT A...-------------------------------------------------------------------------------------------------------------------
		// RA - TRIS(A) - PINS ->OUTER BOX
		JPanel panel_RA = new JPanel();
		panel_RA.setBackground(new Color(255, 250, 240));
		panel_RA.setBounds(136, 10, 360, 100);
		contentPane.add(panel_RA);
		panel_RA.setLayout(new GridLayout(3, 3));

//------LABELS PINS PORT A----------------------------------------------------------------------------------
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

//------TRIS A--------------------------------------------------------------------------------------------------
		JLabel raTRISLabel = new JLabel("TRIS");
		raTRISLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTRISLabel);

		JLabel raTrisPin7Label = new JLabel("-");
		raTrisPin7Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin7Label);

		JLabel raTrisPin6Label = new JLabel("-");
		raTrisPin6Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin6Label);

		JLabel raTrisPin5Label = new JLabel("-");
		raTrisPin5Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin5Label);

		JLabel raTrisPin4Label = new JLabel("!"); // TODO need to connect state of tris with TRISreg from DATAreg
		raTrisPin4Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin4Label);

		JLabel raTrisPin3Label = new JLabel("!"); // TODO need to connect state of tris with TRISreg from DATAreg
		raTrisPin3Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin3Label);

		JLabel raTrisPin2Label = new JLabel("!"); // TODO need to connect state of tris with TRISreg from DATAreg
		raTrisPin2Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin2Label);

		JLabel raTrisPin1Label = new JLabel("!"); // TODO need to connect state of tris with TRISreg from DATAreg
		raTrisPin1Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin1Label);

		JLabel raTrisPin0Label = new JLabel("!"); // TODO need to connect state of tris with TRISreg from DATAreg
		raTrisPin0Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RA.add(raTrisPin0Label);

//------PINS PORT A -------------------------------------------------------------------------------------
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

		// RA-PIN 4
		JToggleButton raPin4ValueTogButt = new JToggleButton("0");
		raPin4ValueTogButt.setBackground(new Color(255, 250, 240));
		ActionListener raPin4AcLis = new ActionListener() { // TODO replace "setText" with 'get value from dataReg' +
															// freeze button if its TRISreg is '0' (=output)
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

		// RA-PIN 3
		JToggleButton raPin3ValueTogButt = new JToggleButton("0");
		raPin3ValueTogButt.setBackground(new Color(255, 250, 240));
		ActionListener raPin3AcLis = new ActionListener() { // TODO replace "setText" with 'get value from dataReg' +
															// freeze button if its TRISreg is '0' (=output)
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

		// RA-PIN 2
		JToggleButton raPin2ValueTogButt = new JToggleButton("0");
		raPin2ValueTogButt.setBackground(new Color(255, 250, 240));
		ActionListener raPin2AcLis = new ActionListener() { // TODO replace "setText" with 'get value from dataReg' +
															// freeze button if its TRISreg is '0' (=output)
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

		// RA-PIN 1
		JToggleButton raPin1ValueTogButt = new JToggleButton("0");
		raPin1ValueTogButt.setBackground(new Color(255, 250, 240));
		ActionListener raPin1AcLis = new ActionListener() { // TODO replace "setText" with 'get value from dataReg' +
															// freeze button if its TRISreg is '0' (=output)
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

		// RA-PIN 0
		JToggleButton raPin0ValueTogButt = new JToggleButton("0");
		raPin0ValueTogButt.setBackground(new Color(255, 250, 240));
		ActionListener raPin0AcLis = new ActionListener() { // TODO replace "setText" with 'get value from dataReg' +
															// freeze button if its TRISreg is '0' (=output)
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

//------PORT B...-----------------------------------------------------------------------------------------------------------------
		JPanel panel_RB = new JPanel();
		panel_RB.setBackground(new Color(255, 250, 240));
		panel_RB.setBounds(506, 10, 360, 100);
		contentPane.add(panel_RB);
		panel_RB.setLayout(new GridLayout(3, 3));

//------LABELS PORT B-------------------------------------------------------------------------------------------------------------
		JLabel rbRBLabel = new JLabel("RB");
		rbRBLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbRBLabel);

		JLabel rbPin7Label = new JLabel("7");
		rbPin7Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbPin7Label);

		JLabel rbPin6Label = new JLabel("6");
		rbPin6Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbPin6Label);

		JLabel rbPin5Label = new JLabel("5");
		rbPin5Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbPin5Label);

		JLabel rbPin4Label = new JLabel("4");
		rbPin4Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbPin4Label);

		JLabel rbPin3Label = new JLabel("3");
		rbPin3Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbPin3Label);

		JLabel rbPin2Label = new JLabel("2");
		rbPin2Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbPin2Label);

		JLabel rbPin1Label = new JLabel("1");
		rbPin1Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbPin1Label);

		JLabel rbPin0Label = new JLabel("0");
		rbPin0Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbPin0Label);

//------TRIS B-----------------------------------------------------------------------------------------------------
		JLabel rbTRISLabel = new JLabel("TRIS");
		rbTRISLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbTRISLabel);

		JLabel rbTrisPin7Label = new JLabel("!"); // TODO need to connect state of tris with TRISreg from DATAreg
		rbTrisPin7Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbTrisPin7Label);

		JLabel rbTrisPin6Label = new JLabel("!"); // TODO need to connect state of tris with TRISreg from DATAreg
		rbTrisPin6Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbTrisPin6Label);

		JLabel rbTrisPin5Label = new JLabel("!"); // TODO need to connect state of tris with TRISreg from DATAreg
		rbTrisPin5Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbTrisPin5Label);

		JLabel rbTrisPin4Label = new JLabel("!"); // TODO need to connect state of tris with TRISreg from DATAreg
		rbTrisPin4Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbTrisPin4Label);

		JLabel rbTrisPin3Label = new JLabel("!"); // TODO need to connect state of tris with TRISreg from DATAreg
		rbTrisPin3Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbTrisPin3Label);

		JLabel rbTrisPin2Label = new JLabel("!"); // TODO need to connect state of tris with TRISreg from DATAreg
		rbTrisPin2Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbTrisPin2Label);

		JLabel rbTrisPin1Label = new JLabel("!"); // TODO need to connect state of tris with TRISreg from DATAreg
		rbTrisPin1Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbTrisPin1Label);

		JLabel rbTrisPin0Label = new JLabel("!"); // TODO need to connect state of tris with TRISreg from DATAreg
		rbTrisPin0Label.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbTrisPin0Label);

//------PINS PORT B---------------------------------------------------------------------------------------------------------
		JLabel rbPINLabel = new JLabel("PIN");
		rbPINLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_RB.add(rbPINLabel);

		// RB-PIN 7
		JToggleButton rbPin7ValueTogButt = new JToggleButton("0");
		rbPin7ValueTogButt.setBackground(new Color(255, 250, 240));
		ActionListener rbPin7AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbPin7ValueTogButt.isSelected()) {
					rbPin7ValueTogButt.setText("1"); // TODO replace "setText" with 'get value from dataReg' + freeze
														// button if its TRISreg is '0' (=output)
				} else {
					rbPin7ValueTogButt.setText("0");
				}
			}
		};
		rbPin7ValueTogButt.addActionListener(rbPin7AcLis);
		panel_RB.add(rbPin7ValueTogButt);

		// RB-PIN 6
		JToggleButton rbPin6ValueTogButt = new JToggleButton("0");
		rbPin6ValueTogButt.setBackground(new Color(255, 250, 240));
		ActionListener rbPin6AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbPin6ValueTogButt.isSelected()) {
					rbPin6ValueTogButt.setText("1"); // TODO replace "setText" with 'get value from dataReg' + freeze
														// button if its TRISreg is '0' (=output)
				} else {
					rbPin6ValueTogButt.setText("0");
				}
			}
		};
		rbPin6ValueTogButt.addActionListener(rbPin6AcLis);
		panel_RB.add(rbPin6ValueTogButt);

		// RB-PIN 5
		JToggleButton rbPin5ValueTogButt = new JToggleButton("0");
		rbPin5ValueTogButt.setBackground(new Color(255, 250, 240));
		ActionListener rbPin5AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbPin5ValueTogButt.isSelected()) {
					rbPin5ValueTogButt.setText("1"); // TODO replace "setText" with 'get value from dataReg' + freeze
														// button if its TRISreg is '0' (=output)
				} else {
					rbPin5ValueTogButt.setText("0");
				}
			}
		};
		rbPin5ValueTogButt.addActionListener(rbPin5AcLis);
		panel_RB.add(rbPin5ValueTogButt);

		// RB-PIN 4
		JToggleButton rbPin4ValueTogButt = new JToggleButton("0");
		rbPin4ValueTogButt.setBackground(new Color(255, 250, 240));
		ActionListener rbPin4AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbPin4ValueTogButt.isSelected()) {
					rbPin4ValueTogButt.setText("1"); // TODO replace "setText" with 'get value from dataReg' + freeze
														// button if its TRISreg is '0' (=output)
				} else {
					rbPin4ValueTogButt.setText("0");
				}
			}
		};
		rbPin4ValueTogButt.addActionListener(rbPin4AcLis);
		panel_RB.add(rbPin4ValueTogButt);

		// RB-PIN 3
		JToggleButton rbPin3ValueTogButt = new JToggleButton("0");
		rbPin3ValueTogButt.setBackground(new Color(255, 250, 240));
		ActionListener rbPin3AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbPin3ValueTogButt.isSelected()) {
					rbPin3ValueTogButt.setText("1"); // TODO replace "setText" with 'get value from dataReg' + freeze
														// button if its TRISreg is '0' (=output)
				} else {
					rbPin3ValueTogButt.setText("0");
				}
			}
		};
		rbPin3ValueTogButt.addActionListener(rbPin3AcLis);
		panel_RB.add(rbPin3ValueTogButt);

		// RB-PIN 2
		JToggleButton rbPin2ValueTogButt = new JToggleButton("0");
		rbPin2ValueTogButt.setBackground(new Color(255, 250, 240));
		ActionListener rbPin2AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbPin2ValueTogButt.isSelected()) {
					rbPin2ValueTogButt.setText("1"); // TODO replace "setText" with 'get value from dataReg' + freeze
														// button if its TRISreg is '0' (=output)
				} else {
					rbPin2ValueTogButt.setText("0");
				}
			}
		};
		rbPin2ValueTogButt.addActionListener(rbPin2AcLis);
		panel_RB.add(rbPin2ValueTogButt);

		// RB-PIN 1
		JToggleButton rbPin1ValueTogButt = new JToggleButton("0");
		rbPin1ValueTogButt.setBackground(new Color(255, 250, 240));
		ActionListener rbPin1AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbPin1ValueTogButt.isSelected()) {
					rbPin1ValueTogButt.setText("1"); // TODO replace "setText" with 'get value from dataReg' + freeze
														// button if its TRISreg is '0' (=output)
				} else {
					rbPin1ValueTogButt.setText("0");
				}
			}
		};
		rbPin1ValueTogButt.addActionListener(rbPin1AcLis);
		panel_RB.add(rbPin1ValueTogButt);

		// RB-PIN 0
		rbPin0ValueTogButt = new JToggleButton("0");
		rbPin0ValueTogButt.setBackground(new Color(255, 250, 240));
		ActionListener rbPin0AcLis = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbPin0ValueTogButt.isSelected()) {
					rbPin0ValueTogButt.setText("1"); // TODO replace "setText" with 'get value from dataReg' + freeze
														// button if its TRISreg is '0' (=output)
				} else {
					rbPin0ValueTogButt.setText("0");
				}
			}
		};
		rbPin0ValueTogButt.addActionListener(rbPin0AcLis);
		panel_RB.add(rbPin0ValueTogButt);
//------new try of GPR-----------------------------------------------------------------
		ramPanel = new RamTablePanel();
		ramPanel.setBounds(900, 26, 476, 677);
		ramPanel.setBackground(new Color(255, 250, 240));
		contentPane.add(ramPanel);

//------SHOW REGISTERS-------------------------------------------------------------------------------------------------------------
//		JScrollPane scrollPane_showRegisters = new JScrollPane();
//		scrollPane_showRegisters.setBounds(880, 10, 496, 693);
//		scrollPane_showRegisters.setBackground(new Color(255, 250, 240));
//		contentPane.add(scrollPane_showRegisters);
//
//------COLUMN HEADERS----------------------------------------------------------------------------------------------------------------
//		Box colHeadersHorizBox = Box.createHorizontalBox();
//		colHeadersHorizBox.setBackground(lightLavender);
//		scrollPane_showRegisters.setColumnHeaderView(colHeadersHorizBox);
//
//		Component distanceHorizGlue0 = Box.createHorizontalGlue();
//		distanceHorizGlue0.setBackground(lightLavender);
//		colHeadersHorizBox.add(distanceHorizGlue0);
//
//		JLabel col0RegsLabel = new JLabel("00");
//		col0RegsLabel.setBackground(darkLavender);
//		colHeadersHorizBox.add(col0RegsLabel);
//
//		Component distanceHorizGlue1 = Box.createHorizontalGlue();
//		distanceHorizGlue1.setBackground(lightLavender);
//		colHeadersHorizBox.add(distanceHorizGlue1);
//
//		JLabel col1RegsLabel = new JLabel("01");
//		col1RegsLabel.setBackground(darkLavender);
//		colHeadersHorizBox.add(col1RegsLabel);
//
//		Component distanceHorizGlue2 = Box.createHorizontalGlue();
//		distanceHorizGlue2.setBackground(lightLavender);
//		colHeadersHorizBox.add(distanceHorizGlue2);
//
//		JLabel col2RegsLabel = new JLabel("02");
//		col2RegsLabel.setBackground(darkLavender);
//		colHeadersHorizBox.add(col2RegsLabel);
//
//		Component distanceHorizGlue3 = Box.createHorizontalGlue();
//		distanceHorizGlue3.setBackground(lightLavender);
//		colHeadersHorizBox.add(distanceHorizGlue3);
//
//		JLabel col3RegsLabel = new JLabel("03");
//		col3RegsLabel.setBackground(darkLavender);
//		colHeadersHorizBox.add(col3RegsLabel);
//
//		Component distanceHorizGlue4 = Box.createHorizontalGlue();
//		distanceHorizGlue4.setBackground(lightLavender);
//		colHeadersHorizBox.add(distanceHorizGlue4);
//
//		JLabel col4RegsLabel = new JLabel("04");
//		col4RegsLabel.setBackground(darkLavender);
//		colHeadersHorizBox.add(col4RegsLabel);
//
//		Component distanceHorizGlue5 = Box.createHorizontalGlue();
//		distanceHorizGlue5.setBackground(lightLavender);
//		colHeadersHorizBox.add(distanceHorizGlue5);
//
//		JLabel col5RegsLabel = new JLabel("05");
//		col5RegsLabel.setBackground(darkLavender);
//		colHeadersHorizBox.add(col5RegsLabel);
//
//		Component distanceHorizGlue6 = Box.createHorizontalGlue();
//		distanceHorizGlue6.setBackground(lightLavender);
//		colHeadersHorizBox.add(distanceHorizGlue6);
//
//		JLabel col6RegsLabel = new JLabel("06");
//		col6RegsLabel.setBackground(darkLavender);
//		colHeadersHorizBox.add(col6RegsLabel);
//
//		Component distanceHorizGlue7 = Box.createHorizontalGlue();
//		distanceHorizGlue7.setBackground(lightLavender);
//		colHeadersHorizBox.add(distanceHorizGlue7);
//
//		JLabel col7RegsLabel = new JLabel("07");
//		col7RegsLabel.setBackground(darkLavender);
//		colHeadersHorizBox.add(col7RegsLabel);
//
//		Component distanceHorizGlue8 = Box.createHorizontalGlue();
//		distanceHorizGlue8.setBackground(lightLavender);
//		colHeadersHorizBox.add(distanceHorizGlue8);

//------ROW HEADERS------------------------------------------------------------------------------------------------------------------
//		Box rowHeadersVerticBox = Box.createVerticalBox();
//		rowHeadersVerticBox.setBackground(new Color(230, 218, 237));
//		scrollPane_showRegisters.setRowHeaderView(rowHeadersVerticBox);
//
//		Component distVertGlue0 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue0);
//
//		JLabel row0RegsLabel = new JLabel("00");
//		row0RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row0RegsLabel);
//
//		Component distVertGlue1 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue1);
//
//		JLabel row1RegsLabel = new JLabel("08");
//		row1RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row1RegsLabel);
//
//		Component distVertGlue2 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue2);
//
//		JLabel row2RegsLabel = new JLabel("10");
//		row2RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row2RegsLabel);
//
//		Component distVertGlue3 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue3);
//
//		JLabel row3RegsLabel = new JLabel("18");
//		row3RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row3RegsLabel);
//
//		Component distVertGlue4 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue4);
//
//		JLabel row4RegsLabel = new JLabel("20");
//		row4RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row4RegsLabel);
//
//		Component distVertGlue5 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue5);
//
//		JLabel row5RegsLabel = new JLabel("28");
//		row5RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row5RegsLabel);
//
//		Component distVertGlue6 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue6);
//
//		JLabel row6RegsLabel = new JLabel("30");
//		row6RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row6RegsLabel);
//
//		Component distVertGlue7 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue7);
//
//		JLabel row7RegsLabel = new JLabel("38");
//		row7RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row7RegsLabel);
//
//		Component distVertGlue8 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue8);
//
//		JLabel row8RegsLabel = new JLabel("40");
//		row8RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row8RegsLabel);
//
//		Component distVertGlue9 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue9);
//
//		JLabel row9RegsLabel = new JLabel("48");
//		row9RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row9RegsLabel);
//
//		Component distVertGlue10 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue10);
//
//		JLabel row10RegsLabel = new JLabel("50");
//		row10RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row10RegsLabel);
//
//		Component distVertGlue11 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue11);
//
//		JLabel row11RegsLabel = new JLabel("58");
//		row11RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row11RegsLabel);
//
//		Component distVertGlue12 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue12);
//
//		JLabel row12RegsLabel = new JLabel("60");
//		row12RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row12RegsLabel);
//
//		Component distVertGlue13 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue13);
//
//		JLabel row13RegsLabel = new JLabel("68");
//		row13RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row13RegsLabel);
//
//		Component distVertGlue14 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue14);
//
//		JLabel row14RegsLabel = new JLabel("70");
//		row14RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row14RegsLabel);
//
//		Component distVertGlue15 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue15);
//
//		JLabel row15RegsLabel = new JLabel("78");
//		row15RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row15RegsLabel);
//
//		Component distVertGlue16 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue16);
//
//		JLabel row16RegsLabel = new JLabel("80");
//		row16RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row16RegsLabel);
//
//		Component distVertGlue17 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue17);
//
//		JLabel row17RegsLabel = new JLabel("88");
//		row17RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row17RegsLabel);
//
//		Component distVertGlue18 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue18);
//
//		JLabel row18RegsLabel = new JLabel("90");
//		row18RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row18RegsLabel);
//
//		Component distVertGlue19 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue19);
//
//		JLabel row19RegsLabel = new JLabel("98");
//		row19RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row19RegsLabel);
//
//		Component distVertGlue20 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue20);
//
//		JLabel row20RegsLabel = new JLabel("A0");
//		row20RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row20RegsLabel);
//
//		Component distVertGlue21 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue21);
//
//		JLabel row21RegsLabel = new JLabel("A8");
//		row21RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row21RegsLabel);
//
//		Component distVertGlue22 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue22);
//
//		JLabel row22RegsLabel = new JLabel("B0");
//		row22RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row22RegsLabel);
//
//		Component distVertGlue23 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue23);
//
//		JLabel row23RegsLabel = new JLabel("B8");
//		row23RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row23RegsLabel);
//
//		Component distVertGlue24 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue24);
//
//		JLabel row24RegsLabel = new JLabel("C0");
//		row24RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row24RegsLabel);
//
//		Component distVertGlue25 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue25);
//
//		JLabel row25RegsLabel = new JLabel("C8");
//		row25RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row25RegsLabel);
//
//		Component distVertGlue26 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue26);
//
//		JLabel row26RegsLabel = new JLabel("D0");
//		row26RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row26RegsLabel);
//
//		Component distVertGlue27 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue27);
//
//		JLabel row27RegsLabel = new JLabel("D8");
//		row27RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row27RegsLabel);
//
//		Component distVertGlue28 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue28);
//
//		JLabel row28RegsLabel = new JLabel("E0");
//		row28RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row28RegsLabel);
//
//		Component distVertGlue29 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue29);
//
//		JLabel row29RegsLabel = new JLabel("E8");
//		row29RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row29RegsLabel);
//
//		Component distVertGlue30 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue30);
//
//		JLabel row30RegsLabel = new JLabel("F0");
//		row30RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row30RegsLabel);
//
//		Component distVertGlue31 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue31);
//
//		JLabel row31RegsLabel = new JLabel("F8");
//		row31RegsLabel.setBackground(darkLavender);
//		rowHeadersVerticBox.add(row31RegsLabel);
//
//		Component distVertGlue32 = Box.createVerticalGlue();
//		rowHeadersVerticBox.add(distVertGlue32);
//
//		JPanel panel_RegisterValues = new JPanel();
//		panel_RegisterValues.setBackground(new Color(255, 250, 240));
//		// scrollPane_showRegisters.setViewportView(panel_RegisterValues);
//		// panel_RegisterValues.setLayout(new BorderLayout());
//		panel_RegisterValues.setLayout(new GridLayout(32, 8));
//		for (int i = 0; i < 32; i++) {
//			for (int j = 0; j < 8; j++) {
//				JLabel cell = new JLabel("00", SwingConstants.CENTER);
//				cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//				cell.setOpaque(true);
//				cell.setBackground(Color.WHITE);
//				panel_RegisterValues.add(cell);
//			}
//		}
//		panel_RegisterValues.revalidate();
//		panel_RegisterValues.repaint();
//
////		JLabel[][] ramLabels = new JLabel[32][8];
//		JPanel ramGridPanel = createRamGridPanel(ramLabels);
//
//		// ️ 不要 add 到 Builder 的 ramGridPanel，而是 add 到新的 scrollPane 或 contentPane
//		JScrollPane ramScrollPane = new JScrollPane(ramGridPanel);
//		ramScrollPane.setPreferredSize(new Dimension(400, 800));
//		panel_RegisterValues.add(ramScrollPane, BorderLayout.NORTH); // or your mainPanel.add(...)
//		
//		scrollPane_showRegisters.setViewportView(panel_RegisterValues);

//		JPanel ramGridPanel = new JPanel(new GridLayout(32, 8));
//		JLabel[][] ramLabels = new JLabel[32][8]; // 用于保存引用，后续可以动态更新
//
//		for (int row = 0; row < 32; row++) {
//			for (int col = 0; col < 8; col++) {
//				JLabel cell = new JLabel("00", SwingConstants.CENTER);
//				cell.setPreferredSize(new Dimension(30, 20));
//				cell.setOpaque(true);
//				cell.setBackground(Color.WHITE);
//				cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//				ramGridPanel.add(cell);
//				ramLabels[row][col] = cell; // 存储引用
//			}
//		}
//		ramGridPanel.setPreferredSize(new Dimension(400, 800));
//		panel_RegisterValues.add(ramGridPanel, BorderLayout.CENTER);
//
//		panel_RegisterValues.revalidate();
//		panel_RegisterValues.repaint();

//---------------new Version of GPR--------------------------------
//		JPanel mainPanel = new JPanel(new BorderLayout());
//		mainPanel.setLayout(new BorderLayout());
//		contentPane.add(mainPanel);
//		// col lables
//		JPanel colLabelPanel = new JPanel(new GridLayout(8, 1));
//		for (int i = 0; i < 8; i++) {
//			colLabelPanel.add(new JLabel("ROW" + i));
//		}
//		mainPanel.add(colLabelPanel, BorderLayout.WEST);
//
//		// row Lables
//		JPanel rowLabelPanel = new JPanel(new GridLayout(1, 32));
//		for (int i = 0; i < 32; i++) {
//			rowLabelPanel.add(new JLabel("Col" + i));
//		}
//		mainPanel.add(rowLabelPanel, BorderLayout.WEST);
//
//		JPanel gridPanel = new JPanel(new GridLayout(8, 32));
//		JLabel[][] cellLables = new JLabel[8][32];
//		for (int row = 0; row < 8; row++) {
//			for (int col = 0; col < 32; col++) {
//				JLabel label = new JLabel(" ", SwingConstants.CENTER);
//				label.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//				gridPanel.add(label);
//				cellLables[row][col] = label;
//			}
//		}
//		mainPanel.add(gridPanel);
		// ===== 创建主Panel，使用BorderLayout布局 =====
//        JPanel mainPanel = new JPanel(new BorderLayout());
//        mainPanel.setSize(419, 503);
//        mainPanel.setLocation(943, 29);
//
//        // ===== 创建列标题Panel =====
//        JPanel colHeader = new JPanel(new GridLayout(1, 8));
//        for (int i = 0; i < 8; i++) {
//            JLabel label = new JLabel(String.format("%02X", i), SwingConstants.CENTER);
//            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//            colHeader.add(label);
//        }
//        mainPanel.add(colHeader, BorderLayout.NORTH);
//        
//        JPanel rowHeader = new JPanel(new GridLayout(32, 1));
//        for (int i = 0; i < 32; i++) {
//            JLabel label = new JLabel(String.format("%02X", i * 8), SwingConstants.CENTER);
//            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
//            rowHeader.add(label);
//        }
//
//        // ===== 创建RAM格子区域（GridPanel） =====
//        JPanel gridPanel = new JPanel(new GridLayout(32, 8));
//        JLabel[][] ramCells=new JLabel[32][8];
//        for (int row = 0; row < 32; row++) {
//            for (int col = 0; col < 8; col++) {
//                JLabel cell = new JLabel("00", SwingConstants.CENTER);
//                cell.setPreferredSize(new Dimension(40, 25));
//                cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//                cell.setOpaque(true);
//                cell.setBackground(Color.WHITE);
//                ramCells[row][col] = cell;
//                
//				gridPanel.add(cell);
//            }
//        }
//
//        contentPane.add(mainPanel,BorderLayout.EAST);
//        
//        JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
//        mainPanel.add(internalFrame, BorderLayout.WEST);
//        internalFrame.setVisible(true);
//        setLocationRelativeTo(null);
//        setVisible(true);

//----------Laufzeit----------------
		JPanel laufzeitPanel = new JPanel();
		laufzeitPanel.setBackground(new Color(255, 250, 240));
		laufzeitPanel.setLayout(null);
		laufzeitPanel.setBounds(394, 208, 200, 83);
		laufzeitPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 230, 240), 2));

		JLabel zeitLabel = new JLabel("Laufzeit:");
		zeitLabel.setFont(new Font("宋体", Font.PLAIN, 18));
		zeitLabel.setVerticalAlignment(SwingConstants.TOP);
		zeitLabel.setBounds(10, 10, 105, 29);
		// zeitLabel.setOpaque(true);
		// zeitLabel.setBackground(new Color(255,250,240));
		// zeitLabel.setBorder(BorderFactory.createLineBorder(new Color(200,230,240),
		// 2));
		laufzeitPanel.add(zeitLabel);

		tickField = new JTextField();
		tickField.setEditable(false);
		tickField.setBounds(66, 44, 80, 29);
		tickField.setBackground(Color.WHITE);
		laufzeitPanel.add(tickField);
		tickField.setColumns(10);

		JLabel lblNewLabel = new JLabel("μs");
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 18));
		lblNewLabel.setBounds(149, 43, 40, 29);
		laufzeitPanel.add(lblNewLabel);

		contentPane.add(laufzeitPanel);
//---------SFR-------------------------------------------------------------------	
		JPanel SFRpanel = new JPanel();
		SFRpanel.setToolTipText("");
		SFRpanel.setLayout(null);
		SFRpanel.setBounds(18, 119, 360, 172);
		SFRpanel.setBackground(new Color(255, 250, 240));
		contentPane.add(SFRpanel);

		JLabel wLable = new JLabel("W");
		wLable.setBounds(20, 10, 20, 20);
		SFRpanel.add(wLable);

		wField = new JTextField();
		wField.setBounds(40, 10, 56, 20);
		wField.setText(String.valueOf(simulator.getW()));
		SFRpanel.add(wField);

		JLabel pcLable = new JLabel("PC");
		pcLable.setBounds(20, 40, 20, 20);
		SFRpanel.add(pcLable);

		pcField = new JTextField();
		pcField.setBounds(40, 40, 56, 20);
		pcField.setText(String.valueOf(simulator.getPC()));
		SFRpanel.add(pcField);

		JLabel zLabel = new JLabel();
		zLabel.setText("Z");
		zLabel.setBounds(20, 81, 20, 15);
		SFRpanel.add(zLabel);

		StackPanel stack = new StackPanel();
		stack.setBounds(180, 10, 150, 160);
		SFRpanel.add(stack);

		zTogButt = new JToggleButton("0");
		zTogButt.setEnabled(false);
		zTogButt.setBounds(10, 99, 40, 33);
		SFRpanel.add(zTogButt);
		zTogButt.setBackground(new Color(255, 250, 240));

		dcTogButt = new JToggleButton("0");
		dcTogButt.setEnabled(false);
		dcTogButt.setBackground(new Color(255, 250, 240));
		dcTogButt.setBounds(60, 99, 40, 33);
		SFRpanel.add(dcTogButt);

		cTogButt = new JToggleButton("0");
		cTogButt.setEnabled(false);
		cTogButt.setBackground(new Color(255, 250, 240));
		cTogButt.setBounds(116, 99, 40, 33);
		SFRpanel.add(cTogButt);

		JLabel dcLabel = new JLabel();
		dcLabel.setText("DC");
		dcLabel.setBounds(72, 81, 20, 15);
		SFRpanel.add(dcLabel);

		JLabel cLabel = new JLabel();
		cLabel.setText("C");
		cLabel.setBounds(126, 81, 20, 15);
		SFRpanel.add(cLabel);
//-----------Frequency-------------------------------------------------------------------------------------	
		JPanel frequencyPanel = new JPanel();
		frequencyPanel.setLayout(null);
		frequencyPanel.setBounds(612, 208, 200, 81);
		frequencyPanel.setBackground(new Color(255, 250, 240));
		frequencyPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 230, 240), 2));
		contentPane.add(frequencyPanel);

		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(43, 46, 120, 25);
		comboBox.addItem("4 MHz");
		comboBox.addItem("1 MHz");
		comboBox.addItem("2 MHz");
		comboBox.addItem("3 MHz");

		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) comboBox.getSelectedItem();
				switch (selected) {
				case "4 MHz":
					tickDuration = 1.0; // 1 us
					break;
				case "1 MHz":
					tickDuration = 4.0;
					break;
				case "2 MHz":
					tickDuration = 2.0;
					break;
				case "8 MHZ":
					tickDuration = 0.5;
					break;
				}

				// 如果你有更新 Laufzeit 显示的函数的话，这里可以调用它
				updateLaufzeit(); // 你可以自己实现这个方法来刷新 textField 的显示
			}
		});

		frequencyPanel.add(comboBox);

		JLabel lblQuarzfrequency = new JLabel("Quarzfrequency:");
		lblQuarzfrequency.setVerticalAlignment(SwingConstants.TOP);
		lblQuarzfrequency.setFont(new Font("宋体", Font.PLAIN, 18));
		lblQuarzfrequency.setBounds(10, 10, 153, 29);
		frequencyPanel.add(lblQuarzfrequency);
//------------stop button------------------------------------------------
		btnStop = new JButton("Stop");
		btnStop.setBackground(new Color(255, 250, 240));
		btnStop.setBounds(755, 483, 116, 39);
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isRunning = false;
				clearTicktime();
			}
		});

		contentPane.add(btnStop);

//------REGISTER TABLE-------------------------------------------------------------------------------------------------------------

//---->OPTION 1: USE JTable<-----------------------------------------------------------------------------------        
		// TODO convert data register intArray into an 2D ObjectArray to use in a JTable
		// ! careful: jTable overrides the Columnheaders

//        String[] colNames = {"00","01","02","03","04","05","06","07"};
//        
//        Object[][] dataRegForTable;
//        
//        for (int i = 0; i < 256; i++) {
//        	//get access to DataMemory 
//        
//        				-> "dataRegForTable[row][col] = dataMemoryArray[x];"
//        
//        	//add always 8 ints per "row" -> = 32 columns with 8 values
//        }
//        
//        JTable registersValueTable = new JTable(dataRegForTable, colNames);
//        panel_RegisterValues.add(registersValueTable);

//---->OPTION 2: USE 256 JTextfields<------------------------------------------------------------------------------

//---->OPTION 3: USE 256 JButtons (actionlistener: open window to change value)------------------------------------

	}

	public void highlightLine(int lineNumber) {
		for (int i = 0; i < instructionRows.size(); i++) {
			if (i == lineNumber) {
				instructionRows.get(i).setBackground(Color.YELLOW);
			} else {
				instructionRows.get(i).setBackground(Color.LIGHT_GRAY);
			}
		}
	}

	public void updatePortA() {
		int portAValue = simulator.getValue(0x05);
		int trisAValue = simulator.getValue(0x85);

		// update TRIS
//        raTrisPin7Label.setText(((trisAValue >> 7) & 1) == 1 ? "!" : "-");
//        raTrisPin6Label.setText(((trisAValue >> 6) & 1) == 1 ? "!" : "-");
//        raTrisPin5Label.setText(((trisAValue >> 5) & 1) == 1 ? "!" : "-");
//        raTrisPin4Label.setText(((trisAValue >> 4) & 1) == 1 ? "!" : "-");
//        raTrisPin3Label.setText(((trisAValue >> 3) & 1) == 1 ? "!" : "-");
//        raTrisPin2Label.setText(((trisAValue >> 2) & 1) == 1 ? "!" : "-");
//        raTrisPin1Label.setText(((trisAValue >> 1) & 1) == 1 ? "!" : "-");
//        raTrisPin0Label.setText(((trisAValue >> 0) & 1) == 1 ? "!" : "-");

		// update PIN
//        raPin7ValueLabel.setText(((portAValue >> 7) & 1) + "");
//        raPin6ValueLabel.setText(((portAValue >> 6) & 1) + "");
//        raPin5ValueLabel.setText(((portAValue >> 5) & 1) + "");
//        raPin4ValueLabel.setText(((portAValue >> 4) & 1) + "");
//        raPin3ValueLabel.setText(((portAValue >> 3) & 1) + "");
//        raPin2ValueLabel.setText(((portAValue >> 2) & 1) + "");
//        raPin1ValueLabel.setText(((portAValue >> 1) & 1) + "");
//        raPin0ValueLabel.setText(((portAValue >> 0) & 1) + "");

//      rbPin7ValueLabel.setText(((portAValue >> 7) & 1) + "");
//      rbPin6ValueLabel.setText(((portAValue >> 6) & 1) + "");
//      rbPin5ValueLabel.setText(((portAValue >> 5) & 1) + "");
//      rbPin4ValueLabel.setText(((portAValue >> 4) & 1) + "");
//      rbPin3ValueLabel.setText(((portAValue >> 3) & 1) + "");
//      rbPin2ValueLabel.setText(((portAValue >> 2) & 1) + "");
//      rbPin1ValueLabel.setText(((portAValue >> 1) & 1) + "");
		rbPin0ValueTogButt.setText(((portAValue >> 0) & 1) + "");

	}

	public void updatePortB() {
		int portB = simulator.getValue(0x06);
		int trisB = simulator.getValue(0x86);
	}

	public void updatePort() {
		updatePortA();
		updatePortB();
	}

	public boolean isBreakpointAt(int pc) {
		if (pc >= 0 && pc < breakpoints.size()) {
			return breakpoints.get(pc).isSelected();
		}
		return false;
	}

	public void resetLaufZeit() {
		startTickCount = simulator.getMemory().getTimer0().getTickCount();
	}

	public void clearTicktime() {// set laufzeit to 0
		startTickCount = 0;
		tickField.setText("0.00");

	}

	public void updateLaufzeit() {
		int currtickCount = simulator.getMemory().getTimer0().getTickCount();
		int diffTick = currtickCount - startTickCount;
		double laufzeit = diffTick * tickDuration;
		tickField.setText(String.format("%.2f", laufzeit));
	}

	private void updateRegisters() {
		if (!isRunning) {
			return;
		}

		SwingUtilities.invokeLater(() -> {
			wField.setText(String.valueOf(simulator.getW()));
			pcField.setText(String.valueOf(simulator.getPC()));
			// other registers......
		});
	}

	private void updateFlags() {
		if (!isRunning)
			return;

		SwingUtilities.invokeLater(() -> {
			boolean z = simulator.getMemory().isZeroFlagSet();
			zTogButt.setSelected(z);
			zTogButt.setText(z ? "1" : "0");

			boolean dc = simulator.getMemory().isDCSet();
			dcTogButt.setSelected(dc);
			dcTogButt.setText(dc ? "1" : "0");

			boolean c = simulator.getMemory().isCarryFlagSet();
			cTogButt.setSelected(c);
			cTogButt.setText(c ? "1" : "0");
		});
	}

	public JPanel createRamGridPanel(JLabel[][] ramLabels) {
		JPanel ramGridPanel = new JPanel(new GridLayout(32, 8));
		for (int row = 0; row < 32; row++) {
			for (int col = 0; col < 8; col++) {
				JLabel cell = new JLabel("00", SwingConstants.CENTER);
				cell.setOpaque(true);
				cell.setBackground(Color.WHITE);
				cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				ramGridPanel.add(cell);
				ramLabels[row][col] = cell;
			}
		}

		// 让 gridPanel 能撑开！！！
		ramGridPanel.setPreferredSize(new Dimension(8 * 40, 32 * 25)); // 每个cell 40x25
		return ramGridPanel;
	}
}
