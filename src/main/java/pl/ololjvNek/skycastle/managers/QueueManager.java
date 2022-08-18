package pl.ololjvNek.skycastle.managers;

import lombok.Getter;
import lombok.Setter;

public class QueueManager {

    @Getter @Setter private static int playerJoin;

    public static void addPlayerJoin(int index){
        playerJoin += index;
    }

}
