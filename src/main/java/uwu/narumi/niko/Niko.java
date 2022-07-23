package uwu.narumi.niko;

import java.io.IOException;
import org.lwjgl.opengl.Display;
import uwu.narumi.niko.command.CommandManager;
import uwu.narumi.niko.command.impl.ExploitCommand;
import uwu.narumi.niko.command.impl.HelpCommand;
import uwu.narumi.niko.command.impl.OnlineCommand;
import uwu.narumi.niko.exploit.ExploitManager;
import uwu.narumi.niko.exploit.impl.creative.AnvilExploit;
import uwu.narumi.niko.exploit.impl.flood.AttackExploit;
import uwu.narumi.niko.exploit.impl.nbt.BookExploit;
import uwu.narumi.niko.exploit.impl.nbt.ExploitFixerExploit;
import uwu.narumi.niko.exploit.impl.nbt.OnePacketExploit;
import uwu.narumi.niko.exploit.impl.other.FaweExploit;
import uwu.narumi.niko.exploit.impl.other.SpamExploit;
import uwu.narumi.niko.helper.NetHelper;

public enum Niko {
  INSTANCE;

  private final CommandManager commandManager;
  private final ExploitManager exploitManager;

  Niko() {
    System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "false");

    commandManager = new CommandManager(
        new ExploitCommand(),
        new HelpCommand(),
        new OnlineCommand()
    );

    exploitManager = new ExploitManager(
        new AnvilExploit(),
        new AttackExploit(),
        new BookExploit(),
        new SpamExploit(),
        new FaweExploit(),
        new ExploitFixerExploit(),
        new OnePacketExploit()
    );
  }

  public CommandManager getCommandManager() {
    return commandManager;
  }

  public ExploitManager getExploitManager() {
    return exploitManager;
  }
}
