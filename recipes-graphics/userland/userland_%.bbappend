FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://0015-EGL-glplatform.h-define-EGL_CAST.patch \
    file://0020-brcmEGL-wayland-support.patch \
"

RPROVIDES_${PN} += " ${@bb.utils.contains('MACHINE_FEATURES', 'vc4graphics', '', 'libegl', d)}"
