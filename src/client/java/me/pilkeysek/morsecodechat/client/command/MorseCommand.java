package me.pilkeysek.morsecodechat.client.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.pilkeysek.morsecodechat.client.MorsecodechatClient;
import me.pilkeysek.morsecodechat.client.util.Util;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;

public class MorseCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {

        dispatcher.register(
                ClientCommandManager.literal(new MorsecodechatClient().CONFIG_MORSECOMMANDNAME)
                        .then(
                                ClientCommandManager.argument("message", StringArgumentType.greedyString())
                                        .executes(context -> {
                                            ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
                                            assert networkHandler != null;

                                            networkHandler.sendChatMessage(Util.stringToMorse(StringArgumentType.getString(context, "message")));

                                            return 1;
                                        })
                        )
        );
    }
}
