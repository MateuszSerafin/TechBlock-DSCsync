package genek530.commons.internal;

public class authUser {
    //uzywane tylko do synchronizacji usera nie uzywac do niczego innego

    long discordID;
    String mcNick;

    String password;

    public authUser(long discordID, String mcnick, String password){
        this.discordID = discordID;
        this.mcNick = mcnick;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public String getMcNick() {
        return mcNick;
    }

    public void setMcNick(String mcNick) {
        this.mcNick = mcNick;
    }

    public long getDiscordID() {
        return discordID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean matchOtherpass(String otherpass){
        return this.password.equals(otherpass);
    }

}
