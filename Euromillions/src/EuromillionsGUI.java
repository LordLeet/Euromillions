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

	private static final int NUMBERS = 5;
	private static final int STARS = 2;

	private String[] ptArray = {"Gerar", "Ver Sorteio", "Gerar quantas vezes? ", "Voltar" , "Números: " , "Estrelas: " , "Verificar" , "Comparar"};
	private String[] ukArray = {"Draw", "Check draw" , "Number of draws? " , "Back" , "Numbers: " , "Stars: " , "Check" , "Compare draw"};

	private LinkedList<JTextField> numberTextFieldList = new LinkedList<JTextField>();
	private LinkedList<JTextField> starsTextFieldList = new LinkedList<JTextField>();

	private ImageIcon pFlag = new ImageIcon(EuromillionsGUI.class.getResource("/pflag.jpg"));
	private ImageIcon ukFlag = new ImageIcon(EuromillionsGUI.class.getResource("/ukflag.jpg"));

	private JFrame resultsFrame = new JFrame();
	private JFrame howManyDrawsFrame = new JFrame();
	private JFrame drawCheckCompareFrame = new JFrame();
	private JFrame pickLanguageFrame = new JFrame();
	private JFrame checkLastDrawFrame = new JFrame();
	private JFrame compareDrawFrame = new JFrame();

	private JButton drawButton = new JButton();
	private JButton pFlagB = new JButton(pFlag);
	private JButton ukFlagB = new JButton(ukFlag);
	private JButton generateButton = new JButton();
	private JButton checkDrawButton = new JButton();
	private JButton backButton = new JButton();
	private JButton backOptionsButton = new JButton();
	private JButton compareDrawButton = new JButton();
	private JButton goCompareDrawButton = new JButton();
	private JButton backCompareButton = new JButton();

	private JTextField input = new JTextField(2);

	private JLabel label = new JLabel();
	private JLabel checkDrawLabel = new JLabel();
	private JLabel askNumbersLabel = new JLabel();
	private JLabel askStarsLabel = new JLabel();

	private Container buttonsContainer = new Container();
	private Container drawComparingContainer = new Container();
	private Container drawComparingButtonContainer = new Container();

	private JScrollPane areaScroll;
	private JTextArea textArea = new JTextArea();
	private int draws;
	private String sDraws;
	private Drawer drawer = new Drawer();

	private LinkedList<JButton> buttonsList = new LinkedList<JButton>();

	public EuromillionsGUI(){

		buttonsList.add(checkDrawButton);
		buttonsList.add(drawButton);
		buttonsList.add(generateButton);
		buttonsList.add(checkDrawButton);
		buttonsList.add(backButton);
		buttonsList.add(backOptionsButton);
		buttonsList.add(compareDrawButton);
		buttonsList.add(goCompareDrawButton);
		buttonsList.add(backCompareButton);

		for (JButton button : buttonsList) {
			button.setFont(new Font("Arial", Font.BOLD, 12));
			button.setForeground(new Color(0x05314d));
		}

		pickLanguageFrame.setLayout(new FlowLayout());
		pickLanguageFrame.setSize(170, 70);
		pickLanguageFrame.setLocationRelativeTo(null);
		pickLanguageFrame.add(pFlagB);
		pickLanguageFrame.add(ukFlagB);
		pickLanguageFrame.setVisible(true);
		pickLanguageFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pickLanguageFrame.setResizable(false);

		compareDrawFrame.setLayout(new BorderLayout());
		compareDrawFrame.setSize(360, 100);
		compareDrawFrame.setLocationRelativeTo(null);
		compareDrawFrame.add(drawComparingContainer);
		drawComparingContainer.setLayout(new FlowLayout());	
		drawComparingContainer.add(askNumbersLabel);

		for (int i = 0; i < NUMBERS; i++) {
			JTextField numberTextField = new JTextField(2);
			numberTextFieldList.add(numberTextField);
			drawComparingContainer.add(numberTextField);
		}

		drawComparingContainer.add(askStarsLabel);		

		for (int i = 0; i < STARS; i++) {
			JTextField starTextField = new JTextField(2);
			starsTextFieldList.add(starTextField);
			drawComparingContainer.add(starTextField);
		}

		drawComparingButtonContainer.setLayout(new FlowLayout());
		compareDrawFrame.add(drawComparingButtonContainer, BorderLayout.SOUTH);
		drawComparingButtonContainer.add(compareDrawButton);
		drawComparingButtonContainer.add(backCompareButton);

		drawCheckCompareFrame.setLayout(new FlowLayout());
		drawCheckCompareFrame.setSize(320, 65);
		drawCheckCompareFrame.add(generateButton);
		drawCheckCompareFrame.add(checkDrawButton);
		drawCheckCompareFrame.add(goCompareDrawButton);
		drawCheckCompareFrame.setLocationRelativeTo(null);
		drawCheckCompareFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawCheckCompareFrame.setResizable(false);

		checkLastDrawFrame.setLayout(new FlowLayout());
		checkLastDrawFrame.setSize(280, 120);
		checkLastDrawFrame.add(checkDrawLabel);
		checkLastDrawFrame.add(backButton);
		checkLastDrawFrame.setLocationRelativeTo(null);

		resultsFrame.setLayout(new BorderLayout());
		resultsFrame.setSize(300, 300);
		resultsFrame.setLocationRelativeTo(null);
		textArea.setEditable(false);
		resultsFrame.add(buttonsContainer, BorderLayout.SOUTH);
		resultsFrame.setResizable(false);
		resultsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		buttonsContainer.setLayout(new GridLayout(1,2));
		buttonsContainer.add(drawButton);
		buttonsContainer.add(backOptionsButton);

		areaScroll = new JScrollPane(textArea);
		areaScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		resultsFrame.add(areaScroll);

		howManyDrawsFrame.setLayout(new FlowLayout());
		howManyDrawsFrame.setSize(200, 60);
		howManyDrawsFrame.setLocationRelativeTo(null);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		label.setForeground(new Color(0x05314d));
		howManyDrawsFrame.add(label);
		howManyDrawsFrame.add(input);
		howManyDrawsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		howManyDrawsFrame.setResizable(false);

		textArea.setFont(new Font("Arial", Font.BOLD, 12));
		textArea.setForeground(new Color(0x0564a1));;

		ukFlagB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				drawer.language = "UK";
				pickLanguageFrame.setVisible(false);
				drawCheckCompareFrame.setVisible(true);
				assignStrings();
			}
		});

		pFlagB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {	
				drawer.language = "P";
				pickLanguageFrame.setVisible(false);
				drawCheckCompareFrame.setVisible(true);
				assignStrings();				
			}
		});

		backCompareButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawCheckCompareFrame.setVisible(true);
				compareDrawFrame.setVisible(false);				
			}
		});

		compareDrawButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				LinkedList<Integer> numbersTempList = new LinkedList<Integer>();
				LinkedList<Integer> starsTempList = new LinkedList<Integer>();

				int intAux;

				try {
					checkDrawLabel.setText(drawer.retreiveResults());
				} catch (IOException e1) {
					e1.printStackTrace();
				}

				for (JTextField numberField : numberTextFieldList) {
					intAux = Integer.parseInt(numberField.getText());
					numbersTempList.add(intAux);
				}

				for (JTextField starField : starsTextFieldList) {
					intAux = Integer.parseInt(starField.getText());
					starsTempList.add(intAux);
				}

				try {

					drawer.compareWithdraw(numbersTempList,starsTempList);

					for (JTextField textField : numberTextFieldList) {
						textField.setBackground(new Color(139,0,0));
						textField.setForeground(Color.WHITE);
					}

					for (JTextField textField : starsTextFieldList) {
						textField.setBackground(new Color(139,0,0));
						textField.setForeground(Color.WHITE);
					}
					
					for (int i = 0; i < drawer.getCorrectNumbers().size(); i++) {
						numberTextFieldList.get(drawer.getCorrectNumbers().get(i)).setBackground(new Color(34,139,34));
					}

					for (int i = 0; i < drawer.getCorrectStars().size(); i++) {
						starsTextFieldList.get(drawer.getCorrectStars().get(i)).setBackground(new Color(34,139,34));
					}

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		checkDrawButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				checkLastDrawFrame.setVisible(true);
				try {
					checkDrawLabel.setText(drawer.retreiveResults());
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkLastDrawFrame.setVisible(false);
				drawCheckCompareFrame.setVisible(true);				
			}
		});

		generateButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {				
				drawCheckCompareFrame.setVisible(false);
				howManyDrawsFrame.setVisible(true);	
			}
		});

		drawButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				draws = 0;
				input.setText("0");
				howManyDrawsFrame.setVisible(true);
				getData();				
			}
		});

		backOptionsButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				drawCheckCompareFrame.setVisible(true);				
			}
		});

		goCompareDrawButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				compareDrawFrame.setVisible(true);
			}
		});

		input.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				howManyDrawsFrame.setVisible(false);
				resultsFrame.setVisible(true);
				getData();	
				drawer.draw(draws);
				for (int i = 0; i < drawer.draws.size(); i++) {
					textArea.append(drawer.draws.get(i));
				}

				drawer.draws.clear();
			}
		});

	}

	public void getData() {
		sDraws = input.getText();
		draws = Integer.parseInt(input.getText());
	}

	public void init() {
		pickLanguageFrame.setVisible(true);
	}

	public static void main(String[] args) {
		EuromillionsGUI g = new EuromillionsGUI();
		g.init();
	}

	private void assignStrings() {

		if (drawer.language.equals("UK")) {
			drawButton.setText(ukArray[0]);
			generateButton.setText(ukArray[0]);
			checkDrawButton.setText(ukArray[1]);
			label.setText(ukArray[2]);
			backButton.setText(ukArray[3]);
			backOptionsButton.setText(ukArray[3]);
			askNumbersLabel.setText(ukArray[4]);
			askStarsLabel.setText(ukArray[5]);
			compareDrawButton.setText(ukArray[6]);
			goCompareDrawButton.setText(ukArray[7]);	
			backCompareButton.setText(ukArray[3]);
		}

		if (drawer.language.equals("P")) {
			drawButton.setText(ptArray[0]);
			generateButton.setText(ptArray[0]);
			checkDrawButton.setText(ptArray[1]);
			label.setText(ptArray[2]);
			backButton.setText(ptArray[3]);
			backOptionsButton.setText(ptArray[3]);
			askNumbersLabel.setText(ptArray[4]);
			askStarsLabel.setText(ptArray[5]);
			compareDrawButton.setText(ptArray[6]);
			goCompareDrawButton.setText(ptArray[7]);
			backCompareButton.setText(ptArray[3]);
		}
	}
}
