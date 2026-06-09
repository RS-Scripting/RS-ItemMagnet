# Installation

This guide explains how to install RS-ItemMagnet on your Paper Minecraft server.

---

# Requirements

* Minecraft 1.21+
* Paper Server
* Java 21+

---

# Download

Download the latest RS-ItemMagnet release from the GitHub Releases page.

---

# Installation Steps

## Step 1

Stop your Minecraft server.

---

## Step 2

Copy the RS-ItemMagnet jar file into your server's:

```text
plugins/
```

directory.

---

## Step 3

Start your server.

During startup RS-ItemMagnet will:

* Create its data folder
* Create its SQLite database
* Create required database tables
* Register commands and permissions

---

## Step 4

Verify successful startup.

You should see messages similar to:

```text
[RS-ItemMagnet] SQLite database initialized.
[RS-ItemMagnet] Database tables initialized.
[RS-ItemMagnet] RS-ItemMagnet Enabled
```

---

# Updating

To update RS-ItemMagnet:

1. Stop the server.
2. Replace the existing jar file.
3. Start the server.

Machine data and configuration are preserved automatically.

---

# Uninstalling

To remove RS-ItemMagnet:

1. Stop the server.
2. Remove the plugin jar.
3. Remove the RS-ItemMagnet data folder if desired.

Warning:

Removing the data folder will permanently delete all ItemMagnet data.

---

# Next Step

Continue with:

[Creating Your First ItemMagnet](Creating-Your-First-ItemMagnet.md)
