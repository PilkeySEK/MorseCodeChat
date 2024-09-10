package me.pilkeysek.morsecodechat.client.util;

import me.pilkeysek.morsecodechat.client.MorsecodechatClient;

import java.util.Arrays;

public class Util {

    private static final char[] english = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
            ',', '.', '?' };

    private static final String[] morse = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..",
            ".---", "-.-", ".-..", "--", "-.", "---", ".---.", "--.-", ".-.",
            "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--..", ".----",
            "..---", "...--", "....-", ".....", "-....", "--...", "---..", "----.",
            "-----", "--..--", ".-.-.-", "..--.." };

    public static String stringToMorse(String str) {

        MorsecodechatClient morsecodechatClient = new MorsecodechatClient();

        StringBuilder result = new StringBuilder();

        for(char c : str.toCharArray()) {
            if(c == ' ') {
                result.append(morsecodechatClient.CONFIG_SPACE);
                continue;
            }
            if(!(new String(english).contains(String.valueOf(c).subSequence(0, 1)))) {
                result.append(morsecodechatClient.CONFIG_CHARNOTFOUND);
                continue;
            }
            result.append(
                    morse[new String(english).indexOf(c)]
                            .replace("-", morsecodechatClient.CONFIG_LONG)
                            .replace(".", morsecodechatClient.CONFIG_SHORT))
                    .append(" ");
        }

        return result.toString();
    }

    public static String morseToString(String morse) {
        String[] array = morse.split(" ");
        StringBuilder result = new StringBuilder();

        String[] morseCloned = Util.morse.clone();

        for(String element : array) {
            if(element.equals("/")) {
                result.append(" ");
                continue;
            }
            if(!Arrays.asList(morseCloned).contains(element)) continue; // If it's not morse (likely) then continue
            result.append(Util.english[Arrays.asList(morseCloned).indexOf(element)]);
        }

        return result.toString();
    }
}
