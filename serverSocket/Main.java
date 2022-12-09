import java.io.File;
import java.io.FileInputStream;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import client.Client;

public class Main {
    public static void main(String[] args) {
        File file = new File("Zanahare.mp3");
        System.out.println(file.canRead());
        try {
            System.out.println(212);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
