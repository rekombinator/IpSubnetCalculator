package com.isk_subnetcalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Calculator {
    
    private final Map<String, Integer> subnets = new HashMap<>();
    
    public void clearSubnets(){
        subnets.clear();
    }
    public void removeSubnet(String name){
        subnets.remove(name);
    }
    
    public void addSubnet(String name, Integer size){
        subnets.put(name, size);
    }
    
        public List<Subnet> calculate(String majorNetwork) {
        
        Map<String, Integer> sortedSubnets = sortMap(subnets);
        
        List<Subnet> output = new ArrayList<>();
        
        int currentIp = findFirstIp(majorNetwork);

        for (String key : sortedSubnets.keySet()) { 
            Subnet subnet = new Subnet();

            subnet.setName(key);
            subnet.setAddress(convertIpToQuartet(currentIp));

            int neededSize = sortedSubnets.get(key);
            subnet.setNeededSize(neededSize);

            int mask = calcMask(neededSize);
            subnet.setMask("/" + mask);
            subnet.setDecMask(toDecMask(mask));

            int allocatedSize = findUsableHosts(mask);
            subnet.setAllocatedSize(allocatedSize);
            subnet.setBroadcast(convertIpToQuartet(currentIp + allocatedSize + 1));
            
            subnet.setCapacity((neededSize * 100) / allocatedSize + " %");

            String firstUsableHost = convertIpToQuartet(currentIp + 1);
            String lastUsableHost = convertIpToQuartet(currentIp + allocatedSize);
            
            subnet.setRange(firstUsableHost + " - " + lastUsableHost);

            output.add(subnet);

            currentIp += allocatedSize + 2;
        }

        return output;
    }
    
    private Map<String, Integer> sortMap(Map<String, Integer> map) {
        
        List<Map.Entry<String, Integer>> entries = new ArrayList<>(map.entrySet());
        
        Collections.sort(entries, (o1, o2) -> o1.getValue().compareTo(o2.getValue()));
        
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        
        entries.forEach((entry) -> {
            sortedMap.put(entry.getKey(), entry.getValue());
        });

        return sortedMap;
    }

    private int convertQuartetToBinaryString(String ipAddress) {
        String[] ip = ipAddress.split("\\.|/");

        int octet1 = Integer.parseInt(ip[0]);
        int octet2 = Integer.parseInt(ip[1]);
        int octet3 = Integer.parseInt(ip[2]);
        int octet4 = Integer.parseInt(ip[3]);

        int output = octet1;
        output = (output << 8) + octet2;
        output = (output << 8) + octet3;
        output = (output << 8) + octet4;

        return output;
    }

    private static String convertIpToQuartet(int ipAddress) {
        int octet1 = (ipAddress >> 24) & 255;
        int octet2 = (ipAddress >> 16) & 255;
        int octet3 = (ipAddress >> 8) & 255;
        int octet4 = ipAddress & 255;

        return octet1 + "." + octet2 + "." + octet3 + "." + octet4;
    }


    private int findFirstIp(String majorNetwork) {
        String[] ip = majorNetwork.split("/");
        int mask = Integer.parseInt(ip[1]); // parse CIDR mask
        int offset = Integer.SIZE - mask;

        int majorAddress = convertQuartetToBinaryString(majorNetwork);
        int firstIp = (majorAddress >> offset) << offset;

        return firstIp;
    }

    private int calcMask(int neededSize) {
//        int highestBit = Integer.highestOneBit(neededSize);  stara wersja przy neededSize 7 dawała 6 jako allocatedSize, dodałem jeden i trzeba sprawdzić czy OK
        int highestBit = Integer.highestOneBit(neededSize + 1 );
        int position = (int) (Math.log(highestBit) / Math.log(2));
        return Integer.SIZE - (position + 1);   // +1 because position starts with 0
    }

    private int findUsableHosts(int mask) {
        return (int) Math.pow(2, Integer.SIZE - mask) - 2;
    }

    private String toDecMask(int mask) {
        if (mask == 0) {
            return "0.0.0.0";
        }
        int allOne = -1;    // '255.255.255.255'
        int shifted = allOne << (Integer.SIZE - mask);

        return convertIpToQuartet(shifted);
    }
  

}
