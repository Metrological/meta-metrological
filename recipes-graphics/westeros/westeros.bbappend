PACKAGECONFIG = "incapp inctest incplayer increndergl incsbprotocol xdgv5"
CXXFLAGS_append = " -DWESTEROS_PLATFORM_DRM"

do_configure_prepend() {
    sed -i -e 's/-lwesteros_simplebuffer_client/-lwesteros_compositor -lwesteros_simplebuffer_client/g' ${S}/drm/westeros-sink/Makefile.am
}

# bbappend for raspberryPi
#
PACKAGECONFIG_rpi = "incapp inctest incplayer increndergl incsbprotocol xdgv4"
CXXFLAGS_append_rpi = "${@bb.utils.contains('MACHINE_FEATURES', 'vc4graphics', ' ', ' -DWESTEROS_PLATFORM_RPI -DWESTEROS_INVERTED_Y -DBUILD_WAYLAND -I${STAGING_INCDIR}/interface/vmcs_host/linux', d)}"
CXXFLAGS_append_rpi = " ${@bb.utils.contains('MACHINE_FEATURES', 'vc4graphics', '-DUSE_MESA', '', d)}"
CXXFLAGS_remove_morty_rpi = "-DUSE_MESA"
CXXFLAGS_remove_daisy_rpi = "-DUSE_MESA"

do_configure_prepend_rpi() {
    sed -i -e 's/-lwesteros_simplebuffer_client/-lwesteros_compositor -lwesteros_simplebuffer_client/g' ${S}/rpi/westeros-sink/Makefile.am
}

do_compile_prepend_rpi () {
	export WESTEROS_COMPOSITOR_EXTRA_LIBS="${@bb.utils.contains("MACHINE_FEATURES", "vc4graphics", "-lEGL -lGLESv2", "-lEGL -lGLESv2 -lbcm_host -lvchostif", d)}"
}
