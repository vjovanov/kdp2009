import java.util.ArrayList;
import java.util.List;

import com.mladen.Client;
import com.mladen.server.MainServer;
import com.mladen.workstation.Workstation;


public class Main {
	
	static List<Workstation> workstations = new ArrayList<Workstation>();
	
	public static void main(String[] arguments) throws Exception {
		
		MainServer server = new MainServer(12345);
		server.initialize();
		server.start();
		
		for (int i = 0; i < 10; i ++) {
		Workstation workstation = new Workstation(1134 + i, "127.0.0.1", 12345, 5);
		workstation.initialize();
		workstation.start();
		}
		
		Client client = new Client("127.0.0.1", 12345);
		for (int i = 0; i < 1; i ++) {
			client.sendSortRequest();
			Thread.sleep(500);
		}
	}
}
