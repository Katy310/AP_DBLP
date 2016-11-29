import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random; 
import javax.swing.table.*;




public class GUI {
	
	
	// All GUI View Elements
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
	private JButton search;
	private JButton searchquery2;
	private JButton searchquery3;
	private JButton reset;
	private JTextField noPublications;
	private JTextField tillyear;
	private JLabel error;
	private JLabel error_Since;
	private JFrame dialog = new JFrame();
	private JTable table;
	private JTable tablequery2;
	private JButton next;
	private JLabel result;
	private JLabel outputLabel;
	private JLabel exactLabel;
	private JLabel outputLabel1;
	private JLabel exactLabel1;
	
	
	//Model Controller elements
	private static Parser parser = new Parser();
	private List<Publication> Query1Result = new ArrayList<Publication>();
	private List<Person> Query2Result = new ArrayList<Person>();
	
	//Basic neccesities
	private String choice = "";
	private String tag;
	private String YearTag;
	private String BtYearTag1, BtYearTag2;
	private boolean SortRelevanceChoice = false;
	private boolean SortDateChoice = false;
	private boolean SinceYearChoice = false;
	private boolean BwYearChoice = false;
	private int pageno;
	
	public static void main(String[] args) 
	{	
		parser.Initialize();
		new GUI();
	}
	
	public GUI(){
		frame = new JFrame();
		homeScreen();
	}
	
	
	
	public void homeScreen()
	{
		panelHead = new JPanel(new BorderLayout());
		labelHead = getLabel("DBLP Query Engine", SwingConstants.CENTER);
		labelHead.setFont(new Font("Serif", Font.BOLD, 24));
		panelHead.add(labelHead, BorderLayout.CENTER);




		mainLeftPanel = new JPanel();
		mainLeftPanel.setMinimumSize(new Dimension(30,30));
		mainLeftPanel.setPreferredSize(new Dimension(200,600));
		
		boxLayout = new BoxLayout(mainLeftPanel, BoxLayout.Y_AXIS);
		
		String[] queries = { "Queries", "Query 1", "Query 2", "Query 3"};
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
		frame.setSize(800,800);
		frame.setVisible(true);
	}


