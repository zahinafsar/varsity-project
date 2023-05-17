package Components;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table extends JTable {
    public Table(DefaultTableModel tableModel) {
        super(tableModel);
        Font tableFont = super.getFont().deriveFont(18f);
        super.setFont(tableFont);
        Font headerFont = super.getTableHeader().getFont().deriveFont(Font.BOLD, 18f);
        super.getTableHeader().setFont(headerFont);
        int rowHeight = 30;
        super.setRowHeight(rowHeight);
    }
}
