package pl.ololjvNek.skycastle.data;

import lombok.Data;
import pl.ololjvNek.skycastle.interfaces.EventType;

import java.util.List;

@Data
public class Event {

    private SkyCastle skyCastle;
    private List<String> events;
    private boolean enabled;

    public Event(SkyCastle skyCastle, List<String> events){
        this.skyCastle = skyCastle;
        this.events = events;
        this.enabled = false;
    }

    public boolean hasEvent(String name){
        return events.contains(name);
    }
}
