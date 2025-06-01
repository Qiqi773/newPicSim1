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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

public class GUIsim extends JFrame {

//	Font smallerFont = new Font();

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    SimulatorInterface simulator = new PicSimulator();

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
        
        //Colours
        Color lightLavender = new Color(230, 218, 237);
        Color darkLavender = new Color(185, 164, 189);

        // GUI Outer Box Frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1400, 750);
        contentPane = new JPanel();
        contentPane.setBackground(darkLavender);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        // GUI Box content
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        

//------SHOW LOADED TESTFILE---------------------------------------------------------------------------------------------------------
        JScrollPane showProgScrollPane = new JScrollPane();
        showProgScrollPane.setBounds(10, 301, 735, 402);
        contentPane.add(showProgScrollPane);

//------CHOOSE FILE------------------------------------------------------------------------------------------------------------------
        JButton buttonChooseFile = new JButton("Choose File"); // FileChooser to open System Explorer to get .LST-Files
        buttonChooseFile.setBackground(lightLavender);
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
        progPanel.setBackground(lightLavender);
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
                    label.setBackground(lightLavender);
                    header.add(label); // Breakpoint
                    header.add(new JLabel("Address"));
                    header.add(new JLabel("Machine Code"));
                    header.add(new JLabel("Instruction"));
                    header.add(new JLabel("Comment"));
                    progPanel.add(header); // add to the head

                    // line by line show the content
                    for (InstructionLine line : lines) {
                        JPanel row = new JPanel(new GridLayout(1, 5));
                        row.setBackground(lightLavender);

                        JCheckBox breakpointBox = new JCheckBox();
                        breakpointBox.setBackground(lightLavender);
                        row.add(breakpointBox);

                        row.add(new JLabel(String.format("%04X", line.getAddress())));
                        row.add(new JLabel(String.format("%04X", line.getMachineCode())));
                        row.add(new JLabel(line.getInstruction()));
                        row.add(new JLabel(line.getComment()));

                        progPanel.add(row);
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
        buttonRun.setBackground(lightLavender);
        buttonRun.addActionListener(new ActionListener() {
            @Override
        	public void actionPerformed(ActionEvent e) {
            															// TODO Run-Button
            }
        });
        buttonRun.setBounds(10, 252, 116, 39);
        contentPane.add(buttonRun);

//------STEP------------------------------------------------------------------------------------------------------------------
        JButton buttonStep = new JButton("Step");
        buttonStep.setBackground(lightLavender);
        buttonStep.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
																		// TODO Step-Button
			}
		});
        buttonStep.setBounds(136, 252, 116, 39);
        contentPane.add(buttonStep);

//------RESET-------------------------------------------------------------------------------------------------------------------
        JButton buttonReset = new JButton("Reset");
        buttonReset.setBackground(lightLavender);
        buttonReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
																		// TODO Reset-Button
			}
		});
        buttonReset.setBounds(262, 252, 116, 39);
        contentPane.add(buttonReset);

