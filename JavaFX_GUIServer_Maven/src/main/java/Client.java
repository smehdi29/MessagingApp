import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;



public class Client extends Thread{

	
	Socket socketClient;
	int id;
	ObjectOutputStream out;
	ObjectInputStream in;
	
	ArrayList<Integer> specific = new ArrayList<Integer>();
	
	private Consumer<Info> callback;
	
	Client(Consumer<Info> call){
	
		callback = call;
	}
	public void run() {
		
		try {
		socketClient= new Socket("127.0.0.1",5554);
	    out = new ObjectOutputStream(socketClient.getOutputStream());
	    in = new ObjectInputStream(socketClient.getInputStream());
	    socketClient.setTcpNoDelay(true);
		}
		catch(Exception e) {}
		
		while(true) {
			 
			try {
				//in.reset();
				Info inp = (Info)in.readObject();
				//Info message = (Info)inp;
				callback.accept(inp);
				//this.specific = inp.specific;
				//System.out.println("accepted: " + inp.message);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	
    }
	
	public void send(String data) {
		
		try {
			Info notif = new Info();
			notif.message = data;
			
			if(this.specific != null && !this.specific.isEmpty()) {
				for(Integer i : this.specific) {
					System.out.println("Inside client: " + i);
				}
				notif.specific = this.specific;
			}
			out.reset();
			out.writeUnshared(notif);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
