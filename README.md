<p align="center">
  <h1 align="center">RS-ItemMagnet</h1>
  <p align="center">
    Automated Item Collection for Modern Minecraft Servers
  </p>
</p>

<p align="center">

![Minecraft](https://img.shields.io/badge/Minecraft-1.21%2B-green)
![Platform](https://img.shields.io/badge/Platform-Paper-orange)
![Storage](https://img.shields.io/badge/Storage-SQLite-blue)
![Status](https://img.shields.io/badge/Status-Stable-success)
![Development](https://img.shields.io/badge/Development-Active-brightgreen)
![RS Ecosystem](https://img.shields.io/badge/RS-Ecosystem-purple)

</p>

---

## Overview

**RS-ItemMagnet** is an automated item collection plugin designed for Minecraft servers that want reliable resource logistics without the complexity of traditional hopper-heavy collection systems.

Players place and configure ItemMagnets to automatically attract nearby dropped items and route them into a designated container while server owners maintain full control through configurable settings, filters, radius controls, permissions, and administrative tools.

Built from the ground up with performance, scalability, and long-term server operation in mind.

---

## Why RS-ItemMagnet?

Traditional collection systems often create:

* Excessive hopper usage
* Server lag
* Large item build-ups
* Complicated item transport systems
* Difficult maintenance and troubleshooting

RS-ItemMagnet replaces those systems with a clean, configurable, database-backed solution that is easy for players to use and easy for administrators to manage.

---

# Features

### Automated Item Collection

Automatically attract nearby dropped items.

### Dynamic GUI Menus

Simple and intuitive inventory-based interfaces.

### Advanced Item Filtering

Control exactly which items are accepted by each ItemMagnet.

### Radius Controls

Configure collection range to fit your automation needs.

### Target Containers

Assign and reassign collection containers at any time.

### Capacity-Aware Collection

Avoid unnecessary item acquisition when storage becomes unavailable.

### SQLite Storage

Reliable persistent data storage.

### Chunk-Safe Design

Built to behave correctly across chunk loads and unloads.

### Performance Focused

Designed to minimize server impact while maximizing functionality.

### Administrative Tools

Powerful management tools for staff and server operators.

---

# RS Ecosystem

RS-ItemMagnet is the collection and routing component of the growing **RS Ecosystem**.

```text
┌─────────────┐
│ RS-MobStand │
└──────┬──────┘
       │ Generate
       ▼
┌──────────────┐
│ RS-ItemMagnet│
└──────┬───────┘
       │ Collect
       ▼
┌───────────┐
│ RS-Auger  │
└─────┬─────┘
      │ Transport
      ▼
┌─────────────┐
│ RS-Warehouse│
└─────────────┘
       Store
```

Together these plugins create a complete automated resource collection and logistics system.

---

# Screenshots

### Main Menu

*Coming Soon*

### Filter Menu

*Coming Soon*

### Radius Configuration

*Coming Soon*

### Item Collection

*Coming Soon*

---

# Commands

| Command       | Description                  |
| ------------- | ---------------------------- |
| `/rsim`       | Open the ItemMagnet menu     |
| `/rsim admin` | Open administrative controls |

---

# Permissions

| Permission            | Description               |
| --------------------- | ------------------------- |
| `rs.itemmagnet.use`   | Allows use of ItemMagnets |
| `rs.itemmagnet.admin` | Administrative access     |

---

# Installation

1. Download the latest release.
2. Place the jar into your server's `plugins` folder.
3. Restart the server.
4. Configure settings as desired.
5. Enjoy automated item collection.

---

# Performance

RS-ItemMagnet was designed with long-term server operation in mind.

Features include:

* SQLite persistence
* Chunk-safe processing
* Efficient task management
* Capacity-aware acquisition
* Optimized item handling
* Minimal server overhead

---

# Development Status

| Component            | Status     |
| -------------------- | ---------- |
| Core System          | ✅ Complete |
| GUI System           | ✅ Complete |
| SQLite Storage       | ✅ Complete |
| Filter System        | ✅ Complete |
| Administrative Tools | ✅ Complete |
| Optimization         | ✅ Complete |
| Public Release       | 🚧 Pending |

---

# Contributors

### Big Pappa

Project Creator
Founder of RSScripting

### Atlas

Architecture, Design & Development Support

---

# Support

If you encounter a bug or have a feature request, please open an issue in the GitHub repository.

---

# License

MIT License

See the LICENSE file for details.

---

<p align="center">
  Built by RSScripting
</p>

<p align="center">
  Generate • Collect • Transport • Store
</p>