//------PORT A...-------------------------------------------------------------------------------------------------------------------
        // RA - TRIS(A) - PINS ->OUTER BOX
        JPanel panel_RA = new JPanel();
        panel_RA.setBackground(lightLavender);
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

        JLabel raTrisPin4Label = new JLabel("!");						// TODO need to connect state of tris with TRISreg from DATAreg
        raTrisPin4Label.setHorizontalAlignment(SwingConstants.CENTER);
        panel_RA.add(raTrisPin4Label);

        JLabel raTrisPin3Label = new JLabel("!");						// TODO need to connect state of tris with TRISreg from DATAreg
        raTrisPin3Label.setHorizontalAlignment(SwingConstants.CENTER);
        panel_RA.add(raTrisPin3Label);

        JLabel raTrisPin2Label = new JLabel("!");						// TODO need to connect state of tris with TRISreg from DATAreg
        raTrisPin2Label.setHorizontalAlignment(SwingConstants.CENTER);
        panel_RA.add(raTrisPin2Label);

        JLabel raTrisPin1Label = new JLabel("!");						// TODO need to connect state of tris with TRISreg from DATAreg
        raTrisPin1Label.setHorizontalAlignment(SwingConstants.CENTER);
        panel_RA.add(raTrisPin1Label);

        JLabel raTrisPin0Label = new JLabel("!");						// TODO need to connect state of tris with TRISreg from DATAreg
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

        //RA-PIN 4
        JToggleButton raPin4ValueTogButt = new JToggleButton("0");
        raPin4ValueTogButt.setBackground(lightLavender);
        ActionListener raPin4AcLis = new ActionListener() {		// TODO replace "setText" with 'get value from dataReg' + freeze button if its TRISreg is '0' (=output)
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

      //RA-PIN 3
        JToggleButton raPin3ValueTogButt = new JToggleButton("0");
        raPin3ValueTogButt.setBackground(lightLavender);
        ActionListener raPin3AcLis = new ActionListener() {		// TODO replace "setText" with 'get value from dataReg' + freeze button if its TRISreg is '0' (=output)
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

      //RA-PIN 2
        JToggleButton raPin2ValueTogButt = new JToggleButton("0");
        raPin2ValueTogButt.setBackground(lightLavender);
        ActionListener raPin2AcLis = new ActionListener() {		// TODO replace "setText" with 'get value from dataReg' + freeze button if its TRISreg is '0' (=output)
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

      //RA-PIN 1
        JToggleButton raPin1ValueTogButt = new JToggleButton("0");
        raPin1ValueTogButt.setBackground(lightLavender);
        ActionListener raPin1AcLis = new ActionListener() {		// TODO replace "setText" with 'get value from dataReg' + freeze button if its TRISreg is '0' (=output)
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
        
      //RA-PIN 0
        JToggleButton raPin0ValueTogButt = new JToggleButton("0");
        raPin0ValueTogButt.setBackground(lightLavender);
        ActionListener raPin0AcLis = new ActionListener() {		// TODO replace "setText" with 'get value from dataReg' + freeze button if its TRISreg is '0' (=output)
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
        panel_RB.setBackground(lightLavender);
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

        JLabel rbTrisPin7Label = new JLabel("!");						// TODO need to connect state of tris with TRISreg from DATAreg
        rbTrisPin7Label.setHorizontalAlignment(SwingConstants.CENTER);
        panel_RB.add(rbTrisPin7Label);

        JLabel rbTrisPin6Label = new JLabel("!");						// TODO need to connect state of tris with TRISreg from DATAreg
        rbTrisPin6Label.setHorizontalAlignment(SwingConstants.CENTER);
        panel_RB.add(rbTrisPin6Label);

        JLabel rbTrisPin5Label = new JLabel("!");						// TODO need to connect state of tris with TRISreg from DATAreg
        rbTrisPin5Label.setHorizontalAlignment(SwingConstants.CENTER);
        panel_RB.add(rbTrisPin5Label);

        JLabel rbTrisPin4Label = new JLabel("!");						// TODO need to connect state of tris with TRISreg from DATAreg
        rbTrisPin4Label.setHorizontalAlignment(SwingConstants.CENTER);
        panel_RB.add(rbTrisPin4Label);

        JLabel rbTrisPin3Label = new JLabel("!");						// TODO need to connect state of tris with TRISreg from DATAreg
        rbTrisPin3Label.setHorizontalAlignment(SwingConstants.CENTER);
        panel_RB.add(rbTrisPin3Label);

        JLabel rbTrisPin2Label = new JLabel("!");						// TODO need to connect state of tris with TRISreg from DATAreg
        rbTrisPin2Label.setHorizontalAlignment(SwingConstants.CENTER);
        panel_RB.add(rbTrisPin2Label);

        JLabel rbTrisPin1Label = new JLabel("!");						// TODO need to connect state of tris with TRISreg from DATAreg
        rbTrisPin1Label.setHorizontalAlignment(SwingConstants.CENTER);
        panel_RB.add(rbTrisPin1Label);

        JLabel rbTrisPin0Label = new JLabel("!");						// TODO need to connect state of tris with TRISreg from DATAreg
        rbTrisPin0Label.setHorizontalAlignment(SwingConstants.CENTER);
        panel_RB.add(rbTrisPin0Label);

//------PINS PORT B---------------------------------------------------------------------------------------------------------
        JLabel rbPINLabel = new JLabel("PIN");
        rbPINLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel_RB.add(rbPINLabel);

        //RB-PIN 7
        JToggleButton rbPin7ValueTogButt = new JToggleButton("0");
        rbPin7ValueTogButt.setBackground(lightLavender);
        ActionListener rbPin7AcLis = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (rbPin7ValueTogButt.isSelected()) {
        			rbPin7ValueTogButt.setText("1");		// TODO replace "setText" with 'get value from dataReg' + freeze button if its TRISreg is '0' (=output)
        		} else {											
        			rbPin7ValueTogButt.setText("0");
        		}
        	}
        };
        rbPin7ValueTogButt.addActionListener(rbPin7AcLis);
        panel_RB.add(rbPin7ValueTogButt);

      //RB-PIN 6
        JToggleButton rbPin6ValueTogButt = new JToggleButton("0");
        rbPin6ValueTogButt.setBackground(lightLavender);
        ActionListener rbPin6AcLis = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (rbPin6ValueTogButt.isSelected()) {
        			rbPin6ValueTogButt.setText("1");		// TODO replace "setText" with 'get value from dataReg' + freeze button if its TRISreg is '0' (=output)
        		} else {											
        			rbPin6ValueTogButt.setText("0");
        		}
        	}
        };
        rbPin6ValueTogButt.addActionListener(rbPin6AcLis);
        panel_RB.add(rbPin6ValueTogButt);

      //RB-PIN 5
        JToggleButton rbPin5ValueTogButt = new JToggleButton("0");
        rbPin5ValueTogButt.setBackground(lightLavender);
        ActionListener rbPin5AcLis = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (rbPin5ValueTogButt.isSelected()) {
        			rbPin5ValueTogButt.setText("1");		// TODO replace "setText" with 'get value from dataReg' + freeze button if its TRISreg is '0' (=output)
        		} else {											
        			rbPin5ValueTogButt.setText("0");
        		}
        	}
        };
        rbPin5ValueTogButt.addActionListener(rbPin5AcLis);
        panel_RB.add(rbPin5ValueTogButt);

      //RB-PIN 4
        JToggleButton rbPin4ValueTogButt = new JToggleButton("0");
        rbPin4ValueTogButt.setBackground(lightLavender);
        ActionListener rbPin4AcLis = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (rbPin4ValueTogButt.isSelected()) {
        			rbPin4ValueTogButt.setText("1");		// TODO replace "setText" with 'get value from dataReg' + freeze button if its TRISreg is '0' (=output)
        		} else {											
        			rbPin4ValueTogButt.setText("0");
        		}
        	}
        };
        rbPin4ValueTogButt.addActionListener(rbPin4AcLis);
        panel_RB.add(rbPin4ValueTogButt);

      //RB-PIN 3
        JToggleButton rbPin3ValueTogButt = new JToggleButton("0");
        rbPin3ValueTogButt.setBackground(lightLavender);
        ActionListener rbPin3AcLis = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (rbPin3ValueTogButt.isSelected()) {
        			rbPin3ValueTogButt.setText("1");		// TODO replace "setText" with 'get value from dataReg' + freeze button if its TRISreg is '0' (=output)
        		} else {											
        			rbPin3ValueTogButt.setText("0");
        		}
        	}
        };
        rbPin3ValueTogButt.addActionListener(rbPin3AcLis);
        panel_RB.add(rbPin3ValueTogButt);

      //RB-PIN 2
        JToggleButton rbPin2ValueTogButt = new JToggleButton("0");
        rbPin2ValueTogButt.setBackground(lightLavender);
        ActionListener rbPin2AcLis = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (rbPin2ValueTogButt.isSelected()) {
        			rbPin2ValueTogButt.setText("1");		// TODO replace "setText" with 'get value from dataReg' + freeze button if its TRISreg is '0' (=output)
        		} else {											
        			rbPin2ValueTogButt.setText("0");
        		}
        	}
        };
        rbPin2ValueTogButt.addActionListener(rbPin2AcLis);
        panel_RB.add(rbPin2ValueTogButt);

      //RB-PIN 1
        JToggleButton rbPin1ValueTogButt = new JToggleButton("0");
        rbPin1ValueTogButt.setBackground(lightLavender);
        ActionListener rbPin1AcLis = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (rbPin1ValueTogButt.isSelected()) {
        			rbPin1ValueTogButt.setText("1");		// TODO replace "setText" with 'get value from dataReg' + freeze button if its TRISreg is '0' (=output)
        		} else {											
        			rbPin1ValueTogButt.setText("0");
        		}
        	}
        };
        rbPin1ValueTogButt.addActionListener(rbPin1AcLis);
        panel_RB.add(rbPin1ValueTogButt);

      //RB-PIN 0
        JToggleButton rbPin0ValueTogButt = new JToggleButton("0");
        rbPin0ValueTogButt.setBackground(lightLavender);
        ActionListener rbPin0AcLis = new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (rbPin0ValueTogButt.isSelected()) {
        			rbPin0ValueTogButt.setText("1");		// TODO replace "setText" with 'get value from dataReg' + freeze button if its TRISreg is '0' (=output)
        		} else {											
        			rbPin0ValueTogButt.setText("0");
        		}
        	}
        };
        rbPin0ValueTogButt.addActionListener(rbPin0AcLis);
        panel_RB.add(rbPin0ValueTogButt);
        
