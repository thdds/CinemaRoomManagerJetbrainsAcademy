package cinema;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Cinema Room Manager project from jetbrains academy. Solved by me.
 */

public class Cinema {
    public static IntPair allCinemaPlaces = createCinema();
    public static ArrayList<IntPair> bookedSeats = new ArrayList<>();
    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Show the seats\n" +
                    "2. Buy a ticket\n" +
                    "3. Statistics\n" +
                    "0. Exit");
            Scanner sc = new Scanner(System.in);
            int num = sc.nextInt();
            switch (num) {
                case 1:
                    printCinemaPlaces();
                    break;
                case 2:
                    bookedSeats.add(bookSeat());
                    printTicketPrice();
                    break;
                case 3:
                    printStatistics();
                    break;
                case 0:
                    return;
            }
        }
    }

    /**
     * prints the statistics of the cinema
     * prints Number of purchased tickets, percentage of purchased tickets, current income based on sold tickets,
     * total potential income if all tickets would be sold based on cinema size.
     */
    public static void printStatistics() {
        System.out.println("Number of purchased tickets: " + bookedSeats.size());
        System.out.print("Percentage: ");
        System.out.printf("%.2f", ((float) bookedSeats.size() / (allCinemaPlaces.row * allCinemaPlaces.column) * 100));
        System.out.println("%");
        System.out.println("Current income: $" + calcTotalSoldTickets());
        System.out.println("Total income: $" + calculateTotalTicketPrice());
    }

    /**
     * calculates the total sum of all sold tickets.
     * @return int value with total sum
     */
    public static int calcTotalSoldTickets() {
        int total = 0;
        for (IntPair bookedSeat : bookedSeats) {
            total += returnTicketPriceForOneSeat(bookedSeat.row);
        }
        return total;
    }

    /**
     * book a Seat from user input and return the coordinates from the booked seat
     * catches if the booked seat is already booked or the input is out of bound
     */
    public static IntPair bookSeat() {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter a row number:");
            int row = sc.nextInt();
            System.out.println("Enter a seat number in that row:");
            int column = sc.nextInt();
            if (isBooked(row, column)) {
                System.out.println("That ticket has already been purchased!");
                continue;
            }
            if (row > allCinemaPlaces.row || column > allCinemaPlaces.column) {
                System.out.println("Wrong input!");
            } else {
                return new IntPair(row, column);
            }
        }
    }

    /**
     * creates a cinema from user input and returns the number of rows and columns
     */
    public static IntPair createCinema() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int zeilen = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int spalten = sc.nextInt();
        return new IntPair(zeilen, spalten);
    }

    /**
     * checks if a seat is already booked and listed in bookedSeats
     * @param a row 
     * @param b column 
     * @return true if seat is alreay booked. false if not. 
     */
    public static boolean isBooked(int a, int b) {
        for (IntPair bookedSeat : bookedSeats) {
            if (bookedSeat.row == a && bookedSeat.column == b) {
                return true;
            }
        }
        return false;
    }

    /**
     * prints all cinema places on console. Places that are booked are marked with an B otherwise S. 
     */
    public static void printCinemaPlaces() {
        int x = allCinemaPlaces.row;
        int y = allCinemaPlaces.column;

        System.out.println("Cinema:");
        for (int i = 0; i <= x; i++) { // Zeilen
            if (i == 0) { // wenn i == 0 dann bitte die Spalten Nummer
                System.out.print("  ");
                for (int p = 1; p <= y; p++) {
                    System.out.print(p + " ");
                }
            } else {
                System.out.print(i + " ");
                for (int h = 0; h < y; h++) { // Spalten
                    if (isBooked(i, h + 1)) {
                        //if (i == a && h + 1 == b) {
                        System.out.print("B ");
                    } else {
                        System.out.print("S ");
                    }
                }
            }
            System.out.println();
        }
    }

    /**
     * prints the formatted ticktprice of the current (last in the list) seat
     */
    public static void printTicketPrice() {
        int row = bookedSeats.get(bookedSeats.size() - 1).row;
        System.out.printf("Ticket Price: $%d", returnTicketPriceForOneSeat(row));
        System.out.println();
    }

    /**
     * returns ticket price based on the row of the seat
     * if it is a cinema with over 60 seats and the seat is in the back, ticket price will be $8 otherwise $10
     * @param row the row
     * @return ticketprice
     */
    public static int returnTicketPriceForOneSeat(int row) {
        int ticketPrice = 10;
        int x = allCinemaPlaces.row;
        int y = allCinemaPlaces.column;
        // calculate sum of seats
        if ((x * y) > 60 && row > (x / 2)) {
            ticketPrice = 8;
        }
        return ticketPrice;
    }

    /**
     * returns the total Income if all seats in the cinema would be sold.
     * @return sum of all prices for all seats in consideration of size of the cinema.
     */
    public static int calculateTotalTicketPrice() {
        int ticketPrice;
        int x = allCinemaPlaces.row;
        int y = allCinemaPlaces.column;
        // calculate sum of seats
        int sumOfSeats = x * y;
        if (sumOfSeats <= 60) {
            ticketPrice = 10 * sumOfSeats;
        } else {
            int half = x / 2;
            int frontrow = half * 10 * y;
            int backrow = (x - half) * 8 * y;
            ticketPrice = frontrow + backrow;
        }
        return ticketPrice;
    }
}

/**
 * a basic datatype to store row and column
 */
class IntPair {
    int row;
    int column;
    IntPair(int row, int column) {this.row=row;this.column=column;}
}