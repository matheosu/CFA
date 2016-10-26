package br.edu.unigranrio.ect.si.cfa.model;

import br.edu.unigranrio.ect.si.cfa.commons.util.NetworkUtils;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.net.InetAddress;
import java.util.Locale;

@Embeddable
public class SessionInfo implements Serializable {

    private static final long serialVersionUID = -301897126090205925L;

    private String locale;
    private String ipAddr;
    private String macAddr;
    private String userAgent;
    private String hostname;
    private String operationSystem;

    public SessionInfo() {
        setLocale(Locale.getDefault().getDisplayName());
    }

    public SessionInfo(String locale) {
        setLocale(locale);
    }

    public SessionInfo(Locale locale) {
        this(locale.getDisplayName());
    }

    public SessionInfo(String locale, String ipAddr, String hostname) {
        this(locale);
        setIpAddr(ipAddr);
        setHostname(hostname);
    }

    public SessionInfo(Locale locale, String ipAddr, String hostname) {
        this(locale);
        setIpAddr(ipAddr);
        setHostname(hostname);
    }

    public SessionInfo(Locale locale, InetAddress address) {
        this(locale, address != null ? address.getHostAddress() : "",
                     address != null ? address.getHostName() : "");
    }

    public SessionInfo(String locale, String ipAddr, String macAddr, String userAgent, String hostname, String operationSystem) {
        this(locale, ipAddr, hostname);
        setMacAddr(macAddr);
        setUserAgent(userAgent);
        setOperationSystem(operationSystem);
    }

    public SessionInfo(Locale locale, InetAddress address, String userAgent, String operationSystem) {
        this(locale, address);
        setUserAgent(userAgent);
        setOperationSystem(operationSystem);
        setMacAddr(address != null ? NetworkUtils.getMacAddress(address) : "");
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String browser) {
        this.userAgent = browser;
    }

    public String getOperationSystem() {
        return operationSystem;
    }

    public void setOperationSystem(String operationSystem) {
        this.operationSystem = operationSystem;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + " [ IP: " + getIpAddr() + "]"
                + " [ MAC: " + getMacAddr() + "]"
                + " [ Hostname: " + getHostname() + "]"
                + " [ Locale: " + getLocale() + "]"
                + " [ OS: " + getOperationSystem() + "]"
                + " [ Browser: " + getUserAgent() + "]"
                ;
    }

}
