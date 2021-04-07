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

COMPATIBLE_MACHINE ?= "null"

inherit autotools pkgconfig
