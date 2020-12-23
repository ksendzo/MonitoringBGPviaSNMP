package rm2;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.*;


public class SnmpFrame extends JFrame implements ItemListener, Runnable{

    private static JComboBox select;
    private static JTable table; 
    private static JScrollPane sp;
    private static JPanel ss;
    private static JLabel label = new JLabel("updated");
    private static JPanel cards = new JPanel(new CardLayout());
    private static JScrollPane[] card = new JScrollPane[3];
    
	private Snmp info;
	
	public SnmpFrame() {
		info = new Snmp();
		loading();	
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	private void loading()  {
		setTitle("SNMP & BGP");
		
		setLayout(new BorderLayout());
		
		select = new JComboBox(info.getHosts());
		select.addItemListener(this);
		ss = new JPanel(new FlowLayout());
		ss.add(select);
		ss.add(label);
		
		add(ss, BorderLayout.NORTH);
	}
	
	private void reloadInfo() {
		label.setText("updating...");
		try {		
			for(int i = 0; i < 3; i++) {
				table = new JTable(info.getInfo(i), info.getKolone());
				table.setBounds(30, 40, 200, 300); 
				card[i] = new JScrollPane(table);
				cards.add(card[i], info.getHosts()[i]);
			}
			
			((CardLayout)cards.getLayout()).show(cards, select.getSelectedItem().toString());
			
	        add(cards, BorderLayout.CENTER); 
	        repaint();
	        setSize(1000, 500); 
	        setVisible(true);
		        
			} catch (IOException e) {
				e.printStackTrace();
			}
		label.setText("UPDATED");
	}
	
	
	@Override
	public synchronized void itemStateChanged(ItemEvent e) {
		if(e.getSource() == select)
			((CardLayout)cards.getLayout()).show(cards, select.getSelectedItem().toString());
	}

	@Override
	public void run() {
		while(true) {
			try {
				reloadInfo();
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
