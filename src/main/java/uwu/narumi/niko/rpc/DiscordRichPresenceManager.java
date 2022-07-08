package uwu.narumi.niko.rpc;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.minecraft.client.Minecraft;

public class DiscordRichPresenceManager implements ReadyCallback {

  private static final Minecraft mc = Minecraft.getMinecraft();
  DiscordRichPresence richPresence;

  public DiscordRichPresenceManager() {
    richPresence = new DiscordRichPresence
        .Builder("https://github.com/hakunyannn/meow")
        .setBigImage("hakunya", "meow")
        .setSmallImage("github", "https://github.com/hakunyannn/meow")
        .setDetails("Loading meow...")
        .setStartTimestamps(System.currentTimeMillis())
        .build();

    init();
    startTask();
    DiscordRPC.discordUpdatePresence(richPresence);
  }

  @Override
  public void apply(DiscordUser discordUser) {
    System.out.println("Initialized DiscordRichPresence API.");
  }

  private void init() {
    DiscordEventHandlers handlers = new DiscordEventHandlers.Builder()
        .setReadyEventHandler((user) ->
            System.out.printf("Connected to %s#%s (%s)%n", user.username, user.discriminator,
                user.userId)).build();

    DiscordRPC.discordInitialize("994993917680418856", handlers, true);
  }

  public void startTask() {
    Executors.newSingleThreadScheduledExecutor()
        .scheduleWithFixedDelay(() -> {
          richPresence.details =
              mc.thePlayer == null ? "nya" : "Nick: " + mc.session.getUsername();
          richPresence.state = mc.getCurrentServerData() == null ? "Offline"
              : "Server: " + mc.getCurrentServerData().serverIP;
          DiscordRPC.discordUpdatePresence(richPresence);
        }, 10, 10, TimeUnit.SECONDS);
  }

  public DiscordRichPresence getRichPresence() {
    return richPresence;
  }
}