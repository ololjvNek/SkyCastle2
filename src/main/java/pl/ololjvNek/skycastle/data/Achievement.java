package pl.ololjvNek.skycastle.data;

import lombok.Data;
import org.bukkit.Material;

import java.util.List;

@Data
public class Achievement {

    private String name, guiName;
    private int countNeed, guiAmount;
    private Material guiItem;
    private byte guiItemByte;
    private List<String> guiLore;
    
}
