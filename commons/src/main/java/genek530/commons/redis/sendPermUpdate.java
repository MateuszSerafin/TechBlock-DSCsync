package genek530.commons.redis;

import java.util.UUID;

public class sendPermUpdate {
    private UUID mcUUID;
    private String permission;

    public sendPermUpdate(UUID mcUUID, String permission){
        this.mcUUID = mcUUID;
        this.permission = permission;
    }

    public UUID getMcUUID() {
        return mcUUID;
    }

    public String getPermission() {
        return permission;
    }
}
