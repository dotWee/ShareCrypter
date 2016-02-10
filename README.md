# ShareCrypter

ShareCrypter is a lightweight dialog-only application, allows you to en-/decrypt your files using the AES/CBC/PKCS5Padding-method. You can also hash your password after the SHA-256 method befor en-/decryption.

Note: This application is only partly working. It currently can't resolve files from a ContentProvider. You need to send files using a normal file browser.

[![Issues](https://img.shields.io/github/issues/dotWee/ShareCrypter.svg)](https://github.com/dotWee/ShareCrypter/issues)
[![Forks](https://img.shields.io/github/forks/dotWee/ShareCrypter.svg)](https://github.com/dotWee/ShareCrypter/network/members)
[![Language](https://img.shields.io/badge/language-java-orange.svg)](https://github.com/dotWee/ShareCrypter/search?l=java)

## Features

<b>The application itself</b>
+ consumes no memory in the background - no background-services, no background-processes 
+ is compatible down to Android Lollipop
+ is fully open source!

<b>Main Features</b>
+ Allows to hash your password using SHA-256 before en-/decryption
+ En-/Decrypts your files using AES/CBC/PKCS5Padding

<b>Design & UI</b>
ShareCrypter follows the official [Material Design](https://www.google.com/design/spec/components/dialogs.html#dialogs-specs) specs for dialogs.

## Dependencies

+ Google's Android Support Package v23.1.1
+ Jake Wharton's [ButterKnife](https://github.com/JakeWharton/butterknife) & [Timber](https://github.com/JakeWharton/timber)
+ Karumi's [Dexter](https://github.com/Karumi/Dexter)

## Changelog

View the [CHANGELOG.md](/docs/CHANGELOG.md).

## Questions / Issues / Bugs

For questions, issues or bugs, check the [Issues](https://github.com/dotWee/ShareCrypter/issues)-section of this repository.

## Build

This project is developed using JetBrain's latest IntelliJ IDEA and the latest Gradle-wrapper.

To compile ShareCrypter, simply import this project into **Android Studio** or **IntelliJ IDEA** and press the build-button.
**You may need to sign the built apk.** Read the official [documentation about signing applications](https://developer.android.com/tools/publishing/app-signing.html) for a how-to.

## License

Copyright (c) 2015 Lukas 'dotwee' Wolfsteiner
The source-code of ShareCrypter is licensed under the [_Do What The Fuck You Want To_](/LICENSE.md) public license.