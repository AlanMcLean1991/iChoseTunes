package ichosetunes;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultTreeModel;

/**
 *
 * @author Alan
 */
public class Gui implements ActionListener {
    JFrame frame;
    JMenuBar menuBar;
    JScrollPane leftScrollPane;
    JScrollPane topScrollPane;
    JScrollPane bottomScrollPane;
    JTextArea songOutputInfo;
    JTextArea processInfo;
    JTree ipodTree;
    JSplitPane leftSplitPane;
    JSplitPane rightSplitPane;
    String startPath;

    Gui() {
        // setup individual aspects of GUI
        this.frame = initFrame();
        this.menuBar = initMenuBar();
        this.songOutputInfo = initSongOutputInfo();
        this.topScrollPane = initTopScrollPane(this.songOutputInfo);
        this.processInfo = initProcessInfo();
        this.bottomScrollPane = initBottomScrollPane(this.processInfo);
        this.rightSplitPane = initRightSplitPane(this.topScrollPane, this.bottomScrollPane);
        this.ipodTree = initIpodTree();
        this.leftScrollPane = initLeftScrollPane(this.ipodTree);
        this.leftSplitPane = initLeftSplitPane(this.leftScrollPane, this.rightSplitPane);

        // add components to GUI
        this.frame.setJMenuBar(this.menuBar);
        this.frame.getContentPane().add(this.leftSplitPane);
        this.frame.pack();
        this.frame.setVisible(true);
    }
    public static void main(String[] args) {
        Gui gui = new Gui();
    }

    public final JFrame initFrame() {
        JFrame tmpFrame = new JFrame("Gui test");
        tmpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tmpFrame.setPreferredSize(new Dimension(900, 500));
        tmpFrame.setMaximumSize(new Dimension(900, 500));
        tmpFrame.setMinimumSize(new Dimension(900, 500));
        tmpFrame.setResizable(false);

        return tmpFrame;
    }
    public final JMenuBar initMenuBar() {
        JMenuBar tmpMenuBar = new JMenuBar();
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        //Build the first menu.
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        tmpMenuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("Open iPod",
                                 KeyEvent.VK_O);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Exit");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menuItem.setAccelerator(KeyStroke.getKeyStroke
                (KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        menuItem.addActionListener(this);
        menu.add(menuItem);

        return tmpMenuBar;
    }
    public final JTextArea initSongOutputInfo() {
        JTextArea ta = new JTextArea("hello my name is alan");
        ta.setPreferredSize(new Dimension(600, 300));
        ta.setEditable(false);
        return ta;
    }
    public final JScrollPane initTopScrollPane(JTextArea songOutputInfo) {
        JScrollPane jsp = new JScrollPane(songOutputInfo);
        jsp.setPreferredSize(new Dimension(600, 300));
        return jsp;
    }
    public final JTextArea initProcessInfo() {
        JTextArea ta = new JTextArea("hello my name is mclean");
        ta.setEditable(false);
        
        return ta;
    }
    public final JScrollPane initBottomScrollPane(JTextArea processInfo) {
        JScrollPane jsp = new JScrollPane(processInfo);
        
        return jsp;
    }
    public final JSplitPane initRightSplitPane(JScrollPane up, JScrollPane down) {
        return new JSplitPane(JSplitPane.VERTICAL_SPLIT, up, down);
    }
    public final JTree initIpodTree() {
        DefaultMutableTreeNode node = null;
        JTree tree = new JTree(node);
        tree.setPreferredSize(new Dimension(200, 500)); // set preffered size is used by pack()

        return tree;
    }
    public final JScrollPane initLeftScrollPane(JTree ipodTree) {
        JScrollPane jsp = new JScrollPane(ipodTree);
        jsp.setPreferredSize(new Dimension(200, 500));
        
        return jsp;
    }
    public final JSplitPane initLeftSplitPane(JScrollPane leftPane, JSplitPane rightPane) {
        return new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPane, rightPane);
    }
    public String chooseDrive() {

        Object[] letters = {"A", "B", "C", "D", "E", "F","G", "H", "I", "J",
                            "K","L", "M", "N", "O", "P", "Q", "R", "S", "T",
                            "U", "V", "W", "X", "Y", "Z"};
        ArrayList<Object> availableDrives = new ArrayList<Object>();
        for(Object s : letters) {
            if(new File(s.toString() + ":/").exists())
                availableDrives.add(s+":/");
       }
        String option = (String)JOptionPane.showInputDialog(
                this.frame, "Please select an option from the list",
                "Choose Drive:", JOptionPane.PLAIN_MESSAGE, null, 
                availableDrives.toArray(), "");

        return option;
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().contentEquals("Open iPod")) {
            String driveLetter = chooseDrive();
            if(new File(driveLetter +"iPod_Control/Music").exists()) {
                this.startPath = driveLetter + "iPod_Control/Music";
                setupTree();
                getID3VTags();
            }
            else
                System.out.println("boooooooooo");
        }
        else if(e.getActionCommand().contentEquals("Exit"))
            System.exit(0);

    }
    public void setupTree() {
        //System.out.println(this.ipodTree.getModel().getRoot().toString());
        DefaultTreeModel treeModel;
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(this.startPath);
        DefaultMutableTreeNode parent;
        DefaultMutableTreeNode child;
        File rootDir = new File(this.startPath);
        File[] subDirs = rootDir.listFiles();

        for(File dirs: subDirs) {
            parent = new DefaultMutableTreeNode(dirs.getName());
            root.add(parent);
            File[] subFiles = dirs.listFiles();
            for(File files: subFiles) {
                child = new DefaultMutableTreeNode(files.getName());
                parent.add(child);
            }
        }
        treeModel = new DefaultTreeModel(root);
        this.ipodTree.setModel(treeModel);

    }
    public void getID3VTags() {
        ArrayList musicFilesPaths = new ArrayList();
        ID3v1 musicFiles[] = null;
        // create a new File object and check path exists
        File rootDir = null;
        try {
            rootDir = new File(this.startPath);
        } catch(NullPointerException e) {}

        // create a File array and add the "F** folders to it
        File[] subDirs = rootDir.listFiles();
        if(subDirs.length > 0)
            System.out.println("Found Folders!");
        else {
            System.out.println("No Folders Found!");
            System.exit(1);
        }

        // cycle through "F**" folders and add mp3's
        for(File tmp: subDirs) {
            File[] subDirMusic = tmp.listFiles();
            // cycle through music files
            for(File music : subDirMusic) {
                musicFilesPaths.add(music.getPath());
            }
        }

        if(musicFilesPaths.size() > 0)
            System.out.println("Found Files!");
        else {
            System.out.println("No Files Found");
            System.exit(1);
        }

        // create ID3v1 object of each file and add them to an array
        System.out.println("Adding files to database...");
        musicFiles = new ID3v1[musicFilesPaths.size()];
        for(int i=0; i<musicFilesPaths.size(); i++) {
            ID3v1 x = new ID3v1(new File((String)musicFilesPaths.get(i)));
            musicFiles[i] = x;
            //System.out.println(x.getSongTitle());
            if(i==0)
                this.processInfo.append((i+1) + "/" + musicFilesPaths.size() + '\n');
            this.processInfo.replaceRange(Integer.toString(i+1), 0, 1);
            //this.processInfo.insert(Integer.toString(i+1), 0);
            //this.processInfo.updateUI();
        }
        this.processInfo.append(musicFiles.length + " added!");

    }
}
