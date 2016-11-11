import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random; 

public class GUI {
	
	private JFrame frame;
	private JPanel panelHead;
	private JLabel labelHead;
	private JPanel mainLeftPanel;
	private BoxLayout boxLayout;
	private JComboBox<String> queryList;

	public void GUI ()
	{
		frame = new JFrame();

		panelHead = new JPanel(new BorderLayout());
		labelHead = getLabel("DBLP Query Engine", SwingConstants.CENTER);
		labelHead.setFont(new Font("Serif", Font.BOLD, 24));
		panelHead.add(labelHead, BorderLayout.CENTER);

		mainLeftPanel = new JPanel();
		mainLeftPanel.setMinimumSize(new Dimension(30,30));
		mainLeftPanel.setPreferredSize(new Dimension(200,600));
		
		boxLayout = new BoxLayout(mainLeftPanel, BoxLayout.Y_AXIS);
		
		String[] queries = { "Queries", "Query 1", "Query 2"};
		queryList = new JComboBox<String>(queries);
		queryList.setSelectedIndex(0);
		mainLeftPanel.add(Box.createRigidArea(new Dimension(0,50)));
		mainLeftPanel.add(queryList);
		
		queryList.addActionListener(new queryListener());
		
		JPanel mainRightPanel = new JPanel();
		mainRightPanel.setMinimumSize(new Dimension(30,30));
		mainRightPanel.setPreferredSize(new Dimension(400,600));


		JSplitPane mainContent = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, mainLeftPanel, mainRightPanel);
		mainContent.setResizeWeight(0.5);
        mainContent.setOneTouchExpandable(true);
        mainContent.setContinuousLayout(true);
        mainContent.setDividerLocation(200 + mainContent.getInsets().left);

		JSplitPane mainFrame = new JSplitPane( JSplitPane.VERTICAL_SPLIT, panelHead, mainContent);
		mainFrame.setResizeWeight(0.5);
        mainFrame.setOneTouchExpandable(true);
        mainFrame.setContinuousLayout(true);
		mainFrame.setDividerLocation(50 + mainFrame.getInsets().left);

		frame.getContentPane().add(mainFrame);
		frame.setSize(600,600);
		frame.setVisible(true);
	}
	
	class queryListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			JComboBox cb = (JComboBox)e.getSource();
		    String query = (String)cb.getSelectedItem();
			if (query.equals("Query 1"))
			{
			    panelHead.setVisible(false);
			}
			else
			{
			    mainLeftPanel.setVisible(false);
			}
		}
	}

	public static JLabel getLabel(String text) 
	{
        return getLabel(text, SwingConstants.LEFT);
    }

    
    public static JLabel getLabel(String text, int alignment) 
    {
        JLabel l = new JLabel(text, alignment);
        return l;
    }


	public static void main(String[] args) 
	{
		GUI main = new GUI();
	}
	
}
