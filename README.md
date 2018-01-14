# ttracker

`ttracker` is command line based personal time tracker, inspired by `ledger-cli`.
It has a pomodoro styled timer which logs data in plain text. These logs are compatibility with [ledger-cli](https://github.com/ledger/ledger).

## Installation

Download from http://example.com/FIXME.

## Usage

    $ lein run -f /path/to/log/file 20 # start 20 min timer
    $ java -jar ttracker-0.1.0-standalone.jar -f /path/to/log/file 20 # start 20 min timer

## Options

1. `-t` `--tags` "Tag:Subtag"
2. `-d` `--description` "A brief description about the task."
3. `-h` `--help` help.

## Examples

    $ lein run -f ~/Documents/2018.dat -t "Work:side projects:ttracker" -d "fix readme" 20

### Bugs

Please open an issue.


## License

Copyright © 2018 Anurag Peshne

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
