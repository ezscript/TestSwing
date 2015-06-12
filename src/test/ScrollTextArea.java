package test;


import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;

/**
 * 测试textArea:
 * 1 滚动条 
 * 2 自动提示： 
 * @author MaXin
 *
 */
public class ScrollTextArea extends JFrame{

    private static final long serialVersionUID = 1L;

    private JPanel jContentPane = null;

    private JScrollPane jScrollPane = null;

    private JTextArea jTextArea = null;

    private String [] arr = {"SELECT","FROM","END","ORDER BY","GROUP BY"};
    
    private DelayThread delayThread;
    
    private JPopupMenu popupMenu;
	/**
    * This method initializes jScrollPane
    *
    * @return javax.swing.JScrollPane
    */
    private JScrollPane getJScrollPane() {
        if (jScrollPane == null) {
            jScrollPane = new JScrollPane();
            jScrollPane.setBounds(new Rectangle(0, 2, 290, 144));
            jScrollPane.setViewportView(getJTextArea());
        }
        
        popupMenu = new JPopupMenu();
    		
		JMenuItem menuItem_1 = new JMenuItem("New menu item");
		popupMenu.add(menuItem_1);
		
		JMenuItem menuItem = new JMenuItem("New menu item");
		popupMenu.add(menuItem);
		popupMenu.addKeyListener(new KeyAdapter() {
        	public void keyPressed(KeyEvent e) {
        		jTextArea.append(e.getKeyChar()+"");
        	}
        });
		
        return jScrollPane;
    }
    
    /**
    * This method initializes jTextArea
    * @return javax.swing.JTextArea
    */
    private JTextArea getJTextArea() {
        if (jTextArea == null) {
            jTextArea = new JTextArea();
            jTextArea.addKeyListener(new KeyAdapter() {
            	public void keyPressed(KeyEvent e) {
            		delayThread.doDlay(700);
            	}
            });
        }
        return jTextArea;
    }

    /**
    * @param args
    */
    public static void main(String[] args) {
    	
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ScrollTextArea thisClass = new ScrollTextArea();
                thisClass.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                thisClass.setVisible(true);
                thisClass.setDelayThread(new DelayThread(thisClass.new DelayMethodImp()));
            }
        });
    }

    /**
    * This is the default constructor
    */
    public ScrollTextArea() {
        super();
        initialize();
    }

    /**
    * This method initializes this
    *
    * @return void
    */
    private void initialize() {
        this.setSize(300, 200);
        this.setContentPane(getJContentPane());
        this.setTitle("JFrame");
    }

    /**
    * This method initializes jContentPane
    *
    * @return javax.swing.JPanel
    */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getJScrollPane(), null);
        }
        return jContentPane;
    }

    public DelayThread getDelayThread() {
		return delayThread;
	}

	public void setDelayThread(DelayThread delayThread) {
		this.delayThread = delayThread;
	}

	private void showMenu(int x, int y) {
		popupMenu.show(jTextArea, x, y);
	}
	public class DelayMethodImp implements DelayThread.DelayMethod{
		public void run() {
			ScrollTextArea.this.jTextArea.getText();
			int posi = jTextArea.getCaretPosition();
			Rectangle r=null;
			try {
				r = jTextArea.modelToView(posi);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
			String text = jTextArea.getText();
			int startPosi = getStartPosi(text,posi-1);
			String temp = text.substring(startPosi,posi);
			System.out.println(temp);
			showMenu(r.x,r.y+18);
		}

		private int getStartPosi(String text, int posi) {
			int start = posi;
			for(; start>=0; start--){
				if(" 	,>%=<\r\n\n\r".indexOf(text.charAt(start)) != -1){
					break;
				}
			}
			start++;
			return start;
		}
	}
}