	public void query1LeftScreen(){
		mainLeftPanel = new JPanel();
		mainLeftPanel.setMinimumSize(new Dimension(30,30));
		mainLeftPanel.setPreferredSize(new Dimension(200,600));
		mainLeftPanel.setVisible(true);
			
		panelHead = new JPanel(new BorderLayout());
		labelHead = getLabel("DBLP Query Engine", SwingConstants.CENTER);
		labelHead.setFont(new Font("Serif", Font.BOLD, 24));
		panelHead.add(labelHead, BorderLayout.CENTER);
			
		Box leftBox = Box.createVerticalBox();
			
		String[] queries = {"Queries", "Query 1", "Query 2","Query 3"};
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
		searchList.addActionListener(new ChoiceListener());
		leftBox.add(Box.createRigidArea(new Dimension(0,10)));
			
		Box tinyBox = Box.createHorizontalBox();
		JLabel tagsLabel = new JLabel("Name/Title tags");
		tinyBox.add(tagsLabel);
		tinyBox.add(Box.createRigidArea(new Dimension(5,0)));
		tags = new JTextField(5);
		tinyBox.add(tags);
		tag = tags.getText();
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
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		ButtonGroup radioGroup = new ButtonGroup();
		JRadioButton rbutton1, rbutton2, rbutton3, rbutton4;
		radioGroup.add(rbutton1 = new JRadioButton("Sort by Date"));
		rbutton1.addActionListener(new SortChoiceListener());
		rbutton1.setName("Sort by Date");
		panel1.add(rbutton1, BorderLayout.PAGE_START);
		tinyBox4.add(panel1);
		tinyBox4.add(Box.createRigidArea(new Dimension(5,0)));
		radioGroup.add(rbutton2 = new JRadioButton("Sort by Relevance"));
		rbutton2.setName("Sort by Relevance");
		rbutton2.addActionListener(new SortChoiceListener());
		panel2.add(rbutton2, BorderLayout.PAGE_START);
		tinyBox4.add(panel2);
		tinyBox4.add(Box.createRigidArea(new Dimension(5,0)));
		radioGroup.add(rbutton3 = new JRadioButton("Since Year"));
		rbutton3.addActionListener(new SortChoiceListener());
		rbutton3.setName("Since Year");
		panel3.add(rbutton3, BorderLayout.PAGE_START);
		tinyBox4.add(panel3);
		tinyBox4.add(Box.createRigidArea(new Dimension(5,0)));
		radioGroup.add(rbutton4 = new JRadioButton("B/w Years"));
		rbutton4.addActionListener(new SortChoiceListener());
		rbutton4.setName("B/w Years");
		panel4.add(rbutton4, BorderLayout.PAGE_START);
		tinyBox4.add(panel4);
		leftBox.add(tinyBox4);
		leftBox.add(Box.createRigidArea(new Dimension(0,10)));

		Box tinyBox5 = Box.createHorizontalBox();
		search = new JButton("Search");
		search.setForeground(Color.WHITE);
		search.setBackground(Color.BLACK);
		tinyBox5.add(search);
		search.addActionListener(new queryListener1());
		tinyBox5.add(Box.createRigidArea(new Dimension(10,10)));
		reset = new JButton("Reset");
		reset.setForeground(Color.WHITE);
		reset.setBackground(Color.RED);
		reset.addActionListener(new resetListener());
		tinyBox5.add(reset);
		leftBox.add(tinyBox5);
		leftBox.add(Box.createRigidArea(new Dimension(0,10)));
		
		Box tinyBox6 = Box.createHorizontalBox();
		error = new JLabel("Cool Down Katyayani");
		tinyBox6.add(error);
		leftBox.add(tinyBox6);


		mainLeftPanel.add(leftBox);
	}
	
	public void query1RightScreenWithTable(){
		mainRightPanel = new JPanel();




		Box rightBox = Box.createVerticalBox();




		String[] columnNames = {"SNo", "Authors", "Title", "Pages", "Year", "Volume", "Book Title", "url"};
		Object[][] data = {
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null},
			{null, null, null, null, null, null, null, null}
		};



		result = new JLabel("No. of Publications: ");

		table = new JTable(data,columnNames);
		
		JScrollPane scrollPane = new JScrollPane(table, 
                                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar bar = scrollPane.getVerticalScrollBar();
		bar.setPreferredSize(new Dimension(500, 0));
		rightBox.add(result);
		rightBox.add(Box.createRigidArea(new Dimension(5,0)));
		rightBox.add(scrollPane);
		
		next = new JButton("Next");
		next.addActionListener(new nextListener());
		rightBox.add(next);
		mainRightPanel.add(rightBox);




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
		frame.setSize(800,800);
		frame.setVisible(true);
	}
	
