/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//import com.sun.xml.internal.ws.util.StringUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Server {
   
    
    class ClientHandler implements Runnable{
        Socket socket;
        BufferedReader reader;
        PrintWriter writer;
        public ClientHandler(Socket socket) throws IOException{
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println("success:" + clientNum++ );
                System.out.println("client success");
                
                //reader.close();
                //writer.close();
                //socket.close();
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    static ArrayList<ClientHandler> clientList;
    static ArrayList<Element> nums;
    static int clientNum=0;
    public static void main(String[] args) throws IOException, InterruptedException{
        
        File file = new File("/home/student/Desktop/sample.txt");
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter any number:");
        String prime = stdin.readLine();
        
        PrintWriter fileWriter = new PrintWriter(file);
        fileWriter.println("Input:" + prime);
        fileWriter.close();
        
        ServerSocket serverSocket = new ServerSocket(5000);  // The port on which this Server program will accept clients
        Server mainServer = new Server();                    // Object of this class
        clientList = new ArrayList<>();
        mainServer.init(serverSocket);                       // Initializes the members of the class by accepting connections on the serverSocket port:5000
        mainServer.giveInputToClients(prime);
        
        int[] done = new int[clientList.size()];
        for(int i=0;i<clientList.size();i++){
            
            String clientPosition="";
            for(int j=0;j<clientList.size();j++){
                if(done[j]==0){
                    // Accepts the postion number printed by the client using socketWriter.println(pos); on line 38
                    clientPosition = clientList.get(i).reader.readLine();   
                    if(isNumeric(clientPosition) && done[j]==0){
                        done[j] = 1;
                        break;
                    }
                }
            }
            int position = Integer.parseInt(clientPosition);
            System.out.println("Read input from client ID:" + position);
            mainServer.readInputNumber(position);
        }
        
        int endarray=0;
        while(true){
            for(int k=0;k<clientList.size();k++){
                String line = clientList.get(k).reader.readLine();
                if(line!=null){
                    if(line.startsWith("Client")){
                        mainServer.writeToFile(line);
                    }else if(line.equals("bye")){
                        endarray++;
                        break;
                    }
                }
            }
            
            if(endarray==clientList.size()){
                break;
            }
        }
        
        serverSocket.close();
    }
    
    void init(ServerSocket serverSocket) throws IOException, InterruptedException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Waiting for connections...");
        while(true){
            Socket socket = serverSocket.accept();
            ClientHandler newClient = new ClientHandler(socket);
            clientList.add(newClient);
            
            Thread t = new Thread(newClient);
            t.start();
            
            //t.join();
            
            System.out.println("Do you want to add more clients(Y/n):");
            String ch = br.readLine();
            
            if(ch.equalsIgnoreCase("n")){
                break;
            }
        }
        
        System.out.println(clientList.size() + " client(s) connected!");
    }
    
    void giveInputToClients(String prime){
        System.out.println("Dividing task...");
        BigInteger bigInt = new BigInteger(prime);
        int n = clientList.size();
        nums = new ArrayList<>();
        
        //BigInteger div = bigInt.divide(BigInteger.valueOf(Long.parseLong(String.valueOf(n))));
        BigInteger nn = new BigInteger(String.valueOf(n));
        BigInteger div = bigInt.divide(nn);
        BigInteger start = new BigInteger(String.valueOf("2"));
        BigInteger end = div;
        
        BigInteger i = new BigInteger(String.valueOf("0"));
        BigInteger one = new BigInteger(String.valueOf("1"));
        for(int j=0;j<(n-1);j++){
            Element currElement = new Element(start, end);
            nums.add(currElement);
            start = end.add(one);
            end = end.add(div);
        }
        //start = end;
        end = bigInt;
        nums.add(new Element(start,end));
        System.out.println("Input Distributed");
        
        for(Element curr : nums){
            System.out.println("Start:" + curr.getStart() + " End:" + curr.getEnd());
        }
    }
    
     void readInputNumber(int pos){
        String start = nums.get(pos).getStart().toString();
        String end = nums.get(pos).getEnd().toString();
        
        clientList.get(pos).writer.println("Start:" + start);
        clientList.get(pos).writer.println("End:" + end);
    }
    
    synchronized void writeToFile(String line) throws FileNotFoundException, IOException{
        File file = new File("/home/student/Desktop/sample.txt");
        FileWriter fw = new FileWriter(file,true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter writer = new PrintWriter(bw);
        writer.println(line);
        writer.close();
    }
    
    class Element{
        BigInteger start;
        BigInteger end;
        public Element(BigInteger start, BigInteger end){
            this.start = start;
            this.end = end;
        }
        
        public BigInteger getStart(){
            return start;
        }
        
        public BigInteger getEnd(){
            return end;
        }
        
        
    }
    public static boolean isNumeric(String str){  
        try  {  
            double d = Double.parseDouble(str);  
        }catch(NumberFormatException nfe){  
            return false;  
        }  
        return true;  
    }
}
