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
	private JComboBox<String> searchList;
	private JPanel mainRightPanel;
	private JSplitPane mainContent;
	private JSplitPane mainFrame;
	private JTextField tags;
	private JTextField sinceYear;
	private JTextField rangeBegin;
	private JTextField rangeEnd;

	public GUI ()
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
		
		mainRightPanel = new JPanel();
		mainRightPanel.setMinimumSize(new Dimension(30,30));
		mainRightPanel.setPreferredSize(new Dimension(400,600));


		mainContent = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, mainLeftPanel, mainRightPanel);
		mainContent.setResizeWeight(0.5);
        mainContent.setOneTouchExpandable(true);
        mainContent.setContinuousLayout(true);
        mainContent.setDividerLocation(225 + mainContent.getInsets().left);

		mainFrame = new JSplitPane( JSplitPane.VERTICAL_SPLIT, panelHead, mainContent);
		mainFrame.setResizeWeight(0.5);
        mainFrame.setOneTouchExpandable(true);
        mainFrame.setContinuousLayout(true);
		mainFrame.setDividerLocation(50 +  mainFrame.getInsets().left);

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
				mainFrame.setVisible(false);

				mainLeftPanel = new JPanel();
				mainLeftPanel.setMinimumSize(new Dimension(30,30));
				mainLeftPanel.setPreferredSize(new Dimension(200,600));
				mainLeftPanel.setVisible(true);
				
				panelHead = new JPanel(new BorderLayout());
				labelHead = getLabel("DBLP Query Engine", SwingConstants.CENTER);
				labelHead.setFont(new Font("Serif", Font.BOLD, 24));
				panelHead.add(labelHead, BorderLayout.CENTER);
				
				Box leftBox = Box.createVerticalBox();
				
				String[] queries = {"Queries", "Query 1", "Query 2"};
				queryList = new JComboBox<String>(queries);
				queryList.setSelectedIndex(1);
				leftBox.add(Box.createRigidArea(new Dimension(0,50)));
				leftBox.add(queryList);
				
				queryList.addActionListener(new queryListener());
				
				String[] searches = { "Search by", "Author", "Title"};
				searchList = new JComboBox<String>(searches);
				searchList.setSelectedIndex(0);
				leftBox.add(Box.createRigidArea(new Dimension(0,10)));
				leftBox.add(searchList);
				leftBox.add(Box.createRigidArea(new Dimension(0,10)));
				
				Box tinyBox = Box.createHorizontalBox();
				JLabel tagsLabel = new JLabel("Name/Title tags");
				tinyBox.add(tagsLabel);
				tinyBox.add(Box.createRigidArea(new Dimension(5,0)));
				tags = new JTextField(5);
				tinyBox.add(tags);
				leftBox.add(tinyBox);
				leftBox.add(Box.createRigidArea(new Dimension(0,10)));

				Box tinyBox2 = Box.createHorizontalBox();
				JLabel sinceLabel = new JLabel("Since Year");
				tinyBox2.add(sinceLabel);
				tinyBox2.add(Box.createRigidArea(new Dimension(20,0)));
				sinceYear = new JTextField("YYYY",4);
				tinyBox2.add(sinceYear);
				leftBox.add(tinyBox2);
				leftBox.add(Box.createRigidArea(new Dimension(0,10)));

				Box tinyBox3 = Box.createHorizontalBox();
				JLabel rangeLabel = new JLabel("Custom Range");
				tinyBox3.add(rangeLabel);
				tinyBox3.add(Box.createRigidArea(new Dimension(5,0)));
				rangeBegin = new JTextField("YYYY",3);
				tinyBox3.add(rangeBegin);
				tinyBox3.add(Box.createRigidArea(new Dimension(5,0)));
				JLabel hyphen = new JLabel("-");
				tinyBox3.add(hyphen);
				tinyBox3.add(Box.createRigidArea(new Dimension(5,0)));
				rangeEnd = new JTextField("YYYY",3);
				tinyBox3.add(rangeEnd);
				tinyBox3.add(Box.createRigidArea(new Dimension(5,0)));
				leftBox.add(tinyBox3);
				leftBox.add(Box.createRigidArea(new Dimension(0,10)));

				Box tinyBox4 = Box.createVerticalBox();
			    ButtonGroup radioGroup = new ButtonGroup();
			    JRadioButton rbutton;
			    radioGroup.add(rbutton = new JRadioButton("Sort by Year"));
			    rbutton.setAlignmentY(Component.TOP_ALIGNMENT);
			    tinyBox4.add(rbutton);
			    tinyBox4.add(Box.createRigidArea(new Dimension(0,10)));
			    radioGroup.add(rbutton = new JRadioButton("Sort by Relevance"));
			    rbutton.setAlignmentY(Component.TOP_ALIGNMENT);
			    tinyBox4.add(rbutton);
			    leftBox.add(tinyBox4);
				leftBox.add(Box.createRigidArea(new Dimension(0,10)));

				mainLeftPanel.add(leftBox);
				
				mainRightPanel = new JPanel();
				mainRightPanel.setMinimumSize(new Dimension(30,30));
				mainRightPanel.setPreferredSize(new Dimension(400,600));

				mainContent = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT, mainLeftPanel, mainRightPanel);
				mainContent.setResizeWeight(0.5);
				mainContent.setOneTouchExpandable(true);
				mainContent.setContinuousLayout(true);
				mainContent.setDividerLocation(225 + mainContent.getInsets().left);

				mainFrame = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelHead, mainContent);
				mainFrame.setResizeWeight(0.5);
				mainFrame.setOneTouchExpandable(true);
				mainFrame.setContinuousLayout(true);
				mainFrame.setDividerLocation(50 + mainFrame.getInsets().left);

				frame.getContentPane().add(mainFrame);
				frame.setSize(600,600);
				frame.setVisible(true);
				
			}
			else
			{
				//query2();
			}
		}
	}


	//public static void query1()
	//{

	//}

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

