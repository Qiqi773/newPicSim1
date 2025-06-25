package gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RamTablePanel extends JPanel {
	private JTable table;
	private DefaultTableModel tableModel;

	public RamTablePanel() {
		setLayout(new BorderLayout());

		String[] columnNames = new String[9]; // 第一列是地址，后面 8 列是数据
		columnNames[0] = "Addr";
		for (int i = 1; i <= 8; i++) {
			columnNames[i] = String.format("%02X", i - 1);
		}

		// 初始化空内容
		Object[][] data = new Object[32][9];
		for (int row = 0; row < 32; row++) {
			data[row][0] = String.format("%02X", row * 8); // 地址列
			for (int col = 1; col <= 8; col++) {
				data[row][col] = "00"; // default
			}
		}

		tableModel = new DefaultTableModel(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(tableModel);
		table.setFont(new Font("Monospaced", Font.PLAIN, 12));
		table.setRowHeight(20);
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	// update the value
	public void updateRam(int[] ram) {
		for (int addr = 0; addr < 256 && addr < ram.length; addr++) {
			int row = addr / 8;
			int col = (addr % 8) + 1; // 第一列是地址，所以 +1
			String hex = String.format("%02X", ram[addr] & 0xFF);
			tableModel.setValueAt(hex, row, col);
		}
	}
}
