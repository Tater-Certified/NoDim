# NoDim
A cross-platform, cross-version dimension-disabling mod

# About
This mod provides gamerules to disable Minecraft's portals: Nether Portal, End Portal, and Gateway Portal.<p>
This will not remove the dimension, but rather disable the teleportation via portals.

# Usage
The mod includes the following gamerules to disable access to certain dimensions:
- `/gamerule disableEnd <bool>`: Disables the End Portal
- `/gamerule disableNether <bool>`: Disables the Nether Portal
- `/gamerule disableGateway <bool>`: Disables the End Gateway Portals

# Supported Platforms
- Fabric/Quilt (1.14.3 - 1.21.8)
- Forge (1.14.3 - 1.21.8)
- NeoForge (1.14.3 - 1.21.8)
- PaperMC/Spigot/Folia (1.14.3 - 1.21.8)
- Sponge (1.14.3 - 1.21.8)

# Installation
## Fabric, Quilt, Forge, NeoForge
Simply put the mod in the mods folder
## Sponge
Simply put the plugin in the plugins folder
## Spigot/PaperMC
1. Install the [Ignite](https://github.com/vectrix-space/ignite) Mixin loader
2. Run the ignite jar alongside the paper/spigot jar
3. Put the mod in the mods folder and restart
## Folia
1. Install the [Ignite](https://github.com/vectrix-space/ignite) Mixin loader
2. Rename the Folia jar to "paper.jar". Alternatively, you can launch the game with the following JVM args: `-Dignite.locator=paper -Dignite.jar=./folia.jar`
3. Run the ignite jar alongside the folia jar
4. Put the mod in the mods folder and restart
