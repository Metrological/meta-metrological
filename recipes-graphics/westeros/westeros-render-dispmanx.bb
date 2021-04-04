require recipes-graphics/westeros/westeros.inc

SUMMARY = "This receipe compiles the westeros compositor dispmanx render module for Raspberry Pi"

S = "${WORKDIR}/git"
LICENSE_LOCATION = "${S}/LICENSE"

DEPENDS = "virtual/egl wayland glib-2.0 westeros"

DEBIAN_NOAUTONAME_${PN} = "1"
DEBIAN_NOAUTONAME_${PN}-dbg = "1"
DEBIAN_NOAUTONAME_${PN}-dev = "1"
DEBIAN_NOAUTONAME_${PN}-staticdev = "1"

SECURITY_CFLAGS_remove="-fpie"
SECURITY_CFLAGS_remove="-pie"

AUTOTOOLS_SCRIPT_PATH = "${S}/rpi/westeros-render-dispmanx"
inherit autotools pkgconfig

do_configure_prepend() {
    sed -i -e 's/-lwesteros_simplebuffer_client/-lwesteros_compositor -lwesteros_simplebuffer_client/g' ${S}/rpi/westeros-sink/Makefile.am
}

COMPATIBLE_HOST_rpi = "${@bb.utils.contains('MACHINE_FEATURES', 'vc4graphics', 'null', '(.*)', d)}"
