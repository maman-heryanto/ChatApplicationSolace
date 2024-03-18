package com.solace.labs.chat;

import java.awt.Component;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;

@SuppressWarnings("serial")
public class JCheckBoxList extends JList<JCheckBox> {
	protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);
	private ChatClientApp chatClientApp = null;

	public JCheckBoxList() {
		setCellRenderer(new CellRenderer());
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int index = locationToIndex(e.getPoint());
				if (index != -1) {
					JCheckBox checkbox = (JCheckBox) getModel().getElementAt(index);
					checkbox.setSelected(!checkbox.isSelected());
					if (checkbox.isSelected()) {
						System.out.println("Checkbox "+ checkbox.getText()+" is checked");
					} else {
						System.out.println("Checkbox "+ checkbox.getText()+" is unchecked");
					}
					chatClientApp.updateDirectorySubscriptions(checkbox.getText(), checkbox.isSelected());
					repaint();
				}
			}
		});
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	public JCheckBoxList(ListModel<JCheckBox> model){
		this();
		setModel(model);
	}

	public void setChatClient(ChatClientApp cCA) {
		chatClientApp = cCA;
	}

	protected class CellRenderer implements ListCellRenderer<JCheckBox> {
		public Component getListCellRendererComponent(
				JList<? extends JCheckBox> list, JCheckBox value, int index,
				boolean isSelected, boolean cellHasFocus) {
			JCheckBox checkbox = value;

			//Drawing checkbox, change the appearance here
			checkbox.setBackground(isSelected ? getSelectionBackground()
					: getBackground());
			checkbox.setForeground(isSelected ? getSelectionForeground()
					: getForeground());
			checkbox.setEnabled(isEnabled());
			checkbox.setFont(getFont());
			checkbox.setFocusPainted(false);
			checkbox.setBorderPainted(true);
			checkbox.setBorder(isSelected ? UIManager
					.getBorder("List.focusCellHighlightBorder") : noFocusBorder);
			return checkbox;
		}
	}
}