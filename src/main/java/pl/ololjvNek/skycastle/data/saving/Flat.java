package pl.ololjvNek.skycastle.data.saving;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.ololjvNek.skycastle.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Flat {

    private static File file;
    private static FileConfiguration configuration;

    public static void saveFlat(String name, HashMap<String, Object> toSave){
        File folder = new File(Main.getPlugin().getDataFolder(), "players");
        if(!folder.exists()){
            folder.mkdirs();
        }
        file = new File(Main.getPlugin().getDataFolder() + "/players", name + ".yml");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            configuration = YamlConfiguration.loadConfiguration(file);
        }else{
            configuration = YamlConfiguration.loadConfiguration(file);
        }
        for(String s : toSave.keySet()){
            configuration.set(s, toSave.get(s));
        }
        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<FileConfiguration> getPlayersFromFile(){
        List<FileConfiguration> fileConfigurations = new ArrayList<>();
        File folder = new File(Main.getPlugin().getDataFolder(), "players");
        if(folder.listFiles() == null){
            return new ArrayList<>();
        }
        for(File file : Objects.requireNonNull(folder.listFiles())){
            fileConfigurations.add(YamlConfiguration.loadConfiguration(file));
        }
        return fileConfigurations;
    }
}
