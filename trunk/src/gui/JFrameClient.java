package gui;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import javax.swing.WindowConstants;
import javax.swing.SwingUtilities;

import com.mladen.Client;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class JFrameClient extends javax.swing.JFrame {
	private JTextField jTextFieldServerIP;
	private JButton jButtonViewArray;
	private JButton jButtonLoadArray;
	private JButton jButtonServerConnect;
	private JButton jButtonStartJob;
	private JButton jButtonCreateArray;
	private Client client;
	
	/**
	* Auto-generated main method to display this JFrame
	*/
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				JFrameClient inst = new JFrameClient();
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
			}
		});
	}
	
	public JFrameClient() {
		super();
		initGUI();
	}
	
	private void initGUI() {
		try {
			BoxLayout thisLayout = new BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS);
			getContentPane().setLayout(thisLayout);
			setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			{
				jTextFieldServerIP = new JTextField();
				getContentPane().add(jTextFieldServerIP);
				getContentPane().add(getJButtonServerConnect());
				getContentPane().add(getJButtonViewArray());
				getContentPane().add(getJButtonStartJob());
				jTextFieldServerIP.setText("127.0.0.1");
				jTextFieldServerIP.setPreferredSize(new java.awt.Dimension(392, 21));
			}
			{
				jButtonCreateArray = new JButton();
				getContentPane().add(jButtonCreateArray);
				getContentPane().add(getJButtonLoadArray());
				jButtonCreateArray.setText("CreateArray");
			}
			pack();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private JButton getJButtonViewArray() {
		if(jButtonViewArray == null) {
			jButtonViewArray = new JButton();
			jButtonViewArray.setText("ViewArray");
		}
		return jButtonViewArray;
	}
	
	private JButton getJButtonStartJob() {
		if(jButtonStartJob == null) {
			jButtonStartJob = new JButton();
			jButtonStartJob.setText("StartJob");
		}
		return jButtonStartJob;
	}
	
	private JButton getJButtonServerConnect() {
		if(jButtonServerConnect == null) {
			jButtonServerConnect = new JButton();
			jButtonServerConnect.setText("Server connect");
		}
		return jButtonServerConnect;
	}
	
	private JButton getJButtonLoadArray() {
		if(jButtonLoadArray == null) {
			jButtonLoadArray = new JButton();
			jButtonLoadArray.setText("Load array from file");
		}
		return jButtonLoadArray;
	}

}
