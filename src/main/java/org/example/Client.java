package org.example;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    private DatagramPacket packet;
    private DatagramSocket ds;

    public void send(String s){
        byte[] buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
        try {
            ds = new DatagramSocket();
            ds.connect(InetAddress.getByName("localhost"), 6666);
            buffer = s.getBytes(StandardCharsets.UTF_8);
            packet.setData(buffer);
            ds.send(packet);
            ds.disconnect();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }finally {
            ds.disconnect();
        }
    }
    public void receive(){
        try {
            byte[] data = new byte[1024];
            packet = new DatagramPacket(data, data.length);
            ds.connect(InetAddress.getByName("localhost"), 6666);

            ds.receive(packet);
            String s = new String(packet.getData(), packet.getOffset(), packet.getLength());
            System.out.println("客户端接收到的数据"+s);
            ds.disconnect();
        }catch (UnknownHostException e){
            e.printStackTrace();
        }catch (IOException u){
            u.printStackTrace();
        }
    }
}
