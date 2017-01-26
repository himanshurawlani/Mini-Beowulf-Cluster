/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;


public class Client {
    public static void main(String[] args) throws IOException{
        Socket socket = new Socket("127.0.0.1", 5000);
        //InputStream inputStream = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(),true);
        String reply = "";
        int pos=-1;
        reply = br.readLine();
        if(reply.startsWith("success")){
            pos = Integer.parseInt(reply.substring(reply.indexOf(":")+1));
            System.out.println("Connected Successfully with server:" + pos);
        }else{
            System.out.println("Something went wrong");
            System.exit(0);
        }
        
        socketWriter.println(pos);
        
        String startString = br.readLine();
        String endString = br.readLine();
        
        //int sno = Integer.parseInt(startString.substring(startString.indexOf(":"))+1);
        //int eno = Integer.
        
        String[] startArray = startString.split(":");
        String[] endArray = endString.split(":");
        BigInteger startNum = new BigInteger(startArray[1]);
        BigInteger endNum = new BigInteger(endArray[1]);
        BigInteger one = new BigInteger("1");
        BigInteger two = new BigInteger("2");
        BigInteger zero = new BigInteger("0");
        //BigInteger j = two;
        //ArrayList<Integer> res = new ArrayList<>();
        int flag = 0;
	int counter=0;
        for(;startNum.compareTo(endNum)<=0;startNum=startNum.add(one)){
            for(BigInteger j=two;j.compareTo(startNum)<0;j=j.add(one)){
                if((startNum.mod(j)).compareTo(zero)==0){
                    flag = 0;
                    break;
                }else{
                    flag = 1;
                }
            }
            if(flag==1){
                //res.add(counter++);
		counter++;
            }
        }
        
        
        //for(Integer curr : res){
            socketWriter.println("Client " + pos + ":" + counter);
        //}
        
        socketWriter.println("bye");
        socket.close();
    }
}
