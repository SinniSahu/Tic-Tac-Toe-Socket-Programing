//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;

import java.io.*;
import java.net.Socket;


public class Client {
	
	protected char[] fields = new char[10];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Client game = new Client();
		
		try
		{
			System.out.println("Client connected... \n Let's Play!");
			Socket soc = new Socket("localhost", 8888);
			DataInputStream din=new DataInputStream(soc.getInputStream());  
			DataOutputStream dout=new DataOutputStream(soc.getOutputStream()); 
			
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));  
		
			
			int st=0;
			String str="", str2="";
			
			while(game.stillEmpty()) {
				// client input from user
				System.out.println("Client's turn 'O' : ");
				st = Integer.parseInt(br.readLine());
				game.fields[st] = 'O';
				
				
				
				// sent to server
				str = String.valueOf(st);
				dout.writeUTF(str);
				dout.flush();
				
				
				// if client won or client tied the game
				if(game.checkWin('O') || !game.stillEmpty()) {
					break;
				}
				
				
				// read server input and show
				str2=din.readUTF(); 
				game.fields[Integer.parseInt(str2)] = 'X';
				game.board();
				if(game.checkWin('X')) {
					System.out.println("Server Won !!");
					break;
				}
				if(!game.stillEmpty()) {
					System.out.println("TIE !!");
					break;
				}
			}
			
			dout.close();  
			soc.close();  
			
			
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
		
//		for(int p=PLAYER_1; p<=PLAYER_2; p++) {
		// TODO Auto-generated method stub
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
//		}
		
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
