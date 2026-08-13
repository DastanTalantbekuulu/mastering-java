# Git Submodules: The Ultimate Guide

This guide covers best practices for managing Git submodules, including high-performance cloning, updating, and clean removal.

## 1. Cloning a Project with Submodules

### Scenario A: Fresh Clone (Fastest Method)
If you are cloning the repository for the first time, use this "all-in-one" command. It clones the main repo and downloads the latest version of submodules in parallel, without unnecessary history.

```bash
git clone --recurse-submodules --shallow-submodules --jobs 10 <repository_url>
```
*   `--recurse-submodules`: Automatically initializes and clones submodules.
*   `--shallow-submodules`: Clones submodules with `depth 1` (saves time/bandwidth).
*   `--jobs 10`: Fetches up to 10 submodules in parallel.

### Scenario B: Already Cloned (Initializing Submodules)
If you already cloned the repo but the submodule folders are empty:

```bash
# Fast initialization (last commit only)
git submodule update --init --recursive --jobs 10 --depth 1
```

---

## 2. Adding a Submodule

### Using Command Line (Recommended)
This is the most reliable method.

```bash
# Syntax: git submodule add <url> <path>
git submodule add https://github.com/example/repo.git libs/my-lib

# Optional: If you want to track a specific branch (e.g., main)
git submodule add -b main https://github.com/example/repo.git libs/my-lib

# Commit the changes
git commit -m "Add submodule: libs/my-lib"
git push
```

### Using IntelliJ IDEA
1. Open **Git** tool window (`Alt+9` / `Cmd+9`).
2. Right-click project root -> **New** -> **Module from Existing Sources** (or **Git** -> **Manage Projects** depending on version).
3. *Better alternative:* Just use the built-in Terminal in IDEA and run the commands above.

---

## 3. Updating Submodules

There are two ways to update submodules. Choose the one that fits your goal.

### Option A: Sync (Match the Main Repo)
Use this when your teammates changed the submodule version and you want to sync your local project to match theirs exactly.

```bash
git submodule update --recursive --jobs 10
```

### Option B: Upgrade (Get Latest Remote Changes)
Use this when you want to update the submodule to the newest commit from its remote branch (e.g., `main` or `master`).

```bash
# 1. Pull latest changes for submodules
git submodule update --remote --merge --jobs 10

# 2. Commit the new version in your main repo
git add <path/to/submodule>
git commit -m "Update submodule to latest version"
```

---

## 4. Removing a Submodule (Cleanly)

Removing a submodule incorrectly can leave "ghost" configurations. Follow these steps for a complete removal.

### Step 1: De-initialize (Unregister)
This removes the submodule from `.git/config` automatically.
```bash
git submodule deinit -f <path/to/submodule>
```

### Step 2: Remove Record and Files
This removes the file from `.gitmodules` and deletes the folder from your working directory.
```bash
git rm -f <path/to/submodule>
```

### Step 3: Cleanup Internal Git Directory
Git keeps a copy of the submodule metadata in `.git/modules`. Remove it to free up space and avoid conflicts if you re-add it later.

**Linux / macOS / Git Bash:**
```bash
rm -rf .git/modules/<path/to/submodule>
```

**Windows (PowerShell):**
```powershell
Remove-Item -Recurse -Force .git/modules/<path/to/submodule>
```

### Step 4: Commit
```bash
git commit -m "Removed submodule <path>"
git push
```

---

## 5. Troubleshooting & Tips

### Fixing "Detached HEAD"
Submodules usually point to a specific commit, so they are in a "Detached HEAD" state. This is normal.
If you need to make changes **inside** a submodule:
1. `cd <submodule_folder>`
2. `git checkout main` (or your branch)
3. Make changes -> Commit -> Push.
4. Go back to main root -> Commit the submodule update.

### Shallow Clone Limitations
If you used `--depth 1` (or `--shallow-submodules`), commands like `git log` inside the submodule will show only one commit. If you need full history later:
```bash
cd <path/to/submodule>
git fetch --unshallow
```