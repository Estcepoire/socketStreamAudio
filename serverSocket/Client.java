package client;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;
import java.util.Scanner;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Client {
    public static void main(String[] args) {
        try {
            Client c= new Client();
            while (true) {
                byte[] mess= c.getdata();
                AudioInputStream audioInputStream = c.writeBytesToStream(mess);
                c.playeur(audioInputStream);
            }
            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void playeur(AudioInputStream audioinputstream) throws Exception {
        Scanner scan = new Scanner(System.in);
        Clip clip= AudioSystem.getClip();
        clip.open(audioinputstream);
        String response= "";
        while(!response.equals("Q")){
            System.out.println("P= Play, S= Stop, R= Reset, Q= Quit");
            System.out.print("Enter your choice : ");
            response= scan.next();
            response= response.toUpperCase();
            switch(response){
                case("P"): clip.start();
                break;
                case("S"): clip.stop();
                break;
                case("R"): clip.setMicrosecondPosition(0);
                break;
                case("Q"): clip.close();
                break;
                default: System.out.println("Invalid response");
            }
        }
    }
    public byte[] getdata() throws Exception {
        Socket socket = new Socket("localhost",6669);
        Socket s=new Socket("localhost",6666);
        DataOutputStream dout=new DataOutputStream(s.getOutputStream());
        System.out.println("0-ARIANA_Ampelatanana");
        System.out.println("Choice:");
        Scanner scan= new Scanner(System.in);
        String msg= (String)scan.nextLine();
        dout.writeUTF(msg);
        dout.flush();
        dout.close();
        s.close();
        DataInputStream dIn = new DataInputStream(socket.getInputStream());
        int length = dIn.readInt();
        byte[] message = new byte[length];
        if(length>0) {
            dIn.readFully(message, 0, message.length);
        }
        socket.close();
        return message;
    }
    public AudioInputStream writeBytesToStream(byte[] bytes) {
        AudioInputStream stream = new AudioInputStream(
            new ByteArrayInputStream(bytes),
            new AudioFormat(44100, 16, 2, true, false),
            bytes.length
        );
        return stream;
    }
}
