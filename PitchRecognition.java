import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import jm.JMC;
import jm.util.*;
import jm.music.data.*;
import java.util.*;


public class PitchRecognition extends Menu implements ActionListener{
	public static final String defaultMessage = "Game Instructions: \n     \u2022 Listen to the note being played\n     \u2022 From the four choices shown, choose the correct note name, and press 'Select' to lock in your choice.\n     \u2022 Press the 'Replay' button if you wish to have the note played again. \n     \u2022 If you choose the correct answer on the 1st try, you will receive five (5) points; if you choose the correct answer on the 2nd try, you will receive three (3) points; if you choose the correct answer on the 3rd try, you will receive (1) point; if you choose the correct answer on the 4th try, you will lose one (1) point. \n     \u2022 The game will be over when you reach a total of 50 or more points.\n     \u2022 Good Luck, and Have Fun!";
	public int accum; 
	ButtonGroup group;
	JRadioButton choiceA, choiceB, choiceC, choiceD;
	String correct; 
	Note n;
	JTextPane tPane;
	JScrollPane sPane;
	JTextField response; 
	JLabel points; 
	int pVal; //point value for each question
	String difficulty = "Easy"; 
	ArrayList<String> notes;
	
	public PitchRecognition() {
		setTitle("Pitch Recognition");
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		createMenuBar();
		panel.setLayout(new GridLayout(0, 1));
		panel.setOpaque(true);
		Color teal = new Color(204, 255, 220);
		panel.setBackground(teal);

	
		
		
		tPane = new JTextPane();
		sPane = new JScrollPane(tPane);
		tPane.setText(defaultMessage);
		tPane.setPreferredSize(new Dimension(100, 100));
		tPane.setEditable(false);

		response = new JTextField("");
		response.setAlignmentX(Component.RIGHT_ALIGNMENT);
		response.setPreferredSize(new Dimension(500, 30));
		response.setEditable(false);
		response.setHorizontalAlignment(JTextField.CENTER);
		response.setMaximumSize(response.getPreferredSize());
		

		choiceA = new JRadioButton();
		choiceA.setMnemonic(KeyEvent.VK_A);
		choiceA.setActionCommand("A");
		choiceB = new JRadioButton();
		choiceB.setMnemonic(KeyEvent.VK_B);
		choiceB.setActionCommand("B");
		choiceC = new JRadioButton();
		choiceC.setMnemonic(KeyEvent.VK_C);
		choiceC.setActionCommand("C");
		choiceD = new JRadioButton();
		choiceD.setMnemonic(KeyEvent.VK_D);
		choiceD.setActionCommand("D");
		
		points = new JLabel();
		points.setAlignmentX(Component.RIGHT_ALIGNMENT);

		JButton choose = new JButton("Select");
		JButton replay = new JButton("Replay");
		replay.setSize(100, 40);
		replay.addActionListener((ActionEvent e) -> {
			Play.midi(n);
		});
		choose.setSize(60, 40);
		choose.addActionListener(this);
		
		group = new ButtonGroup();
		group.add(choiceA);
		group.add(choiceB);
		group.add(choiceC);
		group.add(choiceD);
		panel.add(choiceA);
		panel.add(choiceB);
		panel.add(choiceC);
		panel.add(choiceD);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(true);
		buttonPanel.setBackground(teal);

		JPanel labelPanel = new JPanel();
		labelPanel.setOpaque(true);
		labelPanel.setBackground(teal);

		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		
		labelPanel.add(Box.createHorizontalGlue());
		labelPanel.add(points);
		labelPanel.add(Box.createRigidArea(new Dimension (0, 15)));
		labelPanel.add(sPane);
		labelPanel.add(Box.createRigidArea(new Dimension (0, 15)));
		labelPanel.add(response);
		labelPanel.setBorder(new EmptyBorder(new Insets(10, 110, 50, 10)));
		
		buttonPanel.add(choose);
		buttonPanel.add(replay);
		
		add(panel, BorderLayout.LINE_START);
		add(buttonPanel, BorderLayout.SOUTH);
		add(labelPanel);
		createGame();
		
		setVisible(true);
		
		
		}	
		public void createGame() {
			points.setText("Points: " + accum);
			tPane.setText(defaultMessage);
			tPane.setCaretPosition(0);
			pVal = 5; 
			notes = new ArrayList<String>(Arrays.asList("C", "C#/Db", "D", "D#/Eb", "E", "F", "F#/Gb", "G", "G#/Ab", "A", "A#/Bb", "B"));

			
			ArrayList<JRadioButton> answers = new ArrayList<JRadioButton>(Arrays.asList(choiceA, choiceB, choiceC, choiceD));
			for (int i = 0; i < answers.size(); i++) {
				answers.get(i).setSelected(false);
			}

			int chosen = randomPitch();
			randomizeChoices(notes, answers, chosen);

		}

