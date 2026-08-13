# Troubleshooting

## ItemMagnet will not collect items

Check the following:

1. Confirm the machine is running rather than paused.
2. Confirm a target container is assigned.
3. Confirm the target is a hopper, chest, or barrel.
4. Confirm the filter configuration is allowing the item.
5. Confirm the item is within the configured collection radius.
6. Check the server console for RS-ItemMagnet errors.

## Target container is missing or invalid

Open the ItemMagnet main menu and use **Target Container** to assign a new hopper, chest, or barrel.

## Radius is not what I expected

Open the Radius menu and check the current radius. The server configuration also controls the default and maximum radius.

If vertical collection is restricted, check `item-y-distance` in `config.yml`.

## Configuration changes are not taking effect

Use:

```text
/rsim reload
```

or restart the server.

If a new setting was introduced by an update, check the server console for configuration migration messages.

## Need more help?

When reporting a problem, include:

- RS-ItemMagnet version
- Paper version
- Minecraft version
- Relevant `config.yml` settings
- Server console errors
- Steps required to reproduce the problem
