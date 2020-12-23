package rm2;

import java.io.IOException;

import com.ireasoning.protocol.snmp.*;

public class Snmp {
	
	private static final String community = "si2019";
	private static final int port = 161;
	
	private static final String[] hosts = {	"192.168.10.1",
											"192.168.20.1",
											"192.168.30.1"};
	
	private static final String[] oid = { 	".1.3.6.1.2.1.15.3.1.1", // identifikator
											".1.3.6.1.2.1.15.3.1.2", // stanje
											".1.3.6.1.2.1.15.3.1.4", // bgp verzija
											".1.3.6.1.2.1.15.3.1.5", // ip adresa
											".1.3.6.1.2.1.15.3.1.9", // AS suseda
											".1.3.6.1.2.1.15.3.1.10", // br update in mess
											".1.3.6.1.2.1.15.3.1.11", // br update out mess po susedu
											".1.3.6.1.2.1.15.3.1.19", // keepalive time
											".1.3.6.1.2.1.15.3.1.24"};// elapsed time
	
	private static final String[] kolone = {"identifikator",
											"state",
											"bgp verzija",
											"ip addr",
											"AS",
											"update in",
											"update out",
											"keepalive time",
											"elapsed time"};
	
	
	public String[][] getInfo(int index) throws IOException {
		SnmpTarget target = new SnmpTarget(hosts[index], port, community, community);		
		SnmpSession session = new SnmpSession(target);
		SnmpVarBind[][] table = new SnmpVarBind[9][];
		
		for(int i = 0; i < 9; i++)
			table[i] = session.snmpGetTableColumn(new SnmpOID(oid[i]));
		String[][] rez = new String[table[0].length][9];
		 
		for(int elem = 0; elem < table[0].length; elem++) 
			for(int i = 0; i < 9; i++)
				rez[elem][i] = ""+ table[i][elem].getValue();
		
		session.close();
		return rez;
	}

	
	public String[] getHosts() {
		return hosts;
	}
	public String[] getKolone() {
		return kolone;
	}
	
	public static void main(String args[]) {
		SnmpFrame s = new SnmpFrame();
		Thread t = new Thread(s);
		t.start();
	}
	
}
