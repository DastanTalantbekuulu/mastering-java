# Git Console Commands Cheat Sheet

### Add your own commands and other useful tips via `Pull request`.

<!--
*   [General](https://github.com/cyberspacedk/Git-commands/tree/master/git#General)
*   [Console Commands](https://github.com/cyberspacedk/Git-commands/tree/master/git#Console-commands)
*   [Real Work Examples](https://github.com/cyberspacedk/Git-commands/tree/master/git#Examples) -->

## General

Git is a version control system (for files). It's somewhat like the ability to save in computer games (in Git, the equivalent of a game save is a **commit**). **Important**: adding files to a "save" is a two-step process: first, add the file to the index (`git add`), then "save" it (`git commit`).

Any file in an existing repository directory can either be under version control or not (tracked and untracked).

Tracked files can be in 3 states: unmodified, modified, and staged (ready for commit).

### Key to Understanding

The key to understanding the Git concept is knowing about the "three trees":

- **Working Directory** — The project's file system (the files you are working with).
- **Index (Staging Area)** — A list of files and directories tracked by Git, an intermediate storage for changes (editing, deleting tracked files).
- **`.git/` Directory** — All version control data for this project (the entire development history: commits, branches, tags, etc.).

**Commit** — A "save" (stores a set of changes made in the working directory since the previous commit). A commit is immutable; it cannot be edited.

All commits (except the very first one) have one or more parent commits because commits store changes from previous states.

### Simple Workflow

- Editing, adding, deleting files (the actual work).
- Indexing/Adding files to the index (telling Git which changes need to be committed).
- Committing (fixing the changes).
- Return to step 1 or go to sleep.

### Pointers

- `HEAD` — A pointer to the current commit or the current branch (which ultimately points to a commit). It points to the parent of the commit that will be created next.
- `ORIG_HEAD` — A pointer to the commit from which you just moved `HEAD` (using `git reset ...`, for example).
- **Branch** (`master`, `develop`, etc.) — A pointer to a commit. When a commit is added, the branch pointer moves from the parent commit to the new one.
- **Tags** — Simple pointers to commits. They do not move.

### Settings

Before starting work, you need to configure a few settings:

```bash
git config --global user.name "Your Name" # set the name that will be attached to your commits
git config --global user.email "e@w.com"  # set the email that will be in the committer description
```

If you are on Windows:

```bash
git config --global core.autocrlf true # enable conversion of line endings from CRLF to LF
```

### Specifying Untracked Files

Files and directories that should not be included in the repository are listed in the `.gitignore` file. Usually, these are installed dependencies (`node_modules/`, `bower_components/`), build outputs `build/` or `dist/`, and similar files created during installation or runtime. Each file or directory is listed on a new line; [wildcards can be used](https://git-scm.com/book/en/v2/Git-Basics-Recording-Changes-to-the-Repository#_ignoring_files).

### Console

[How to use the Bash console in Windows, basic commands](https://github.com/cyberspacedk/BASH-Commands).

### Long Output in Console: Vim

Executing some console commands results in very long output (example: viewing the history of all changes in a file using `git log -p fileName.txt`). This launches the [Vim](https://en.wikipedia.org/wiki/Vim) editor right inside the console. It works in several modes, of which you will be interested in the **Insert** mode (text editing) and the **Normal** (Command) mode. To get from Vim back to the console, you need to type <kbd>:q</kbd> in command mode. To switch to command mode from any other mode: press <kbd>Esc</kbd>.

If you need to write something, press <kbd>i</kbd> — this switches to text insertion mode. If you need to save changes, switch to command mode and type <kbd>:w</kbd>.

# Vim (some commands)

```bash
# Key presses
ESC     — switch to command mode
i       — switch to text editing mode
ZQ (Shift held, pressed sequentially) — quit without saving
ZZ (Shift held, pressed sequentially) — save and quit
```

```bash
# Input in command mode
:q!             — quit without saving
:wq             — save file and quit
:w filename.txt — save file as filename.txt
```

## Console Commands

### Create a new repository

```bash
git init             # create a new project in the current directory
git init folder-name # create a new project in the specified directory
```

### Cloning a repository

```bash
# clone a remote repository into a directory with the same name
git clone https://github.com/cyberspacedk/Git-commands.git

# clone a remote repository into the directory "FolderName"
git clone https://github.com/cyberspacedk/Git-commands.git FolderName

# clone a repository into the current directory
git clone https://github.com:nicothin/web-design.git .
```

### Viewing changes

```bash
git status              # show the state of the repository (tracked, modified, new files, etc.)
git diff                # compare working directory and index (untracked files are IGNORED)
git diff --color-words  # compare working directory and index, show differences by words (untracked files are IGNORED)
git diff index.html     # compare a file from the working directory and the index
git diff HEAD           # compare working directory and the commit pointed to by HEAD (untracked files are IGNORED)
git diff --staged       # compare index and the HEAD commit
git diff master feature # see what has been done in the feature branch compared to the master branch
git diff --name-only master feature # see what has been done in feature compared to master, show only filenames
git diff master...feature # see what has been done in the feature branch since it diverged from master
```

### Adding changes to the index

```bash
git add .        # add all new, modified, deleted files from the current directory and its subdirectories to the index
git add text.txt # add the specified file to the index (whether modified, deleted, or new)
git add -i       # launch the interactive shell to add only selected files to the index
git add -p       # show new/modified files one by one with their changes and ask about tracking/indexing
```

### Removing changes from the index

```bash
git reset            # remove all added changes from the index (changes remain in the working directory), the opposite of git add
git reset readme.txt # remove changes to the specified file from the index (changes remain in the working directory)
```

### Undoing changes

```bash
git checkout text.txt      # DANGEROUS: discard changes in the file, revert to the state currently in the index
git reset --hard           # DANGEROUS: discard changes; revert to the commit pointed to by HEAD (uncommitted changes are removed from the index and working directory, untracked files remain)
git clean -df              # remove untracked files and directories
```

### Commits

```bash
git commit -m "Name of commit"    # fix staged changes in a commit, add a message
git commit -a -m "Name of commit" # stage tracked files (ONLY tracked, NOT new files) and commit, add a message
```

### Undoing commits and moving through history

All commits that have already been pushed to a remote repository should be undone with new commits (`git revert`) to avoid history problems for other project participants.

```bash
git revert HEAD --no-edit    # create a new commit that reverts the changes of the last commit without launching the message editor
git revert b9533bb --no-edit # same, but reverts changes made by the commit with the specified hash (b9533bb)
```

**All commands listed below can ONLY be executed if the commits have not yet been sent to the remote repository.**

```bash
# WARNING! Dangerous commands, uncommitted changes may be lost
git commit --amend -m "Title"     # "re-commit" the changes of the last commit, replace it with a new commit with a different message (moves current branch back one commit, keeping working dir and index "as is", creates new commit with data from "cancelled" commit but new message)
git reset --hard @~      # move HEAD (and branch) to the previous commit, make working directory and index exactly as they were at the moment of the previous commit
git reset --hard 75e2d51 # move HEAD (and branch) to the commit with the specified hash, make working directory and index exactly as they were at the moment of that commit
git reset --soft @~      # move HEAD (and branch) to the previous commit, but leave all changes in the working directory and index
git reset --soft @~2     # same, but move HEAD (and branch) 2 commits back
git reset @~             # move HEAD (and branch) to the previous commit, leave working directory as is, reset index to how it was at the previous commit (more convenient than git reset --soft @~ if you need to re-stage)
# Almost like git reset --hard, but safer: you won't lose changes in the working directory
git reset --keep @~      # move HEAD (and branch) to the previous commit, reset index, but keep changes in working directory if possible (if a file with changes was modified between commits, an error will occur and the switch won't happen)
```

### Temporarily switch to another commit

```bash
git checkout b9533bb # switch to the commit with the specified hash (move HEAD to the specified commit, revert working directory to the state at that commit)
git checkout master  # switch to the commit pointed to by master (move HEAD to the commit master points to, revert working directory to the state at that commit)
```

### Switch to another commit and continue working from there

Requires creating a new branch starting from the specified commit.

```bash
git checkout -b new-branch 5589877   # create branch new-branch starting from commit 5589877 (move HEAD to commit, revert working dir, create branch pointer with specified name)
```

### Restoring changes

```bash
git checkout 5589877 index.html  # restore the specified file in the working directory to its state at the specified commit (and stage this change) (use git reset index.html to unstage but keep changes in file)
```

### Copying a commit (cherry-picking)

```bash
git cherry-pick 5589877          # copy changes from the specified commit to the active branch and commit them
git cherry-pick master~2..master # copy changes from master (last 2 commits) to the active branch
git cherry-pick -n 5589877       # copy changes from the specified commit to the active branch but DO NOT COMMIT (implies we will commit later manually)
git cherry-pick master..feature  # copy changes from all commits in feature branch since it diverged from master to the active branch (looks like a merge, but it's copying changes), commit them; may cause conflicts
git cherry-pick --abort    # abort a conflicted cherry-pick
git cherry-pick --continue # continue a conflicted cherry-pick (works only after resolving the conflict)
```

### Deleting a file

```bash
git rm text.txt    # delete a tracked unmodified file and stage this change
git rm -f text.txt # delete a tracked modified file and stage this change
git rm -r log/     # delete all content of the tracked directory log/ and stage this change
git rm ind*        # delete all tracked files with names starting with "ind" in current directory and stage
git rm --cached readme.txt # remove a file from tracking but keep it in the working directory (FILE REMAINS IN PLACE) (often used for files accidentally added to tracking)
```

### Moving/Renaming files

Git does not have a concept of renaming. Renaming is perceived as deleting the old file and creating a new one. The fact of renaming can only be determined after indexing the change.

```bash
git mv text.txt test_new.txt # rename "text.txt" to "test_new.txt" and stage this change
git mv readme_new.md folder/ # move file readme_new.md to directory folder/ (must exist) and stage this change
```

### Commit History

Exit long log output: `q`.

```bash
git log master             # show commits in the specified branch
git log -2                 # show the last 2 commits in the active branch
git log -2 --stat          # show the last 2 commits and statistics of changes made
git log -p -22             # show the last 22 commits and the diffs at the line level
git log --graph -10        # show the last 10 commits with ASCII representation of branching
git log --since=2.weeks    # show commits from the last 2 weeks
git log --after '2018-06-30' # show commits made after the specified date
git log index.html         # show history of changes to index.html (commits only)
git log -5 index.html      # show history of changes to index.html, last 5 commits (commits only)
git log -p index.html      # show history of changes to index.html (commits and diffs)
git log -G'myFunction' -p  # show all commits where lines containing myFunction were changed (regex in quotes)
git log -L '/<head>/','/<\/head>/':index.html # show changes from start regex to end regex in the specified file
git log --grep fix         # show commits with "fix" in the description (case-sensitive, current branch only)
git log --grep fix -i      # show commits with "fix" in the description (case-insensitive, current branch only)
git log --grep 'fix(ing|me)' -P # show commits matching the regex in description (current branch only)
git log --pretty=format:"%h - %an, %ar : %s" -4 # show last 4 commits with custom formatting
git log --pretty=format:"%h %ad | %s%d [%an]" --graph --date=short # my output format, assigned to a shell alias
git log master..branch_99  # show commits in branch_99 that are not merged into master
git log branch_99..master  # show commits in master that are not merged into branch_99
git log master...branch_99 --boundary -- graph # show commits from specified branches starting from where they diverged (divergence commit will be shown)
```

```bash
git show 60d6582           # show changes from the commit with the specified hash
git show HEAD~             # show data about the previous commit in the active branch
git show @~                # same as above
git show HEAD~3            # show data about the commit 3 commits ago
git show my_branch~2       # show data about the commit 2 commits ago in the specified branch
git show @~:index.html     # show content of the specified file at the moment of the previous (from HEAD) commit
git show :/"footer"        # show the newest commit with the specified word in the description (from any branch)
```

### Who wrote the line

```bash
git blame README.md --date=short -L 5,8 # show lines 5-8 of the specified file and the commits where they were added
```

### History of pointer changes (branches, HEAD)

```
git reflog -20             # show the last 20 changes of the HEAD pointer position
git reflog --format='%C(auto)%h %<|(20)%gd %C(blue)%cr%C(reset) %gs (%s)' -20 # same, but with timestamps
```

### Branches

```bash
git branch                 # show list of branches
git branch -v              # show list of branches and the last commit in each
git branch new_branch      # create a new branch with the specified name at the current commit
git branch new_branch 5589877 # create a new branch with the specified name at the specified commit
git branch -f master 5589877  # move the master branch to the specified commit
git branch -f master master~2 # move the master branch 2 commits back
git checkout new_branch    # switch to the specified branch
git checkout -b new_branch # create a new branch with the specified name and switch to it
git checkout -B master 5589877 # move the branch with the specified name to the specified commit and switch to it
git merge hotfix           # merge data from the hotfix branch into the current branch
git merge hotfix -m "Hotfix" # merge data from hotfix into current branch (merge commit message specified)
git merge hotfix --log     # merge data from hotfix into current branch, show editor for commit message, add messages of merged commits
git merge hotfix --no-ff   # merge data from hotfix into current branch, forbid fast-forward, changes from hotfix "stay" there, only a merge commit appears in active branch
git branch -d hotfix       # delete the hotfix branch (used if its changes are already merged into the main branch)
git branch --merged        # show branches already merged into the active one
git branch --no-merged     # show branches not merged into the active one
git branch -a              # show all existing branches (including remote ones)
git branch -m old_branch_name new_branch_name # locally rename branch old_branch_name to new_branch_name
git branch -m new_branch_name # locally rename the CURRENT branch to new_branch_name
git push origin :old_branch_name new_branch_name # apply rename in the remote repository
git branch --unset-upstream # finish the rename process
```

### Tags

```bash
git tag v1.0.0               # create a tag with the specified name at the commit HEAD points to
git tag -a -m 'Production!' v1.0.1 master # create a tag with description on the commit master points to
git tag -d v1.0.0            # delete tag(s) with the specified name
git tag -n                   # show all tags and 1 line of the commit message they point to
git tag -n -l 'v1.*'         # show all tags starting with 'v1.*'
```

### Temporary saving changes without committing

```bash
git stash     # temporarily save uncommitted changes and remove them from the working directory
git stash pop # return changes saved by git stash to the working directory
```

### Remote Repositories

There are two common ways to link a remote repository to a local one: via HTTPS and via SSH. If you don't have SSH configured (or don't know what it is), link the remote repository via HTTPS (the address of the linked repo must start with https://).

```bash
git remote -v              # show list of remote repositories linked to local
git branch -r              # show remote branches
git branch -a              # show all branches (local and remote)
git remote remove origin   # remove the link to the remote repository with the alias origin
git remote add origin https://github.com:nicothin/test.git # add a remote repository (alias origin) with the specified URL
git remote rm origin       # remove the link to the remote repository
git remote show origin     # get data about the remote repository with alias origin
git fetch origin           # download all branches from the remote repository (alias origin), but do not merge with local branches
git fetch origin master    # same, but downloads only the specified branch
git checkout --track origin/github_branch # create a local branch github_branch (data taken from remote repo origin, branch github_branch) and switch to it
git push origin master     # send data from local master branch to the remote repository (alias origin)
git pull origin            # merge changes from the remote repository (all branches)
git pull origin master     # merge changes from the remote repository (only the specified branch)
```

### Merge Conflict

Assume a situation: there is a `master` branch and a `feature` branch. Both branches have commits made after they diverged. We try to merge `feature` into `master` (`git merge feature`) and get a conflict because both branches have changes to the same line in `index.html`.

When a conflict occurs, the repository is in a suspended merge state. You need to keep only the necessary code in the conflicting parts of the files, stage the changes, and commit.

```bash
git merge feature                # merge changes from feature branch into active branch
git merge-base master feature    # show the hash of the last common commit for the two specified branches
git checkout --ours index.html   # keep the state of the branch WE ARE MERGING INTO (in this example — master) in the conflicting file
git checkout --theirs index.html # keep the state of the branch WE ARE MERGING FROM (in this example — feature) in the conflicting file
git checkout --merge index.html  # show comparison of merging branches content in the conflicting file (for manual editing)
git checkout --conflict=diff3  --merge index.html # show comparison plus what was in the conflict spot at the common ancestor commit
```

```bash
git reset --hard  # stop this interrupted merge, revert working directory and index to HEAD state, and I'll go cry a little
git reset --merge # stop this interrupted merge, but leave changes not committed before the merge (for cases when merge is done on non-clean status)
git reset --abort # same as above
```

### "Moving" a branch (Rebase)

You can "move" the point where a branch diverged from the main branch to an arbitrary commit. This is needed so that the "moved" branch gets changes made in the main branch (after the moved branch diverged).

Do not "move" a branch if it has already been pushed to a remote repository.

```bash
git rebase master # move all commits (create copies) of the active branch as if it branched off from master at its current tip (often causes conflicts)
git rebase --onto master feature # move commits of the active branch onto master, starting from where the active branch diverged from feature
git rebase --abort # abort a conflicted rebase, return working directory and index to state before rebase
git rebase --continue # continue a conflicted rebase (works only after resolving conflict and staging the resolution)
```

#### How to undo a rebase

```bash
git reflog feature -2        # look at the move log of the rebased branch (feature in this example), see the last commit BEFORE rebase, move the branch pointer there
git reset --hard feature@{1} # move feature branch pointer one commit back, update working directory and index
```

### Miscellaneous

```bash
git archive -o ./project.zip HEAD # create an archive with the project file structure at the specified path (repository state corresponding to HEAD)
```

## Examples

Collecting a collection of simple and complex workflow examples.

### Starting Work

Creating a new repository, first commit, linking a remote repository from github.com, pushing changes to the remote repository.

```bash
# sequence of actions:
# project directory created, we are inside it
git init                      # create repo in this directory
touch readme.md               # create file readme.md
git add readme.md             # add file to index
git commit -m "Start"         # create commit
git remote add origin https://github.com:nicothin/test.git # add previously created empty remote repo
git push -u origin master     # send data from local repo to remote (to master branch)
```

### "Injecting changes" into a commit

Only if the commit has not yet been sent to the remote repository.

```bash
# sequence of actions:
subl inc/header.html          # edit and save "header" markup
git add inc/header.html       # stage the modified file
git commit -m "Removed phone from header" # make commit
# WARNING: commit has not yet been pushed to remote
# realize we needed to do something else in this commit.
subl inc/header.html          # make changes
git add inc/header.html       # stage modified file (git add . works too)
git commit --amend -m "Header: task #34 complete" # re-do the commit
```

### Working with branches

There is `master` (public site version), we perform a large task (redesign "header"), but during work, a need arises to fix a critical bug (wrong contact in "footer").

```bash
# sequence of actions:
git checkout -b new-page-header # create new branch for header task and switch to it
subl inc/header.html            # edit header markup
git commit -a -m "New header: logo change" # commit (work not finished yet)
# turns out there is a bug with the contact in the footer
git checkout master             # return to master branch
subl inc/footer.html            # fix bug and save footer markup
git commit -a -m "Fix footer contact" # commit
git push                        # push commit with quick critical fix to master in remote repo
git checkout new-page-header    # switch back to new-page-header to continue work on header
subl inc/header.html            # edit and save header markup
git commit -a -m "New header: navigation change" # commit (header work done)
git checkout master             # switch to master
git merge new-page-header       # merge changes from new-page-header into master
git branch -d new-page-header   # delete branch new_page_header
```

### Working with branches, merging and rolling back to pre-merge state

There was a `fix` branch where a bug was fixed. Fixed, merged `fix` into `master`. But it turned out this fix breaks some functionality. Need to roll back `master` to the state without the merge (existence of the bug is less critical than broken functionality).

```bash
# we are in fix branch, bug is "fixed"
git checkout master            # switch to master
git merge fix                  # merge changes from fix into master
# see problem: part of functionality broke
git checkout fix               # switch to fix (git won't let us move master while we are on it)
git branch -f master ORIG_HEAD # move master branch to commit specified in ORIG_HEAD (the one master pointed to before merging fix)
```

### Working with branches, merge conflict

There is a `master` branch (public site version), in two parallel branches (`branch-1` and `branch-2`) the same place in the same file was edited. `branch-1` was merged into master, attempting to merge the second one causes a conflict.

```bash
# sequence of actions:
git checkout master           # switch to master
git checkout -b branch-1      # create branch-1 based on master
subl .                        # edit and save files
git commit -a -m "Edit 1"     # commit
git checkout master           # return to master
git checkout -b branch-2      # create branch-2 based on master
subl .                        # edit and save files
git commit -a -m "Edit 2"     # commit
git checkout master           # return to master
git merge branch-1            # merge branch-1 into current (master), success (auto-merge)
git merge branch-2            # merge branch-2 into current (master), CONFLICT
# Automatic merge failed; fix conflicts and then commit the result.
subl .                        # choose parts to keep in conflicting files, save
git commit -a -m "Conflict resolution" # commit conflict resolution result
```

### Syncing a fork with the master repository

There is a repository on github.com, we made a fork of it, added some changes. The original (upstream) repository was updated. Task: pull changes from the upstream repository (which were made there after we forked it).

```bash
# sequence of actions:
git remote add upstream https://github.com:address.git # add remote repo: alias — upstream, URL of master repo
git fetch upstream            # fetch all branches of master repo, but don't merge with ours yet
git checkout master           # switch to master branch of our repo
git merge upstream/master     # merge fetched master branch of upstream repo into our master branch
```

### Mistake: committed to master, but realized should have been in a new branch

**IMPORTANT: this works only if the commit has not yet been pushed to remote.**

```bash
# sequence of actions:
# made changes, staged, committed to master, but NOT PUSHED YET
git checkout -b new-branch    # create new branch from master
git checkout master           # switch to master
git reset HEAD~ --hard        # move master pointer (branch) 1 commit back
git checkout new-branch       # switch back to new branch to continue work
```

### Need to restore file content to state at a specific commit (hash known)

```bash
# sequence of actions:
git checkout f26ed88 -- index.html # restore specified file in working directory to state at specified commit, stage this change
git commit -am "Navigation fixes"  # commit
```

### Login and password requested for every action with github (or other remote)

This refers specifically to asking for a login + password pair, not a passphrase. This happens because Git by default does not save the password for HTTPS access.

Simple solution: [tell git to cache your password](https://help.github.com/articles/caching-your-github-password-in-git/).

## `.gitattributes`

```
* text=auto

*.html diff=html
*.css  diff=css
*.scss diff=css
```
