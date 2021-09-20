//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {
	
	protected char[] fields = new char[10];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Server game = new Server();
		
		
		try
		{
			System.out.println("Waiting for client...");
			ServerSocket ss = new ServerSocket(8888);
			Socket soc = ss.accept();
			DataInputStream din=new DataInputStream(soc.getInputStream());
			DataOutputStream dout=new DataOutputStream(soc.getOutputStream());
			BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

			
			int  st2=0;
			String str="", str2 = "";
			
			while(game.stillEmpty()) {
				
				// read client input and show
				str=din.readUTF();
				game.fields[Integer.parseInt(str)] = 'O';
				game.board();
				
				if(game.checkWin('O')) {
					System.out.println("Client Won !!");
					break;
				}
				if(!game.stillEmpty()) {
					System.out.println("TIE !!");
					break;
				}
				
				// server input from user
				System.out.println("Server's Turn 'X' : ");
				st2 = Integer.parseInt(userInput.readLine());
				game.fields[st2] = 'X';
				
				
				// sent to client
				str2 = String.valueOf(st2);
				dout.writeUTF(str2);
				dout.flush();
				
				// if server won or server tied the game
				if(game.checkWin('X') || !game.stillEmpty()) {
					break;
				}
				
			}
			
			
			din.close();  
			soc.close();  
			ss.close();  

			
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	
	protected void board() {
		// TODO Auto-generated method stub
		
		System.out.println(fields[7] + " | "+ fields[8] + " | " + fields[9]);
		System.out.println("_________");
		System.out.println(fields[4] + " | "+ fields[5] + " | " + fields[6]);
		System.out.println("_________");
		System.out.println(fields[1] + " | "+ fields[2] + " | " + fields[3]);

	}
	
	protected boolean checkWin(char c) {
		
		if(	   (c == fields[7] && fields[7] == fields[8] && fields[8] == fields[9] )
			|| (c == fields[4] && fields[4] == fields[5] && fields[5] == fields[6] )
			|| (c == fields[1] && fields[1] == fields[2] && fields[2] == fields[3] )
			|| (c == fields[7] && fields[7] == fields[4] && fields[4] == fields[1] )
			|| (c == fields[8] && fields[8] == fields[5] && fields[5] == fields[2] )
			|| (c == fields[9] && fields[9] == fields[6] && fields[6] == fields[3] )
			|| (c == fields[7] && fields[7] == fields[5] && fields[5] == fields[3] )
			|| (c == fields[1] && fields[1] == fields[5] && fields[5] == fields[9] )
				)
			{
			return true;
			}
		
		return false;

	}
	
	protected boolean stillEmpty() {
		for(int i=1; i<=9; i++) {
			if(fields[i] == '\0') {
				return true;
			}
		}
		return false;
	}

}
