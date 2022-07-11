package uwu.narumi.niko.hook;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import org.lwjgl.opengl.GL11;
import uwu.narumi.niko.helper.ChatHelper;
//import uwu.narumi.niko.helper.OpenGlHelper;
import uwu.narumi.niko.helper.SystemHelper;
import uwu.narumi.niko.helper.TimeHelper;
import uwu.narumi.niko.holder.Holder;

public class GuiInGameHook extends GuiIngame {

  private static final Minecraft mc = Minecraft.getMinecraft();

  public GuiInGameHook(Minecraft mcIn) {
    super(mcIn);
  }

  @Override
  public void renderGameOverlay(float partialTicks) {
    super.renderGameOverlay(partialTicks);

    /*
    GL11.glScaled(2.0, 2.0, 2.0);
    mc.fontRendererObj.drawStringWithShadow("§7meow", 1, 1, 1);
    GL11.glScaled(0.5, 0.5, 0.5);
    */

    if (!mc.isSingleplayer() && !mc.gameSettings.showDebugInfo) {
      //int x = (int) mc.thePlayer.posX, y = (int) mc.thePlayer.posY, z = (int) mc.thePlayer.posZ;
      long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
      long lastPacketMS = TimeHelper.getCurrentTime() - Holder.getLastPacketMS();
      if (lastPacketMS > 1000 && Holder.getTPS() > 0.00) //idk xd, imagine doesn't know how to gix -0.00
      {
        Holder.setTPS(Holder.getTPS() - 0.01);
      }

      GL11.glScaled(2.0, 2.0, 2.0);
      mc.fontRendererObj.drawStringWithShadow("§7meow", 1, 1, 1);
      GL11.glScaled(0.5, 0.5, 0.5);

      mc.fontRendererObj.drawStringWithShadow(ChatHelper.fix(
          String.format("&fCPU Usage: %.2f",
              SystemHelper.getOsBean().getProcessCpuLoad() * 1000 / 10)
              + "&6%"), 5, 20, 0);
      mc.fontRendererObj.drawStringWithShadow(ChatHelper.fix(String
              .format("&fRAM Usage: %s/%s", SystemHelper.humanReadableByteCount(usedMemory, false),
                  SystemHelper.humanReadableByteCount(Runtime.getRuntime().totalMemory(), false))), 5,
          30, 0);
      mc.fontRendererObj
          .drawStringWithShadow(ChatHelper.fix("&fSession: " + mc.session.getUsername()), 5, 40,
              0);
      mc.fontRendererObj
          .drawStringWithShadow(ChatHelper.fix("&fServer: " + mc.getCurrentServerData().serverIP),
              5, 50, 0);

      if (mc.thePlayer.getClientBrand() != null) {
        String brand = mc.thePlayer.getClientBrand().contains("<- ") ?
            mc.thePlayer.getClientBrand().split(" ")[0] + " -> " + mc.thePlayer.getClientBrand()
                .split("<- ")[1] : mc.thePlayer.getClientBrand().split(" ")[0];
        mc.fontRendererObj.drawStringWithShadow(
            ChatHelper.fix("&fServer brand: " + brand), 5, 60, 0);
      }

      /*
      mc.fontRendererObj
          .drawStringWithShadow(ChatHelper.fix("&fFPS: &d" + Minecraft.debugFPS), 5, 60, 0);
      mc.fontRendererObj
          .drawStringWithShadow(ChatHelper.fix(String.format("&fX, Y, Z: &d%s, %s, %s", x, y, z)),
              5, 70, 0);
      */

      if (Holder.getTPS() != -1) {
        String tps = String.format((Holder.getTPS() > 15
            ? "&a%.2f" : (Holder.getTPS() > 10
            ? "&e%.2f" : (Holder.getTPS() > 5
            ? "&6%.2f" : "&c%.2f"))), Holder.getTPS());

        mc.fontRendererObj
            .drawStringWithShadow(ChatHelper.fix(String.format("&fTPS: %s", tps)), 5, 70, 0);
      }

      if (Holder.getLastPacketMS() != -1) {
        String packetMs = (lastPacketMS < 1000
            ? "&a" + lastPacketMS : (lastPacketMS < 7000
            ? "&e" + lastPacketMS : (lastPacketMS < 15000
            ? "&6" + lastPacketMS : "&c" + lastPacketMS)));

        mc.fontRendererObj
            .drawStringWithShadow(ChatHelper.fix(
                lastPacketMS > 30500 ? "&fLast packet: &6Broken pipe"
                    : String.format("&fLast packet: %s&7ms &fago", packetMs)), 5, 80, 0);

      }
    }
  }
}
