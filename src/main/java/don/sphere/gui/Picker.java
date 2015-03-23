package don.sphere.gui;

import don.sphere.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by Don on 22/03/2015.
 */
public class Picker extends JFrame implements ActionListener {
    private final static String ACTION_COMMAND_SELECT_ORIGINAL = "ac_select_original", ACTION_COMMAND_SELECT_MODIFIED = "ac_select_modified", ACTION_COMMAND_SELECT_EXPORTED = "ac_select_exported";
    private final static Dimension MINIMUM_WINDOW_SIZE = new Dimension(640, 480);
    private GridBagLayout mGridBagLayoutManager;
    private Container mMainContainer;
    private JButton mBtnSelectOriginal, mBtnSelectModified, mBtnSelectExport;
    private JLabel mImagePreview, mLabelOriginalFile, mLabelModifiedFile, mLabelTitleOriginalFile, mLabelTitleModifiedFile;
    private FileManipulator mFileManipulator = new FileManipulator();

    public Picker() {
        this.setMinimumSize(MINIMUM_WINDOW_SIZE);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.mMainContainer = new JPanel();
        this.mGridBagLayoutManager = new GridBagLayout();
        this.mMainContainer.setLayout(this.mGridBagLayoutManager);

        this.add(this.mMainContainer);
        // pack();
        this.mMainContainer.setVisible(true);
        this.addControls();
        // Center the window
        this.setLocationRelativeTo(null);
        // show window
        this.setVisible(true);
    }

    private void addControls() {

        this.mBtnSelectOriginal = new JButton("Choose Original File");
        this.mBtnSelectOriginal.addActionListener(this);
        this.mBtnSelectOriginal.setActionCommand(ACTION_COMMAND_SELECT_ORIGINAL);
        this.mBtnSelectModified = new JButton("Choose Modified File");
        this.mBtnSelectModified.addActionListener(this);
        this.mBtnSelectModified.setActionCommand(ACTION_COMMAND_SELECT_MODIFIED);
        this.mBtnSelectExport = new JButton("Export File");
        this.mBtnSelectExport.addActionListener(this);
        this.mBtnSelectExport.setActionCommand(ACTION_COMMAND_SELECT_EXPORTED);
        this.mLabelOriginalFile = new JLabel("Original File");
        this.mLabelModifiedFile = new JLabel("Modified File");
        this.mLabelTitleOriginalFile = new JLabel("Original File");
        this.mLabelTitleModifiedFile = new JLabel("Modified File");
        this.addControl(this.mBtnSelectOriginal, 0, 0, 1, 1);
        this.addControl(this.mBtnSelectModified, 1, 0, 1, 1);
        this.addControl(this.mBtnSelectExport, 1, 3, 1, 1);
        this.addControl(this.mLabelModifiedFile, 0, 2, 1, 1);
        this.addControl(this.mLabelOriginalFile, 0, 1, 1, 1);
        this.addControl(this.mLabelTitleModifiedFile, 1, 2, 1, 1);
        this.addControl(this.mLabelTitleOriginalFile, 1, 1, 1, 1);
        this.mImagePreview = new JLabel();
        this.mImagePreview.setMaximumSize(new Dimension(200, 100));
        this.addControl(this.mImagePreview, 0, 2, 2, 2);

    }

    private void addControl(final JComponent component, final int x, final int y) {
        this.addControl(component, x, y, 1, 1);
    }

    private void addControl(final JComponent component, final int x, final int y, final int width, final int height) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        this.mMainContainer.add(component, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(ACTION_COMMAND_SELECT_ORIGINAL)) {
            selectOriginalFile();
        } else if (e.getActionCommand().equals(ACTION_COMMAND_SELECT_MODIFIED)) {
            selectModifiedFile();
        } else if (e.getActionCommand().equals(ACTION_COMMAND_SELECT_EXPORTED)) {
            selectExportFile();
        }
    }

    private void selectOriginalFile() {
        final File file = openSelectFileDialog("Select original File", false);
        if (file != null) {
            this.mFileManipulator.setFileOriginal(file);
            this.mLabelOriginalFile.setText(file.getName());
        }
    }

    private void selectExportFile() {
        final File file = openSelectFileDialog("Select export File", true);
        if (file != null) {
            try {
                this.mFileManipulator.exportFile(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void selectModifiedFile() {
        final File file = openSelectFileDialog("Select modified File", false);
        if (file != null) {
            this.mFileManipulator.setFileModified(file);
            this.mLabelModifiedFile.setText(file.getName());
        }
    }


    private final File openSelectFileDialog(final String title, final boolean saveDialog) {
        final JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(title);
        final int returnVal = saveDialog ? fc.showSaveDialog(this) : fc.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            Log.d("Selected File: " + file.getName());
            return file;
        } else {
            Log.d("User canceled file selection");
            return null;
        }
    }
}
