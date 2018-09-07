import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import java.net.*;

public class MainView extends Menu implements ActionListener{
	private BufferedImage img;
	public MainView() {
		setTitle("Welcome to EarBud!");
		setSize(700, 400);
		//setBackground(Color.RED);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new MyPanel();
		add(panel);
		//panel.setBackground(Color.RED);
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		try {
			img = ImageIO.read(new File("music-notes-background.jpg"));

		}
		catch (IOException e) {
			System.out.println("Photo Not Found :(");
			System.exit(1);
		}
		JButton btn1 = new JButton("Click For Score View");
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.5;
		btn1.setSize(30, 30);
		btn1.addActionListener(this);
		JLabel title = new JLabel ("Welcome!");
		title.setBounds(getWidth() / 2, 0, 300, 100);
		title.setFont(title.getFont().deriveFont(64f));
		title.setForeground(Color.WHITE);
		//add(btn1);
		//panel.add(btn1, c);
		createMenuBar();
		setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		new IntervalRecognition();
		setVisible(false);
	}
	/*
	protected void createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        JMenuItem newMI = new JMenuItem("New Game");
        JMenuItem openMI = new JMenuItem("Open");
        JMenuItem saveMI = new JMenuItem("Save");


        JMenuItem eMenuItem = new JMenuItem("Exit");
        newMI.setMnemonic(KeyEvent.VK_N);
        newMI.addActionListener(this);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.addActionListener((ActionEvent e) -> {
        	System.exit(0);
        });

        JMenu diff = new JMenu("Difficulty");
        diff.setMnemonic(KeyEvent.VK_D);
        ButtonGroup difGroup = new ButtonGroup();
        JRadioButtonMenuItem easy = new JRadioButtonMenuItem("Easy");
        easy.setSelected(true);
        diff.add(easy);
        easy.addItemListener((ItemEvent e) -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("Easy");
                }
            });

        JRadioButtonMenuItem med = new JRadioButtonMenuItem("Medium");
        diff.add(med);
        med.addItemListener((ItemEvent e) -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("Medium");
                }
            });
		JRadioButtonMenuItem diffic = new JRadioButtonMenuItem("Difficult");
        diff.add(diffic);
        diffic.addItemListener((ItemEvent e) -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("Difficult");
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
*/
	private class MyPanel extends JPanel {
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	}
}

}