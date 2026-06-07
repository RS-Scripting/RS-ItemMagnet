package com.rsscripting.itemmagnet.utils;

import com.rsscripting.itemmagnet.RSItemMagnet;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class GitHubUpdateChecker {

    /*
    |--------------------------------------------------------------------------
    | UPDATE STATUS
    |--------------------------------------------------------------------------
    */

    private static String latestVersion = null;

    private static String updateStatus =
            "Checking...";

    /*
    |--------------------------------------------------------------------------
    | CHECK FOR UPDATES
    |--------------------------------------------------------------------------
    */

    public static void checkForUpdates() {

        Bukkit.getScheduler().runTaskAsynchronously(

                RSItemMagnet.getInstance(),

                () -> {

                    try {

                        String apiURL =
                                "https://raw.githubusercontent.com/"
                                        + RSConstants.GITHUB_USER
                                        + "/"
                                        + RSConstants.GITHUB_REPOSITORY
                                        + "/master/pom.xml";

                        HttpURLConnection connection =
                                (HttpURLConnection)
                                        URI.create(apiURL)
                                                .toURL()
                                                .openConnection();

                        connection.setRequestMethod("GET");

                        BufferedReader reader =
                                new BufferedReader(
                                        new InputStreamReader(
                                                connection.getInputStream()
                                        )
                                );

                        StringBuilder response =
                                new StringBuilder();

                        String line;

                        while ((line = reader.readLine()) != null) {

                            response.append(line);

                        }

                        reader.close();

                        String pom =
                                response.toString();

                        latestVersion =
                                pom.split("<version>")[1]
                                        .split("</version>")[0]
                                        .trim();

                        String currentVersion =
                                VersionUtils.getVersion();

                        /*
                        |--------------------------------------------------------------------------
                        | UPDATE AVAILABLE
                        |--------------------------------------------------------------------------
                        */

                        if (!latestVersion.equalsIgnoreCase(
                                currentVersion
                        )) {

                            updateStatus =
                                    "Update Available";

                            Bukkit.getConsoleSender().sendMessage(

                                    RSConstants.PREFIX
                                            + "Update available: "
                                            + latestVersion
                                            + " (Current: "
                                            + currentVersion
                                            + ")"

                            );

                        }

                        /*
                        |--------------------------------------------------------------------------
                        | UP TO DATE
                        |--------------------------------------------------------------------------
                        */

                        else {

                            updateStatus =
                                    "Up To Date";

                            Bukkit.getConsoleSender().sendMessage(

                                    RSConstants.PREFIX
                                            + "RS-ItemMagnet is up to date."

                            );

                        }

                    }

                    catch (Exception exception) {

                        updateStatus =
                                "Unable To Check";

                        Bukkit.getConsoleSender().sendMessage(

                                RSConstants.PREFIX
                                        + "Failed to check for updates."

                        );

                    }

                }

        );

    }

    /*
    |--------------------------------------------------------------------------
    | GET LATEST VERSION
    |--------------------------------------------------------------------------
    */

    public static String getLatestVersion() {

        return latestVersion;

    }

    /*
    |--------------------------------------------------------------------------
    | GET UPDATE STATUS
    |--------------------------------------------------------------------------
    */

    public static String getUpdateStatus() {

        return updateStatus;

    }

}