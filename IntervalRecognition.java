import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import jm.JMC;
import jm.util.*;
import jm.music.data.*;
import java.util.*;


public class IntervalRecognition extends Menu implements ActionListener{
	public static final String defaultMessage = "Game Instructions: \n     \u2022 Listen to the played Interval.\n     \u2022 From the four choices shown, choose the correct interval, and press 'Select' to lock in your choice.\n     \u2022 Press the 'Replay' button if you wish to have the interval played again. \n     \u2022 If you choose the correct answer on the 1st try, you will receive five (5) points; if you choose the correct answer on the 2nd try, you will receive three (3) points; if you choose the correct answer on the 3rd try, you will receive (1) point; if you choose the correct answer on the 4th try, you will lose one (1) point. \n     \u2022 The game will be over when you reach a total of 50 or more points.\n     \u2022 Good Luck, and Have Fun!";
	public int accum; 
	ButtonGroup group;
	JRadioButton choiceA, choiceB, choiceC, choiceD;
	String correct; 
	Phrase interval;
	JTextPane tPane;
	JScrollPane sPane;
	JTextField response; 
	JLabel points; 
	int pVal; //point value for each question
	String difficulty; 
	
	public IntervalRecognition() {
		setTitle("Interval Recognition");
		setSize(700, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		createMenuBar();
		panel.setLayout(new GridLayout(0, 1));
		
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
			Play.midi(interval);
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
		JPanel labelPanel = new JPanel();
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
		interval = new Phrase();
		ArrayList<String> choices; 
		ArrayList<String> e_choices = new ArrayList<String>(Arrays.asList("Unison", "Minor 2nd", "Major 2nd", "Minor 3rd", "Major 3rd", "Perfect 4th", "Tritone", "Perfect 5th"));
		ArrayList<String> m_choices = new ArrayList<String>(Arrays.asList("Unison", "Minor 2nd", "Major 2nd", "Minor 3rd", "Major 3rd", "Perfect 4th", "Tritone", "Perfect 5th", "Minor 6th", "Major 6th", "Minor 7th", "Major 7th", "Octave"));
		ArrayList<String> h_choices = new ArrayList<String>(Arrays.asList("Unison", "Minor 2nd", "Major 2nd", "Minor 3rd", "Major 3rd", "Perfect 4th", "Tritone", "Perfect 5th", "Minor 6th", "Major 6th", "Minor 7th", "Major 7th", "Octave", "Minor 9th", "Major 9th", "Minor 10th", "Major 10th", "Perfect 11th", "Augmented 11th", "Perfect 12th"));
		ArrayList<JRadioButton> answers = new ArrayList<JRadioButton>(Arrays.asList(choiceA, choiceB, choiceC, choiceD));
		for (int i = 0; i < answers.size(); i++) {
			answers.get(i).setSelected(false);
		}
		int pos = randomInterval();
		switch (level) {
			case "Easy":
				choices = e_choices;
				break;
			case "Medium":
				choices = m_choices;
				break;
			case "Difficult":
				choices = h_choices;
				break;
			default: choices = e_choices;
				break;
		}		
		randomizeChoices(choices, answers, pos);
	}
	private void randomizeChoices(ArrayList<String> c, ArrayList<JRadioButton> a, int p) {
		int aPos = (int) (Math.random() * 4);
		JRadioButton correctAnswer = a.get(aPos);
		correct = correctAnswer.getActionCommand();
		correctAnswer.setText(c.get(p));
		c.remove(p);
		a.remove(aPos);

		while (a.size() > 0) {
			aPos = (int) (Math.random() * a.size());
			int cPos = (int) (Math.random() * c.size());
			a.get(aPos).setText(c.get(cPos));
			c.remove(cPos);
			a.remove(aPos);
		}
	}
	private int randomInterval() {
		int multiplier;
		int [] posneg = {-1, 1};
		switch (level) {
			case "Easy":
				multiplier = 8;
				break;
			case "Medium":
				multiplier = 12;
				break;
			case "Difficult":
				multiplier = 20 * posneg[(int) (Math.random() * 2)];
				break;
			default: multiplier = 1;
				break;
		}
		Note n = new Note();
		//Phrase phr = new Phrase();
		int pitch = (int) (Math.random() * 30) + 48;
		n.setPitch(pitch);
		interval.addNote(n);
		int pitchVal = n.getPitch();
		pitchVal += (int) (Math.random() * multiplier);
		Note n2 = new Note();
		n2.setPitch(pitchVal);
		interval.addNote(n2);
		Play.midi(interval);
		return Math.abs(pitchVal - pitch);
	}
	/*
	private void changePane(String s) {
		if (s.equals("correct")) {
			response.setText("Correct! Nice Work!     \u2713");
		}
		else {
			response.setText("Try Again.");
		}

	}*/

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
        	new IntervalRecognition();
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
                    level = "Easy";
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
                    level = "Medium";
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
                    level = "Difficult";
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