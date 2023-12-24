# Minecraft Offline Uptime Plugin

## Overview

The Minecraft Offline Uptime Plugin is a Plugin for tracking and displaying server uptime in a readable format. With this plugin, you can easily monitor how long your Minecraft server has been running, even across different dates.

## Features

- Records server uptime in hours, minutes, and seconds.
- Displays server uptime for different dates.
- Writes server uptime information to a log file in a user-friendly format.

## Usage

The plugin automatically records server uptime in the `uptime.log` file within the plugin folder. The log file is formatted to display server uptime for each date, with the latest date appearing at the top.

To view the server uptime, open the `uptime.log` file using a text editor.

## Example

`Server uptime for 23/12/2023 (14:30:45):
02 hours
15 minutes
23 seconds`

`Server uptime for 22/12/2023 (20:10:12):
      01 hours
     45 minutes
     37 seconds`


## Config

Here's the entire config:

 `# Hi! welcome to the config file!
# PLEASE DO NOT TOUCH!
config-version: 1
# Edit how many second should the plugin wait before starting the uptime counter
start-time-after: 0
`
'config-version': This is the version of the config. please DO NOT change this

'start-time-after': Its pretty much how many second should the plugin wait before starting the uptime counter.

## License

This plugin is licensed under the [GNU General Public Licence](LICENSE).
