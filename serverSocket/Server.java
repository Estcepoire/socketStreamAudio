package server;

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.File;

import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.FileSystem;
import java.nio.file.Path;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
public class Server {
    public  byte[] getAudio(File file) throws Exception{
        AudioInputStream f = AudioSystem.getAudioInputStream(file);
        System.out.print(f.getFrameLength());
        byte[] buf = new byte[(int)file.length()];
        // System.out.println(f.getFormat().getSampleRate()+"  "+f.getFormat().getChannels()+"   "+f.getFormat().getSampleSizeInBits()) ;
        f.readNBytes(buf, 0, (int)file.length());
        return buf;
    }
    public void connectServer(byte[] buffer,ServerSocket s){
        try {
            byte[] message = buffer;
            Socket socket = s.accept();
            DataOutputStream dOut = new DataOutputStream(socket.getOutputStream());
            dOut.writeInt(message.length);
            dOut.write(message);
            }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public File[] getMusic(String directory) {
        File dir = new File(directory);
        File[] files = dir.listFiles();
        // System.out.println("cvb");
        // for (int i = 0; i < files.length; i++) {
        //     System.out.println(files[i].getName());
        // }
        return files;
    }
    public static void main(String[] args) {
        try{
            ServerSocket ss=new ServerSocket(6666);
            ServerSocket sa= new ServerSocket(6669);
            while (true) {
            Socket s=ss.accept();
            DataInputStream dis= new DataInputStream(s.getInputStream());
            String str=(String)dis.readUTF();
            int i = Integer.parseInt(str);
            // ss.close();
            Server se = new Server();
            File[] files = se.getMusic("wav/");
                byte[] b = se.getAudio(files[i]);
                se.connectServer(b,sa);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}