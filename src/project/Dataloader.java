/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Dataloader {
    static ArrayList<ArrayList> data=new ArrayList<ArrayList>();
    
    public Dataloader(){
        
    }
    
    private static String [] readFile(String filename) throws IOException{
        BufferedReader br=null;
        try{
           br=new BufferedReader(new FileReader(filename)); 
           StringBuilder sb=new StringBuilder();
           String line=br.readLine();
           while(line!=null){
               sb.append(line);
               sb.append("\n");
               line=br.readLine();
           }
           String everything=sb.toString();
           String [] blocks=everything.split("\n");
           return blocks;
        }catch (Exception e){
           e.printStackTrace();
           return null;
           
        }finally{
            br.close();
        }
        
    }
    
    public static ArrayList populateData() throws IOException{
       
        String arr[]=readFile("D:/957321/PortalSystem/src/portalsystem/data.txt/");
        for (int i=0;i<arr.length;i++){
            String[] details=arr[i].split(",");
             ArrayList<String> user=new ArrayList<>();
             for (int j=0;j<details.length;j++){
                 user.add(details[j]);
             }
             data.add(user);
        }
        return data;
    }
    public static void main(String[] args) throws IOException{
        populateData();
        for (int i=0;i<data.size();i++){
            System.out.println(data.get(i).toString());
        }
    }
}
