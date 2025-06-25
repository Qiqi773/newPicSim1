package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StackPanel extends JPanel {
	private JTable table;
	private DefaultTableModel model;

	public StackPanel() {
		setLayout(new BorderLayout());

		model = new DefaultTableModel(new Object[] { "Stack" }, 8); // 8 Zeilen
		table = new JTable(model);
		table.setEnabled(false); // 不允许手动编辑

		add(new JScrollPane(table), BorderLayout.CENTER);
		clearStack();
	}

	// 更新显示的堆栈内容
	public void updateStack(int[] stackData, int sp) {
		for (int i = 0; i < 8; i++) {
			model.setValueAt(String.format("0x%04X", stackData[i]), i, 0);
		}

		// 高亮当前 Stackpointer 的行
		table.clearSelection();
		table.setRowSelectionInterval(sp, sp);
	}

	public void clearStack() {
		for (int i = 0; i < 8; i++) {
			model.setValueAt("0000", i, 0);
		}
		table.clearSelection();
	}
}
