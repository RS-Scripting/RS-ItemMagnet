# Configuration

The configuration file is located at:

`plugins/RS-ItemMagnet/config.yml`

## Current Settings

```yaml
# Default scan radius for RS-ItemMagnet to scan for dropped items.
default-radius: 10

# Maximum radius players may set their RS-ItemMagnets to.
# 0 = Unlimited.
max-radius: 25

# Seconds allowed to select a target Container.
conversion-timeout: 30

# Distance in blocks ABOVE and BELOW the magnet
# that items may be collected from.
#
# 0 = Unlimited (legacy behavior)
# >0 = Limit vertical collection distance.
item-y-distance: 0

# Whether OP players bypass permissions checks
admin-bypass: true

# Enable debug logging.
debug-mode: false
```

## `default-radius`

Sets the starting collection radius for newly created ItemMagnets.

Default:

`10`

## `max-radius`

Sets the maximum radius players may configure on an ItemMagnet.

`0` means unlimited.

Default:

`25`

## `conversion-timeout`

Sets how many seconds a player has to select a target container during conversion.

Default:

`30`

## `item-y-distance`

Controls the vertical collection distance above and below the magnet.

- `0` = unlimited vertical distance, preserving the legacy behavior.
- `1` = collect up to 1 block above and 1 block below.
- `2` = collect up to 2 blocks above and 2 blocks below.
- Higher values increase the allowed vertical distance accordingly.

Default:

`0`

## `admin-bypass`

When enabled, OP players can bypass permission checks used by the plugin.

Default:

`true`

## `debug-mode`

Enables additional debug logging for troubleshooting.

Default:

`false`

## Configuration Migration

RS-ItemMagnet checks for missing configuration settings when it starts. New settings can be added to an existing configuration without replacing the server owner's existing values.
