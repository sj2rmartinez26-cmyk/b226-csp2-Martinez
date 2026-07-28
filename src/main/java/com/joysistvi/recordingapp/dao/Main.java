package com.joysistvi.recordingapp.dao;

import com.joysistvi.recordingapp.config.DbConnection;
import com.joysistvi.recordingapp.controller.*;
import com.joysistvi.recordingapp.model.User;
import com.joysistvi.recordingapp.repository.*;
import com.joysistvi.recordingapp.repository.impl.*;
import com.joysistvi.recordingapp.service.*;
import com.joysistvi.recordingapp.view.*;

import java.util.Scanner;

public class Main {

    // Controllers
    private static UserController userController;
    private static ArtistController artistController;
    private static AlbumController albumController;
    private static SongController songController;
    private static PlaylistController playlistController;
    private static PlaylistSongsController playlistSongsController;

    // Views
    private static ArtistView artistView;
    private static AlbumView albumView;
    private static SongView songView;
    private static PlaylistView playlistView;
    private static PlaylistSongsView playlistSongsView;

    // Session State
    private static User currentUser = null;

    public static void main(String[] args) {
        initializeDependencies();
        Scanner scanner = new Scanner(System.in);
        boolean appRunning = true;

        System.out.println("=========================================");
        System.out.println("   WELCOME TO RECORDING STUDIO APP   ");
        System.out.println("=========================================");

        while (appRunning) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit Application");

            System.out.print("Select an option (1-3): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleLogin(scanner);
                    if (currentUser != null) {
                        // Check role after login
                        if ("ADMIN".equalsIgnoreCase(currentUser.getRole())) {
                            runAdminDashboard(scanner);
                        } else {
                            runUserDashboard(scanner);
                        }
                    }
                    break;
                case "2":
                    handleRegistration(scanner);
                    break;
                case "3":
                    appRunning = false;
                    System.out.println("\nThank you for using Recording Studio App. Goodbye!");
                    break;


                default:
                    System.out.println("[!] Invalid option. Please enter 1, 2, or 3.");
            }
        }
        scanner.close();
    }

    // ==========================================
    // INITIALIZATION & DEPENDENCY INJECTION
    // ==========================================
    private static void initializeDependencies() {
        DbConnection dbConn = new DbConnection();

        // Repositories
        UserRepository userRepo = new UserRepositoryImpl(dbConn);
        ArtistRepository artistRepo = new ArtistRepositoryImpl(dbConn);
        AlbumRepository albumRepo = new AlbumRepositoryImpl(dbConn, artistRepo);
        SongRepository songRepo = new SongRepositoryImpl(dbConn);
        PlaylistRepository playlistRepo = new PlaylistRepositoryImpl(dbConn);
        PlaylistSongsRepository psRepo = new PlaylistSongsRepositoryImpl(dbConn);

        // Services
        UserService userService = new UserService(userRepo);
        ArtistService artistService = new ArtistService(artistRepo);
        AlbumService albumService = new AlbumService(albumRepo, artistRepo);
        SongService songService = new SongService(songRepo);
        PlaylistService playlistService = new PlaylistService(playlistRepo, userRepo);
        PlaylistSongsService psService = new PlaylistSongsService(psRepo, playlistRepo, songRepo);

        // Controllers
        userController = new UserController(userService);
        artistController = new ArtistController(artistService);
        albumController = new AlbumController(albumService);
        songController = new SongController(songService);
        playlistController = new PlaylistController(playlistService);
        playlistSongsController = new PlaylistSongsController(psService);

        // Views
        artistView = new ArtistView();
        albumView = new AlbumView();
        songView = new SongView();
        playlistView = new PlaylistView();
        playlistSongsView = new PlaylistSongsView();
    }

    // ==========================================
    // AUTHENTICATION FLOW
    // ==========================================
    private static void handleLogin(Scanner scanner) {
        System.out.println("\n--- USER LOGIN ---");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        if (userController.login(username, password)) {
            // Retrieve session user
            for (User u : userController.getAll()) {
                if (u.getUsername().equalsIgnoreCase(username)) {
                    currentUser = u;
                    break;
                }
            }
            System.out.println("[✓] Login Successful! Welcome, " + username + ".");
        } else {
            System.out.println("[!] Invalid username or password.");
        }
    }

    private static void handleRegistration(Scanner scanner) {
        System.out.println("\n--- USER REGISTRATION ---");
        System.out.print("Choose Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Choose Password: ");
        String password = scanner.nextLine().trim();

        if (userController.register(username, password)) {
            System.out.println("[✓] Account created successfully! You can now login.");
        } else {
            System.out.println("[!] Registration failed. Username may already exist.");
        }
    }

    // ==========================================
    // USER DASHBOARD
    // ==========================================
    private static void runUserDashboard(Scanner scanner) {
        boolean inUserMenu = true;

        while (inUserMenu) {
            System.out.println("\n===== USER DASHBOARD =====");
            System.out.println("1. Browse Songs");
            System.out.println("2. Browse Albums");
            System.out.println("3. Browse Artists");
            System.out.println("4. My Playlists");
            System.out.println("0. Logout");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    songView.displaySongs(songController.getAllSongs());
                    break;
                case "2":
                    albumView.displayAlbums(albumController.getAll());
                    break;
                case "3":
                    artistView.displayArtists(artistController.getAll());
                    break;
                case "4":
                    handleMyPlaylistsMenu(scanner);
                    break;
                case "0":
                    currentUser = null;
                    inUserMenu = false;
                    System.out.println("[✓] Logged out successfully.");
                    break;
                default:
                    System.out.println("[!] Invalid choice. Please try again.");
            }
        }
    }

    private static void handleMyPlaylistsMenu(Scanner scanner) {
        boolean inPlaylistMenu = true;

        while (inPlaylistMenu) {
            System.out.println("\n--- MY PLAYLISTS MENU ---");
            System.out.println("1. View My Playlists");
            System.out.println("2. View Songs in a Playlist");
            System.out.println("3. Create New Playlist");
            System.out.println("4. Manage Playlist (Add/Remove Songs)");
            System.out.println("5. Delete Playlist");
            System.out.println("0. Back to User Dashboard");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    playlistView.displayPlaylists(
                            playlistController.getAll().stream()
                                    .filter(p -> p.getUserId() == currentUser.getId())
                                    .toList()
                    );
                    break;
                case "2":
                    handleViewPlaylistSongs(scanner);
                    break;
                case "3":
                    System.out.print("Enter Playlist Name: ");
                    String pName = scanner.nextLine().trim();
                    if (playlistController.create(pName, currentUser.getId())) {
                        System.out.println("[✓] Playlist created successfully!");
                    } else {
                        System.out.println("[!] Failed to create playlist.");
                    }
                    break;
                case "4":
                    handlePlaylistManagement(scanner);
                    break;
                case "5":
                    System.out.print("Enter ID of Playlist to delete: ");
                    try {
                        int pId = Integer.parseInt(scanner.nextLine().trim());
                        if (playlistController.delete(pId)) {
                            System.out.println("[✓] Playlist deleted successfully.");
                        } else {
                            System.out.println("[!] Failed to delete playlist.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("[!] Invalid ID format.");
                    }
                    break;
                case "0":
                    inPlaylistMenu = false;
                    break;
                default:
                    System.out.println("[!] Invalid choice. Please try again.");
            }
        }
    }

    private static void handleViewPlaylistSongs(Scanner scanner) {
        System.out.println("\n--- YOUR PLAYLISTS ---");
        var userPlaylists = playlistController.getAll().stream()
                .filter(p -> p.getUserId() == currentUser.getId())
                .toList();

        if (userPlaylists.isEmpty()) {
            System.out.println("[!] You don't have any playlists yet.");
            return;
        }

        playlistView.displayPlaylists(userPlaylists);

        System.out.print("Enter Playlist ID to view its songs: ");
        try {
            int playlistId = Integer.parseInt(scanner.nextLine().trim());

            boolean ownsPlaylist = userPlaylists.stream().anyMatch(p -> p.getId() == playlistId);
            if (!ownsPlaylist) {
                System.out.println("[!] Invalid Playlist ID or you do not own this playlist.");
                return;
            }

            var songs = playlistSongsController.getPlaylistSongs(playlistId);
            playlistSongsView.displayPlaylistSongs(playlistId, songs);

        } catch (NumberFormatException e) {
            System.out.println("[!] Invalid input. Please enter a numeric Playlist ID.");
        }
    }

    private static void handlePlaylistManagement(Scanner scanner) {
        System.out.print("Enter Playlist ID to manage: ");
        try {
            int pId = Integer.parseInt(scanner.nextLine().trim());

            // View current songs in playlist
            playlistSongsView.displayPlaylistSongs(pId, playlistSongsController.getPlaylistSongs(pId));

            System.out.println("\nOptions: (1) Add Song  (2) Remove Song  (3) Back");
            System.out.print("Choice: ");
            String subChoice = scanner.nextLine().trim();

            if (subChoice.equals("1")) {
                songView.displaySongs(songController.getAllSongs());
                System.out.print("Enter Song ID to add: ");
                int songId = Integer.parseInt(scanner.nextLine().trim());
                if (playlistSongsController.addSongToPlaylist(pId, songId)) {
                    System.out.println("[✓] Song added to playlist!");
                } else {
                    System.out.println("[!] Failed to add song. Ensure IDs exist and song isn't already added.");
                }
            } else if (subChoice.equals("2")) {
                System.out.print("Enter Song ID to remove: ");
                int songId = Integer.parseInt(scanner.nextLine().trim());
                if (playlistSongsController.removeSongFromPlaylist(pId, songId)) {
                    System.out.println("[✓] Song removed from playlist.");
                } else {
                    System.out.println("[!] Failed to remove song.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("[!] Invalid ID input.");
        }
    }

    // ==========================================
    // ADMIN DASHBOARD
    // ==========================================
    private static void runAdminDashboard(Scanner scanner) {
        boolean inAdminMenu = true;

        while (inAdminMenu) {
            System.out.println("\n=========================================");
            System.out.println("             ADMIN DASHBOARD             ");
            System.out.println("=========================================");
            System.out.println("1. Manage Artists");
            System.out.println("2. Manage Albums");
            System.out.println("3. Manage Songs");
            System.out.println("4. Manage Users"); // <--- NEW OPTION
            System.out.println("5. Exit Admin Dashboard");
            System.out.print("Choose an option (1-5): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    manageArtists(scanner);
                    break;
                case "2":
                    manageAlbums(scanner);
                    break;
                case "3":
                    manageSongs(scanner);
                    break;
                case "4":
                    manageUsers(scanner); // <--- NEW CASE
                    break;
                case "5":
                    inAdminMenu = false;
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("[!] Invalid option. Select 1-5.");
            }
        }
    }
    // --- USER MANAGEMENT ---
    private static void manageUsers(Scanner scanner) {
        System.out.println("\n--- MANAGE USERS ---");

        // Display all current users
        var users = userController.getAll();
        if (users.isEmpty()) {
            System.out.println("[!] No users found.");
        } else {
            System.out.println("+----+--------------------------------+--------------------+");
            System.out.println("| ID | Username                       | Role               |");
            System.out.println("+----+--------------------------------+--------------------+");
            for (User u : users) {
                System.out.printf("| %-2d | %-30s | %-18s |\n", u.getId(), u.getUsername(), u.getRole());
            }
            System.out.println("+----+--------------------------------+--------------------+");
        }

        System.out.println("1. Add User | 2. Update User Role | 3. Delete User | 4. Back");
        System.out.print("Option: ");
        String opt = scanner.nextLine().trim();

        try {
            switch (opt) {
                case "1":
                    System.out.print("Enter Username: ");
                    String username = scanner.nextLine().trim();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine().trim();
                    System.out.print("Enter Role (ADMIN/USER): ");
                    String role = scanner.nextLine().trim().toUpperCase();

                    if (userController.register(username, password)) {
                        System.out.println("[✓] User added successfully.");
                    } else {
                        System.out.println("[!] Failed to add user.");
                    }
                    break;

                case "2":
                    System.out.print("Enter User ID to Update Role: ");
                    int updateId = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter New Role (ADMIN/USER): ");
                    String newRole = scanner.nextLine().trim().toUpperCase();

                    // Make sure your userController/userService has an update or updateRole method
                    if (userController.updateRole(updateId, newRole)) {
                        System.out.println("[✓] User role updated successfully.");
                    } else {
                        System.out.println("[!] Failed to update user role.");
                    }
                    break;

                case "3":
                    System.out.print("Enter User ID to Delete: ");
                    int delId = Integer.parseInt(scanner.nextLine().trim());

                    // Prevent admin from deleting themselves
                    if (currentUser != null && currentUser.getId() == delId) {
                        System.out.println("[!] You cannot delete your own account while logged in.");
                        break;
                    }

                    if (userController.delete(delId)) {
                        System.out.println("[✓] User deleted successfully.");
                    } else {
                        System.out.println("[!] Failed to delete user.");
                    }
                    break;

                case "4":
                    break;

                default:
                    System.out.println("[!] Invalid option.");
            }
        } catch (NumberFormatException e) {
            System.out.println("[!] Invalid numeric input.");
        }
    }

    // --- ARTIST MANAGEMENT ---
    private static void manageArtists(Scanner scanner) {
        System.out.println("\n--- MANAGE ARTISTS ---");
        artistView.displayArtists(artistController.getAll());
        System.out.println("1. Add Artist | 2. Update Artist | 3. Delete Artist | 4. Back");
        System.out.print("Option: ");
        String opt = scanner.nextLine().trim();

        try {
            switch (opt) {
                case "1":
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter Genre: ");
                    String genre = scanner.nextLine().trim();
                    if (artistController.create(name, genre)) System.out.println("[✓] Artist added.");
                    break;
                case "2":
                    System.out.print("Enter Artist ID to Update: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter New Name: ");
                    String newName = scanner.nextLine().trim();
                    System.out.print("Enter New Genre: ");
                    String newGenre = scanner.nextLine().trim();
                    if (artistController.update(id, newName, newGenre)) System.out.println("[✓] Artist updated.");
                    break;
                case "3":
                    System.out.print("Enter Artist ID to Delete: ");
                    int delId = Integer.parseInt(scanner.nextLine().trim());
                    if (artistController.delete(delId)) System.out.println("[✓] Artist deleted.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("[!] Invalid numeric input.");
        }
    }

    // --- ALBUM MANAGEMENT ---
    private static void manageAlbums(Scanner scanner) {
        System.out.println("\n--- MANAGE ALBUMS ---");
        albumView.displayAlbums(albumController.getAll());
        System.out.println("1. Add Album | 2. Update Album | 3. Delete Album | 4. Back");
        System.out.print("Option: ");
        String opt = scanner.nextLine().trim();

        try {
            switch (opt) {
                case "1":
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine().trim();
                    System.out.print("Enter Release Year: ");
                    int year = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter Artist ID: ");
                    int artistId = Integer.parseInt(scanner.nextLine().trim());
                    if (albumController.create(title, year, artistId)) System.out.println("[✓] Album added.");
                    break;
                case "2":
                    System.out.print("Enter Album ID to Update: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter New Title: ");
                    String newTitle = scanner.nextLine().trim();
                    System.out.print("Enter New Year: ");
                    int newYear = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter New Artist ID: ");
                    int newArtistId = Integer.parseInt(scanner.nextLine().trim());
                    if (albumController.update(id, newTitle, newYear, newArtistId)) System.out.println("[✓] Album updated.");
                    break;
                case "3":
                    System.out.print("Enter Album ID to Delete: ");
                    int delId = Integer.parseInt(scanner.nextLine().trim());
                    if (albumController.delete(delId)) System.out.println("[✓] Album deleted.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("[!] Invalid numeric input.");
        }
    }

    // --- SONG MANAGEMENT ---
    private static void manageSongs(Scanner scanner) {
        System.out.println("\n--- MANAGE SONGS ---");
        songView.displaySongs(songController.getAllSongs());
        System.out.println("1. Add Song | 2. Update Song | 3. Delete Song | 4. Back");
        System.out.print("Option: ");
        String opt = scanner.nextLine().trim();

        try {
            switch (opt) {
                case "1":
                    System.out.print("Enter Title: ");
                    String title = scanner.nextLine().trim();
                    System.out.print("Enter Length (mm:ss): ");
                    String len = scanner.nextLine().trim();
                    System.out.print("Enter Genre: ");
                    String genre = scanner.nextLine().trim();
                    System.out.print("Enter Album ID: ");
                    int albumId = Integer.parseInt(scanner.nextLine().trim());

                    // Check if the boolean returned is true
                    if (songController.createSong(title, len, genre, albumId)) {
                        System.out.println("[✓] Song added.");
                    } else {
                        System.out.println("[!] Failed to add song.");
                    }
                    break;
                case "2":
                    System.out.print("Enter Song ID to Update: ");
                    int id = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Enter New Title: ");
                    String newTitle = scanner.nextLine().trim();
                    System.out.print("Enter New Length: ");
                    String newLen = scanner.nextLine().trim();
                    System.out.print("Enter New Genre: ");
                    String newGenre = scanner.nextLine().trim();
                    System.out.print("Enter New Album ID: ");
                    int newAlbumId = Integer.parseInt(scanner.nextLine().trim());
                    if (songController.updateSong(id, newTitle, newLen, newGenre, newAlbumId)) System.out.println("[✓] Song updated.");
                    break;
                case "3":
                    System.out.print("Enter Song ID to Delete: ");
                    int delId = Integer.parseInt(scanner.nextLine().trim());
                    if (songController.deleteSong(delId)) System.out.println("[✓] Song deleted.");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("[!] Invalid numeric input.");
        }
    }
}