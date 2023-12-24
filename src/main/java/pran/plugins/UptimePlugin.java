package pran.plugins;

import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.*;

public class UptimePlugin extends JavaPlugin {
    private long startTime;

    @Override
    public void onEnable() {
        startTime = System.currentTimeMillis();

        if (previousLogFileExists()) {
            long previousUptime = readPreviousUptime();
            sendUptimeMessage(previousUptime);
        }
    }

    @Override
    public void onDisable() {
        long uptime = System.currentTimeMillis() - startTime;
        writeUptimeToLogFile(uptime);
    }

    private boolean previousLogFileExists() {
        File folder = getDataFolder();
        File logFile = new File(folder, "uptime.log");
        return logFile.exists();
    }

    private long readPreviousUptime() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(getDataFolder(), "uptime.log")));
            String uptimeString = reader.readLine();
            reader.close();

            return Long.parseLong(uptimeString);
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void writeUptimeToLogFile(long uptime) {
    try {
        File folder = getDataFolder();
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Create a SimpleDateFormat object with the desired date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(new Date());

        // Create a SimpleDateFormat object with the desired time format
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedTime = timeFormat.format(new Date());

        // Combine the formatted date and time for the log entry
        String logEntry = String.format("Server uptime for %s (%s):\n", formattedDate, formattedTime);
        logEntry += String.format("              %d hours\n", uptime / 3600000); // Convert milliseconds to hours
        logEntry += String.format("              %d minutes\n", (uptime % 3600000) / 60000); // Convert remaining milliseconds to minutes
        logEntry += String.format("              %d seconds\n", (uptime % 60000) / 1000); // Convert remaining milliseconds to seconds

        // Read existing content from the log file
        StringBuilder existingContent = new StringBuilder();
        if (folder.exists()) {
            File logFile = new File(folder, "uptime.log");
            if (logFile.exists()) {
                // Read existing content from the log file
                BufferedReader reader = new BufferedReader(new FileReader(logFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    existingContent.append(line).append("\n");
                }
                reader.close();
            }
        }

        // Write the new log entry followed by existing content
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(folder, "uptime.log")));
        writer.write(logEntry);
        writer.write(existingContent.toString());
        writer.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    private void sendUptimeMessage(long uptime) {
        getLogger().info("Previous server uptime: " + formatUptime(uptime));
    }

    private String formatUptime(long uptime) {
        long seconds = uptime / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        return String.format("%02d:%02d:%02d", hours, minutes % 60, seconds % 60);
    }
}
