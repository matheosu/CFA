package br.edu.unigranrio.ect.si.cfa.commons.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;

public final class NetworkUtils {

    private static final String MAC_ADDR_FORMAT = "%02X%s";
    private static final String MAC_ADDR_DEFAULT = "00-00-00-00-00-00";
    private static final Logger logger = LoggerFactory.getLogger(NetworkUtils.class);

    private NetworkUtils(){}

    public static String getMacAddress(InetAddress inetAddress) {
        try {
            NetworkInterface netInterface = NetworkInterface.getByInetAddress(inetAddress);
            byte[] mac = netInterface.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++)
                sb.append(String.format(MAC_ADDR_FORMAT, mac[i], (i < mac.length - 1) ? "-" : ""));

            return sb.toString();
        } catch (Exception e) {
            logger.error("Cannot get MacAddres from this InetAddress: " + inetAddress);
        }
        return MAC_ADDR_DEFAULT;
    }

}