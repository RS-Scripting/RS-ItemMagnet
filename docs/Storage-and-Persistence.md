# Storage and Persistence

RS-ItemMagnet uses SQLite to store machine data.

This allows machine information to persist automatically across server restarts and reloads.

---

# What Is Stored?

The following information is stored:

* Machine location
* Owner information
* Assigned container
* Radius settings
* Filter configuration
* Machine status

---

# Persistence Features

RS-ItemMagnet automatically preserves machine data across:

* Server restarts
* Chunk unloads
* Chunk reloads
* Player disconnects
* Player reconnects

No manual intervention is required.

---

# Database System

RS-ItemMagnet uses SQLite.

Benefits include:

* No external database required
* Simple setup
* Reliable storage
* Fast lookups
* Easy backups

---

# Backups

Server administrators should include the RS-ItemMagnet data folder in normal server backup procedures.

This ensures all machine information can be restored if necessary.

---

# Database Safety

Machine data is automatically saved and updated as changes occur.

Unexpected server shutdowns should not normally result in data loss.

---

# Troubleshooting

If machine data appears missing:

1. Verify the database file exists.
2. Check server logs for database errors.
3. Restore from backup if necessary.

---

# Related Documentation

* Troubleshooting
* Admin Guide
