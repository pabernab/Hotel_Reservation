package com.cs157a;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class MyJDBC {

    public static void main(String[] args)
    {



        /*
        test any of the functions you write here.
        important note: our mysql databases will have different usernames and passwords,
        so change the code as necessary to make the connections work.
         */

        Scanner input = new Scanner(System.in);
        System.out.println("Are you a returning user? Press 1 for yes and 2 for no.");
        int numb = input.nextInt();
        input.nextLine();
        boolean done = false;
        String name;
        String userID;
        int age;

        while(!done)
        {
            switch(numb){

                case 1:
                {
                    System.out.println("Please enter your user ID:");
                    userID = input.nextLine();
                    System.out.println("Welcome back! Here are your current reservations: ");
                    login(userID);
                    done = true;
                    break;

                }

                case 2:
                {
                    System.out.println("Please enter your name.");
                    name = input.nextLine();
                    System.out.println("Please enter your age.");
                    age = input.nextInt();

                    done = true;
                    break;


                }
        }
        }
    }

    public static void login(String userID){
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_system", "root", "Pob9483wtf213!");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select * from reservation where uID = " + userID);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public static void makeReservation()
    {
        try {

            Scanner scan = new Scanner(System.in);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_system", "root",
                    "password");

            System.out.print("Your user Id: ");
            int userId = scan.nextInt();

            System.out.print("Room id: ");
            int roomID = scan.nextInt();

            System.out.println("Start date(format: yyyy-mm-dd): ");
            String startDate = scan.next();

            System.out.println("End date(format: yyyy-mm-dd): ");
            String endDate = scan.next();

            scan.close();

            PreparedStatement stmt = connection.prepareStatement(
                    "insert into `reservation` (uID, roomID, startDate, endDate, updatedAt)  values(?, ?, ? , ?, current_date )");

            stmt.setInt(1, userId);
            stmt.setInt(2, roomID);
            stmt.setDate(3, Date.valueOf(startDate));
            stmt.setDate(4, Date.valueOf(endDate));
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkIn()
    {

        try {

            Scanner scan = new Scanner(System.in);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_reservation", "root", "Trey1998$$$");

            System.out.print("Enter user Id: ");
            int userId = scan.nextInt();

            System.out.print("Enter room id: ");
            int roomID = scan.nextInt();

            System.out.println("Enter reservation date(format: yyyy-mm-dd): ");
            String resDate = scan.next();

            PreparedStatement stmt = connection.prepareStatement("insert into `room` (roomID, roomType)  values(?, ?)");

            stmt.setInt(1, userId);
            stmt.setInt(2, roomID);
            stmt.executeUpdate();

            if (resDate.equals(LocalDate.now().toString())){
                System.out.println("Thank you for checking in!");
            }
            else{
                System.out.println("Your reservation is not for today!");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void editReservation()
    {
        //jonathan
        // insert code here. use any parameters as needed.
        try {

            Scanner scan = new Scanner(System.in);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_system", "root",
                    "password");

            System.out.print("Enter reservation Id: ");
            int reservationID = scan.nextInt();

            System.out.print("Room id: ");
            int roomID = scan.nextInt();

            System.out.println("Start date(format: yyyy-mm-dd): ");
            String startDate = scan.next();

            System.out.println("End date(format: yyyy-mm-dd): ");
            String endDate = scan.next();

            scan.close();

            PreparedStatement stmt = connection.prepareStatement(
                    "update reservation set roomId = ?, startDate = ?, endDate = ?, " +
                            "updatedAt = current_date where rID = ? ");

            stmt.setInt(1, roomID);
            stmt.setDate(2, Date.valueOf(startDate));
            stmt.setDate(3, Date.valueOf(endDate));
            stmt.setInt(4, reservationID);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelReservation()
    {
        //jonathan
        //insert code here. use any parameters as needed
        try {

            Scanner scan = new Scanner(System.in);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_system", "root",
                    "password");

            System.out.print("Enter reservation Id: ");
            int reservationID = scan.nextInt();

            System.out.print("Enter user id: ");
            int userID = scan.nextInt();

            scan.close();

            PreparedStatement stmt = connection.prepareStatement(
                    "delete from reservation where rID = ? and uID = ? ");

            stmt.setInt(1, reservationID);
            stmt.setInt(2, userID);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void orderRoomService()
    {
        //jonathan
        //insert code here. use any parameters as needed
        try {

            Scanner scan = new Scanner(System.in);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_system", "root",
                    "password");

            System.out.print("Enter payment Id: ");
            int pID = scan.nextInt();

            System.out.print("Reservation id: ");
            int reservationID = scan.nextInt();


            System.out.print("Amount: ");
            int amount = scan.nextInt();

            scan.close();

            PreparedStatement stmt = connection.prepareStatement(
                    "insert into `payment` (pId, rId, amount, serviceType)  values(?, ?, ? , ?)");

            stmt.setInt(1, pID);
            stmt.setInt(2, reservationID);
            stmt.setInt(3, amount);
            stmt.setString(4, "Room Service");
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void useGym()
    {
        //jonathan
        //insert code here.
        try {

            Scanner scan = new Scanner(System.in);
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_system", "root",
                    "password");

            System.out.print("Enter payment Id: ");
            int pID = scan.nextInt();

            System.out.print("Reservation id: ");
            int reservationID = scan.nextInt();


            System.out.print("Amount: ");
            int amount = scan.nextInt();

            scan.close();

            PreparedStatement stmt = connection.prepareStatement(
                    "insert into `payment` (pId, rId, amount, serviceType)  values(?, ?, ? , ?)");

            stmt.setInt(1, pID);
            stmt.setInt(2, reservationID);
            stmt.setInt(3, amount);
            stmt.setString(4, "Gym");
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static void adminReservations(int adminID)
    {
        if (adminID > 8999)
        {
            try {

                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_system", "root", "Pob9483wtf213!");

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery("select * from reservation");
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                while (resultSet.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = resultSet.getString(i);
                        System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    System.out.println("");
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("That is not a Admin ID. Exiting...");
            return;
        }

    }

    public static void adminCheckRoom()
    {
        //paul
        //insert code here
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_system", "root", "Pob9483wtf213!");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select roomID from reservations, room where isOccupied = false and reservations.roomID = room.roomID");
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void cleanRoom()
    {
        //paul
        //insert code here
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_system", "root", "Pob9483wtf213!");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select roomID from room where cleaned = false");
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void adminCheckIn()
    {
        //paul
        //insert code here
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_system", "root", "Pob9483wtf213!");

            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("select name, uID, isOccupied from user, reservation, room where getDate() >= startDate or getDate() <= endDate");
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }



    public static void checkReservationDate()
    {
        //trey
        //insert code here
    }

    public static void calculateTotal()
    {
        //trey
        //insert code here
    }

    public static void usePool()
    {
        //trey
        //insert code here
    }







    }

    /*

    public static void insertIntoDatabase(String name, String age){
        try {

            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hotel_system", "root", "Pob9483wtf213!");

            Statement statement = connection.createStatement();

            PreparedStatement stmt = connection.prepareStatement("insert into user(uID, name, age) values (?, ?, ?");
            stmt.setString(2, name);
            stmt.setString(3, age);


            ResultSet resultSet = statement.executeQuery("select * from reservation where uID = " + userID);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = resultSet.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }


     */
