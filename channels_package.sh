#!/bin/bash
channels="fir googleplay"
#channels="dev"

apk_path=release # dir which apk dist and mapping.zip dist
module_name=app # app module name

pre_package(){
    if [ -d $apk_path ];then
        rm -r $apk_path
    fi
    mkdir $apk_path
}

package(){
    for channel in $channels ; do
        echo $channel
        gradle aR -PchannelName=$channel
        apk=`ls -t -1 apk/ | grep 'release' | grep 'apk$' | head -1`
        cp apk/$apk $apk_path/$channel-$apk
    done
}

post_package(){
    zip -r $apk_path/mapping.zip $module_name/build/outputs/mapping
}

pre_package
package
post_package
