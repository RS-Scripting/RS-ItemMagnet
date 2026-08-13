# Usage

## Creating an ItemMagnet

An ItemMagnet is based on a lodestone.

The current conversion process requires the player to use the plugin's required conversion item on the lodestone and then select a target container.

Supported target containers are:

- Hopper
- Chest
- Barrel

## Main Menu

Interacting with an ItemMagnet opens its main configuration menu.

The current menu provides controls for:

- **Convert Back** — restores the lodestone from the ItemMagnet.
- **Running / Paused** — pauses or resumes the machine.
- **Radius** — opens the radius configuration menu.
- **Show Radius** — closes the menu and displays a temporary particle outline of the collection radius to the player.
- **Target Container** — assigns or changes the container used for deposits.
- **Filter** — configures item filtering.
- **Admin** — opens administrative controls when permitted.

## Radius

The radius controls how far the ItemMagnet searches for dropped items.

The current scanner uses the configured radius on the X, Y, and Z axes. The optional `item-y-distance` configuration setting can limit the vertical collection distance above and below the magnet.

The radius menu provides:

- `-10`
- `-5`
- `-1`
- `+1`
- `+5`
- `+10`

The configured maximum radius is enforced when players increase the radius.

## Radius Preview

Select **Show Radius** from the main menu to display the collection boundary temporarily.

The preview is visible only to the player who requested it and shows the collection area without creating a permanent world effect.

## Target Containers

An ItemMagnet can deposit into a hopper, chest, or barrel.

The target can be assigned during conversion and can later be changed from the main menu.

While selecting a target, normal interaction with non-container blocks remains available. Selecting a valid hopper, chest, or barrel assigns it as the target.

## Filters

The filter menu allows the ItemMagnet to control which item types it accepts.

Filter modes and individual filter entries are managed through the ItemMagnet inventory menus.

## Pause and Resume

The main menu displays the current machine state. A running ItemMagnet can be paused and resumed without converting the machine back into a normal lodestone.
