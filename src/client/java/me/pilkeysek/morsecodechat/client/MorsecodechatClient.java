package me.pilkeysek.morsecodechat.client;

import me.pilkeysek.morsecodechat.client.command.MorseCommand;
import me.pilkeysek.morsecodechat.client.command.MorsetranslateCommand;
import me.pilkeysek.morsecodechat.client.util.SimpleConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

public class MorsecodechatClient implements ClientModInitializer {

    public static Logger LOGGER;
    public static final String NAMESPACE = "MorseCodeChat";
    // Change the version accordingly, also in the gradle.properties
    public static final String VERSION = "0.1.3";

    SimpleConfig CONFIG = SimpleConfig.of("morsecodechat").provider( this::configProvider ).request();
    private String configProvider(String filename) { return "# MorseCodeChat config\nshort=.\nlong=-\ncharnotfound=<?>\nspace= / \nmorsecommandname=morse\ntranslatecommandname=translate"; }
    public final String CONFIG_SHORT = CONFIG.getOrDefault("short", ".");
    public final String CONFIG_LONG = CONFIG.getOrDefault("long", "-");
    public final String CONFIG_CHARNOTFOUND = CONFIG.getOrDefault("charnotfound", "<?>");
    public final String CONFIG_SPACE = CONFIG.getOrDefault("space", " / ");
    public final String CONFIG_MORSECOMMANDNAME = CONFIG.getOrDefault("morsecommandname", "morse");
    public final String CONFIG_TRANSLATECOMMANDNAME = CONFIG.getOrDefault("translatecommandname", "translate");

    private static KeyBinding toggleTranslationKeybinding;

    public static boolean translationEnabled = true;
    public static boolean skipNextMessage = false;

    @Override
    public void onInitializeClient() {
        LOGGER = LogManager.getLogger(NAMESPACE);

        LOGGER.info("MorseCodeChat v" + VERSION + " loaded!");

        toggleTranslationKeybinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                        "key.morsecodechat.toggleTranslation",
                        InputUtil.Type.KEYSYM,
                        GLFW.GLFW_KEY_Z,
                        "category.morsecodechat.main"
                ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while(toggleTranslationKeybinding.wasPressed()) {
                translationEnabled = !translationEnabled; // Toggle

                assert MinecraftClient.getInstance().player != null;

                if(translationEnabled) MinecraftClient.getInstance().player.sendMessage(Text.translatable("chat.morsecodechat.toggleMessage").formatted(Formatting.GREEN).append(Text.translatable("chat.morsecodechat.enabled").formatted(Formatting.AQUA)));
                else MinecraftClient.getInstance().player.sendMessage(Text.translatable("chat.morsecodechat.toggleMessage").formatted(Formatting.GREEN).append(Text.translatable("chat.morsecodechat.disabled").formatted(Formatting.RED)));
            }
        });

            ClientCommandRegistrationCallback.EVENT.register(((dispatcher, registryAccess) -> {
                MorseCommand.register(dispatcher);
                MorsetranslateCommand.register(dispatcher);
            }));
    }
}
