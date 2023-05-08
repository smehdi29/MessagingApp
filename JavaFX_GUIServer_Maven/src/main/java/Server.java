import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;
/*
 * Clicker: A: I really get it    B: No idea what you are talking about
 * C: kind of following
 */

public class Server{

	int count = 1;	
	ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
	TheServer server;
	private Consumer<Info> callback;
	
	
	Server(Consumer<Info> call){
	
		callback = call;
		server = new TheServer();
		server.start();
	}
	
	
	public class TheServer extends Thread{
		
		public void run() {
		
			try(ServerSocket mysocket = new ServerSocket(5554);){
		    System.out.println("Server is waiting for a client!");
		  
			
		    while(true) {
		
				ClientThread c = new ClientThread(mysocket.accept(), count);
				clients.add(c);
				Info notif = new Info();
				notif.message = "client has connected to server: " + "client #" + count;
				notif.active = new ArrayList<String>();
				for(ClientThread i : clients) {
					notif.active.add("Client: #"+i.count);
				}
				callback.accept(notif);
				
				c.start();
				
				count++;
				
			    }
			}//end of try
				catch(Exception e) {
					Info notif = new Info();
					notif.message = "Server socket did not launch";
					callback.accept(notif);
				}
			}//end of while
		}
	

		class ClientThread extends Thread{
			
		
			Socket connection;
			int count;
			ObjectInputStream in;
			ObjectOutputStream out;
			ArrayList<Integer> specific;
			
			ClientThread(Socket s, int count){
				this.connection = s;
				this.count = count;	
			}
			
			public void updateClients(String message) {
				for(int i = 0; i < clients.size(); i++) {
					ClientThread t = clients.get(i);
					
					try {
						Info notif = new Info();
						notif.message = message;
						notif.active = new ArrayList<String>();
						//notif.specific = this.specific;
						for(ClientThread x : clients) {
							notif.active.add("Client: #"+x.count);
						}
					 t.out.writeObject(notif);
					}
					catch(Exception e) {}
				}
				
			}
			
			public void updateSpecificClients(String message, ArrayList<Integer> spec) {
				synchronized(clients) {
				for(ClientThread t : clients) {
					if(spec.contains(t.count) || t == this) {
						try {
							Info notif = new Info();
							notif.message = message;
							//notif.specific = this.specific;
							notif.active = new ArrayList<String>();
							for(ClientThread x : clients) {
								notif.active.add("Client: #"+x.count);
							}
							t.out.writeObject(notif);
						 
						}
						catch(Exception e) {}
					}
				}
				}
				
			}
			

			
			public void run(){
					
				try {
					in = new ObjectInputStream(connection.getInputStream());
					out = new ObjectOutputStream(connection.getOutputStream());
					connection.setTcpNoDelay(true);	
				}
				catch(Exception e) {
					System.out.println("Streams not open");
				}
				
				
				updateClients("new client on server: client #"+count);
				
				
					
				 while(true) {
					    try {
					    	Info data = (Info) in.readUnshared();
					    	String message = "Client " + count + " said: " + data.message;
					    	Info notif = new Info();
					    	notif.message = message;
					    	notif.specific = data.specific;
					    	
					    	//this.specific = notif.specific;
					    	
					    	callback.accept(notif);
					    	
					    	
					    	if(!notif.message.equals("")) {
						    	if(!data.isSpecific())
						    		updateClients(message);
						    	else {
						    		updateSpecificClients(message, notif.specific);
						    	}
					    	}
					    	
					    }
					    catch(Exception e) {
					    	Info notif = new Info();
					    	notif.message = "OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!";
					    	
					    	callback.accept(notif);
					    	
					    	//updateClients("-" + count);
					    	clients.remove(this);
					    	/*if(specific != null)
					    		specific.remove(this.count);*/
					    	updateClients("Client #"+count+" has left the server!");
					    	
					    	//e.printStackTrace();
					    	break;
					    }
					}
				}//end of run
			
			
		}//end of client thread
}


	
	
	
