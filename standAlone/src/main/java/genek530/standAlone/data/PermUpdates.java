package genek530.standAlone.data;

import java.util.HashMap;

//tldr is when you update permission from mc -> it updates on dsc and then it receives it information creating a undesired behaviour
//when you add permission it should be temporarly skiped for partiuclar person
public class PermUpdates {
    public static HashMap<Long, Long> addSkip = new HashMap<>();
    public static HashMap<Long, Long> remSkip = new HashMap<>();

}
