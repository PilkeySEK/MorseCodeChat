package me.pilkeysek.morsecodechat.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import me.pilkeysek.morsecodechat.client.MorsecodechatClient;
import me.pilkeysek.morsecodechat.client.util.Util;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ChatHud.class)
public class ChatHudMixin {
    @Inject(method = "addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V", at = @At("HEAD"))
    void injected2(Text message, MessageSignatureData signatureData, MessageIndicator indicator, CallbackInfo ci, @Local(argsOnly = true) LocalRef<Text> refMessage) {
        if(!MorsecodechatClient.translationEnabled) return;
        String translatedString = Util.morseToString(refMessage.get().getString());
        if(translatedString.isEmpty()) return; // If the message doesn't contain any morse code
        Formatting color = Formatting.AQUA;
        refMessage.set(message.copy().append(Text.translatable("symbol.morsecodechat.translation").formatted(color, Formatting.ITALIC)).append(Text.literal(translatedString).formatted(color, Formatting.ITALIC)));
    }
}
