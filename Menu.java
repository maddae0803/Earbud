import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.*;

public class Menu extends JFrame {
    protected String level = "Easy"; 
	
    protected void createMenuBar() {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        JMenu newMI = new JMenu("New Game");
        JMenuItem intRec = new JMenuItem("Interval Recognition");
        intRec.addActionListener((ActionEvent e) -> {
            new IntervalRecognition();
            setVisible(false);
        });
        JMenuItem pitchRec = new JMenuItem("Pitch Recognition");
        newMI.add(intRec);
        newMI.add(pitchRec);

        JMenuItem openMI = new JMenuItem("Open");
        JMenuItem saveMI = new JMenuItem("Save");


        JMenuItem eMenuItem = new JMenuItem("Exit");
        newMI.setMnemonic(KeyEvent.VK_N);
        /*
        newMI.addActionListener((ActionEvent e) -> {
        	new IntervalRecognition();
        	setVisible(false);
        });**/
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.addActionListener((ActionEvent e) -> {
        	System.exit(0);
        });

       // JMenu diff = new JMenu("Difficulty");
        //diff.setMnemonic(KeyEvent.VK_D);
        ButtonGroup difGroup = new ButtonGroup();
        /*
        JRadioButtonMenuItem easy = new JRadioButtonMenuItem("Easy");
        easy.setSelected(true);
        diff.add(easy);
        easy.addItemListener((ItemEvent e) -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    level = "Easy";
                    new IntervalRecognition();
                    easy.setSelected(true);
                }
            });

        JRadioButtonMenuItem med = new JRadioButtonMenuItem("Medium");
        diff.add(med);
        med.addItemListener((ItemEvent e) -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    level = "Medium";
                    new IntervalRecognition();
                    med.setSelected(true);
                }
            });
		JRadioButtonMenuItem diffic = new JRadioButtonMenuItem("Difficult");
        diff.add(diffic);
        diffic.addItemListener((ItemEvent e) -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    level = "Difficult";
                    new IntervalRecognition();
                    diffic.setSelected(true);
                }
            });


        difGroup.add(easy);
        difGroup.add(med);
        difGroup.add(diffic);**/

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
        //menubar.add(diff);
        menubar.add(help);
        setJMenuBar(menubar);
}
}