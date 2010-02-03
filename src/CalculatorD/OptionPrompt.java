package CalculatorD;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

import java.util.Hashtable;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Prompts the user to change GUI options. Inspiration provided by 
 * <a href="http://java.sun.com/docs/books/tutorial/uiswing/examples/components/ListDialogRunnerProject/src/components/ListDialog.java">
 * ListDialog.java</a>.
 * @author Dustin Leavins
 */
public class OptionPrompt extends JDialog {
    private static GUIOptions guiOptions;
    private static CalculatorJFrame ownerFrame;
    private static OptionPrompt op;

    private JSlider displayFontSizeSlider;
    private JSlider buttonFontSizeSlider;
    private ButtonGroup decimalButtonUsageGroup;
    private JRadioButton useDecimalKeyForDeleteButton;
    private JRadioButton useDecimalKeyForDecimalButton;
    private ButtonGroup displayAlignmentGroup;
    private JRadioButton leftAlignmentButton;
    private JRadioButton rightAlignmentButton;
    private JRadioButton centerAlignmentButton;
    private JButton saveAndQuitButton;
    private JButton quitButton;

    private static final long serialVersionUID = 5593943455855642863L;

    /**
     * Show the prompt to the user.
     * @param currentOptions options to display and manipulate
     * @param f the <code>CalculatorJFrame</code> calling this method
     */
    public static void showPrompt(GUIOptions currentOptions, 
            CalculatorJFrame f) {
        GUIOptions tempOptions = (GUIOptions) currentOptions.clone();
        op = new OptionPrompt(tempOptions,f);
        op.pack();
        op.setVisible(true);
    }

