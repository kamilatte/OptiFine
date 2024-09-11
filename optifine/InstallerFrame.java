package optifine;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class InstallerFrame extends JFrame {
  private JLabel ivjLabelOfVersion = null;
  
  private JLabel ivjLabelMcVersion = null;
  
  private JPanel ivjPanelCenter = null;
  
  private JButton ivjButtonInstall = null;
  
  private JButton ivjButtonClose = null;
  
  private JPanel ivjPanelBottom = null;
  
  private JPanel ivjPanelContentPane = null;
  
  IvjEventHandler ivjEventHandler = new IvjEventHandler();
  
  private JTextArea ivjTextArea = null;
  
  private JButton ivjButtonExtract = null;
  
  private JLabel ivjLabelFolder = null;
  
  private JTextField ivjFieldFolder = null;
  
  private JButton ivjButtonFolder = null;
  
  class IvjEventHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      if (e.getSource() == InstallerFrame.this.getButtonClose())
        InstallerFrame.this.connEtoC2(e); 
      if (e.getSource() == InstallerFrame.this.getButtonExtract())
        InstallerFrame.this.connEtoC3(e); 
      if (e.getSource() == InstallerFrame.this.getButtonFolder())
        InstallerFrame.this.connEtoC4(e); 
      if (e.getSource() == InstallerFrame.this.getButtonInstall())
        InstallerFrame.this.connEtoC1(e); 
    }
  }
  
  public InstallerFrame() {
    initialize();
  }
  
  private void customInit() {
    try {
      pack();
      setDefaultCloseOperation(3);
      File dirMc = Utils.getWorkingDirectory();
      getFieldFolder().setText(dirMc.getPath());
      getButtonInstall().setEnabled(false);
      getButtonExtract().setEnabled(false);
      String ofVer = Installer.getOptiFineVersion();
      Utils.dbg("OptiFine Version: " + ofVer);
      String[] ofVers = Utils.tokenize(ofVer, "_");
      String mcVer = ofVers[1];
      Utils.dbg("Minecraft Version: " + mcVer);
      String ofEd = Installer.getOptiFineEdition(ofVers);
      Utils.dbg("OptiFine Edition: " + ofEd);
      String ofEdClear = ofEd.replace("_", " ");
      ofEdClear = ofEdClear.replace(" U ", " Ultra ");
      ofEdClear = ofEdClear.replace("L ", "Light ");
      getLabelOfVersion().setText("OptiFine " + ofEdClear);
      getLabelMcVersion().setText("for Minecraft " + mcVer);
      getButtonInstall().setEnabled(true);
      getButtonExtract().setEnabled(true);
      getButtonInstall().requestFocus();
      if (!Installer.isPatchFile())
        getButtonExtract().setVisible(false); 
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      InstallerFrame frm = new InstallerFrame();
      Utils.centerWindow(frm, null);
      frm.show();
    } catch (Exception e) {
      String msg = e.getMessage();
      if (msg != null && msg.equals("QUIET"))
        return; 
      e.printStackTrace();
      String str = Utils.getExceptionStackTrace(e);
      str = str.replace("\t", "  ");
      JTextArea textArea = new JTextArea(str);
      textArea.setEditable(false);
      Font f = textArea.getFont();
      Font f2 = new Font("Monospaced", f.getStyle(), f.getSize());
      textArea.setFont(f2);
      JScrollPane scrollPane = new JScrollPane(textArea);
      scrollPane.setPreferredSize(new Dimension(600, 400));
      JOptionPane.showMessageDialog(null, scrollPane, "Error", 0);
    } 
  }
  
  private void handleException(Throwable e) {
    String msg = e.getMessage();
    if (msg != null && msg.equals("QUIET"))
      return; 
    e.printStackTrace();
    String str = Utils.getExceptionStackTrace(e);
    str = str.replace("\t", "  ");
    JTextArea textArea = new JTextArea(str);
    textArea.setEditable(false);
    Font f = textArea.getFont();
    Font f2 = new Font("Monospaced", f.getStyle(), f.getSize());
    textArea.setFont(f2);
    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(600, 400));
    JOptionPane.showMessageDialog(null, scrollPane, "Error", 0);
  }
  
  private JLabel getLabelOfVersion() {
    if (this.ivjLabelOfVersion == null)
      try {
        this.ivjLabelOfVersion = new JLabel();
        this.ivjLabelOfVersion.setName("LabelOfVersion");
        this.ivjLabelOfVersion.setBounds(2, 5, 385, 42);
        this.ivjLabelOfVersion.setFont(new Font("Dialog", 1, 18));
        this.ivjLabelOfVersion.setHorizontalAlignment(0);
        this.ivjLabelOfVersion.setPreferredSize(new Dimension(385, 42));
        this.ivjLabelOfVersion.setText("OptiFine ...");
      } catch (Throwable ivjExc) {
        handleException(ivjExc);
      }  
    return this.ivjLabelOfVersion;
  }
  
  private JLabel getLabelMcVersion() {
    if (this.ivjLabelMcVersion == null)
      try {
        this.ivjLabelMcVersion = new JLabel();
        this.ivjLabelMcVersion.setName("LabelMcVersion");
        this.ivjLabelMcVersion.setBounds(2, 38, 385, 25);
        this.ivjLabelMcVersion.setFont(new Font("Dialog", 1, 14));
        this.ivjLabelMcVersion.setHorizontalAlignment(0);
        this.ivjLabelMcVersion.setPreferredSize(new Dimension(385, 25));
        this.ivjLabelMcVersion.setText("for Minecraft ...");
      } catch (Throwable ivjExc) {
        handleException(ivjExc);
      }  
    return this.ivjLabelMcVersion;
  }
  
  private JPanel getPanelCenter() {
    if (this.ivjPanelCenter == null)
      try {
        this.ivjPanelCenter = new JPanel();
        this.ivjPanelCenter.setName("PanelCenter");
        this.ivjPanelCenter.setLayout((LayoutManager)null);
        this.ivjPanelCenter.add(getLabelOfVersion(), getLabelOfVersion().getName());
        this.ivjPanelCenter.add(getLabelMcVersion(), getLabelMcVersion().getName());
        this.ivjPanelCenter.add(getTextArea(), getTextArea().getName());
        this.ivjPanelCenter.add(getLabelFolder(), getLabelFolder().getName());
        this.ivjPanelCenter.add(getFieldFolder(), getFieldFolder().getName());
        this.ivjPanelCenter.add(getButtonFolder(), getButtonFolder().getName());
      } catch (Throwable ivjExc) {
        handleException(ivjExc);
      }  
    return this.ivjPanelCenter;
  }
  
  private JButton getButtonInstall() {
    if (this.ivjButtonInstall == null)
      try {
        this.ivjButtonInstall = new JButton();
        this.ivjButtonInstall.setName("ButtonInstall");
        this.ivjButtonInstall.setPreferredSize(new Dimension(100, 26));
        this.ivjButtonInstall.setText("Install");
      } catch (Throwable ivjExc) {
        handleException(ivjExc);
      }  
    return this.ivjButtonInstall;
  }
  
  private JButton getButtonClose() {
    if (this.ivjButtonClose == null)
      try {
        this.ivjButtonClose = new JButton();
        this.ivjButtonClose.setName("ButtonClose");
        this.ivjButtonClose.setPreferredSize(new Dimension(100, 26));
        this.ivjButtonClose.setText("Cancel");
      } catch (Throwable ivjExc) {
        handleException(ivjExc);
      }  
    return this.ivjButtonClose;
  }
  
  private JPanel getPanelBottom() {
    if (this.ivjPanelBottom == null)
      try {
        this.ivjPanelBottom = new JPanel();
        this.ivjPanelBottom.setName("PanelBottom");
        this.ivjPanelBottom.setLayout(new FlowLayout(1, 15, 10));
        this.ivjPanelBottom.setPreferredSize(new Dimension(390, 55));
        this.ivjPanelBottom.add(getButtonInstall(), getButtonInstall().getName());
        this.ivjPanelBottom.add(getButtonExtract(), getButtonExtract().getName());
        this.ivjPanelBottom.add(getButtonClose(), getButtonClose().getName());
      } catch (Throwable ivjExc) {
        handleException(ivjExc);
      }  
    return this.ivjPanelBottom;
  }
  
  private JPanel getPanelContentPane() {
    if (this.ivjPanelContentPane == null)
      try {
        this.ivjPanelContentPane = new JPanel();
        this.ivjPanelContentPane.setName("PanelContentPane");
        this.ivjPanelContentPane.setLayout(new BorderLayout(5, 5));
        this.ivjPanelContentPane.setPreferredSize(new Dimension(394, 203));
        this.ivjPanelContentPane.add(getPanelCenter(), "Center");
        this.ivjPanelContentPane.add(getPanelBottom(), "South");
      } catch (Throwable ivjExc) {
        handleException(ivjExc);
      }  
    return this.ivjPanelContentPane;
  }
  
  private void initialize() {
    try {
      setName("InstallerFrame");
      setSize(404, 236);
      setDefaultCloseOperation(0);
      setResizable(false);
      setTitle("OptiFine Installer");
      setContentPane(getPanelContentPane());
      initConnections();
    } catch (Throwable ivjExc) {
      handleException(ivjExc);
    } 
    customInit();
  }
  
  public void onInstall() {
    try {
      File dirMc = new File(getFieldFolder().getText());
      if (!dirMc.exists()) {
        Utils.showErrorMessage("Folder not found: " + dirMc.getPath());
        return;
      } 
      if (!dirMc.isDirectory()) {
        Utils.showErrorMessage("Not a folder: " + dirMc.getPath());
        return;
      } 
      Installer.doInstall(dirMc);
      Utils.showMessage("OptiFine is successfully installed.");
      dispose();
    } catch (Exception e) {
      handleException(e);
    } 
  }
  
  public void onExtract() {
    try {
      File dirMc = new File(getFieldFolder().getText());
      if (!dirMc.exists()) {
        Utils.showErrorMessage("Folder not found: " + dirMc.getPath());
        return;
      } 
      if (!dirMc.isDirectory()) {
        Utils.showErrorMessage("Not a folder: " + dirMc.getPath());
        return;
      } 
      boolean ok = Installer.doExtract(dirMc);
      if (ok) {
        Utils.showMessage("OptiFine is successfully extracted.");
        dispose();
      } 
    } catch (Exception e) {
      handleException(e);
    } 
  }
  
  public void onClose() {
    dispose();
  }
  
  private void connEtoC1(ActionEvent arg1) {
    try {
      onInstall();
    } catch (Throwable ivjExc) {
      handleException(ivjExc);
    } 
  }
  
  private void connEtoC2(ActionEvent arg1) {
    try {
      onClose();
    } catch (Throwable ivjExc) {
      handleException(ivjExc);
    } 
  }
  
  private void initConnections() throws Exception {
    getButtonFolder().addActionListener(this.ivjEventHandler);
    getButtonInstall().addActionListener(this.ivjEventHandler);
    getButtonExtract().addActionListener(this.ivjEventHandler);
    getButtonClose().addActionListener(this.ivjEventHandler);
  }
  
  private JTextArea getTextArea() {
    if (this.ivjTextArea == null)
      try {
        this.ivjTextArea = new JTextArea();
        this.ivjTextArea.setName("TextArea");
        this.ivjTextArea.setBounds(15, 66, 365, 44);
        this.ivjTextArea.setEditable(false);
        this.ivjTextArea.setEnabled(true);
        this.ivjTextArea.setFont(new Font("Dialog", 0, 12));
        this.ivjTextArea.setLineWrap(true);
        this.ivjTextArea.setOpaque(false);
        this.ivjTextArea.setPreferredSize(new Dimension(365, 44));
        this.ivjTextArea.setText("This installer will install OptiFine in the official Minecraft launcher and will create a new profile \"OptiFine\" for it.");
        this.ivjTextArea.setWrapStyleWord(true);
      } catch (Throwable ivjExc) {
        handleException(ivjExc);
      }  
    return this.ivjTextArea;
  }
  
  private JButton getButtonExtract() {
    if (this.ivjButtonExtract == null)
      try {
        this.ivjButtonExtract = new JButton();
        this.ivjButtonExtract.setName("ButtonExtract");
        this.ivjButtonExtract.setPreferredSize(new Dimension(100, 26));
        this.ivjButtonExtract.setText("Extract");
      } catch (Throwable ivjExc) {
        handleException(ivjExc);
      }  
    return this.ivjButtonExtract;
  }
  
  private void connEtoC3(ActionEvent arg1) {
    try {
      onExtract();
    } catch (Throwable ivjExc) {
      handleException(ivjExc);
    } 
  }
  
  private JLabel getLabelFolder() {
    if (this.ivjLabelFolder == null)
      try {
        this.ivjLabelFolder = new JLabel();
        this.ivjLabelFolder.setName("LabelFolder");
        this.ivjLabelFolder.setBounds(15, 116, 47, 16);
        this.ivjLabelFolder.setPreferredSize(new Dimension(47, 16));
        this.ivjLabelFolder.setText("Folder");
      } catch (Throwable ivjExc) {
        handleException(ivjExc);
      }  
    return this.ivjLabelFolder;
  }
  
  private JTextField getFieldFolder() {
    if (this.ivjFieldFolder == null)
      try {
        this.ivjFieldFolder = new JTextField();
        this.ivjFieldFolder.setName("FieldFolder");
        this.ivjFieldFolder.setBounds(62, 114, 287, 20);
        this.ivjFieldFolder.setEditable(false);
        this.ivjFieldFolder.setPreferredSize(new Dimension(287, 20));
      } catch (Throwable ivjExc) {
        handleException(ivjExc);
      }  
    return this.ivjFieldFolder;
  }
  
  private JButton getButtonFolder() {
    if (this.ivjButtonFolder == null)
      try {
        this.ivjButtonFolder = new JButton();
        this.ivjButtonFolder.setName("ButtonFolder");
        this.ivjButtonFolder.setBounds(350, 114, 25, 20);
        this.ivjButtonFolder.setMargin(new Insets(2, 2, 2, 2));
        this.ivjButtonFolder.setPreferredSize(new Dimension(25, 20));
        this.ivjButtonFolder.setText("...");
      } catch (Throwable ivjExc) {
        handleException(ivjExc);
      }  
    return this.ivjButtonFolder;
  }
  
  public void onFolderSelect() {
    File dirMc = new File(getFieldFolder().getText());
    JFileChooser jfc = new JFileChooser(dirMc);
    jfc.setFileSelectionMode(1);
    jfc.setAcceptAllFileFilterUsed(false);
    if (jfc.showOpenDialog(this) == 0) {
      File dir = jfc.getSelectedFile();
      getFieldFolder().setText(dir.getPath());
    } 
  }
  
  private void connEtoC4(ActionEvent arg1) {
    try {
      onFolderSelect();
    } catch (Throwable ivjExc) {
      handleException(ivjExc);
    } 
  }
}
