#!/bin/bash

SCRIPT_NAME=upm
SHARE_PATH="/usr/local/share/$SCRIPT_NAME"

main() { 
    local action="$1"
    local app_name="$2"

    case "$action" in
    install | uninstall)
        source "$SHARE_PATH/manage.sh" "$action" "$app_name"
        exit 1
        ;;
    --help)
        cat "$SHARE_PATH/help.txt"
        exit 1
        ;;
    --list)
        source "$SHARE_PATH/list.sh"
        exit 1
        ;;
    *)
        echo "Usage: $SCRIPT_NAME --help"
        exit 1
        ;;
esac
}

main "$@"