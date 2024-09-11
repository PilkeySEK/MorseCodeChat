# MorseCodeChat

A simple utility mod that adds the ability to translate & send morse code messages

### Features
- Live chat translation (see images below & gallery)
- `/morse` command (translates text & automatically sends it)
- `/translate` command (only translates, option to copy the morse code message)
- Toggle chat translation on and off with a keybind (`Z` or `Y` by default, depending on your keyboard layout)

### Config
Found in `<instance>/config/morsecodechat.properties`
```
# MorseCodeChat config
short=.
long=-
# when a char, such as `§` does not have a morse code value
charnotfound=<?>
space= / 

morsecommandname=morse
translatecommandname=translate
```
**NOTE** that changing the `short`, `long` and `space` values will make the morse code not able to be properly translated by others anymore!

**Also NOTE** that if you have a mod/client such as meteor or asteroid, you'll need to change the command prefix from `.` to something else


![Chat message being translated](https://cdn.modrinth.com/data/cached_images/1e9558282e6b47bfb6437288c5c5d022fd0bdd14.png)


### Want to contribute?
Just make a PR and describe your changes :]