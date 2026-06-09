# Troubleshooting

This guide covers common issues and their solutions.

---

# Machine Not Collecting Items

## Target Container Missing

The assigned container no longer exists.

Symptoms:

* Machine shows a warning state.
* Items remain on the ground.

Solution:

* Replace the container.
* Reassign the target container if necessary.

---

## Container Full

The target container has no available space.

Symptoms:

* Items remain on the ground.
* Machine appears operational.

Solution:

* Remove items from the container.
* Expand storage capacity.

RS-ItemMagnet automatically resumes collection when space becomes available.

---

## Filter Blocking Items

The item is not allowed by the machine's filter configuration.

Solution:

* Review filter settings.
* Add the desired item to the filter.

---

## Item Outside Radius

The item is located outside the configured collection area.

Solution:

* Increase radius.
* Move the machine closer to the collection area.

---

# Machine Lost After Restart

RS-ItemMagnet stores machine data in SQLite.

Machine data should automatically persist across:

* Server restarts
* Chunk unloads
* Player relogs

If data is missing, contact the server administrator.

---

# Ownership Problems

Only the machine owner may modify settings.

Administrators with appropriate permissions may bypass ownership restrictions.

---

# Still Need Help?

If the problem persists:

1. Verify plugin version.
2. Review server logs.
3. Open an issue on the GitHub repository.

---

# Known Behavior

## Full Containers

When a container becomes full:

* Items remain on the ground.
* No items are lost.
* Collection automatically resumes when storage space becomes available.

This behavior is intentional.
