# !/bin/sh

export ip=$1
export user=$2
export passwd=$3

#rdesktop -f $ip -u $user -p $passwd -r clipboard:PRIMARYCLIPBOARD -r sound：local -g workarea -r disk:USB=/mnt/usb -x lan
rdesktop $ip -u $user -p $passwd -r clipboard:PRIMARYCLIPBOARD -r sound：local -r disk:USB=/media -x lan -f
