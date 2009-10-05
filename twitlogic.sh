#!/bin/bash

# Path to JAR
JAR=`dirname $0`/target/twitlogic-*-full.jar

# Find Java
if [ "$JAVA_HOME" = "" ] ; then
        JAVA="java"
else
        JAVA="$JAVA_HOME/bin/java"
fi

# Set Java options
if [ "$JAVA_OPTIONS" = "" ] ; then
        JAVA_OPTIONS="-Xms32M -Xmx512M"
fi

#if [ $# -ge 1 ] ; then
#    JAVA_OPTIONS=$JAVA_OPTIONS" -net.fortytwo.twitlogic.configurationProperties="$1
#fi

# Launch the application
$JAVA $JAVA_OPTIONS -cp $JAR net.fortytwo.twitlogic.TwitLogic

# Return the program's exit code
exit $?
