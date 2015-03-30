import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

public class EuromillionsGUI {

	private String[] ptArray = {"Gerar", "Ver Sorteio", "Gerar quantas vezes? ", "Voltar" };
	private String[] ukArray = {"Draw", "Check Draw" , "Number of draws? " , "Back"};
	
	private ImageIcon pFlag = new ImageIcon(EuromillionsGUI.class.getResource("/pflag.jpg"));
	private ImageIcon ukFlag = new ImageIcon(EuromillionsGUI.class.getResource("/ukflag.jpg"));
	
	private JFrame reDrawFrame = new JFrame("EuroMillions");
	private JFrame numberFrame = new JFrame("EuroMillions");
	private JFrame whatFrame = new JFrame("EuroMillions");
	private JFrame langFrame = new JFrame("EuroMillions");
	private JFrame checkFrame = new JFrame("EuroMillions");
	
	private JButton drawButton = new JButton();
	private JButton pFlagB = new JButton(pFlag);
	private JButton ukFlagB = new JButton(ukFlag);
	private JButton generateButton = new JButton();
	private JButton checkDrawButton = new JButton();
	private JButton backButton = new JButton();
	private JButton backOptionsButton = new JButton();
	
	private JTextArea textArea = new JTextArea();
	private JTextField input = new JTextField(3);
	private JLabel label = new JLabel();
	private JLabel checkDrawLabel = new JLabel();
	private JScrollPane areaScroll;
	
	private Container buttonsContainer = new Container();
	private int draws;
	private String sDraws;
	private Drawer l = new Drawer();
	
	private LinkedList<JButton> buttonsList = new LinkedList<JButton>();
	
	public EuromillionsGUI(){
		
		buttonsList.add(checkDrawButton);
		buttonsList.add(drawButton);
		buttonsList.add(generateButton);
		buttonsList.add(checkDrawButton);
		buttonsList.add(backButton);
		buttonsList.add(backOptionsButton);
		
		for (JButton button : buttonsList) {
			button.setFont(new Font("Arial", Font.BOLD, 12));
			button.setForeground(new Color(0x05314d));
		}
		
		langFrame.setLayout(new FlowLayout());
		langFrame.setSize(170, 70);
		langFrame.setLocationRelativeTo(null);
		langFrame.add(pFlagB);
		langFrame.add(ukFlagB);
		langFrame.setVisible(true);
		
		whatFrame.setLayout(new FlowLayout());
		whatFrame.setSize(210, 65);
		whatFrame.add(generateButton);
		whatFrame.add(checkDrawButton);
		whatFrame.setLocationRelativeTo(null);
		
		checkFrame.setLayout(new FlowLayout());
		checkFrame.setSize(280, 120);
		checkFrame.add(checkDrawLabel);

		checkFrame.add(backButton);
		checkFrame.setLocationRelativeTo(null);
		
		reDrawFrame.setLayout(new BorderLayout());
		reDrawFrame.setSize(300, 300);
		reDrawFrame.setLocationRelativeTo(null);
		textArea.setEditable(false);
		reDrawFrame.add(buttonsContainer, BorderLayout.SOUTH);
		
		buttonsContainer.setLayout(new GridLayout(1,2));
		buttonsContainer.add(drawButton);
		buttonsContainer.add(backOptionsButton);
		
		areaScroll = new JScrollPane(textArea);
		areaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		reDrawFrame.add(areaScroll);
		
		numberFrame.setLayout(new FlowLayout());
		numberFrame.setSize(200, 60);
		numberFrame.setLocationRelativeTo(null);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setForeground(new Color(0x05314d));
		numberFrame.add(label);
		numberFrame.add(input);
		
		textArea.setFont(new Font("Arial", Font.BOLD, 12));
		textArea.setForeground(new Color(0x0564a1));;
		
		ukFlagB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				l.language = "UK";
				langFrame.setVisible(false);
				whatFrame.setVisible(true);
				assignStrings();
			}
		});
		
		pFlagB.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {	
				l.language = "P";
				langFrame.setVisible(false);
				whatFrame.setVisible(true);
				assignStrings();				
			}
		});
		
		checkDrawButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				checkFrame.setVisible(true);
				try {
					checkDrawLabel.setText(l.retreiveResults());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				checkFrame.setVisible(false);
				whatFrame.setVisible(true);				
			}
		});
		
		generateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {				
				whatFrame.setVisible(false);
				numberFrame.setVisible(true);	
			}
		});
		
		drawButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				draws = 0;
				input.setText("0");
				numberFrame.setVisible(true);
				getData();				
			}
		});
		
		backOptionsButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				whatFrame.setVisible(true);				
			}
		});
		
		input.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				numberFrame.setVisible(false);
				reDrawFrame.setVisible(true);
				getData();	
				l.draw(draws);
				for (int i = 0; i < l.draws.size(); i++) {
					textArea.append(l.draws.get(i));
				}
				
				l.draws.clear();
			}
		});

		whatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		whatFrame.setResizable(false);
		langFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		langFrame.setResizable(false);
		numberFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		numberFrame.setResizable(false);
		reDrawFrame.setResizable(false);
		reDrawFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void getData() {
		sDraws = input.getText();
		draws = Integer.parseInt(input.getText());
	}
	
	public void init() {
		langFrame.setVisible(true);
	}

	public static void main(String[] args) {
		EuromillionsGUI g = new EuromillionsGUI();
		g.init();
	}
	
	private void assignStrings() {
		
		if (l.language.equals("UK")) {
			drawButton.setText(ukArray[0]);
			generateButton.setText(ukArray[0]);
			checkDrawButton.setText(ukArray[1]);
			label.setText(ukArray[2]);
			backButton.setText(ukArray[3]);
			backOptionsButton.setText(ukArray[3]);
		}
		
		if (l.language.equals("P")) {
			drawButton.setText(ptArray[0]);
			generateButton.setText(ptArray[0]);
			checkDrawButton.setText(ptArray[1]);
			label.setText(ptArray[2]);
			backButton.setText(ptArray[3]);
			backOptionsButton.setText(ptArray[3]);
		}
	}
}
