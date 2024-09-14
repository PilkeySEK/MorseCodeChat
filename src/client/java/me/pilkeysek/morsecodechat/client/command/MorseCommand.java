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
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class MorseCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {

        dispatcher.register(
                ClientCommandManager.literal(new MorsecodechatClient().CONFIG_MORSECOMMANDNAME)
                        .then(
                                ClientCommandManager.argument("message", StringArgumentType.greedyString())
                                        .executes(context -> {
                                            String message = StringArgumentType.getString(context, "message");
                                            String morseMessage = Util.stringToMorse(message);

                                            if(morseMessage.length() > 256) {
                                                context.getSource().sendFeedback(
                                                        Text.translatable("chat.morsecodechat.morseTooLong")
                                                                .formatted(Formatting.RED)
                                                                .append(Text.translatable("chat.morsecodechat.copyInstead")
                                                                        .setStyle(
                                                                                Style.EMPTY.withClickEvent(
                                                                                        new ClickEvent(
                                                                                                ClickEvent.Action.COPY_TO_CLIPBOARD,
                                                                                                morseMessage
                                                                                        )
                                                                                ).withHoverEvent(
                                                                                        new HoverEvent(
                                                                                                HoverEvent.Action.SHOW_TEXT,
                                                                                                Text.translatable("chat.morsecodechat.copyTheMorse")
                                                                                                        .formatted(Formatting.DARK_GREEN)
                                                                                        )
                                                                                )
                                                                        )

                                                                ));
                                                return 0;
                                            }

                                            ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
                                            assert networkHandler != null;

                                            networkHandler.sendChatMessage(morseMessage);

                                            return 1;
                                        })
                        )
        );
    }
}
