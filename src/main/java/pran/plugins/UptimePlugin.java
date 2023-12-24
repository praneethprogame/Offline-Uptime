package pran.plugins;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public class UptimePlugin extends JavaPlugin {
    private long startTime;

    @Override
    public void onEnable() {
        System.out.println("Starting Uptime record");
        if (getConfig().contains("startTime")) {
            startTime = getConfig().getLong("startTime");
        } else {
            startTime = System.currentTimeMillis();
            getConfig().set("startTime", startTime);
            saveConfig();
        }

        if (previousLogFileExists()) {
            long previousUptime = readPreviousUptime();
            sendUptimeMessage(previousUptime);
        }
    }

    @Override
    public void onDisable() {

        System.out.println("Stoping Uptime record");
        System.out.println("Writing to Log file");
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

            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(folder, "uptime.log")));
            writer.write(formatUptime(uptime));
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
