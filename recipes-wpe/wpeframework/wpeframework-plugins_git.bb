SUMMARY = "WPE Framework common plugins"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=39fb5e7bc6aded7b6d2a5f5aa553425f"
PR = "r1"

require include/wpeframework-plugins.inc

SRC_URI = "git://github.com/rdkcentral/ThunderNanoServices.git;protocol=git;branch=master \
           file://index.html \
           file://osmc-devinput-remote.json \
           file://0001-westeros-preload-libwesteros_gl.so.0.0.0.patch \
           "

SRCREV = "01d6f82a3a2e1dcc6c7aeee3e22369b220efc0d2"
# ----------------------------------------------------------------------------

# More complicated plugins are moved seperate includes

include include/compositor.inc
include include/dialserver.inc
include include/displayinfo.inc
include include/firmwarecontrol.inc
include include/ioconnector.inc
include include/network.inc
include include/power.inc
include include/playerinfo.inc
include include/remotecontrol.inc

# ----------------------------------------------------------------------------

WPEFRAMEWORK_LOCATIONSYNC_URI ?= "http://jsonip.metrological.com/?maf=true"
PLUGIN_WEBSERVER_PORT ?= "8080"
PLUGIN_WEBSERVER_PATH ?= "/var/www/"

# ----------------------------------------------------------------------------

PACKAGECONFIG ?= " \
    ${@bb.utils.contains('MACHINE_FEATURES', 'bluetooth',           'bluetoothcontrol', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'bluetooth',           'bluetoothremote', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'compositor',           'compositor', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd',              'systemdconnector', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'thunder',              'network', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'wifi',                'network wifi', '', d)} \
    ${@bb.utils.contains('STREAMER_DISTRO_PACKAGE_AVAILABLE', 'True', 'streamer', '', d)} \
    dhcpserver dictionary ioconnector remote remote-devinput systemcommands timesync virtualinput webserver \
"

PACKAGECONFIG_append_brcm = " displayinfo snapshot volumecontrol"
PACKAGECONFIG[bluetoothcontrol] = "-DPLUGIN_BLUETOOTH=ON -DPLUGIN_BLUETOOTH_AUTOSTART=true,-DPLUGIN_BLUETOOTH=OFF,,bluez5"
PACKAGECONFIG[bluetoothremote]  = "-DPLUGIN_BLUETOOTHREMOTECONTROL=ON -DPLUGIN_BLUETOOTHREMOTECONTROL_AUTOSTART=true,-DPLUGIN_BLUETOOTHREMOTECONTROL=OFF,"

PACKAGECONFIG[dhcpserver]     = "-DPLUGIN_DHCPSERVER=ON,-DPLUGIN_DHCPSERVER=OFF,"
PACKAGECONFIG[dictionary]     = "-DPLUGIN_DICTIONARY=ON,-DPLUGIN_DICTIONARY=OFF,"
PACKAGECONFIG[dsgcc_client]   = "-DPLUGIN_DSGCCCLIENT=ON,,broadcom-refsw"
PACKAGECONFIG[dsresolution]   = "-DPLUGIN_DSRESOLUTION=ON,,devicesettings"
PACKAGECONFIG[filetransfer]   = "-DPLUGIN_FILETRANSFER=ON,-DPLUGIN_FILETRANSFER=OFF,"
PACKAGECONFIG[snapshot]       = "-DPLUGIN_SNAPSHOT=ON,-DPLUGIN_SNAPSHOT=OFF,libpng"
PACKAGECONFIG[systemcommands]   = "-DPLUGIN_SYSTEMCOMMANDS=ON,-DPLUGIN_SYSTEMCOMMANDS=OFF,"
PACKAGECONFIG[systemdconnector] = "-DPLUGIN_SYSTEMDCONNECTOR=ON,-DPLUGIN_SYSTEMDCONNECTOR=OFF,"
PACKAGECONFIG[timesync]       = "-DPLUGIN_TIMESYNC=ON,-DPLUGIN_TIMESYNC=OFF,"
PACKAGECONFIG[virtualinput]   = "-DPLUGIN_COMPOSITOR_VIRTUALINPUT=ON,-DPLUGIN_COMPOSITOR_VIRTUALINPUT=OFF,"
PACKAGECONFIG[volumecontrol]  = "-DPLUGIN_VOLUMECONTROL=ON,-DPLUGIN_VOLUMECONTROL=OFF,"
PACKAGECONFIG[volumecontrol_rdkhal]  = "-DRDK_AUDIO_HAL=ON,-DRDK_AUDIO_HAL=OFF,"
PACKAGECONFIG[webproxy]       = "-DPLUGIN_WEBPROXY=ON,-DPLUGIN_WEBPROXY=OFF,"
PACKAGECONFIG[webserver]      = "-DPLUGIN_WEBSERVER=ON \
                                 -DPLUGIN_WEBSERVER_PORT="${PLUGIN_WEBSERVER_PORT}" \
                                 -DPLUGIN_WEBSERVER_PATH="${PLUGIN_WEBSERVER_PATH}" \
                                 -DPLUGIN_DEVICEINFO=ON \
                                ,-DPLUGIN_WEBSERVER=OFF,"
PACKAGECONFIG[webshell]       = "-DPLUGIN_WEBSHELL=ON,-DPLUGIN_WEBSHELL=OFF,"

WPE_WIFICONTROL_DEP          ?= ""
PACKAGECONFIG[wifi]           = "-DPLUGIN_WIFICONTROL=ON,-DPLUGIN_WIFICONTROL=OFF,,wpa-supplicant ${WPE_WIFICONTROL_DEP}"
PACKAGECONFIG[wifi_rdkhal]    = "-DPLUGIN_USE_RDK_HAL_WIFI=ON,-DPLUGIN_USE_RDK_HAL_WIFI=OFF,,wifi-hal"

# ----------------------------------------------------------------------------

EXTRA_OECMAKE += " \
    -DBUILD_REFERENCE=${SRCREV} \
    -DBUILD_SHARED_LIBS=ON \
"

# ----------------------------------------------------------------------------

do_install_append() {
    if ${@bb.utils.contains("PACKAGECONFIG", "webserver", "true", "false", d)}
    then
      install -d ${D}/var/www
      install -m 0755 ${WORKDIR}/index.html ${D}/var/www/
      install -d ${D}${PLUGIN_WEBSERVER_PATH}
    fi
}

# ----------------------------------------------------------------------------

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/wpeframework/plugins/*.so ${libdir}/*.so ${datadir}/WPEFramework/* /var/www/index.html"
FILES_${PN} += "${includedir}/WPEFramework/*"
FILES_${PN}-dev += "${libdir}/cmake/*"

INSANE_SKIP_${PN} += "libdir staticdev dev-so"
INSANE_SKIP_${PN}-dbg += "libdir"
