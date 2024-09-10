package me.pilkeysek.morsecodechat.client.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import me.pilkeysek.morsecodechat.client.MorsecodechatClient;
import me.pilkeysek.morsecodechat.client.util.Util;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.UnaryOperator;

public class MorsetranslateCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {

        dispatcher.register(
                ClientCommandManager.literal(new MorsecodechatClient().CONFIG_TRANSLATECOMMANDNAME)
                        .then(
                                ClientCommandManager.argument("message", StringArgumentType.greedyString())
                                        .executes(context -> {
                                            ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
                                            assert networkHandler != null;

                                            networkHandler.sendChatMessage(Util.stringToMorse(StringArgumentType.getString(context, "message")));

                                            String morseText = Util.stringToMorse(StringArgumentType.getString(context, "message"));

                                            context.getSource().sendFeedback(
                                                    Text.literal(morseText)
                                                            .formatted(Formatting.AQUA)
                                                            .append(Text.literal(" [COPY]")
                                                                    .formatted(Formatting.BLUE)
                                                                    .setStyle(
                                                                            Style.EMPTY.withClickEvent(new ClickEvent(
                                                                                    ClickEvent.Action.COPY_TO_CLIPBOARD, morseText
                                                                            )))));

                                            return 1;
                                        })
                        )
        );
    }
}
