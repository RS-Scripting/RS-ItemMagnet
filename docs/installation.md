# Installation

## Requirements

RS-ItemMagnet is built for Paper Minecraft servers using the 1.21 API line.

## Install

1. Download the latest RS-ItemMagnet release.
2. Place the plugin JAR in the server's `plugins` folder.
3. Start or restart the server.
4. Confirm that RS-ItemMagnet enables successfully in the server console.
5. Review `plugins/RS-ItemMagnet/config.yml` and adjust settings if needed.

The plugin creates and initializes its SQLite database automatically.

## Updating

1. Stop the server.
2. Replace the existing RS-ItemMagnet JAR with the new version.
3. Start the server.
4. Allow the plugin to perform any configuration migrations required by the new version.

When a new configuration setting is introduced, RS-ItemMagnet is designed to add missing settings without replacing the server owner's existing configuration values.
