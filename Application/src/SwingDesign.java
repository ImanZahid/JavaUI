import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javafx.embed.swing.SwingNode;

public class SwingDesign {
    private JTable table;
    private JPanel mainPanel;
    private TableRowSorter<DefaultTableModel> sorter;
    private SwingNode tableNode; // the added SwingNode

    public SwingDesign() {
        createSwingContent();
        createAndSetSwingNode(); // setup the SwingNode
    }

    private void createSwingContent() {
        String[] columnNames = {"Name", "Surname", "Gender"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        mainPanel = new JPanel(new GridLayout(1, 1));
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane); 
    }

    // this new method creates and sets the content for the SwingNode
    private void createAndSetSwingNode() {
        this.tableNode = new SwingNode();
        SwingUtilities.invokeLater(() -> {
            this.tableNode.setContent(mainPanel);
        });
    }

    public JTable getTable() {
        return table;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public TableRowSorter<DefaultTableModel> getSorter() {
        return sorter;
    }

    public SwingNode getTableNode() { // Getter for SwingNode
        return this.tableNode;
    }
    public Dimension getTablePreferredSize() {
        return table.getPreferredSize();
    }

    public void setTablePreferredSize(Dimension size) {
        // Assuming you have a reference to your JTable or whatever Swing component you're using.
        table.setPreferredScrollableViewportSize(size);
        table.revalidate();
        table.repaint();
    }


}
