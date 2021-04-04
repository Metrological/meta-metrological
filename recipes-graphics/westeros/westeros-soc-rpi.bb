require recipes-graphics/westeros/westeros.inc

SUMMARY = "This receipe compiles the westeros compositor components for RaspberryPi: westeros-gl, westeros-egl"

S = "${WORKDIR}/git"
LICENSE_LOCATION = "${S}/LICENSE"
AUTOTOOLS_SCRIPT_PATH = "${S}/rpi"

DEPENDS += "wayland virtual/egl glib-2.0"
PROVIDES = "westeros-soc"
RPROVIDES_${PN} = "westeros-soc"

python __anonymous() {
    if d.getVar("SOC_FAMILY", True) != "rpi":
        raise bb.parse.SkipPackage("RaspberryPi SOC specific westeros backend")
}
CXXFLAGS_append_rpi = " -I ${STAGING_INCDIR}/interface/vmcs_host/linux/"

SECURITY_CFLAGS_remove = "-fpie"
SECURITY_CFLAGS_remove = "-pie"

DEBIAN_NOAUTONAME_${PN} = "1"
DEBIAN_NOAUTONAME_${PN}-dbg = "1"
DEBIAN_NOAUTONAME_${PN}-dev = "1"
DEBIAN_NOAUTONAME_${PN}-staticdev = "1"

inherit autotools pkgconfig

FILES_${PN} = "${libdir}/*"

do_configure_prepend() {
    sed -i -e 's/-lwesteros_simplebuffer_client/-lwesteros_compositor -lwesteros_simplebuffer_client/g' ${AUTOTOOLS_SCRIPT_PATH}/westeros-sink/Makefile.am
}

COMPATIBLE_HOST_rpi = "${@bb.utils.contains('MACHINE_FEATURES', 'vc4graphics', 'null', '(.*)', d)}"
