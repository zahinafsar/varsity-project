#!/bin/bash

SCRIPT_NAME="upm"
SHARE_PATH="/usr/local/share/$SCRIPT_NAME"
JSON_FILE="$SHARE_PATH/commands.json"

list_apps() {
    if [ ! -f "$JSON_FILE" ]; then
        echo "Error: JSON file not found at $JSON_FILE"
        exit 1
    fi
    
    app_names=$(jq -r '.[].name' "$JSON_FILE")
    counter=1
    echo "Available applications:"
    for app in $app_names; do
        echo "$counter. $app"
        counter=$((counter + 1))
    done
}

list_apps
