package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Server {
    private int port = 6666;
    private byte[] buffer = new byte[1024];
    private DatagramPacket packet = new DatagramPacket(this.buffer, buffer.length);
    private DatagramSocket ds;

    public Server(int port) {
        this.port = port;
    }
    public void receiveRuest(){
        try {
            ds = new DatagramSocket(this.port);
            ds.receive(packet);
            String s = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
            System.out.println("服务器收取的数据包"+s);
        }catch (SocketException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    public void sendData(){
        try {
            byte[] data = "welcome to here".getBytes(StandardCharsets.UTF_8);
            packet.setData(data);
            ds = new DatagramSocket(this.port);
            ds.send(packet);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Server server = new Server(6666);
                while (true){
                    server.receiveRuest();
                    System.out.println("uu");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    server.sendData();
                }
            }}
            );
        t.start();

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Client client = new Client();
                client.send("hello angela");
//                client.receive();
            }
        });
        t2.start();
    }



//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                Server server = new Server(6666);
//                while (true){
//                    server.receiveRuest();
//                    System.out.println("uu");
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    server.sendData();
//                }
//            }
//        };
//
//        Runnable r2 = new Runnable() {
//            @Override
//            public void run() {
//                Client client = new Client();
//                client.send("hello angela");
//                client.receive();
//            }
//        };
//    }
}
