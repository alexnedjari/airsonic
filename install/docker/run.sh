#!/bin/bash

set -e

mkdir -p $AIRSONIC_DIR/data/transcode
ln -fs /usr/bin/ffmpeg $AIRSONIC_DIR/data/transcode/ffmpeg
ln -fs /usr/bin/lame $AIRSONIC_DIR/data/transcode/lame

if [[ $# -lt 1 ]] || [[ ! "$1" == "java"* ]]; then

    java_opts_array=()
    while IFS= read -r -d '' item; do
        java_opts_array+=( "$item" )
    done < <([[ $JAVA_OPTS ]] && xargs printf '%s\0' <<<"$JAVA_OPTS")
    exec java -Xmx256m \
     -Dserver.host=0.0.0.0 \
     -Dserver.port=$AIRSONIC_PORT \
     -Dserver.contextPath=$CONTEXT_PATH \
     -Dminisonic.home=$AIRSONIC_DIR/data \
     -Dminisonic.defaultMusicFolder=$AIRSONIC_DIR/music \
     -Dminisonic.defaultPodcastFolder=$AIRSONIC_DIR/podcasts \
     -Dminisonic.defaultPlaylistFolder=$AIRSONIC_DIR/playlists \
     -Djava.awt.headless=true \
     "${java_opts_array[@]}" \
     -jar minisonic.war "$@"
fi

exec "$@"
