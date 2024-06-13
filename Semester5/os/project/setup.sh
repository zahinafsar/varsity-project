#!/bin/bash

SCRIPT_NAME="upm"
SCRIPT_INSTALL_PATH="/usr/local/bin"
SHARE_PATH="/usr/local/share/$SCRIPT_NAME"

if [[ $EUID -ne 0 ]]; then
   echo "This script must be run as root. Please use sudo."
   exit 1
fi

mkdir -p "$SHARE_PATH"

cp "upm.sh" "$SCRIPT_INSTALL_PATH/$SCRIPT_NAME"
cp -a "." "$SHARE_PATH"

chmod +x "$SCRIPT_INSTALL_PATH/$SCRIPT_NAME"
chmod -R a+rX "$SHARE_PATH"

if [[ -f "$SCRIPT_INSTALL_PATH/$SCRIPT_NAME" && -f "$SHARE_PATH/help.txt" && -f "$SHARE_PATH/commands.json" ]]; then
  echo "$SCRIPT_NAME has been installed successfully to $SCRIPT_INSTALL_PATH."
  echo "Modules has been installed to $SHARE_PATH."
  echo "You can now run '$SCRIPT_NAME --help' to see the help message."
else
  echo "There was an issue installing $SCRIPT_NAME. Please check the script and try again."
fi
