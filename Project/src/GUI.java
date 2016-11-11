import java.awt.*;
import javax.swing.*;


public class GUI {
	
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
		JFrame frame = new JFrame();

		JPanel panelHead = new JPanel(new BorderLayout());
		JLabel labelHead = getLabel("DBLP Query Engine", SwingConstants.CENTER);
		labelHead.setFont(new Font("Serif", Font.BOLD, 24));
		panelHead.add(labelHead, BorderLayout.CENTER);

		JPanel mainLeftPanel = new JPanel();
		mainLeftPanel.setMinimumSize(new Dimension(30,30));
		mainLeftPanel.setPreferredSize(new Dimension(200,600));
		
		BoxLayout boxLayout = new BoxLayout(mainLeftPanel, BoxLayout.Y_AXIS);
		
		String[] queries = { "Queries", "Query 1", "Query 2"};
		JComboBox<String> queryList = new JComboBox<String>(queries);
		queryList.setSelectedIndex(0);
		mainLeftPanel.add(Box.createRigidArea(new Dimension(0,100)));
		mainLeftPanel.add(queryList);
		
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
	
}
