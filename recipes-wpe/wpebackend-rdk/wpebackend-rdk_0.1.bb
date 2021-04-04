SUMMARY = "WPE WebKit RDK backend"
HOMEPAGE = "https://github.com/WebPlatformForEmbedded"
SECTION = "wpe"
LICENSE = "BSD-2-Clause"
LIC_FILES_CHKSUM = "file://COPYING;md5=ab5b52d145a58f5fcc0e2a531e7a2370"

DEPENDS += "libwpe glib-2.0"

SRC_URI = "git://github.com/WebPlatformForEmbedded/WPEBackend-rdk.git;protocol=http;branch=master"
SRCREV = "e0b491a9e30a05a094069a5d5037884703870e4a"

S = "${WORKDIR}/git"

PROVIDES += "virtual/wpebackend"
RPROVIDES_${PN} += "virtual/wpebackend"

inherit cmake pkgconfig

# BACKEND has too many flavors to pick on through conventional methods
# By default we'll take wpeframework when compositor is enabled in DISTRO_FEATURES.
# However if that is not available take westeros when wayland is there, if all that is not take the EGLFS for SOC
# which in our case most commonly is RPI
def backendselector(d):
    wayland = bb.utils.contains('DISTRO_FEATURES', 'wayland', True, False, d)
    compositor = bb.utils.contains('DISTRO_FEATURES', 'compositor', True, False, d)

    if compositor == False and wayland == False:
        return 'rpi'
    elif compositor == False and wayland == True:
        return 'westeros'
    else:
        return 'wpeframework'


# Default back end selections. Please override in your machine config using WPE_BACKEND=<> to meet your machine required
WPE_BACKEND     ?= "${@backendselector(d)}"

PACKAGECONFIG ?= "${WPE_BACKEND} virtualinput"

# device specific backends
PACKAGECONFIG[intelce]          = "-DUSE_BACKEND_INTEL_CE=ON -DUSE_KEY_INPUT_HANDLING_LINUX_INPUT=ON,,intelce-display"
PACKAGECONFIG[nexus]            = "-DUSE_BACKEND_BCM_NEXUS=ON -DUSE_KEY_INPUT_HANDLING_LINUX_INPUT=ON,,broadcom-refsw"
PACKAGECONFIG[rpi]              = "-DUSE_BACKEND_BCM_RPI=ON -DUSE_KEY_INPUT_HANDLING_LINUX_INPUT=ON,,userland"

# Wayland selectors
PACKAGECONFIG[wayland]          = "-DUSE_BACKEND_WAYLAND=ON -DUSE_KEY_INPUT_HANDLING_LINUX_INPUT=OFF,,wayland libxkbcommon"
PACKAGECONFIG[westeros]         = "-DUSE_BACKEND_WESTEROS=ON -DUSE_BACKEND_BCM_RPI=OFF -DUSE_KEY_INPUT_HANDLING_LINUX_INPUT=OFF,,wayland westeros libxkbcommon"
PACKAGECONFIG[bcm-weston]       = "-DUSE_BACKEND_BCM_NEXUS_WAYLAND=ON,-DUSE_BACKEND_BCM_NEXUS_WAYLAND=OFF,,"
PACKAGECONFIG[wpeframework]     = "-DUSE_BACKEND_WPEFRAMEWORK=ON -DUSE_KEY_INPUT_HANDLING_LINUX_INPUT=OFF -DUSE_INPUT_LIBINPUT=OFF,,wpeframework-clientlibraries wpeframework-plugins libxkbcommon xkeyboard-config"

# MESA
PACKAGECONFIG[westeros-mesa]    = "-DUSE_BACKEND_WESTEROS_MESA=ON,,"

# other switches
PACKAGECONFIG[debug]            = "-DCMAKE_BUILD_TYPE=Debug,-DCMAKE_BUILD_TYPE=Release,"
PACKAGECONFIG[libinput]         = "-DUSE_INPUT_LIBINPUT=ON,-DUSE_INPUT_LIBINPUT=OFF,libinput,"
PACKAGECONFIG[libinput-udev]    = "-DUSE_INPUT_UDEV=ON,-DUSE_INPUT_UDEV=OFF,libinput,"
PACKAGECONFIG[virtualinput]     = "-DUSE_VIRTUAL_KEYBOARD=ON,-DUSE_VIRTUAL_KEYBOARD=OFF,wpeframework-clientlibraries,"

CXXFLAGS += "${@bb.utils.contains('DISTRO_FEATURES', 'wayland', '-DWL_EGL_PLATFORM', '', d)}"

# FIXME RDEPENDS on PACKAGECONFIG is not behaving as it should
RDEPENDS_${PN} += "${@bb.utils.contains('PACKAGECONFIG', 'wpeframework', 'xkeyboard-config', '', d)}"

FILES_SOLIBSDEV = ""
FILES_${PN} += "${libdir}/libWPEBackend-default.so ${libdir}/libWPEBackend-rdk.so"
INSANE_SKIP_${PN} = "dev-so textrel"
