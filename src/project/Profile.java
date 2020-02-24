/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author hp
 */
public class Profile {

    /**
     * @param args the command line arguments
     */
    static ArrayList<ArrayList> data=new ArrayList<>();
    static ArrayList<User> users=new ArrayList();
    static String session;
    static Scanner s=new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        if(connection.fetchItem("select count(username) from user", "count(username)").equals("0")){ //data from the file has not been loaded into the database
            loadUsers();
            login();
        }else{ //data has already been loaded into the database
            login();
        }
    }
    public static void login(){
        System.out.println("User Login");
        System.out.print("Enter username: ");
        String username=s.next();
        System.out.print("Enter password: ");
        String password=s.next();
        String count=connection.fetchItem("select count(username) from user where username='"+username+"'and password='"+password+"'", "count(username)");
        System.out.println(count);
        if(connection.fetchItem("select count(username) from user where username='"+username+"' and password='"+password+"'", "count(username)").equals("1")){
            System.out.println("Login successful");
            session=username;
            String status=connection.fetchItem("select status from user where username='"+username+"'", "status");
            if(status.equals("selected")){
                selectedMenu();
            }else{
                unselectedMenu();
            }
        }else{
            System.out.println("Invalid username or password");
            login();
        }
    }
    public static void unselectedMenu(){
        System.out.println("Select option");
        System.out.println("1. Send Invititations\n2.View & Accept the Invitations\n3. Log out");
        String option=s.next();
        switch(option){
            case "1":
                sendInvite();
                break;
            case "2":
                viewAndAcceptInvite();
                break;
            case "3":
            	System.out.println("Logged out successfully");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid option");
                unselectedMenu();
                break;
                
        }
    }
    public static void selectedMenu(){
        String name=connection.fetchItem("select name from user where username='"+session+"'", "name");
        System.out.println("Welcome "+name);
        String partner=connection.fetchItem("select name from user where username=(select partner from user where username='"+session+"')", "name");
        System.out.println("Congartulations!Your partner is: "+partner);
        System.out.print("Enter 1 to log out: ");
        String choice=s.next();
        if(choice.equals("1")){
        	System.out.println("Logged out successfully");
            System.exit(0);
        }else{
            System.out.println("Invalid option");
            selectedMenu();
        }
        
    }
    public static void sendInvite(){
        System.out.println("Select a user to invite");
        ArrayList<String> dt=connection.fetch("select name from user where status='unselected' and username not in (select invited from invites where invitee='"+session+"')and username!= '"+session+"' limit 5", "name");
        for(int i=0;i<dt.size();i++){
            System.out.println(i+". "+dt.get(i));
        }
        System.out.print("Your choice: ");
        String choice=s.next();
        while(verifyInput(choice)==false){
            System.out.println("Enter the correct input: ");
            choice=s.next();
        }
        if(Integer.parseInt(choice)>=0 && Integer.parseInt(choice)<=dt.size()-1){
            String name=dt.get(Integer.parseInt(choice));
            String username=connection.fetchItem("select username from user where name='"+name+"'", "username");
            if(connection.Insert("insert into invites (invitee,invited) values ('"+session+"','"+username+"')")){
                System.out.println("Invitation sent");
                unselectedMenu();
            }
        }else{
            System.out.println("Enter the correct user");
            sendInvite();
        }
    }
    public static void viewAndAcceptInvite(){
        System.out.println("Select a user to accept invite");
        ArrayList<String> dt=connection.fetch("select name from user where status='unselected' and username in (select invitee from invites where invited='"+session+"') limit 5", "name");
        for(int i=0;i<dt.size();i++){
            System.out.println(i+". "+dt.get(i));
        }
        System.out.print("Your choice: ");
        String choice=s.next();
        while(verifyInput(choice)==false){
            System.out.println("Enter the correct input: ");
            choice=s.next();
        }
        if(Integer.parseInt(choice)>=0 && Integer.parseInt(choice)<=dt.size()-1){
            String name=dt.get(Integer.parseInt(choice));
            String username=connection.fetchItem("select username from user where name='"+name+"'", "username");
            if(connection.Update("update user set partner='"+username+"', status='selected' where username='"+session+"'") && connection.Update("update user set partner='"+session+"', status='selected' where username='"+username+"'")){
                System.out.println("Invitation Accepted");
                selectedMenu();
            }
        }else{
            System.out.println("Enter the correct user");
            sendInvite();
        }
    }
    public static boolean verifyInput(String input){
        try{
            Integer.parseInt(input);
            return true;
        }catch(NumberFormatException e){
            return false;
        }
    }
    public static void loadUsers() throws IOException{
        data=Dataloader.populateData();
        for(int i=0;i<data.size();i++){
            ArrayList<String> info=data.get(i);
            String username=info.get(0);
            String name=info.get(1);
            String password=info.get(2);
            String status=info.get(3);
            User user=new User(username,name,password,status);
            users.add(user);
        }
        for(int i=0;i<users.size();i++){
            User user=users.get(i);
            int count=0;
           if(connection.Insert("insert into user (username,name,password,status) values ('"+user.getUserName()+"','"+user.getName()+"','"+user.getPassword()+"','"+user.getStatus()+"')")){
               count++;
           }
        }
    }
}