	public void query2RightScreenWithTable(){
		mainRightPanel = new JPanel();
		
		Box rightBox = Box.createVerticalBox();

		String[] columnNames = {"Authors"};
		Object[][] data = {
			{null},
			{null},
			{null},
			{null},
			{null},
			{null},
			{null},
			{null},
			{null},
			{null},
			{null},
			{null},
			{null},
			{null},
			{null},
			{null},
			{null},
			{null},
			{null},
			{null}
		};



		result = new JLabel("No. of Authors: ");
		tablequery2 = new JTable(data,columnNames);
		
		JScrollPane scrollPane = new JScrollPane(tablequery2, 
                                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		JScrollBar bar = scrollPane.getVerticalScrollBar();
		bar.setPreferredSize(new Dimension(500, 0));
		rightBox.add(result);
		rightBox.add(Box.createRigidArea(new Dimension(5,0)));
		rightBox.add(scrollPane);
		
		next = new JButton("Next");
		next.addActionListener(new nextQuery2());
		rightBox.add(next);
		mainRightPanel.add(rightBox);




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
		frame.setSize(800,800);
		frame.setVisible(true);
	}
	
	public void query1RightScreenWithoutTable(){
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
		frame.setSize(800,800);
		frame.setVisible(true);
	}
	
	public void query2LeftScreen(){
		
		mainLeftPanel = new JPanel();
		mainLeftPanel.setMinimumSize(new Dimension(30,30));
		mainLeftPanel.setPreferredSize(new Dimension(200,600));
		mainLeftPanel.setVisible(true);
		
		panelHead = new JPanel(new BorderLayout());
		labelHead = getLabel("DBLP Query Engine", SwingConstants.CENTER);
		labelHead.setFont(new Font("Serif", Font.BOLD, 24));
		panelHead.add(labelHead, BorderLayout.CENTER);
		
		Box leftBox = Box.createVerticalBox();
		
		String[] queries = {"Queries", "Query 1", "Query 2", "Query 3"};
		queryList = new JComboBox<String>(queries);
		queryList.setSelectedIndex(2);
		leftBox.add(Box.createRigidArea(new Dimension(0,50)));
		leftBox.add(queryList);
		leftBox.add(Box.createRigidArea(new Dimension(0,50)));
		
		queryList.addActionListener(new queryListener());




		Box tinyBox = Box.createHorizontalBox();
		JLabel publicationsLabel = new JLabel("No. of Publications");
		tinyBox.add(publicationsLabel);
		tinyBox.add(Box.createRigidArea(new Dimension(5,0)));
		noPublications = new JTextField(5);
		tinyBox.add(noPublications);
		leftBox.add(tinyBox);
		leftBox.add(Box.createRigidArea(new Dimension(0,50)));




		Box tinyBox2 = Box.createHorizontalBox();
		searchquery2 = new JButton("Search");
		searchquery2.setForeground(Color.WHITE);
		searchquery2.setBackground(Color.BLACK);
		searchquery2.addActionListener(new Query2Listener());
		tinyBox2.add(searchquery2);
		tinyBox2.add(Box.createRigidArea(new Dimension(10,10)));
		reset = new JButton("Reset");
		reset.setForeground(Color.WHITE);
		reset.setBackground(Color.RED);
		reset.addActionListener(new resetListener());
		tinyBox2.add(reset);
		leftBox.add(tinyBox2);
		leftBox.add(Box.createRigidArea(new Dimension(0,50)));




		mainLeftPanel.add(leftBox);
	}
	
	public void query3LeftScreen(){
		
		mainLeftPanel = new JPanel();
		mainLeftPanel.setMinimumSize(new Dimension(30,30));
		mainLeftPanel.setPreferredSize(new Dimension(200,600));
		mainLeftPanel.setVisible(true);
		
		panelHead = new JPanel(new BorderLayout());
		labelHead = getLabel("DBLP Query Engine", SwingConstants.CENTER);
		labelHead.setFont(new Font("Serif", Font.BOLD, 24));
		panelHead.add(labelHead, BorderLayout.CENTER);
		
		Box leftBox = Box.createVerticalBox();
		
		String[] queries = {"Queries", "Query 1", "Query 2","Query 3"};
		queryList = new JComboBox<String>(queries);
		queryList.setSelectedIndex(3);
		leftBox.add(Box.createRigidArea(new Dimension(0,50)));
		leftBox.add(queryList);
		leftBox.add(Box.createRigidArea(new Dimension(0,50)));
		
		queryList.addActionListener(new queryListener());




		Box tinyBox = Box.createHorizontalBox();
		JLabel authorLabel = new JLabel("Author Name");
		tinyBox.add(authorLabel);
		tinyBox.add(Box.createRigidArea(new Dimension(5,0)));
		noPublications = new JTextField(5);
		tinyBox.add(noPublications);
		leftBox.add(tinyBox);
		leftBox.add(Box.createRigidArea(new Dimension(0,50)));

		Box tinyBox1 = Box.createHorizontalBox();
		JLabel yearLabel = new JLabel("Till Year");
		tinyBox1.add(yearLabel);
		tinyBox1.add(Box.createRigidArea(new Dimension(5,0)));
		tillyear = new JTextField(5);
		tinyBox1.add(tillyear);
		leftBox.add(tinyBox1);
		leftBox.add(Box.createRigidArea(new Dimension(0,50)));


		Box tinyBox2 = Box.createHorizontalBox();
		searchquery3 = new JButton("Search");
		searchquery3.setForeground(Color.WHITE);
		searchquery3.setBackground(Color.BLACK);
		searchquery3.addActionListener(new Query3Listener());
		tinyBox2.add(searchquery3);
		tinyBox2.add(Box.createRigidArea(new Dimension(10,10)));
		reset = new JButton("Reset");
		reset.setForeground(Color.WHITE);
		reset.setBackground(Color.RED);
		reset.addActionListener(new resetListener());
		tinyBox2.add(reset);
		leftBox.add(tinyBox2);
		leftBox.add(Box.createRigidArea(new Dimension(0,50)));




		mainLeftPanel.add(leftBox);
	}
	
	public void query3RightScreen(){
		mainRightPanel = new JPanel();
		mainRightPanel.setMinimumSize(new Dimension(30,30));
		mainRightPanel.setPreferredSize(new Dimension(400,600));

		Box rightBox = Box.createVerticalBox();
		outputLabel = new JLabel("Output: ");
		outputLabel1 = new JLabel("");
		Box tinyBox = Box.createHorizontalBox();
		tinyBox.add(outputLabel);
		tinyBox.add(Box.createRigidArea(new Dimension(10,0)));
		tinyBox.add(outputLabel1);
		tinyBox.add(Box.createRigidArea(new Dimension(10,0)));
		rightBox.add(tinyBox);
		rightBox.add(Box.createRigidArea(new Dimension(0,10)));
		
		Box tinyBox1 = Box.createHorizontalBox();
		exactLabel = new JLabel("Exact Value: ");
		exactLabel1 = new JLabel("");
		tinyBox1.add(exactLabel);
		tinyBox1.add(Box.createRigidArea(new Dimension(10,0)));
		tinyBox1.add(exactLabel1);
		tinyBox1.add(Box.createRigidArea(new Dimension(10,0)));
		rightBox.add(tinyBox1);
		rightBox.add(Box.createRigidArea(new Dimension(0,10)));
		
		mainRightPanel.add(rightBox);


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
		frame.setSize(800,800);
		frame.setVisible(true);
	}
	
	class queryListener1 implements ActionListener
	{
		
		@Override
		public void actionPerformed(ActionEvent e)
		{	
			boolean x = false;
			mainFrame.setVisible(false);
//			String SortBy;
			//parser.Query1(choice, tag, Sorter, y1, y2);
			
			choice = (String)searchList.getSelectedItem();
			choice = choice.toLowerCase();
			if(choice.equals("search by") || choice.equals("")){
				error = new JLabel("Invalid Choice of Query Bro!");
//				error.setText("Invalid Choice of Query Bro!");
				x = true;
			}
			else if(SortRelevanceChoice){
				SortRelevanceChoice = false;
				tag = tags.getText();
				System.out.println(choice + " , " + tag + " , " + "Relevance");
				parser.Query1(choice,tag,"Relevance",0,0);
				if(choice.equals("author")){
					Query1Result = parser.getQuery1aResult();
				}
				else if(choice.equals("title")){
					Query1Result = parser.getQuery1bResult();
				}
			}
			else if(SortDateChoice){
				SortDateChoice = false;
				tag = tags.getText();
				System.out.println(choice + " , " + tag + " , " + "Date");
				parser.Query1(choice, tag, "Date", 0, 0);
				if(choice.equals("author")){
					Query1Result = parser.getQuery1aResult();
				}
				else if(choice.equals("title")){
					Query1Result = parser.getQuery1bResult();
				}
			}
			else if(SinceYearChoice){
				SinceYearChoice = false;
				tag = tags.getText();
				int y1 = Integer.parseInt(sinceYear.getText());
				parser.Query1(choice, tag,"Year", y1, 0);
				if(choice.equals("author")){
					Query1Result = parser.getQuery1aResult();
				}
				else if(choice.equals("title")){
					Query1Result = parser.getQuery1bResult();
				}
			}
			else if(BwYearChoice){
				BwYearChoice = false;
				tag = tags.getText();
				int y1 = Integer.parseInt(rangeBegin.getText());
				int y2 = Integer.parseInt(rangeEnd.getText());
				System.out.println(choice + " , " + tag + " , " + "Date" + y1 + y2 + "mogm");
				parser.Query1(choice, tag, "BetweenYear", y1, y2);
				if(choice.equals("author")){
					Query1Result = parser.getQuery1aResult();
				}
				else if(choice.equals("title")){
					Query1Result = parser.getQuery1bResult();
				}
			}	
			
			//display Query1 on table
			if(x == false){
				
				query1LeftScreen();		
				query1RightScreenWithTable();
				result.setText("No. of Publications : " + Query1Result.size());
				pageno = 0;
				int limit = 0;
				if(Query1Result.size() > 20){
					limit = 20;
				}
				else{
					limit = Query1Result.size();
				}
				
				for ( int i = 0; i < limit; i++)	
				{
					table.setValueAt(i+1, i, 0);
					table.setValueAt(Query1Result.get(i).getFuckingAuthors(), i, 1);
					table.setValueAt(Query1Result.get(i).getTitle(), i, 2);
					table.setValueAt(Query1Result.get(i).getPages(), i, 3);
					table.setValueAt(Query1Result.get(i).getYear(), i, 4);
					table.setValueAt(Query1Result.get(i).getVolume(), i, 5);
					table.setValueAt(Query1Result.get(i).getJournalBook(), i, 6);
					table.setValueAt(Query1Result.get(i).getURL(), i, 7);
					
				}
				pageno = 1; 
			}
		
		else{
			query1LeftScreen();
			query1RightScreenWithoutTable();
		}
		}
	}
	
	class queryListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			@SuppressWarnings("rawtypes")
			JComboBox cb = (JComboBox)e.getSource();
		    String query = (String)cb.getSelectedItem();
		    if (query.equals("Queries"))
		    {	
		    	mainFrame.setVisible(false);
		    	homeScreen();
		    }
		    else if (query.equals("Query 1"))
			{	
				mainFrame.setVisible(false);
				query1LeftScreen();
				query1RightScreenWithoutTable();
				
			}
			else if(query.equals("Query 2"))
			{
				mainFrame.setVisible(false);

				query2LeftScreen();
				query1RightScreenWithoutTable();
			}
			else
			{
				mainFrame.setVisible(false);
				query3LeftScreen();
				query1RightScreenWithoutTable();
			}
		}
	}
	
	class ChoiceListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			@SuppressWarnings("rawtypes")
			JComboBox cb = (JComboBox)e.getSource();
		    choice = (String)cb.getSelectedItem();
		    System.out.println(choice);
		}
	}
	
	class SortChoiceListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			JRadioButton rb = (JRadioButton)e.getSource();
			if (rb.getName().equals("Sort by Date")){
				SortDateChoice = true;
				SortRelevanceChoice = false;
				SinceYearChoice = false;
				BwYearChoice = false;
				System.out.println("Year");
			}
			else if (rb.getName().equals("Sort by Relevance")){
				SortRelevanceChoice = true;
				SortDateChoice = false;
				SinceYearChoice = false;
				BwYearChoice = false;
				System.out.println("Relevance");
			}else if (rb.getName().equals("Since Year")){
				SortRelevanceChoice = false;
				SortDateChoice = false;
				SinceYearChoice = true;
				BwYearChoice = false;
				System.out.println("Since Year");
			}else if (rb.getName().equals("B/w Years")){
				SortRelevanceChoice = false;
				SortDateChoice = false;
				SinceYearChoice = false;
				BwYearChoice = true;
				System.out.println("B/w Years");
			}
		}
	}
	
	class OKSince implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			if(!isInteger(YearTag)){
				error_Since.setText("Invalid Input Try Again !");
			}
			else{
				dialog.setVisible(false);
			}
		}
	
	
	}
	
	public static boolean isInteger(String s){
		try{
			Integer.parseInt(s);
			return true;
		}catch(NumberFormatException ex){
			return false;
		}
	}
	// Label Functions
	public static JLabel getLabel(String text) 
	{
       	return getLabel(text, SwingConstants.LEFT);
    }


    public static JLabel getLabel(String text, int alignment) 
    {
       	JLabel l = new JLabel(text, alignment);
       	return l;
    }


    class nextListener implements ActionListener
    {
    	@Override
		public void actionPerformed(ActionEvent e)
		{
    		mainFrame.setVisible(false);
			query1RightScreenWithTable();
			pageno++;
			int j=0, i;
			int limit;
			result.setText("No. of Publications : " + Query1Result.size());
			if(Query1Result.size() >= pageno*20){
				limit = pageno*20;
			}
			else{
				limit = Query1Result.size();
			}
			for ( i = (pageno-1)*20; i < limit; i++)
			{
				table.setValueAt(i+1, j, 0);
				table.setValueAt(Query1Result.get(i).getFuckingAuthors(), j, 1);
				table.setValueAt(Query1Result.get(i).getTitle(), j, 2);
				table.setValueAt(Query1Result.get(i).getPages(), j, 3);
				table.setValueAt(Query1Result.get(i).getYear(), j, 4);
				table.setValueAt(Query1Result.get(i).getVolume(), j, 5);
				table.setValueAt(Query1Result.get(i).getJournalBook(), j, 6);
				table.setValueAt(Query1Result.get(i).getURL(), j, 7);
				j++;
			}
		}
    }




    class resetListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			mainFrame.setVisible(false);
			homeScreen();
			Query1Result.clear();
			Query2Result.clear();
		}
	}
    
    class Query2Listener implements ActionListener
    {
    	public void actionPerformed(ActionEvent e){
    		mainFrame.setVisible(false);
    		query2RightScreenWithTable();
    		parser.Query2(Integer.parseInt(noPublications.getText()));
    		Query2Result = parser.getQuery2Result();
    		result.setText("No. of Authors : " + Query2Result.size());
    		int i;
    		int limit;
    		if(Query2Result.size() >= 20){
    			limit = 20;
    		}
    		else{
    			limit = Query2Result.size();
    		}
    		pageno = 0;
    		for(i = 0;i < limit; i++){
    			tablequery2.setValueAt(Query2Result.get(i).getPrimName(), i, 0);
    		}
    		pageno = 1;
    	}
    }
    
    class nextQuery2 implements ActionListener
	{
		public void actionPerformed(ActionEvent e){
			mainFrame.setVisible(false);
			query2RightScreenWithTable();
			result.setText("No. of Authors : " + Query2Result.size());
			pageno++;
			int i,limit;
			if(Query2Result.size() >= 20*pageno){
    			limit = 20*pageno;
    		}
    		else{
    			limit = Query2Result.size();
    		}
			int j = 0;
			for ( i = (pageno-1)*20; i < limit; i++){
				tablequery2.setValueAt(Query2Result.get(i).getPrimName(), j, 0);
				j++;
			}
		}
	}
    
    class Query3Listener implements ActionListener
    {
    	public void actionPerformed(ActionEvent e){
    		mainFrame.setVisible(false);
    		query3RightScreen();
//    		System.out.println(tillyear.getText());
//    		System.out.println(noPublications.getText());
    		int year = Integer.parseInt(tillyear.getText());
    		String auth = noPublications.getText();
    		parser.Query3(auth, year);
    		exactLabel1.setText(Double.toString(parser.getExact()));
    		outputLabel1.setText(Double.toString(parser.getOutput()));
    	}
    }
    
    
	
}












