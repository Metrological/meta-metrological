require recipes-graphics/westeros/westeros.inc

SUMMARY = "This receipe compiles the westeros gl component for drm supported platforms, currently the HiKey board"
LICENSE_LOCATION = "${S}/../LICENSE"

S = "${WORKDIR}/git/drm"

COMPATIBLE_MACHINE = "(hikey-32|dragonboard-410c-32|dragonboard-820c-32|poplar|imx8mqevk)"

COMPATIBLE_HOST_rpi = "${@bb.utils.contains('MACHINE_FEATURES', 'vc4graphics', 'null', '(.*)', d)}"

DEPENDS = "wayland virtual/egl glib-2.0 libdrm"

PROVIDES = "westeros-soc"
RPROVIDES_${PN} = "westeros-soc"

CFLAGS_append = " -I${STAGING_INCDIR}/libdrm"

SECURITY_CFLAGS_remove = "-fpie"
SECURITY_CFLAGS_remove = "-pie"

DEBIAN_NOAUTONAME_${PN} = "1"
DEBIAN_NOAUTONAME_${PN}-dbg = "1"
DEBIAN_NOAUTONAME_${PN}-dev = "1"
DEBIAN_NOAUTONAME_${PN}-staticdev = "1"

inherit autotools pkgconfig

FILES_${PN} = "${libdir}/*"