//------SHOW REGISTERS-------------------------------------------------------------------------------------------------------------
        JScrollPane scrollPane_showRegisters = new JScrollPane();
        scrollPane_showRegisters.setBounds(876, 10, 500, 693);
        contentPane.add(scrollPane_showRegisters);
   
//------COLUMN HEADERS-------------------------------------------------------------------------------------------------------------
        JPanel panel_ColHeaders = new JPanel();
        panel_ColHeaders.setBackground(lightLavender);
        scrollPane_showRegisters.setColumnHeaderView(panel_ColHeaders);
        
        JLabel col0RegsLabel = new JLabel("00");
        col0RegsLabel.setBackground(darkLavender);
        panel_ColHeaders.add(col0RegsLabel);
        
        JLabel col1RegsLabel = new JLabel("01");
        col1RegsLabel.setBackground(darkLavender);
        panel_ColHeaders.add(col1RegsLabel);
        
        JLabel col2RegsLabel = new JLabel("02");
        col2RegsLabel.setBackground(darkLavender);
        panel_ColHeaders.add(col2RegsLabel);
        
        JLabel col3RegsLabel = new JLabel("03");
        col3RegsLabel.setBackground(darkLavender);
        panel_ColHeaders.add(col3RegsLabel);
        
        JLabel col4RegsLabel = new JLabel("04");
        col4RegsLabel.setBackground(darkLavender);
        panel_ColHeaders.add(col4RegsLabel);
        
        JLabel col5RegsLabel = new JLabel("05");
        col5RegsLabel.setBackground(darkLavender);
        panel_ColHeaders.add(col5RegsLabel);
        
        JLabel col6RegsLabel = new JLabel("06");
        col6RegsLabel.setBackground(darkLavender);
        panel_ColHeaders.add(col6RegsLabel);
        
        JLabel col7RegsLabel = new JLabel("07");
        col7RegsLabel.setBackground(darkLavender);
        panel_ColHeaders.add(col7RegsLabel);
        
