package com.team.dy.study.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

public class NetStudy {
    public static void main(String[] args) throws UnknownHostException {
        if(args.length>0){
            String host = args[0];
            InetAddress[] allByName = Inet4Address.getAllByName(host);
            for (InetAddress inetAddress : allByName) {
                System.out.println("inetAddress = " + inetAddress);
            }
        }else {
            InetAddress loopbackAddress = Inet4Address.getLoopbackAddress();
            System.out.println("loopbackAddress = " + loopbackAddress.getHostAddress());
        }
    }
}
