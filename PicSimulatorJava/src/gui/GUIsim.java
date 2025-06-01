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
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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

        // GUI Outer Box Frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1400, 750);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(185, 164, 189));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        // GUI Box content
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JScrollPane showProgScrollPane = new JScrollPane();
        showProgScrollPane.setBounds(10, 301, 735, 402);
        contentPane.add(showProgScrollPane);

//------CHOOSE FILE------------------------------------------------------------------------------------------------------------------
        JButton buttonChooseFile = new JButton("Choose File"); // FileChooser to open System Explorer to get .LST-Files
        buttonChooseFile.setBackground(new Color(230, 218, 237));
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
        progPanel.setBackground(new Color(230, 218, 237));
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
                    label.setBackground(new Color(230, 218, 237));
                    header.add(label); // Breakpoint
                    header.add(new JLabel("Address"));
                    header.add(new JLabel("Machine Code"));
                    header.add(new JLabel("Instruction"));
                    header.add(new JLabel("Comment"));
                    progPanel.add(header); // add to the head

                    // line by line show the content
                    for (InstructionLine line : lines) {
                        JPanel row = new JPanel(new GridLayout(1, 5));
                        row.setBackground(new Color(230, 218, 237));

                        JCheckBox breakpointBox = new JCheckBox();
                        breakpointBox.setBackground(new Color(230, 218, 237));
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
        buttonRun.setBackground(new Color(230, 218, 237));
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
        buttonStep.setBackground(new Color(230, 218, 237));
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
        buttonReset.setBackground(new Color(230, 218, 237));
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
        panel_RA.setBackground(new Color(230, 218, 237));
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
        raPin4ValueTogButt.setBackground(new Color(230, 218, 237));
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
        raPin3ValueTogButt.setBackground(new Color(230, 218, 237));
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
        raPin2ValueTogButt.setBackground(new Color(230, 218, 237));
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
        raPin1ValueTogButt.setBackground(new Color(230, 218, 237));
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
        raPin0ValueTogButt.setBackground(new Color(230, 218, 237));
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
        panel_RB.setBackground(new Color(230, 218, 237));
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
        rbPin7ValueTogButt.setBackground(new Color(230, 218, 237));
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
        rbPin6ValueTogButt.setBackground(new Color(230, 218, 237));
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
        rbPin5ValueTogButt.setBackground(new Color(230, 218, 237));
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
        rbPin4ValueTogButt.setBackground(new Color(230, 218, 237));
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
        rbPin3ValueTogButt.setBackground(new Color(230, 218, 237));
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
        rbPin2ValueTogButt.setBackground(new Color(230, 218, 237));
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
        rbPin1ValueTogButt.setBackground(new Color(230, 218, 237));
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
        rbPin0ValueTogButt.setBackground(new Color(230, 218, 237));
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

    }
}
