package com.joysistvi.recordingapp.dao;

import com.joysistvi.recordingapp.config.DbConnection;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        DbConnection dbConnection = new DbConnection();
        SongDao songDao = new SongDao(dbConnection);
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            System.out.println("\n--- Song Management Menu ---");
            System.out.println("1. Add Song");
            System.out.println("2. Update Song");
            System.out.println("3. Delete Song");
            System.out.println("4. View All Songs");
            System.out.println("5. Exit");
            System.out.print("Enter choice (1-5): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();

                    System.out.print("Enter length (e.g. 03:45): ");
                    String length = scanner.nextLine();

                    System.out.print("Enter genre: ");
                    String genre = scanner.nextLine();

                    System.out.print("Enter album ID: ");
                    int albumId = Integer.parseInt(scanner.nextLine());

                    songDao.createSong(title, length, genre, albumId);
                    break;

                case "2":
                    System.out.print("Enter ID of the song to update: ");
                    int updateId = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter new title: ");
                    String newTitle = scanner.nextLine();

                    System.out.print("Enter new length: ");
                    String newLength = scanner.nextLine();

                    System.out.print("Enter new genre: ");
                    String newGenre = scanner.nextLine();

                    System.out.print("Enter new album ID: ");
                    int newAlbumId = Integer.parseInt(scanner.nextLine());

                    songDao.updateSong(updateId, newTitle, newLength, newGenre, newAlbumId);
                    break;

                case "3":
                    System.out.print("Enter ID of the song to delete: ");
                    int deleteId = Integer.parseInt(scanner.nextLine());

                    songDao.deleteSong(deleteId);
                    break;

                case "4":
                    songDao.readSong();
                    break;

                case "5":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please enter 1, 2, 3, 4, or 5.");
                    break;
            }
        }

        scanner.close();
    }
}