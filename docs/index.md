# RS-ItemMagnet Documentation

RS-ItemMagnet is an automated item collection plugin for Paper Minecraft servers.

## Documentation

- [Installation](installation.md)
- [Usage](usage.md)
- [Configuration](configuration.md)
- [Commands](commands.md)
- [Permissions](permissions.md)
- [Troubleshooting](troubleshooting.md)

## What RS-ItemMagnet Does

An ItemMagnet converts a lodestone into an automated item collector. It scans for dropped items within its configured radius and deposits accepted items into a configured hopper, chest, or barrel.

ItemMagnets are configured through inventory menus and store their machine data persistently using SQLite.

## Core Features

- Configurable collection radius
- Automatic item collection
- Target hopper, chest, or barrel assignment
- Target reassignment after conversion
- Item filtering
- Pause and resume controls
- Radius preview using a temporary particle outline
- Vertical collection-distance control
- SQLite persistence
- Administrative controls
- OP update-available notification

## Requirements

- Paper 1.21+
- Java version supported by the Paper version being used
- A server with permission support

## License

RS-ItemMagnet is released under the MIT License.
