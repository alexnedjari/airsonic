#!/bin/bash
# contrib/deploy.sh
# minisonic/minisonic
#
# Helper script to shorten dev/build/deployment
#

sudo systemctl stop tomcat
sudo rm /var/lib/tomcat/webapps/minisonic* -rf
sudo cp minisonic-main/target/minisonic.war /var/lib/tomcat/webapps/
sudo systemctl start tomcat

