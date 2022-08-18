package pl.ololjvNek.skycastle.data;

import lombok.Data;

import java.util.UUID;

@Data
public class Disco {

    private UUID uuid;
    private boolean smooth,random,speed;
    private int smoothInt;

    public Disco(UUID uuid){
        this.uuid = uuid;
        smooth = false;
        random = false;
        speed = false;
        smoothInt = 0;
    }

    public void addSmoothInt(int index){
        smoothInt += index;
    }
}