		private void randomizeChoices(ArrayList<String> n, ArrayList<JRadioButton> a, int c) {
			int aPos = (int) (Math.random() * 4);
			JRadioButton correctAnswer = a.get(aPos);
			correct = correctAnswer.getActionCommand();
			correctAnswer.setText(n.get(c));
			n.remove(c);
			a.remove(aPos);

			while (a.size() > 0) {
				aPos = (int) (Math.random() * a.size());
				int cPos = (int) (Math.random() * n.size());
				a.get(aPos).setText(n.get(cPos));
				n.remove(cPos);
				a.remove(aPos);
			}
		}

		private int randomPitch() {
			n = new Note();
			int pitch = (int) (Math.random() * 40) + 30;
			n.setPitch(pitch);
			Play.midi(n);
			return pitch % 12; 
		}
		public void actionPerformed(ActionEvent e) {
			if (group.getSelection().getActionCommand().equals(correct) == true) {
				response.setText("Correct! Nice Work!     \u2713");
				accum += pVal; 
				group.getSelection().setSelected(false);
				try {
					Thread.sleep(300);
				if (accum >= 50) {
					points.setText("Points: " + accum);
					response.setText("Game Over: Congratulations!");
				}
				else {
					createGame();
				}
			}
			catch (InterruptedException ex) {
			}
		}
		else {
				response.setText("Try Again.");
				pVal -= 2;
			}


		}

		protected void createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        JMenuItem newMI = new JMenuItem("New Game");
        JMenuItem openMI = new JMenuItem("Open");
        JMenuItem saveMI = new JMenuItem("Save");
        JMenuItem eMenuItem = new JMenuItem("Exit");

        newMI.setMnemonic(KeyEvent.VK_N);
        newMI.addActionListener((ActionEvent e) -> {
        	new PitchRecognition();
        	setVisible(false);
        });
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.addActionListener((ActionEvent e) -> {
        	new MainView();
        	setVisible(false);
        });

        JMenu diff = new JMenu("Difficulty");
        diff.setMnemonic(KeyEvent.VK_D);
        ButtonGroup difGroup = new ButtonGroup();
        JRadioButtonMenuItem easy = new JRadioButtonMenuItem("Easy");
        easy.setSelected(true);
        diff.add(easy);
        easy.addItemListener((ItemEvent e) -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    difficulty= "Easy";
                    easy.setSelected(true);
                    accum = 0;
                    response.setText("");
            	    createGame();
                    
                }
            });

        JRadioButtonMenuItem med = new JRadioButtonMenuItem("Medium");
        diff.add(med);
        med.addItemListener((ItemEvent e) -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    difficulty = "Medium";
                    med.setSelected(true);
                    accum = 0;
                    response.setText("");
                    createGame();
                    
                }
            });
		JRadioButtonMenuItem diffic = new JRadioButtonMenuItem("Difficult");
        diff.add(diffic);
        diffic.addItemListener((ItemEvent e) -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    difficulty = "Difficult";
                    diffic.setSelected(true);
                    accum = 0;
                    response.setText("");
                    createGame();
                }
            });


        difGroup.add(easy);
        difGroup.add(med);
        difGroup.add(diffic);

        JMenu help = new JMenu("Help");
        JMenuItem hitem1 = new JMenuItem("Report an Issue");
        JMenuItem hitem2 = new JMenuItem("Application FAQs");
        help.add(hitem1);
        help.add(hitem2);

        file.add(newMI);
        file.add(new JSeparator());
        file.add(openMI);
        file.add(new JSeparator());
        file.add(saveMI);
        file.add(new JSeparator());
        file.add(eMenuItem);

        menubar.add(file);
        menubar.add(diff);
        menubar.add(help);
        setJMenuBar(menubar);
}
	}