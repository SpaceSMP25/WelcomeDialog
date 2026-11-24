# WelcomeDialog

A PaperMC 26.3+ plugin that displays a native dialog modal to every player on join with configurable Discord, Start Exploring, and Website buttons.

## Requirements
- **Server:** PaperMC 26.3+
- **Java:** Java 25
- **Build tool:** Maven 3.9+

## Building
```bash
mvn clean package
```
The compiled plugin JAR will be output to `target/welcomedialog-1.0.0.jar`.

## Configuration (`plugins/WelcomeDialog/config.json`)
All text fields (`title`, `body`, and button `label` values) support legacy `&` color codes and `&#RRGGBB` hex colors.

## Commands & Permissions
- `/info` — Reopen the welcome dialog (available to all players)
- `/welcomedialog reload` — Hot-reload `config.json` (`welcomedialog.admin`, default: OP)
- `/welcomedialog preview` — Preview the dialog in-game (`welcomedialog.admin`, default: OP)