//------ROW HEADERS----------------------------------------------------------------------------------------------------------------
        JPanel panel_RowHeaders = new JPanel();
        panel_RowHeaders.setBackground(lightLavender);
        scrollPane_showRegisters.setRowHeaderView(panel_RowHeaders);
        panel_RowHeaders.setLayout(new GridLayout(0, 32, 0, 0));
        
        JLabel row0RegsLabel = new JLabel("00");
        row0RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row0RegsLabel);
        
        JLabel row1RegsLabel = new JLabel("08");
        row1RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row1RegsLabel);
        
        JLabel row2RegsLabel = new JLabel("10");
        row2RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row2RegsLabel);
        
        JLabel row3RegsLabel = new JLabel("18");
        row3RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row3RegsLabel);
        
        JLabel row4RegsLabel = new JLabel("20");
        row4RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row4RegsLabel);
        
        JLabel row5RegsLabel = new JLabel("28");
        row5RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row5RegsLabel);
        
        JLabel row6RegsLabel = new JLabel("30");
        row6RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row6RegsLabel);
        
        JLabel row7RegsLabel = new JLabel("38");
        row7RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row7RegsLabel);
        
        JLabel row8RegsLabel = new JLabel("40");
        row8RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row8RegsLabel);
        
        JLabel row9RegsLabel = new JLabel("48");
        row9RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row9RegsLabel);
        
        JLabel row10RegsLabel = new JLabel("50");
        row10RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row10RegsLabel);
        
        JLabel row11RegsLabel = new JLabel("58");
        row11RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row11RegsLabel);
        
        JLabel row12RegsLabel = new JLabel("60");
        row12RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row12RegsLabel);
        
        JLabel row13RegsLabel = new JLabel("68");
        row13RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row13RegsLabel);
        
        JLabel row14RegsLabel = new JLabel("70");
        row14RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row14RegsLabel);
        
        JLabel row15RegsLabel = new JLabel("78");
        row15RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row15RegsLabel);
        
        JLabel row16RegsLabel = new JLabel("80");
        row16RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row16RegsLabel);
        
        JLabel row17RegsLabel = new JLabel("88");
        row17RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row17RegsLabel);
        
        JLabel row18RegsLabel = new JLabel("90");
        row18RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row18RegsLabel);
        
        JLabel row19RegsLabel = new JLabel("98");
        row19RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row19RegsLabel);
        
        JLabel row20RegsLabel = new JLabel("A0");
        row20RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row20RegsLabel);
        
        JLabel row21RegsLabel = new JLabel("A8");
        row21RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row21RegsLabel);
        
        JLabel row22RegsLabel = new JLabel("B0");
        row22RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row22RegsLabel);
        
        JLabel row23RegsLabel = new JLabel("B8");
        row23RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row23RegsLabel);
        
        JLabel row24RegsLabel = new JLabel("C0");
        row24RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row24RegsLabel);
        
        JLabel row25RegsLabel = new JLabel("C8");
        row25RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row25RegsLabel);
        
        JLabel row26RegsLabel = new JLabel("D0");
        row26RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row26RegsLabel);
        
        JLabel row27RegsLabel = new JLabel("D8");
        row27RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row27RegsLabel);
        
        JLabel row28RegsLabel = new JLabel("E0");
        row28RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row28RegsLabel);
        
        JLabel row29RegsLabel = new JLabel("E8");
        row29RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row29RegsLabel);
        
        JLabel row30RegsLabel = new JLabel("F0");
        row30RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row30RegsLabel);
        
        JLabel row31RegsLabel = new JLabel("F8");
        row31RegsLabel.setBackground(darkLavender);
        panel_RowHeaders.add(row31RegsLabel);
        
//------REGISTER TABLE-------------------------------------------------------------------------------------------------------------
        
        

        
        
        

    }
}