    /**
     * Private constructor.  Use <code>showPrompt</code> instead.
     * @param currentOptions options to display and manipulate
     * @param f the <code>CalculatorJFrame</code> calling this method
     */
    private OptionPrompt(GUIOptions currentOptions, CalculatorJFrame f) {
        super(f);
        GridLayout promptLayout = new GridLayout(5,1);
        JPanel displayFontSizePanel = new JPanel();
        JPanel buttonFontSizePanel = new JPanel();
        JPanel decimalKeyUsagePanel = new JPanel();
        JPanel displayAlignmentPanel = new JPanel();
        JPanel saveAndCancelPanel = new JPanel();

        guiOptions = currentOptions;
        ownerFrame = f;

        this.setTitle("Options");

        displayFontSizeSlider = new JSlider();
        displayFontSizeSlider.setMinimum(GUIOptions.MIN_FONT_SIZE);
        displayFontSizeSlider.setMaximum(GUIOptions.MAX_FONT_SIZE);
        displayFontSizeSlider.setValue(
            this.guiOptions.displayFontSize());
        displayFontSizeSlider.setPaintLabels(true);

        // Label table for displayFontSizeSlider
        Hashtable<Integer, JLabel> tssLabelTable = 
            new Hashtable<Integer, JLabel>();
        tssLabelTable.put(new Integer(GUIOptions.MIN_FONT_SIZE), 
            new JLabel("Small"));
        tssLabelTable.put(new Integer(GUIOptions.MAX_FONT_SIZE), 
            new JLabel("Large"));
        displayFontSizeSlider.setLabelTable(tssLabelTable);

        
        decimalButtonUsageGroup = new ButtonGroup();
        useDecimalKeyForDeleteButton = new JRadioButton("Delete");
        useDecimalKeyForDecimalButton = new JRadioButton("Decimal");

        decimalButtonUsageGroup.add(useDecimalKeyForDeleteButton);
        decimalButtonUsageGroup.add(useDecimalKeyForDecimalButton);

        displayAlignmentGroup = new ButtonGroup();
        leftAlignmentButton = new JRadioButton("Left");
        centerAlignmentButton = new JRadioButton("Center");
        rightAlignmentButton = new JRadioButton("Right");

        displayAlignmentGroup.add(leftAlignmentButton);
        displayAlignmentGroup.add(centerAlignmentButton);
        displayAlignmentGroup.add(rightAlignmentButton);


        buttonFontSizeSlider = new JSlider();
        buttonFontSizeSlider.setMinimum(GUIOptions.MIN_FONT_SIZE);
        buttonFontSizeSlider.setMaximum(GUIOptions.MAX_FONT_SIZE);
        buttonFontSizeSlider.setValue(
            this.guiOptions.buttonFontSize());
        buttonFontSizeSlider.setPaintLabels(true);

        // Label table for buttonFontSizeSlider
        buttonFontSizeSlider.setLabelTable(tssLabelTable);

        if (guiOptions.useDecimalButtonForDelete()) {
            useDecimalKeyForDeleteButton.setSelected(true);
        }
        else {
            useDecimalKeyForDecimalButton.setSelected(true);
        }

        switch (guiOptions.horizontalAlignment()) {
        case (GUIOptions.LEFT_HORIZONTAL_ALIGNMENT):
            leftAlignmentButton.setSelected(true);
            break;
        case (GUIOptions.RIGHT_HORIZONTAL_ALIGNMENT):
            rightAlignmentButton.setSelected(true);
            break;
        default: // case (GuiOptions.CENTER_HORIZONTAL_ALIGNMENT)
            centerAlignmentButton.setSelected(true);
            break;
        }

        saveAndQuitButton = new JButton("OK");
        quitButton = new JButton("Cancel");

        this.setLayout(promptLayout);
        decimalKeyUsagePanel.setLayout(new BorderLayout());
        displayAlignmentPanel.setLayout(new BorderLayout());

        displayFontSizePanel.add(
            new JLabel("Text size of number display:"));
        displayFontSizePanel.add(displayFontSizeSlider);


        buttonFontSizePanel.add(
            new JLabel("Text size of buttons."));
        buttonFontSizePanel.add(buttonFontSizeSlider);


        decimalKeyUsagePanel.add(
            new JLabel("Use decimal key on numpad for:"), 
            BorderLayout.NORTH);
        decimalKeyUsagePanel.add(
            useDecimalKeyForDeleteButton, 
            BorderLayout.CENTER);
        decimalKeyUsagePanel.add(
            useDecimalKeyForDecimalButton, 
            BorderLayout.SOUTH);

        displayAlignmentPanel.add(
            new JLabel("Alignment of input text:"), 
            BorderLayout.NORTH);
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new GridLayout(1,3));
        tempPanel.add(leftAlignmentButton);
        tempPanel.add(centerAlignmentButton);
        tempPanel.add(rightAlignmentButton);
        displayAlignmentPanel.add(tempPanel, BorderLayout.CENTER);


        saveAndCancelPanel.add(saveAndQuitButton);
        saveAndCancelPanel.add(quitButton);

        this.add(displayFontSizePanel);
        this.add(buttonFontSizePanel);
        this.add(decimalKeyUsagePanel);
        this.add(displayAlignmentPanel);
        this.add(saveAndCancelPanel);

        saveAndQuitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                int alignment;
                boolean udbd = 
                    useDecimalKeyForDeleteButton.isSelected();

                if (leftAlignmentButton.isSelected()) {
                    alignment = GUIOptions.LEFT_HORIZONTAL_ALIGNMENT;
                }
                else if (rightAlignmentButton.isSelected()) {
                    alignment = GUIOptions.RIGHT_HORIZONTAL_ALIGNMENT;
                }
                else {
                    alignment = GUIOptions.CENTER_HORIZONTAL_ALIGNMENT;
                }


                OptionPrompt.guiOptions = new GUIOptions(
                    displayFontSizeSlider.getValue(),
                    buttonFontSizeSlider.getValue(),
                    alignment,
                    udbd);


                OptionPrompt.op.setVisible(false);
                ownerFrame.setOptions(guiOptions);
            }
        });

        quitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                OptionPrompt.op.setVisible(false);
            }
        });
    }
}