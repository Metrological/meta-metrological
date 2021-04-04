SINK_SOC_PATH = "drm"
SINK_SOC_PATH_rpi = "${@bb.utils.contains('MACHINE_FEATURES', 'vc4graphics', 'v4l2', 'rpi', d)}"

AUTOTOOLS_SCRIPT_PATH = "${S}/${SINK_SOC_PATH}/westeros-sink"

LICENSE_LOCATION = "${S}/LICENSE"

CFLAGS_append_rpi = " ${@bb.utils.contains('MACHINE_FEATURES', 'vc4graphics', '', \
                      '-DWESTEROS_PLATFORM_RPI -DWESTEROS_INVERTED_Y -DBUILD_WAYLAND -x c++ \
                      -I${STAGING_INCDIR}/interface/vmcs_host/linux', d)}"

CFLAGS_append = " -DWESTEROS_PLATFORM_DRM -x c++"

PACKAGECONFIG[gst1] = "--enable-gstreamer1=yes,--enable-gstreamer1=no,gstreamer1.0"

PACKAGECONFIG = "gst1"

do_configure_prepend() {
  ln -sf ../../westeros-sink/westeros-sink.c ${AUTOTOOLS_SCRIPT_PATH}
  ln -sf ../../westeros-sink/westeros-sink.h ${AUTOTOOLS_SCRIPT_PATH}
}

do_configure_prepend_rpi () {
  sed -i -e 's/-lwesteros_simplebuffer_client/-lwesteros_compositor -lwesteros_simplebuffer_client/g' ${AUTOTOOLS_SCRIPT_PATH}/Makefile.am
  ln -sf ../../westeros-sink/westeros-sink.c ${AUTOTOOLS_SCRIPT_PATH}
  ln -sf ../../westeros-sink/westeros-sink.h ${AUTOTOOLS_SCRIPT_PATH}
}

FILES_${PN} += "${libdir}/gstreamer-*/*.so"
FILES_${PN}-dev += "${libdir}/gstreamer-*/*.la"
FILES_${PN}-dbg += "${libdir}/gstreamer-*/.debug/*"
FILES_${PN}-staticdev += "${libdir}/gstreamer-*/*.a "
